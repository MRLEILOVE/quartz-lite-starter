package com.leigq.quartzlite.starter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮件发送工具
 * <p>
 * 创建人：leigq <br>
 * 创建时间：2018-11-06 17:34 <br>
 */
public class EmailSender {

	private final Logger log = LoggerFactory.getLogger(EmailSender.class);

	/**
	 * The Java mail sender.
	 */
	private final JavaMailSender javaMailSender;
	/**
	 * 邮件发送者
	 */
	@Value("${spring.mail.username}")
	private String formMail;

	/**
	 * Instantiates a new Email utils.
	 *
	 * @param javaMailSender the java mail sender
	 */
	public EmailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * 发送简单的文本邮件
	 *
	 * @param subject 主题
	 * @param text    内容
	 * @param to      收件人(可多个)
	 * @return the boolean
	 */
	public boolean sendSimpleMail(String subject, String text, String... to) {
		SimpleMailMessage sm = Holder.simpleMailMessage;
		sm.setFrom(formMail);
		sm.setSubject(subject);
		sm.setText(text);
		sm.setTo(to);
		try {
			javaMailSender.send(sm);
			return true;
		} catch (MailException e) {
			log.error("发送简单的文本邮件异常：", e);
			return false;
		}
	}


	/**
	 * 使用静态内部类实现单例模式获取 <code>SimpleMailMessage</code> 实例，线程安全。
	 *
	 * @author leigq
	 * @date 2020 -06-12 16:25:10
	 */
	private static class Holder {
		static final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
	}

}
