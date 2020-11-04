package com.leigq.quartzlite.autoconfigure.configure;

import com.leigq.quartzlite.autoconfigure.interceptor.QuartzLiteLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * MVC配置，在这里可以加入定义的拦截器
 * <br/>
 * 有两种方式配置，推荐使用 "实现 WebMvcConfigurer 接口 " 实现
 * <br/>
 * 参考： https://blog.csdn.net/cp026la/article/details/86518655
 * <p>
 * <br/>
 * {@link @SpringBootConfiguration} 继承自 {@link @Configuration}，二者功能也一致，标注当前类是配置类，
 * 并会将当前类内声明的一个或多个以@Bean注解标记的方法的实例纳入到spring容器中，并且实例名就是方法名。
 * {@link @EnableWebMvc} 启用此注解，SpringBoot 关于 MVC 的自动装载会失效，交由开发者自定义
 *
 * @author leigq <br>
 * @date 2019-05-24 23:50 <br>
 */
@Configuration
public class MvcAutoConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/* 添加自定义拦截器 */
		QuartzLiteLoginInterceptor quartzLiteLoginInterceptor = new QuartzLiteLoginInterceptor();
		// 登录拦截器
		registry.addInterceptor(quartzLiteLoginInterceptor)
				// 添加拦截规则，先把所有路径都加入拦截，再一个个排除
				.addPathPatterns("/quartz-lite/**")
				.addPathPatterns("/**/quartz-lite-*.html/**")
				// 排除拦截，表示该路径不用拦截
				.excludePathPatterns("/quartz-lite/user/login", "/quartz-lite/quartz-lite-login.html", "/quartz-lite/user/imgCode", "/templates/quartzlite/**", "/static/quartzlite/**");
	}

	/**
	 * 添加资源处理程序
	 *
	 * @author ：LeiGQ <br>
	 * @date ：2019-05-24 23:52 <br>
	 * <p>
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/quartz-lite/**")
				.addResourceLocations("classpath:/templates/quartzlite/");

		// 这是请求url的匹配模式，匹配url根路径下的所有路径（包括子路径，如果只有一个*，那就不包括子路径）
		registry.addResourceHandler("/static/quartzlite/**")
				// 这是文件路径的匹配模式，值上面匹配的路径在这个文件夹下面找文件
				.addResourceLocations("classpath:/static/quartzlite/");
	}
}