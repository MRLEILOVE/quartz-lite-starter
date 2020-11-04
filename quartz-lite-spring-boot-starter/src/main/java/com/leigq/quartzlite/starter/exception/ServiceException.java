package com.leigq.quartzlite.starter.exception;

import com.leigq.quartzlite.starter.util.ValidUtils;

/**
 * 业务异常，Service 层使用，其它具体业务异常可继承此异常
 * <br>
 * 异常说明:<br/>
 * <ul>
 *     <li>1、配合全局异常处理使用，全局异常捕获 ServiceException 及其子异常</li>
 *     <li>2、Service 层配合 {@link ValidUtils} 工具使用来控制条件、流程语句</li>
 * </ul>
 * <p>
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/31 22:04
 */
public class ServiceException extends RuntimeException {
	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
