package com.leigq.quartzlite.starter.autoconfigure;

import com.leigq.quartzlite.autoconfigure.configure.QuartzLiteAutoConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配需要Spring托管的Bean
 *
 * @author leigq
 * @date 2020 /11/3 21:17
 */
@Configuration
@ComponentScan(basePackages = {"com.leigq.quartzlite.starter"})
@AutoConfigureAfter(value = {MailSenderAutoConfiguration.class, QuartzLiteAutoConfig.class})
public class AutoConfiguration {

}
