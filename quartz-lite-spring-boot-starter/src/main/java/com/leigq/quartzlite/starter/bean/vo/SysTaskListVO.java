package com.leigq.quartzlite.starter.bean.vo;

import lombok.Data;


/**
 * 任务列表 VO
 * <p>
 *
 * @author leigq <br>
 * @date 2019/5/19 1:14 <br>
 */
@Data
public class SysTaskListVO {

	/**
	 * The Task id.
	 */
	private Long taskId;

	/**
	 * 任务名
	 */
	private String taskName;

	/**
	 * 任务组
	 */
	private String taskGroup;

	/**
	 * 任务说明
	 */
	private String note;

	/**
	 * 执行类
	 */
	private String taskClass;

	/**
	 * 执行参数
	 */
	private String execParams;

	/**
	 * 定时规则
	 */
	private String cron;

	/**
	 * 上次执行时间
	 */
	private String prevFireTime;
	/**
	 * 下次执行时间
	 */
	private String nextFireTime;

	/**
	 * 最近一次执行结果 （成功:1、失败:0)
	 */
	private Integer execResult;

	/**
	 * 是否允许并发，0(false)：不允许 1（true）：允许
	 */
	private Boolean concurrent;

	/**
	 * 时区
	 */
	private String timeZoneId;

	/**
	 * 状态
	 */
	private String triggerState;

}
