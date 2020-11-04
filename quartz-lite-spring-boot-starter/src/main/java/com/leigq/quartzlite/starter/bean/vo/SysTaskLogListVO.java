package com.leigq.quartzlite.starter.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务执行日志列表 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysTaskLogListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 自增主键
	 */
	private Long id;
	/**
	 * 任务名
	 */
	private String taskName;
	/**
	 * 执行时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date execDate;
	/**
	 * 执行结果（成功:1、失败:0)
	 */
	private Integer execResult;
	/**
	 * 抛出的异常信息
	 */
	private String execResultText;
}