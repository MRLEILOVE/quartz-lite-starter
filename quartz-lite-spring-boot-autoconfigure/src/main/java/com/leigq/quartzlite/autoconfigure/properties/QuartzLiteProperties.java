package com.leigq.quartzlite.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * QuartzLite 的属性，在 Quartz 中做增加
 *
 * @author leiguoqing
 * @date 2020 -08-08 19:27:40
 */
@ConfigurationProperties("spring.quartz")
public class QuartzLiteProperties {

	public static final String DEFAULT_LOGIN_USERNAME = "admin";

	public static final String DEFAULT_LOGIN_PASSWORD = "admin";

	/**
	 * 可视化任务页面配置
	 */
	private TaskView taskView;

	/**
	 * 任务执行异常邮件配置
	 */
	private Mail mail;

	/**
	 * 安全相关配置
	 */
	private Security security;


	/**
	 * Gets task view.
	 *
	 * @return the task view
	 */
	public TaskView getTaskView() {
		return taskView;
	}

	/**
	 * Sets task view.
	 *
	 * @param taskView the task view
	 */
	public void setTaskView(TaskView taskView) {
		this.taskView = taskView;
	}

	/**
	 * Gets mail.
	 *
	 * @return the mail
	 */
	public Mail getMail() {
		return mail;
	}

	/**
	 * Sets mail.
	 *
	 * @param mail the mail
	 */
	public void setMail(Mail mail) {
		this.mail = mail;
	}


	/**
	 * Gets security.
	 *
	 * @return the security
	 */
	public Security getSecurity() {
		return security;
	}

	/**
	 * Sets security.
	 *
	 * @param security the security
	 */
	public void setSecurity(Security security) {
		this.security = security;
	}

	/**
	 * 任务执行异常邮件配置
	 */
	public static class Mail {
		/**
		 * 是否启用
		 */
		private boolean enable = false;

		/**
		 * 发送邮件的邮箱
		 */
		private String sendEmailForm;

		/**
		 * 接受异常信息的邮箱
		 */
		private Set<String> sendEmailTo;

		/**
		 * Gets enable.
		 *
		 * @return the enable
		 */
		public boolean getEnable() {
			return enable;
		}

		/**
		 * Sets enable.
		 *
		 * @param enable the enable
		 */
		public void setEnable(boolean enable) {
			this.enable = enable;
		}

		public String getSendEmailForm() {
			return sendEmailForm;
		}

		public void setSendEmailForm(String sendEmailForm) {
			this.sendEmailForm = sendEmailForm;
		}

		/**
		 * Gets receive username.
		 *
		 * @return the receive username
		 */
		public Set<String> getSendEmailTo() {
			return sendEmailTo;
		}

		/**
		 * Sets receive username.
		 *
		 * @param sendEmailTo the receive username
		 */
		public void setSendEmailTo(Set<String> sendEmailTo) {
			this.sendEmailTo = sendEmailTo;
		}
	}


	/**
	 * 可视化任务页面配置
	 */
	public static class TaskView {
		/**
		 * 登录用户名
		 */
		private String loginUsername = DEFAULT_LOGIN_USERNAME;

		/**
		 * 登录密码
		 */
		private String loginPassword = DEFAULT_LOGIN_PASSWORD;

		/**
		 * Gets login username.
		 *
		 * @return the login username
		 */
		public String getLoginUsername() {
			return loginUsername;
		}

		/**
		 * Sets login username.
		 *
		 * @param loginUsername the login username
		 */
		public void setLoginUsername(String loginUsername) {
			this.loginUsername = loginUsername;
		}

		/**
		 * Gets login password.
		 *
		 * @return the login password
		 */
		public String getLoginPassword() {
			return loginPassword;
		}

		/**
		 * Sets login password.
		 *
		 * @param loginPassword the login password
		 */
		public void setLoginPassword(String loginPassword) {
			this.loginPassword = loginPassword;
		}
	}


	/**
	 * The type Security.
	 */
	public static class Security {

		private Security.Auth auth;

		/**
		 * Gets auth.
		 *
		 * @return the auth
		 */
		public Security.Auth getAuth() {
			return auth;
		}

		/**
		 * Sets auth.
		 *
		 * @param auth the auth
		 */
		public void setAuth(Security.Auth auth) {
			this.auth = auth;
		}

		/**
		 * The type Auth.
		 */
		public static class Auth {

			/**
			 * RSA 公钥
			 */
			private String pubKey;

			/**
			 * RSA 私钥
			 */
			private String priKey;

			/**
			 * Gets pub key.
			 *
			 * @return the pub key
			 */
			public String getPubKey() {
				return pubKey;
			}

			/**
			 * Sets pub key.
			 *
			 * @param pubKey the pub key
			 */
			public void setPubKey(String pubKey) {
				this.pubKey = pubKey;
			}

			/**
			 * Gets pri key.
			 *
			 * @return the pri key
			 */
			public String getPriKey() {
				return priKey;
			}

			/**
			 * Sets pri key.
			 *
			 * @param priKey the pri key
			 */
			public void setPriKey(String priKey) {
				this.priKey = priKey;
			}
		}
	}
}
