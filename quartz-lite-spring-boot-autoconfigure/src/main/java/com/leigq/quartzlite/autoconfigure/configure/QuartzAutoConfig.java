package com.leigq.quartzlite.autoconfigure.configure;

import com.leigq.quartzlite.autoconfigure.properties.QuartzProperties;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 自定义的 Quartz 属性自动装配
 *
 * @author leiguoqing
 * @date 2020-08-09 21:23:12
 */
@ConditionalOnClass({Scheduler.class, SchedulerFactoryBean.class,
		PlatformTransactionManager.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(QuartzProperties.class)
@Configuration
public class QuartzAutoConfig {

}
