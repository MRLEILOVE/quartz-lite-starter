package com.leigq.quartzlite.autoconfigure.bean.common;

import java.io.Serializable;

/**
 * 响应对象。包含处理结果（Meta）和返回数据（Data）两部分，在 Controller 处理完请求后将此对象转换成 json 返回给前台。注意：
 * <ul>
 * <li>处理成功一般返回处理结果和返回数据，失败只返回处理结果。具体返回什么需看接口文档。</li>
 * </ul>
 * <p>
 *
 * @author ：leigq <br>
 * 创建时间：2017年10月9日 下午3:26:17 <br>
 * <p>
 */
public final class Response implements Serializable {

	private static final long serialVersionUID = -3839826779450485786L;

	/**
	 * 默认成功响应码
	 */
	private static final Integer DEFAULT_SUCCESS_CODE = 0;

	/**
	 * 默认成功响应信息
	 */
	private static final String DEFAULT_SUCCESS_MSG = "请求/成功！";

	/**
	 * 默认失败响应码
	 */
	private static final Integer DEFAULT_FAIL_CODE = -1;

	/**
	 * 默认失败响应信息
	 */
	private static final String DEFAULT_FAIL_MSG = "请求/处理失败！";


	/**
	 * 元数据，包含响应码和信息。
	 * <p>
	 * 创建人：leigq <br>
	 * 创建时间：2017年10月9日 下午3:31:17 <br>
	 */
	private Meta meta;

	/**
	 * 返回数据
	 */
	private Object data;


	public Response() {
	}

	/**
	 * Instantiates a new Response.
	 *
	 * @param meta 元数据，包含响应码和信息。
	 * @param data 返回数据
	 */
	public Response(Meta meta, Object data) {
		this.meta = meta;
		this.data = data;
	}

	/**
	 * 处理成功响应，默认(0)响应码，默认信息，无返回数据
	 *
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:25 <br>
	 */
	public static Response success() {
		Meta meta = new Meta(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG);
		return new Response(meta, null);
	}

	/**
	 * 处理成功响应，默认(0)响应码，自定义信息，无返回数据
	 *
	 * @param msg 处理结果信息
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:25 <br>
	 */
	public static Response success(String msg) {
		Meta meta = new Meta(DEFAULT_SUCCESS_CODE, msg);
		return new Response(meta, null);
	}


	/**
	 * 处理成功响应，默认(0)响应码，默认信息，有返回数据。
	 *
	 * @param data 返回数据
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:25 <br>
	 */
	public static Response success(Object data) {
		Meta meta = new Meta(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG);
		return new Response(meta, data);
	}


	/**
	 * 处理成功响应，默认(0)响应码，自定义信息，有返回数据
	 *
	 * @param msg  处理结果信息
	 * @param data 返回数据
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:25 <br>
	 */
	public static Response success(String msg, Object data) {
		Meta meta = new Meta(DEFAULT_SUCCESS_CODE, msg);
		return new Response(meta, data);
	}


	/**
	 * 处理成功响应，自定义响应码，自定义信息，有返回数据
	 *
	 * @param code 自定义响应码
	 * @param msg  处理结果信息
	 * @param data 返回数据
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:25 <br>
	 */
	public static Response success(Integer code, String msg, Object data) {
		Meta meta = new Meta(code, msg);
		return new Response(meta, data);
	}


	/**
	 * 处理失败响应，返回默认(-1)响应码、默认信息，无返回数据。
	 *
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:22 <br>
	 */
	public static Response fail() {
		Meta meta = new Meta(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG);
		return new Response(meta, null);
	}


	/**
	 * 处理失败响应，返回默认(-1)响应码、自定义信息，无返回数据。
	 *
	 * @param msg 处理结果信息
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:22 <br>
	 */
	public static Response fail(String msg) {
		Meta meta = new Meta(DEFAULT_FAIL_CODE, msg);
		return new Response(meta, null);
	}


	/**
	 * 处理失败响应，默认(-1)响应码，默认信息，有返回数据。
	 *
	 * @param data 返回数据
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:22 <br>
	 */
	public static Response fail(Object data) {
		Meta meta = new Meta(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG);
		return new Response(meta, data);
	}


	/**
	 * 处理失败响应，自定义响应码，自定义信息，无返回数据
	 *
	 * @param code 自定义响应码
	 * @param msg  自定义信息
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:22 <br>
	 */
	public static Response fail(Integer code, String msg) {
		Meta meta = new Meta(code, msg);
		return new Response(meta, null);
	}

	/**
	 * 处理失败响应，默认(-1)响应码，自定义信息，有返回数据
	 *
	 * @param msg  处理结果信息
	 * @param data 返回数据
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:22 <br>
	 */
	public static Response fail(String msg, Object data) {
		Meta meta = new Meta(DEFAULT_FAIL_CODE, msg);
		return new Response(meta, data);
	}


	/**
	 * 处理失败响应，自定义响应码，自定义信息，有返回数据
	 *
	 * @param code 自定义响应码
	 * @param msg  处理结果信息
	 * @param data 返回数据
	 * @return 响应对象
	 * <p>
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-20 15:22 <br>
	 */
	public static Response fail(Integer code, String msg, Object data) {
		Meta meta = new Meta(code, msg);
		return new Response(meta, data);
	}

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMeat(Meta meta) {
		this.meta = meta;
	}

	/**
	 * 元数据，包含响应码和信息。
	 * <p>
	 * 创建人：leigq <br>
	 * 创建时间：2017年10月9日 下午3:31:17 <br>
	 */
	private static class Meta {

		/**
		 * 处理结果代码
		 */
		private Integer code;
		/**
		 * 处理结果信息
		 */
		private String msg;

		public Meta() {
		}

		public Meta(Integer code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

}
