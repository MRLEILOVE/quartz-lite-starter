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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 自动装配需要Spring托管的Bean
 *
 * @author leigq
 * @date 2020/11/3 21:17
 */
@Configuration
public class AutoConfiguration {

	private final JavaMailSender javaMailSender;
	private final SysTaskMapper sysTaskMapper;
	private final MapperFactory mapperFactory;
	private final Scheduler scheduler;
	private final QuartzProperties quartzProperties;

	public AutoConfiguration(Scheduler scheduler, MapperFactory mapperFactory, SysTaskMapper sysTaskMapper, JavaMailSender javaMailSender, QuartzProperties quartzProperties) {
		this.scheduler = scheduler;
		this.mapperFactory = mapperFactory;
		this.sysTaskMapper = sysTaskMapper;
		this.javaMailSender = javaMailSender;
		this.quartzProperties = quartzProperties;
	}

	@Bean
	public QuartzJobService quartzJobService() {
		return new QuartzJobService(scheduler);
	}

	@Bean
	public SysTaskService sysTaskService() {
		return new SysTaskService(quartzJobService(), sysTaskMapper, sysTaskLogService());
	}

	@Bean
	public SysTaskLogService sysTaskLogService() {
		return new SysTaskLogService(mapperFactory);
	}


	@Bean
	public EmailSender emailSender() {
		return new EmailSender(javaMailSender);
	}

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}

	@Bean
	public BaseJobDisallowConcurrent baseJobDisallowConcurrent() {
		return new BaseJobDisallowConcurrent();
	}

	@Bean
	public LoginController loginController() {
		return new LoginController(quartzProperties);
	}


	@Bean
	public SysTaskController sysTaskController() {
		return new SysTaskController(sysTaskService());
	}


	@Bean
	public SysTaskLogController sysTaskLogController() {
		return new SysTaskLogController(sysTaskLogService());
	}

}
