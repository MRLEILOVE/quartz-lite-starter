package com.leigq.quartzlite.autoconfigure.configure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.leigq.quartzlite.autoconfigure.properties.QuartzLiteProperties;
import com.leigq.quartzlite.autoconfigure.util.RsaCoder;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * Quartz-Lite自动装配
 *
 * @author leiguoqing
 * @date 2020-08-09 21:23:12
 */
@MapperScan("com.leigq.quartzlite.starter.domain.mapper")
@AutoConfigureAfter({QuartzAutoConfiguration.class, MybatisPlusAutoConfiguration.class})
@EnableConfigurationProperties(QuartzLiteProperties.class)
@Configuration
public class QuartzLiteAutoConfig {
	/**
	 * MP 分页插件
	 * @author ：LeiGQ <br>
	 * @date ：2019-06-14 10:43 <br>
	 * <p>
	 */
	@Bean
	@ConditionalOnMissingBean(value = PaginationInterceptor.class)
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		List<ISqlParser> sqlParserList = new ArrayList<>();
		// 攻击 SQL 阻断解析器、加入解析链 作用！阻止恶意的全表更新删除
		sqlParserList.add(new BlockAttackSqlParser());
		paginationInterceptor.setSqlParserList(sqlParserList);
		return paginationInterceptor;
	}


	/**
	 * 注入 DefaultMapperFactory，方便在使用的地方直接注入
	 *
	 * @return the mapper factory
	 */
	@Bean(name = "quartzLiteMapperFactory")
	@ConditionalOnMissingBean(MapperFactory.class)
	public MapperFactory getMapperFactory() {
		return new DefaultMapperFactory.Builder().build();
	}

	public QuartzLiteAutoConfig(QuartzLiteProperties quartzLiteProperties) {
		// 初始化 RSACoder 中的公钥、私钥
		RsaCoder.PUB_KEY_BASE64 = quartzLiteProperties.getSecurity().getAuth().getPubKey();
		Logger log = LoggerFactory.getLogger(QuartzLiteAutoConfig.class);
		try {
			RsaCoder.PUB_KEY = RsaCoder.restorePubKey(RsaCoder.PUB_KEY_BASE64);
			log.info("init PUB_KEY success");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("init PUB_KEY error：", e);
		}

		RsaCoder.PRI_KEY_BASE64 = quartzLiteProperties.getSecurity().getAuth().getPriKey();
		try {
			RsaCoder.PRI_KEY = RsaCoder.restorePriKey(RsaCoder.PRI_KEY_BASE64);
			log.info("init PRI_KEY success");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("init PRI_KEY error：", e);
		}
	}

}
