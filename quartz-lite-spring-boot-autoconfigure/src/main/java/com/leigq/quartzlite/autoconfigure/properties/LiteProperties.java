package com.leigq.quartzlite.autoconfigure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description
 *
 * @author leiguoqing
 * @date 2020-08-08 19:27:40
 */
@ConfigurationProperties("lite")
public class LiteProperties {

	private Security security;

	public Security getSecurity() {
		return security;
	}

	public void setSecurity(Security security) {
		this.security = security;
	}

	public static class Security {

		private Auth auth;

		public Auth getAuth() {
			return auth;
		}

		public void setAuth(Auth auth) {
			this.auth = auth;
		}

		public static class Auth {

			/**
			 * RSA 公钥
			 */
			private String pubKey;

			/**
			 * RSA 私钥
			 */
			private String priKey;

			public String getPubKey() {
				return pubKey;
			}

			public void setPubKey(String pubKey) {
				this.pubKey = pubKey;
			}

			public String getPriKey() {
				return priKey;
			}

			public void setPriKey(String priKey) {
				this.priKey = priKey;
			}
		}
	}
}
