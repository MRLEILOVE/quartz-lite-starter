package com.leigq.quartzlite.autoconfigure.banner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Banner
 *
 * @author leigq
 * @date 2020/11/4 16:06
 */
@Component
public class QuartzLiteBanner {

	@Value("${quartz.lite.version}")
	private String version;

    /**
	 * Print banner.
	 */
	public void printBanner() {
        String bannerMsg = "\n" +
                "   ____                   _       _      _ _       \n" +
                "  / __ \\                 | |     | |    (_) |      \n" +
                " | |  | |_   _  __ _ _ __| |_ ___| |     _| |_ ___ \n" +
                " | |  | | | | |/ _` | '__| __|_  / |    | | __/ _ \\\n" +
                " | |__| | |_| | (_| | |  | |_ / /| |____| | ||  __/\n" +
                "  \\___\\_\\\\__,_|\\__,_|_|   \\__/___|______|_|\\__\\___|\n";
        System.out.println(bannerMsg);
		System.out.print(" ::Quartz Lite::");
		System.out.printf("\t\tversion: %s    by: %s", version, "leigq");
		System.out.println();
	}
}
