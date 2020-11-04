package com.leigq.quartzlite.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * description
 *
 * @author leiguoqing
 * @date 2020 -08-08 19:27:40
 */
@ConfigurationProperties("spring.quartz")
public class QuartzProperties {

	/**
	 * 可视化任务页面配置
	 */
	private TaskView taskView;

	/**
	 * 任务执行异常邮件配置
	 */
	private Mail mail;

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
	 * 任务执行异常邮件配置
	 */
	public static class Mail {
		/**
		 * 是否启用
		 */
		private boolean enable = false;

		/**
		 * 接受异常信息的邮箱
		 */
		private Set<String> receiveUsername;

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

		/**
		 * Gets receive username.
		 *
		 * @return the receive username
		 */
		public Set<String> getReceiveUsername() {
			return receiveUsername;
		}

		/**
		 * Sets receive username.
		 *
		 * @param receiveUsername the receive username
		 */
		public void setReceiveUsername(Set<String> receiveUsername) {
			this.receiveUsername = receiveUsername;
		}
	}


	/**
	 * 可视化任务页面配置
	 */
	public static class TaskView {
		/**
		 * 登录用户名
		 */
		private String loginUsername = "admin";

		/**
		 * 登录密码
		 */
		private String loginPassword = "admin";

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


}
