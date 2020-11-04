package com.leigq.quartzlite.starter.bean.job;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.leigq.quartzlite.autoconfigure.properties.QuartzLiteProperties;
import com.leigq.quartzlite.starter.bean.dto.TaskExecuteDTO;
import com.leigq.quartzlite.starter.bean.enumeration.SysTaskExecResultEnum;
import com.leigq.quartzlite.starter.domain.entity.SysTaskLog;
import com.leigq.quartzlite.starter.service.SysTaskLogService;
import com.leigq.quartzlite.starter.service.SysTaskService;
import com.leigq.quartzlite.starter.util.DateUtils;
import com.leigq.quartzlite.starter.util.EmailSender;
import com.leigq.quartzlite.starter.util.ExceptionDetailUtils;
import com.leigq.quartzlite.starter.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 任务执行的抽象类（模板），自己的任务继承此类，重写 execute 方法来执行自己的任务
 *
 * @author leigq
 */
public abstract class BaseTaskExecute {

    private final Logger log = LoggerFactory.getLogger(BaseTaskExecute.class);
    private TaskExecuteDTO taskExecuteDTO;
    @Autowired
    private SysTaskService sysTaskService;
    @Autowired
    private SysTaskLogService sysTaskLogService;
    @Autowired
    private QuartzLiteProperties quartzLiteProperties;
    @Autowired
    private EmailSender emailSender;

    public BaseTaskExecute() {
    }

    public void setTaskExecuteDTO(TaskExecuteDTO taskExecuteDTO) {
        this.taskExecuteDTO = taskExecuteDTO;
    }


    /**
     * 自定义任务子类需实现此方法
     *
     * @param dataMap 添加任务时配置的参数，如：aaa=111;bbb=222
     * @throws Exception the exception
     */
    public abstract void execute(Map<String, Object> dataMap) throws Exception;


    /**
     * Execute.
     */
    public void execute() {
        // 添加任务执行日志，默认执行中
        SysTaskLog sysTaskLog = SysTaskLog.builder()
                .taskName(taskExecuteDTO.getTaskName())
                .execDate(new Date())
                .execResult(SysTaskExecResultEnum.EXECUTING.getValue())
                .execResultText("任务执行中....")
                .taskId(taskExecuteDTO.getTaskId())
                .build();
        final Long logId = sysTaskLogService.addLog(sysTaskLog);

        // 更新任务的最近一次执行时间、最近一次执行结果字段，默认执行中
        sysTaskService.updateExecDateAndExecResult(taskExecuteDTO.getTaskId(), SysTaskExecResultEnum.EXECUTING);

        try {
            // 获取任务携带的参数
            Map<String, Object> dataMap = taskExecuteDTO.getDataMap();

            final long startTime = System.currentTimeMillis();
            // 调用子类的任务
            this.execute(dataMap);
            final long endTime = System.currentTimeMillis();


            /* 任务执行成功后的操作 */
            // 更新任务的最近一次执行结果为成功
            sysTaskService.updateExecResult(taskExecuteDTO.getTaskId(), SysTaskExecResultEnum.SUCCESS);

            // 记录任务执行耗时
            String format = "任务执行成功：开始时间：[%s]，结束时间：[%s]，共耗时：[%s]";
            String sTime = DateUtils.String.from(startTime);
            String eTime = DateUtils.String.from(endTime);
            sysTaskLogService.updateExecResultAndExecResultText(logId, SysTaskExecResultEnum.SUCCESS,
                    String.format(format, sTime, eTime, DateUtils.Format.formatDuring(endTime - startTime)));

        } catch (Exception e) {
            this.log.error(String.format("任务：[ %s ] 执行异常：", taskExecuteDTO.getTaskName()), e);

            // 将执行结果改为失败并记录异常信息
            sysTaskLogService.updateExecResultToFail(logId, e);

            // 将任务最近一次执行结果改为失败
            sysTaskService.updateExecResult(taskExecuteDTO.getTaskId(), SysTaskExecResultEnum.FAILURE);

            // 发送邮件
            sendEmail(e);
            // 回滚事务
            rollBackTransaction();
        }
    }


    /**
     * Send email.
     *
     * @param e the e
     */
    private void sendEmail(Exception e) {
        if (!quartzLiteProperties.getMail().getEnable()) {
            return;
        }
        final Set<String> receiveUsername = quartzLiteProperties.getMail().getReceiveUsername();
        if (CollectionUtils.isEmpty(receiveUsername)) {
            return;
        }
        final String errorMsg = String.format("任务：[ %s ] 执行异常：", taskExecuteDTO.getTaskName());
        final String[] usernames = receiveUsername.toArray(new String[0]);

        ThreadPoolUtils.execute(() -> emailSender.sendSimpleMail("任务执行异常", errorMsg + ExceptionDetailUtils.getThrowableDetail(e), usernames));
    }

    /**
     * 事务回滚
     */
    private void rollBackTransaction() {
        try {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } catch (NoTransactionException noTransactionException) {
            // 没事务不回滚
            log.warn("没事务不回滚");
        }
    }
}
