package com.leigq.quartzlite.autoconfigure.properties;

import com.leigq.quartzlite.autoconfigure.util.RsaCoder;
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
     * 是否显示 Banner，默认 true
     */
    private Boolean showBanner;

	/**
	 * 可视化任务页面配置
	 */
	private TaskView taskView = new TaskView();

	/**
	 * 任务执行异常邮件配置
	 */
	private Mail mail;

	/**
	 * 安全相关配置
	 */
	private Security security;


    /**
     * 是否显示 Banner，默认 true
     *
     * @return the show banner
     */
    public Boolean getShowBanner() {
        return showBanner;
    }

    /**
     * 是否显示 Banner，默认 true
     *
     * @param showBanner the show banner
     */
    public void setShowBanner(Boolean showBanner) {
        this.showBanner = showBanner;
    }

    /**
	 * 可视化任务页面配置
	 *
	 * @return the task view
	 */
	public TaskView getTaskView() {
		return taskView;
	}

	/**
	 * 可视化任务页面配置
	 *
	 * @param taskView the task view
	 */
	public void setTaskView(TaskView taskView) {
		this.taskView = taskView;
	}

	/**
	 * 任务执行异常邮件配置
	 *
	 * @return the mail
	 */
	public Mail getMail() {
		return mail;
	}

	/**
	 * 任务执行异常邮件配置
	 *
	 * @param mail the mail
	 */
	public void setMail(Mail mail) {
		this.mail = mail;
	}


	/**
	 * 安全相关配置
	 *
	 * @return the security
	 */
	public Security getSecurity() {
		return security;
	}

	/**
	 * 安全相关配置
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
		 * 接受异常信息的邮箱，可配置多个
		 */
		private Set<String> sendEmailTo;

        /**
         * 是否启用
         *
         * @return the enable
         */
        public boolean getEnable() {
			return enable;
		}

		/**
		 * 是否启用
		 *
		 * @param enable the enable
		 */
		public void setEnable(boolean enable) {
			this.enable = enable;
		}

        /**
         * 发送邮件的邮箱
         *
         * @return the send email form
         */
        public String getSendEmailForm() {
			return sendEmailForm;
		}

        /**
         * 发送邮件的邮箱
         *
         * @param sendEmailForm the send email form
         */
        public void setSendEmailForm(String sendEmailForm) {
			this.sendEmailForm = sendEmailForm;
		}

		/**
		 * 接受异常信息的邮箱，可配置多个
		 *
		 * @return the receive username
		 */
		public Set<String> getSendEmailTo() {
			return sendEmailTo;
		}

		/**
		 * 接受异常信息的邮箱，可配置多个
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
         * 是否内嵌，默认 false， 启用内嵌后将会放开 QuartzLite的登录拦截器，用户可以不登陆直接访问任务页面及对任务进行操作，此时，登录拦截会交给第三方系统处理
         */
        private boolean embedded = true;

		/**
		 * 登录用户名
		 */
		private String loginUsername = DEFAULT_LOGIN_USERNAME;

		/**
		 * 登录密码
		 */
		private String loginPassword = DEFAULT_LOGIN_PASSWORD;

        /**
         * 是否内嵌，默认 false， 启用内嵌后将会放开 QuartzLite的登录拦截器，用户可以不登陆直接访问任务页面及对任务进行操作，此时，登录拦截会交给第三方系统处理
         *
         * @return the embedded
         */
        public boolean getEmbedded() {
            return embedded;
        }

        /**
         * 是否内嵌，默认 false， 启用内嵌后将会放开 QuartzLite的登录拦截器，用户可以不登陆直接访问任务页面及对任务进行操作，此时，登录拦截会交给第三方系统处理
         *
         * @param embedded the embedded
         */
        public void setEmbedded(boolean embedded) {
            this.embedded = embedded;
        }

        /**
		 * 登录用户名
		 *
		 * @return the login username
		 */
		public String getLoginUsername() {
			return loginUsername;
		}

		/**
		 * 登录用户名
		 *
		 * @param loginUsername the login username
		 */
		public void setLoginUsername(String loginUsername) {
			this.loginUsername = loginUsername;
		}

		/**
		 * 登录密码
		 *
		 * @return the login password
		 */
		public String getLoginPassword() {
			return loginPassword;
		}

		/**
		 * 登录密码
		 *
		 * @param loginPassword the login password
		 */
		public void setLoginPassword(String loginPassword) {
			this.loginPassword = loginPassword;
		}
	}


	/**
	 * 安全相关配置
	 */
	public static class Security {

        /**
         * 可视化页面认证
         */
        private Security.Auth auth;

		/**
		 * 可视化页面认证
		 *
		 * @return the auth
		 */
		public Security.Auth getAuth() {
			return auth;
		}

		/**
		 * 可视化页面认证
		 *
		 * @param auth the auth
		 */
		public void setAuth(Security.Auth auth) {
			this.auth = auth;
		}

		/**
		 * 可视化页面认证
		 */
		public static class Auth {

			/**
			 * RSA 公钥
             * <br/>
             * 可以使用 {@link RsaCoder#generateKeyPair()} 方法重新生成
			 */
			private String pubKey;

			/**
			 * RSA 私钥
             * <br/>
             * 可以使用 {@link RsaCoder#generateKeyPair()} 方法重新生成
			 */
			private String priKey;

			/**
			 * RSA 公钥
             * <br/>
             * 可以使用 {@link RsaCoder#generateKeyPair()} 方法重新生成
			 *
			 * @return the pub key
			 */
			public String getPubKey() {
				return pubKey;
			}

			/**
			 * RSA 公钥
             * <br/>
             * 可以使用 {@link RsaCoder#generateKeyPair()} 方法重新生成
			 *
			 * @param pubKey the pub key
			 */
			public void setPubKey(String pubKey) {
				this.pubKey = pubKey;
			}

			/**
			 * RSA 私钥
             * <br/>
             * 可以使用 {@link RsaCoder#generateKeyPair()} 方法重新生成
			 *
			 * @return the pri key
			 */
			public String getPriKey() {
				return priKey;
			}

			/**
			 * RSA 私钥
             * <br/>
             * 可以使用 {@link RsaCoder#generateKeyPair()} 方法重新生成
			 *
			 * @param priKey the pri key
			 */
			public void setPriKey(String priKey) {
				this.priKey = priKey;
			}
		}
	}
}
