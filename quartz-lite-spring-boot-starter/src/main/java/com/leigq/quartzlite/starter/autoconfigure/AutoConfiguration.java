package com.leigq.quartzlite.starter.autoconfigure;

import com.leigq.quartzlite.autoconfigure.properties.QuartzProperties;
import com.leigq.quartzlite.starter.bean.job.BaseJobDisallowConcurrent;
import com.leigq.quartzlite.starter.controller.LoginController;
import com.leigq.quartzlite.starter.controller.SysTaskController;
import com.leigq.quartzlite.starter.controller.SysTaskLogController;
import com.leigq.quartzlite.starter.domain.mapper.SysTaskMapper;
import com.leigq.quartzlite.starter.service.QuartzJobService;
import com.leigq.quartzlite.starter.service.SysTaskLogService;
import com.leigq.quartzlite.starter.service.SysTaskService;
import com.leigq.quartzlite.starter.util.EmailSender;
import com.leigq.quartzlite.starter.util.SpringContextHolder;
import ma.glasnost.orika.MapperFactory;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 自动装配需要Spring托管的Bean
 *
 * @author leigq
 * @date 2020 /11/3 21:17
 */
@Configuration
public class AutoConfiguration {

	private final Logger log = LoggerFactory.getLogger(AutoConfiguration.class);


	/**
	 * Quartz job service quartz job service.
	 *
	 * @param scheduler the scheduler
	 * @return the quartz job service
	 */
	@Bean
	@ConditionalOnMissingBean(QuartzJobService.class)
	public QuartzJobService quartzJobService(Scheduler scheduler) {
		final QuartzJobService quartzJobService = new QuartzJobService(scheduler);
		log.info("quartzJobService bean init [{}]", quartzJobService);
		return quartzJobService;
	}

	/**
	 * Sys task service sys task service.
	 *
	 * @param quartzJobService  the quartz job service
	 * @param sysTaskMapper     the sys task mapper
	 * @param sysTaskLogService the sys task log service
	 * @return the sys task service
	 */
	@Bean
	@ConditionalOnMissingBean(SysTaskService.class)
	public SysTaskService sysTaskService(QuartzJobService quartzJobService, SysTaskMapper sysTaskMapper, SysTaskLogService sysTaskLogService) {
		final SysTaskService sysTaskService = new SysTaskService(quartzJobService, sysTaskMapper, sysTaskLogService);
		log.info("sysTaskService bean init [{}]", sysTaskService);
		return sysTaskService;
	}

	/**
	 * Sys task log service sys task log service.
	 *
	 * @param mapperFactory the mapper factory
	 * @return the sys task log service
	 */
	@Bean
	@ConditionalOnMissingBean(SysTaskLogService.class)
	public SysTaskLogService sysTaskLogService(MapperFactory mapperFactory) {
		final SysTaskLogService sysTaskLogService = new SysTaskLogService(mapperFactory);
		log.info("sysTaskLogService bean init [{}]", sysTaskLogService);
		return sysTaskLogService;
	}


	/**
	 * Email sender email sender.
	 *
	 * @param javaMailSender the java mail sender
	 * @return the email sender
	 */
	@Bean
	@ConditionalOnMissingBean(EmailSender.class)
	@ConditionalOnBean(value = JavaMailSender.class)
	public EmailSender emailSender(JavaMailSender javaMailSender) {
		final EmailSender emailSender = new EmailSender(javaMailSender);
		log.info("emailSender bean init [{}]", emailSender);
		return emailSender;
	}

	/**
	 * Spring context holder spring context holder.
	 *
	 * @return the spring context holder
	 */
	@Bean
	@ConditionalOnMissingBean(SpringContextHolder.class)
	public SpringContextHolder springContextHolder() {
		final SpringContextHolder springContextHolder = new SpringContextHolder();
		log.info("springContextHolder bean init [{}]", springContextHolder);
		return springContextHolder;
	}

	/**
	 * Base job disallow concurrent base job disallow concurrent.
	 *
	 * @return the base job disallow concurrent
	 */
	@Bean
	@ConditionalOnMissingBean(BaseJobDisallowConcurrent.class)
	public BaseJobDisallowConcurrent baseJobDisallowConcurrent() {
		final BaseJobDisallowConcurrent baseJobDisallowConcurrent = new BaseJobDisallowConcurrent();
		log.info("baseJobDisallowConcurrent bean init [{}]", baseJobDisallowConcurrent);
		return baseJobDisallowConcurrent;
	}

	/**
	 * Login controller login controller.
	 *
	 * @param quartzProperties the quartz properties
	 * @return the login controller
	 */
	@Bean
	@ConditionalOnMissingBean(LoginController.class)
	public LoginController loginController(QuartzProperties quartzProperties) {
		final LoginController loginController = new LoginController(quartzProperties);
		log.info("loginController bean init [{}]", loginController);
		return loginController;
	}


	/**
	 * Sys task controller sys task controller.
	 *
	 * @param sysTaskService the sys task service
	 * @return the sys task controller
	 */
	@Bean
	@ConditionalOnMissingBean(SysTaskController.class)
	public SysTaskController sysTaskController(SysTaskService sysTaskService) {
		final SysTaskController sysTaskController = new SysTaskController(sysTaskService);
		log.info("sysTaskController bean init [{}]", sysTaskController);
		return sysTaskController;
	}


	/**
	 * Sys task log controller sys task log controller.
	 *
	 * @param sysTaskLogService the sys task log service
	 * @return the sys task log controller
	 */
	@Bean
	@ConditionalOnMissingBean(SysTaskLogController.class)
	public SysTaskLogController sysTaskLogController(SysTaskLogService sysTaskLogService) {
		final SysTaskLogController sysTaskLogController = new SysTaskLogController(sysTaskLogService);
		log.info("sysTaskLogController bean init [{}]", sysTaskLogController);
		return sysTaskLogController;
	}

}
