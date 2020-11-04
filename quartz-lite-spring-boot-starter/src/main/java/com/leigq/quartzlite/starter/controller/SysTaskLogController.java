package com.leigq.quartzlite.starter.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leigq.quartzlite.autoconfigure.bean.common.Response;
import com.leigq.quartzlite.starter.bean.vo.SysTaskLogListVO;
import com.leigq.quartzlite.starter.service.SysTaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务日志 Controller
 * <p>
 * 创建人：LeiGQ <br>
 * 创建时间：2020/08/04 14:23 <br>
 * </p>
 */
@Slf4j
@RestController
@RequestMapping("/quartz-lite")
public class SysTaskLogController {

	private final SysTaskLogService sysTaskLogService;

	public SysTaskLogController(SysTaskLogService sysTaskLogService) {
		this.sysTaskLogService = sysTaskLogService;
	}


	/**
	 * 查询任务日志列表
	 * 创建人：LeiGQ <br>
	 * 创建时间：2019/5/19 13:49 <br>
	 */
	@GetMapping("/logs/{task_id}/{page_num}/{page_size}")
	public Response queryTaskLogList(@PathVariable("task_id") Long taskId, @PathVariable("page_num") Integer pageNum, @PathVariable("page_size") Integer pageSize) {
		final IPage<SysTaskLogListVO> sysTaskLogList = sysTaskLogService.taskLogList(taskId, pageNum, pageSize);
		return Response.success(sysTaskLogList);
	}


}
