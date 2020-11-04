package com.leigq.quartzlite.starter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 获取抛出的异常详细信息
 *
 * @author leiguoqing
 * @date 2020 -08-11 18:14:01
 */
public final class ExceptionDetailUtils {

	private static final Logger log = LoggerFactory.getLogger(ExceptionDetailUtils.class);

	private ExceptionDetailUtils() {

	}

	/**
	 * 获取异常详细信息，知道出了什么错，错在哪个类的第几行 .
	 *
	 * @param ex the ex
	 * @return throwable detail
	 */
	public static String getThrowableDetail(Throwable ex) {
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw, true)) {
			ex.printStackTrace(pw);
			return sw.toString();
		} catch (IOException e) {
			log.error("获取PrintWriter异常", e);
		}
		return null;
	}
}
