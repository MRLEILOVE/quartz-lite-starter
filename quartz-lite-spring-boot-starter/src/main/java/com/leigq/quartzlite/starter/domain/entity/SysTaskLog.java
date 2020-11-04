package com.leigq.quartzlite.starter.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务执行日志表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_task_log")
public class SysTaskLog implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 任务名
	 */
	@TableField(value = "task_name")
	private String taskName;
	/**
	 * 执行时间
	 */
	@TableField(value = "exec_date")
	private Date execDate;
	/**
	 * 执行结果 (成功:1、失败:0、正在执行：-1)
	 */
	@TableField(value = "exec_result")
	private Integer execResult;
	/**
	 * 成功信息或抛出的异常信息
	 */
	@TableField(value = "exec_result_text")
	private String execResultText;
	/**
	 * 任务ID，外键
	 */
	@TableField(value = "task_id")
	private Long taskId;
}