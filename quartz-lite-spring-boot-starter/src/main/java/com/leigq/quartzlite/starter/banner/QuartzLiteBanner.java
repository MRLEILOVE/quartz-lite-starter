package com.leigq.quartzlite.starter.banner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * Banner
 *
 * @author leigq
 * @date 2020/11/4 16:06
 */
@Component
@Slf4j
public class QuartzLiteBanner {

	private static final String BANNER_MSG = "\n" +
			"   ____                   _       _      _ _       \n" +
			"  / __ \\                 | |     | |    (_) |      \n" +
			" | |  | |_   _  __ _ _ __| |_ ___| |     _| |_ ___ \n" +
			" | |  | | | | |/ _` | '__| __|_  / |    | | __/ _ \\\n" +
			" | |__| | |_| | (_| | |  | |_ / /| |____| | ||  __/\n" +
			"  \\___\\_\\\\__,_|\\__,_|_|   \\__/___|______|_|\\__\\___|\n";

	/**
	 * Print banner.
	 */
	public void printBanner() {
		System.out.println(BANNER_MSG);
		System.out.printf("\t\t\t\t\t\t\t\t\t\t\t%s", getVersion());
		System.out.println();
	}

	private String getVersion() {
		try {
			final Properties properties = PropertiesLoaderUtils.loadAllProperties("app.properties");
			return properties.getProperty("app.version");
		} catch (IOException e) {
			log.error("app.properties load error");
		}
		return null;
	}
}
