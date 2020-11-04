package com.leigq.quartzlite.starter.bean.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务执行结果
 */
@AllArgsConstructor
@Getter
public enum SysTaskExecResultEnum {

	/**
	 * 任务执行成功
	 */
	SUCCESS(1),

	/**
	 * 任务执行失败
	 */
	FAILURE(0),

	/**
	 * 正在执行
	 */
	EXECUTING(-1),

	;

	private final Integer value;
}
