package com.leigq.quartzlite.autoconfigure.configure;

import com.leigq.quartzlite.autoconfigure.properties.LiteProperties;
import com.leigq.quartzlite.autoconfigure.util.RsaCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 本项目自定义属性自动装载
 *
 * @author leiguoqing
 * @date 2020-08-09 21:23:12
 */
@EnableConfigurationProperties(LiteProperties.class)
@Configuration
public class LiteAutoConfig {

	public LiteAutoConfig(LiteProperties liteProperties) {
		// 初始化 RSACoder 中的公钥、私钥
		RsaCoder.PUB_KEY_BASE64 = liteProperties.getSecurity().getAuth().getPubKey();
		Logger log = LoggerFactory.getLogger(LiteAutoConfig.class);
		try {
			RsaCoder.PUB_KEY = RsaCoder.restorePubKey(RsaCoder.PUB_KEY_BASE64);
			log.info("初始化公钥成功");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("初始化公钥异常：", e);
		}

		RsaCoder.PRI_KEY_BASE64 = liteProperties.getSecurity().getAuth().getPriKey();
		try {
			RsaCoder.PRI_KEY = RsaCoder.restorePriKey(RsaCoder.PRI_KEY_BASE64);
			log.info("初始化私钥成功");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("初始化私钥异常：", e);
		}
	}
}
