package com.leigq.quartzlite.autoconfigure.configure;

import com.leigq.quartzlite.autoconfigure.properties.QuartzProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义的 Quartz 属性自动装配
 *
 * @author leiguoqing
 * @date 2020-08-09 21:23:12
 */
@AutoConfigureAfter({QuartzAutoConfiguration.class})
@EnableConfigurationProperties(QuartzProperties.class)
@Configuration
public class QuartzAutoConfig {

}
