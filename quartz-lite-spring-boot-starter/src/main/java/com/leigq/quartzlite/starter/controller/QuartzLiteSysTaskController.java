package com.leigq.quartzlite.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.leigq.quartzlite.autoconfigure.bean.common.Response;
import com.leigq.quartzlite.starter.bean.job.BaseTaskExecute;
import com.leigq.quartzlite.starter.bean.vo.AddSysTaskVO;
import com.leigq.quartzlite.starter.bean.vo.SysTaskListVO;
import com.leigq.quartzlite.starter.bean.vo.UpdateSysTaskVO;
import com.leigq.quartzlite.starter.exception.QuartzLiteGlobalExceptionHand;
import com.leigq.quartzlite.starter.service.SysTaskService;
import com.leigq.quartzlite.starter.util.QuartzLiteSpringContextHolder;
import com.leigq.quartzlite.starter.util.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 任务 Controller
 * <p>
 *
 * @author leigq <br>
 * @date 2020/08/04 14:23 <br>
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/quartz-lite")
public class QuartzLiteSysTaskController {

	/**
	 * 系统自己创建的任务表服务
	 */
	private final SysTaskService sysTaskService;

	public QuartzLiteSysTaskController(SysTaskService sysTaskService) {
		this.sysTaskService = sysTaskService;
	}

	/**
	 * 添加任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:37 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks")
	public Response addTask(@Valid AddSysTaskVO addSysTaskVO, BindingResult bindingResult) {
		String msg = QuartzLiteGlobalExceptionHand.handleBindingResult(bindingResult);
		if (StringUtils.isNotBlank(msg)) {
			return Response.fail(msg);
		}
		this.checkTaskClassName(addSysTaskVO.getTaskClass());
		this.checkExecParams(addSysTaskVO.getExecParams());
		this.checkCron(addSysTaskVO.getCron());
		sysTaskService.addTask(addSysTaskVO);
		return Response.success("创建定时任务成功！");
	}


	/**
	 * 更新任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:49 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PutMapping("/tasks")
	public Response updateTask(@Valid UpdateSysTaskVO updateSysTaskVO, BindingResult bindingResult) {
		String msg = QuartzLiteGlobalExceptionHand.handleBindingResult(bindingResult);
		if (StringUtils.isNotBlank(msg)) {
			return Response.fail(msg);
		}
		this.checkTaskClassName(updateSysTaskVO.getTaskClass());
		this.checkExecParams(updateSysTaskVO.getExecParams());
		this.checkCron(updateSysTaskVO.getCron());
		sysTaskService.updateTask(updateSysTaskVO);
		return Response.success("更新任务成功！");
	}

	/**
	 * 执行任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:48 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks/action/execute/{task_name}/{task_group}")
	public Response executeTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.executeTask(taskName, taskGroup);
		return Response.success("执行任务成功！");
	}


	/**
	 * 暂停任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:48 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks/action/pause/{task_name}/{task_group}")
	public Response pauseTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.pauseTask(taskName, taskGroup);
		return Response.success("暂停任务成功！");
	}

	/**
	 * 恢复任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:48 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@PostMapping("/tasks/action/resume/{task_name}/{task_group}")
	public Response resumeTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.resumeTask(taskName, taskGroup);
		return Response.success("恢复任务成功！");
	}


	/**
	 * 删除任务
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:49 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@DeleteMapping("/tasks/{task_name}/{task_group}")
	public Response deleteTask(@PathVariable("task_name") String taskName, @PathVariable("task_group") String taskGroup) {
		sysTaskService.deleteTask(taskName, taskGroup);
		return Response.success("删除任务成功！");
	}

	/**
	 * 查询任务列表
	 * <p>
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:49 <br>
	 * <p>
	 * 修改人： <br>
	 * 修改时间： <br>
	 * 修改备注： <br>
	 * </p>
	 */
	@GetMapping("/tasks/{page_num}/{page_size}")
	public Response queryTaskList(@PathVariable("page_num") Integer pageNum, @PathVariable("page_size") Integer pageSize) {
		final IPage<SysTaskListVO> sysTaskList = sysTaskService.taskList(pageNum, pageSize);
		return Response.success(sysTaskList);
	}


	/**
	 * 验证执行参数格式
	 *
	 * @param execParams 执行参数
	 */
	private void checkExecParams(String execParams) {
		if (StringUtils.isNotBlank(execParams)) {
			ValidUtils.checkArg(!execParams.contains("="), "执行参数格式错误");
		}
	}


	/**
	 * 验证表达式格式
	 *
	 * @param cron 表达式
	 */
	private void checkCron(String cron) {
		// 验证表达式格式
		if (!CronExpression.isValidExpression(cron)) {
			ValidUtils.throwException("表达式格式错误！");
		}
	}

	/**
	 * 检验任务执行类名时候正确
	 *
	 * @param taskClass 执行类名
	 */
	private void checkTaskClassName(String taskClass) {
		Class<?> clazz = null;
		try {
			// 获取自定义任务的类
			clazz = Class.forName(taskClass);
		} catch (ClassNotFoundException e) {
			ValidUtils.throwException("找不到执行类，请检查执行类是否配置正确！");
		}
		if (BaseTaskExecute.class.getName().equals(taskClass)) {
			ValidUtils.throwException(String.format("执行类不可配置为%s！", BaseTaskExecute.class.getSimpleName()));
		}
		try {
			// 获取自定义任务实例，自定义任务全部继承 BaseTaskExecute
			final Object bean = QuartzLiteSpringContextHolder.getBean(clazz);
			if (!(bean instanceof BaseTaskExecute)) {
				ValidUtils.throwException(String.format("请确保执行类继承%s类！", BaseTaskExecute.class.getSimpleName()));
			}
		} catch (NoSuchBeanDefinitionException e) {
			ValidUtils.throwException("找不到执行类，请检查执行类是否配置@Component注解！");
		}
	}

}
