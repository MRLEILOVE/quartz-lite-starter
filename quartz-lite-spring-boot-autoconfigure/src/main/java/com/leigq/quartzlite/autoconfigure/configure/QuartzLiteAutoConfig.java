package com.leigq.quartzlite.autoconfigure.configure;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.leigq.quartzlite.autoconfigure.banner.QuartzLiteBanner;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Quartz-Lite自动装配
 *
 * @author leiguoqing
 * @date 2020-08-09 21:23:12
 */
@ComponentScan(basePackages = "com.leigq.quartzlite.autoconfigure")
@MapperScan("com.leigq.quartzlite.starter.domain.mapper")
@AutoConfigureAfter({QuartzAutoConfiguration.class, MybatisPlusAutoConfiguration.class})
@EnableConfigurationProperties(QuartzLiteProperties.class)
@Configuration
public class QuartzLiteAutoConfig {

	Logger log = LoggerFactory.getLogger(QuartzLiteAutoConfig.class);

	/**
	 * MP 分页插件
	 *
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
		log.info("init PaginationInterceptor success");
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
		final DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		log.info("init mapperFactory success");
		return mapperFactory;
	}

    /**
     * 构造方法
     *
     * @param quartzLiteBanner     the quartz lite banner
     * @param quartzLiteProperties the quartz lite properties
     */
    public QuartzLiteAutoConfig(QuartzLiteBanner quartzLiteBanner, QuartzLiteProperties quartzLiteProperties) {
        final Boolean showBanner = quartzLiteProperties.getShowBanner();
        // 打印 Banner
        if (Objects.nonNull(showBanner) && showBanner) {
            quartzLiteBanner.printBanner();
        }
        this.initTaskView(quartzLiteProperties);
        this.initSecurityAuth(quartzLiteProperties);
    }


	private void initTaskView(QuartzLiteProperties quartzLiteProperties) {
		QuartzLiteProperties.TaskView taskView = quartzLiteProperties.getTaskView();
		if (Objects.isNull(taskView)) {
			taskView = new QuartzLiteProperties.TaskView();
			taskView.setLoginUsername(QuartzLiteProperties.DEFAULT_LOGIN_USERNAME);
			taskView.setLoginPassword(QuartzLiteProperties.DEFAULT_LOGIN_USERNAME);
		}
		quartzLiteProperties.setTaskView(taskView);
		log.info("init taskView success");
	}


	private void initSecurityAuth(QuartzLiteProperties quartzLiteProperties) {
		final QuartzLiteProperties.Security security = quartzLiteProperties.getSecurity();
		if (Objects.isNull(security)) {
			return;
		}
		final QuartzLiteProperties.Security.Auth auth = security.getAuth();
		if (Objects.isNull(auth)) {
			return;
		}
		// 初始化用户配置的公钥
		this.initPubKey(auth.getPubKey());
		// 初始化用户配置的私钥
		this.initPriKey(auth.getPriKey());
	}

	private void initPubKey(String pubKey) {
		if (StringUtils.isEmpty(pubKey)) {
			return;
		}
		RsaCoder.PUB_KEY_BASE64 = pubKey;
		try {
			RsaCoder.PUB_KEY = RsaCoder.restorePubKey(RsaCoder.PUB_KEY_BASE64);
			log.info("init PUB_KEY success");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("init PUB_KEY error：", e);
		}
	}

	private void initPriKey(String priKey) {
		if (StringUtils.isEmpty(priKey)) {
			return;
		}
		RsaCoder.PRI_KEY_BASE64 = priKey;
		try {
			RsaCoder.PRI_KEY = RsaCoder.restorePriKey(RsaCoder.PRI_KEY_BASE64);
			log.info("init PRI_KEY success");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error("init PRI_KEY error：", e);
		}
	}

}
