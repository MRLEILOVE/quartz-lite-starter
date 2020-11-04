package com.leigq.quartzlite.starter.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * 系统用户
 *
 * @author leiguoqing
 * @date 2020-08-08 22:30:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVO {

	@NotEmpty(message = "用户名不能为空")
	private String username;

	@NotEmpty(message = "密码不能为空")
	private String password;

	/**
	 * 时间戳，前端 RSA 加密传输的
	 * <br/>
	 * 时间戳机制主要用来应对非法的 DDOS 攻击
	 */
	@NotEmpty
	private String timestamp;

	/**
	 * 图像验证码
	 */
	@NotEmpty(message = "验证码不能为空")
	private String validCode;

	/**
	 * 签名的 Key，前端 RSA 加密传输的，采用8位随机数
	 */
	@NotEmpty
	private String signKey;

	/**
	 * 签名，签名生成规则 MD5(username={username}&password={password}&timestamp={timestamp}&signKey={signKey})
	 * <br/>
	 * 使用签名防止被抓包修改请求参数
	 */
	@NotEmpty
	private String sign;

}
