package com.leigq.quartzlite.starter.banner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Banner
 *
 * @author leigq
 * @date 2020/11/4 16:06
 */
@Component
@Slf4j
public class QuartzLiteBanner {

	@Value("${quartz.lite.version}")
	private String version;

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
		System.out.print(" ::Quartz Lite::");
		System.out.printf("\t\tversion: %s    by: %s", version, "leigq");
		System.out.println();
	}
}
