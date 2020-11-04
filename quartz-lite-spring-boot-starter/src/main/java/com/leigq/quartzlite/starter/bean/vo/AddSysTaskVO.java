package com.leigq.quartzlite.starter.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 添加任务接受参数 VO
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/22 16:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddSysTaskVO implements Serializable {

	private static final long serialVersionUID = 7803099260867270252L;

	/**
	 * 任务名
	 */
	@NotEmpty(message = "任务名不能为空！")
	private String taskName;

	/**
	 * 任务组
	 */
	@NotEmpty(message = "任务分组不能为空！")
	private String taskGroup;

	/**
	 * 执行类
	 */
	@NotEmpty(message = "全类名不能为空！")
	private String taskClass;

	/**
	 * 任务说明
	 */
	@NotEmpty(message = "任务说明不能为空！")
	private String note;

	/**
	 * 定时规则
	 */
	@NotEmpty(message = "定时规则(表达式)不能为空！")
	private String cron;

	/**
	 * 执行参数，前端类似这样传 aaa=11;bbb=222
	 */
	private String execParams;

	/**
	 * 是否允许并发，0(false)：不允许 1（true）：允许
	 */
	private Boolean concurrent;

}
