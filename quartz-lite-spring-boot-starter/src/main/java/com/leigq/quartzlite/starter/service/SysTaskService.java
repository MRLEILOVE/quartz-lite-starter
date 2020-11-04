package com.leigq.quartzlite.starter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.leigq.quartzlite.starter.bean.dto.AddQuartzJobDTO;
import com.leigq.quartzlite.starter.bean.enumeration.SysTaskExecResultEnum;
import com.leigq.quartzlite.starter.bean.vo.AddSysTaskVO;
import com.leigq.quartzlite.starter.bean.vo.SysTaskListVO;
import com.leigq.quartzlite.starter.bean.vo.UpdateSysTaskVO;
import com.leigq.quartzlite.starter.domain.entity.SysTask;
import com.leigq.quartzlite.starter.domain.mapper.SysTaskMapper;
import com.leigq.quartzlite.starter.exception.ServiceException;
import com.leigq.quartzlite.starter.util.ValidUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * 系统自己创建的任务表服务
 *
 * @author leigq
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SysTaskService extends ServiceImpl<SysTaskMapper, SysTask> {

    /**
     * Quartz自带表的任务服务
     */
    private final QuartzJobService quartzJobService;
    private final SysTaskMapper sysTaskMapper;
    private final SysTaskLogService sysTaskLogService;

    public SysTaskService(QuartzJobService quartzJobService, SysTaskMapper sysTaskMapper, SysTaskLogService sysTaskLogService) {
        this.quartzJobService = quartzJobService;
        this.sysTaskMapper = sysTaskMapper;
        this.sysTaskLogService = sysTaskLogService;
    }

    /**
     * 添加任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:20 <br>
     *
     * @param addSysTaskVO 添加任务接受参数 VO
     */
    public void addTask(AddSysTaskVO addSysTaskVO) {
        this.checkTaskAlreadyExists(addSysTaskVO.getTaskName(), addSysTaskVO.getTaskGroup());
        this.checkTaskClassAlreadyExists(addSysTaskVO.getTaskClass());
        try {
            // 先添加一条任务记录到自己的任务表，应该后面任务日志需要任务id
            SysTask sysTask = SysTask.builder().build();
            BeanUtils.copyProperties(addSysTaskVO, sysTask);
            sysTask.setCreateTime(new Date());
            // 创建人根据自己的系统确定，这里默认写死
            sysTask.setCreator(1L);
            sysTask.setUpdateTime(new Date());
            // 修改人根据自己的系统确定，这里默认写死
            sysTask.setModifier(1L);
            this.save(sysTask);

            // 添加 Quartz 任务表
            AddQuartzJobDTO addQuartzJobDTO = AddQuartzJobDTO.builder().build();
            BeanUtils.copyProperties(addSysTaskVO, addQuartzJobDTO);
            // 转换执行参数为 Map
            addQuartzJobDTO.transExecParams(addSysTaskVO.getExecParams());
            addQuartzJobDTO.setTaskId(sysTask.getId());
            quartzJobService.addJob(addQuartzJobDTO);
        } catch (SchedulerException e) {
            throw new ServiceException("添加任务失败", e);
        }
    }


    /**
     * 更新任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:50 <br>
     *
     * @param updateSysTaskVO the update sys task vo
     */
    public void updateTask(UpdateSysTaskVO updateSysTaskVO) {

        final String taskName = updateSysTaskVO.getTaskName();
        final String taskGroup = updateSysTaskVO.getTaskGroup();

        final SysTask sysTask = getSysTask(taskName, taskGroup);
        ValidUtils.isNull(sysTask, "查询不到此任务！");

        if (!Objects.equals(sysTask.getTaskClass(), updateSysTaskVO.getTaskClass())) {
            this.checkTaskClassAlreadyExists(updateSysTaskVO.getTaskClass());
        }

        try {
            BeanUtils.copyProperties(updateSysTaskVO, sysTask);
            // 修改人这里先写死，可以自己根据系统更改
            sysTask.setModifier(1L);
            sysTask.setUpdateTime(new Date());
            // 更新自定义任务表
            this.updateById(sysTask);

            // 更新 Quartz 框架表，只能先删除旧任务，在添加一个新任务
            quartzJobService.deleteJob(taskName, taskGroup);

            AddQuartzJobDTO addQuartzJobDTO = AddQuartzJobDTO.builder().build();
            BeanUtils.copyProperties(updateSysTaskVO, addQuartzJobDTO);
            // 转换执行参数为 Map
            addQuartzJobDTO.transExecParams(updateSysTaskVO.getExecParams());
            addQuartzJobDTO.setTaskId(sysTask.getId());
            quartzJobService.addJob(addQuartzJobDTO);

            // 修改任务后手动执行
            this.pauseTask(addQuartzJobDTO.getTaskName(), addQuartzJobDTO.getTaskGroup());
        } catch (SchedulerException e) {
            throw new ServiceException("更新任务失败", e);
        }
    }


    /**
     * 执行任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     *
     * @param taskName  类名
     * @param taskGroup 类组名
     */
    public void executeTask(String taskName, String taskGroup) {
        try {
            quartzJobService.executeJob(taskName, taskGroup);
        } catch (SchedulerException e) {
            throw new ServiceException("执行任务失败", e);
        }
    }


    /**
     * 暂停任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 2:57 <br>
     *
     * @param taskName  类名
     * @param taskGroup 类组名
     */
    public void pauseTask(String taskName, String taskGroup) {
        try {
            quartzJobService.pauseJob(taskName, taskGroup);
        } catch (SchedulerException e) {
            throw new ServiceException("暂停任务失败", e);
        }
    }


    /**
     * 恢复任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:00 <br>
     *
     * @param taskName  类名
     * @param taskGroup 类组名
     */
    public void resumeTask(String taskName, String taskGroup) {
        try {
            quartzJobService.resumeJob(taskName, taskGroup);
        } catch (SchedulerException e) {
            throw new ServiceException("恢复任务失败", e);
        }
    }


    /**
     * 删除任务
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/28 3:53 <br>
     *
     * @param taskName  任务类名
     * @param taskGroup 类组名
     */
    public void deleteTask(String taskName, String taskGroup) {
        try {
            final SysTask sysTask = this.getSysTask(taskName, taskGroup);
            ValidUtils.isNull(sysTask, "查询不到此任务！");
            // 删除自定义任务表
            this.removeById(sysTask.getId());
            quartzJobService.deleteJob(taskName, taskGroup);
            // 删除对应任务的执行日志
            sysTaskLogService.deleteByTaskId(sysTask.getId());
        } catch (SchedulerException e) {
            throw new ServiceException("删除任务失败", e);
        }
    }

    /**
     * 获取单个任务
     *
     * @param taskName  the task name
     * @param taskGroup the task group
     * @return the sys task
     */
    public SysTask getSysTask(String taskName, String taskGroup) {
        return this.getOne(Wrappers.<SysTask>lambdaQuery()
                .eq(SysTask::getTaskName, taskName)
                .eq(SysTask::getTaskGroup, taskGroup)
        );
    }


    /**
     * 获取任务列表
     * <p>
     * 创建人：LeiGQ <br>
     * 创建时间：2019/5/19 1:18 <br>
     */
    public IPage<SysTaskListVO> taskList(int pageNum, int pageSize) {
        Page<SysTaskListVO> page = new Page<>(pageNum, pageSize);
        return sysTaskMapper.taskList(page);
    }


    /**
     * 修改任务的最近一次执行结果
     *
     * @param taskId     the task id
     * @param resultEnum the result enum
     * @return result
     */
    public boolean updateExecResult(Long taskId, SysTaskExecResultEnum resultEnum) {
        return this.update(Wrappers.<SysTask>lambdaUpdate()
                .set(SysTask::getExecResult, resultEnum.getValue())
                .eq(SysTask::getId, taskId)
        );
    }


    /**
     * 更新任务的最近一次执行时间、最近一次执行结果字段
     *
     * @param taskId     the task id
     * @param resultEnum the result enum
     * @return the boolean
     */
    public boolean updateExecDateAndExecResult(Long taskId, SysTaskExecResultEnum resultEnum) {
        return this.update(Wrappers.<SysTask>lambdaUpdate()
                .set(SysTask::getExecDate, new Date())
                .set(SysTask::getExecResult, resultEnum.getValue())
                .eq(SysTask::getId, taskId)
        );
    }

    /**
     * 验证该任务组中此任务是否已存在
     *
     * @param taskName  the task name
     * @param taskGroup the task group
     */
    private void checkTaskAlreadyExists(String taskName, String taskGroup) {
        // 每个任务组中的任务名称不能重复
        final int count = this.count(Wrappers.<SysTask>lambdaQuery()
                .eq(SysTask::getTaskName, taskName)
                .eq(SysTask::getTaskGroup, taskGroup)
        );

        ValidUtils.checkArg(count > 0, "该任务组中已存在此任务，请勿重复添加!");
    }


    /**
     * 验证该任务执行类是否已经存在
     *
     * @param taskClass the task class
     */
    private void checkTaskClassAlreadyExists(String taskClass) {
        // 判断类名是否存在
        final int taskClassCount = this.count(Wrappers.<SysTask>lambdaQuery()
                .eq(SysTask::getTaskClass, taskClass)
        );
        ValidUtils.checkArg(taskClassCount > 0, "该执行类已存在，请勿重复添加！");
    }

}
