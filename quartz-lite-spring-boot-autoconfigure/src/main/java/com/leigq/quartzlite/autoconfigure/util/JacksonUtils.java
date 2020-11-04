package com.leigq.quartzlite.autoconfigure.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


/**
 * Jackson 工具类
 * </br>
 * <ul>
 *     <li>兼容 Java8 的时间，</li>
 *     <li>不会出现科学计算法，已做处理</li>
 * </ul>
 *
 * @author leigq
 * @date 2020 -07-22 13:09:05
 */
public final class JacksonUtils {

	/**
	 * 提供一个全局可用的序列化 Bean，该对象只在本类使用，不提供给其他类用。<br/>
	 */
	private static final ObjectMapper OBJECT_MAPPER = SingletonEnum.INSTANCE.objectMapper;
	private static final String DATE_FORMATTER = "yyyy-MM-dd";
	private static final String TIME_FORMATTER = "HH:mm:ss";
	private static final String DATE_TIME_FORMATTER = DATE_FORMATTER + " " + TIME_FORMATTER;

	private JacksonUtils() {
	}


	/**
	 * 将对象转为 JSON 字符串
	 *
	 * @param <T> the type parameter
	 * @param obj 可以是不带泛型的Java集合、Map、POJO对象、数组，也可以是复杂的对象
	 * @return json字符串 string
	 * @author leigq
	 * @date 2020 -07-22 11:58:18
	 */
	public static <T> String objToJson(T obj) {
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new JacksonUtilsException(e);
		}
	}


	/**
	 * 将 JSON 字符串转为不带泛型的对象, 只能转换成不带泛型的Java集合、Map、POJO这类简单对象
	 *
	 * @param <T>  the type parameter
	 * @param json json字符串
	 * @param type 对象的类型
	 * @return 对象
	 * @author leigq
	 * @date 2020 -07-22 11:58:21
	 */
	public static <T> T jsonToObj(String json, Class<T> type) {
		try {
			return OBJECT_MAPPER.readValue(json, type);
		} catch (IOException e) {
			throw new JacksonUtilsException(e);
		}
	}


	/**
	 * 将 JSON 字符串转为带泛型的对象
	 * <br>
	 *
	 * @param <T>             the type parameter
	 * @param json            the json
	 * @param collectionClass 集合类型
	 * @param elementClasses  元素类型
	 * @return 比如 {@code Set<Permission>} 这类带泛型的对象
	 * @author leigq
	 * @date 2020 -07-22 14:15:05
	 */
	public static <T> T jsonToObjOfParametric(String json, Class<?> collectionClass, Class<?>... elementClasses) {
		try {
			JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
			return OBJECT_MAPPER.readValue(json, javaType);
		} catch (IOException e) {
			throw new JacksonUtilsException(e);
		}
	}

	/**
	 * 使用枚举实现单例
	 * <br/>
	 *
	 * @author leigq
	 * @date 2020 -07-24 11:14:28
	 */
	private enum SingletonEnum {

		INSTANCE;

		private final ObjectMapper objectMapper = CustomObjectMapper.newInstance();
	}

	/**
	 * 自定义 ObjectMapper 类
	 */
	public static class CustomObjectMapper {

		private CustomObjectMapper() {

		}

		/**
		 * 对外提供一个自定义配置以增强 ObjectMapper 默认配置，主要体现在 Java8 时间处理、科学记数法处理
		 * <br/>
		 * 如果你还需要额外的增强配置可以这样使用：<br/>
		 * <per>
		 * ObjectMapper objectMapper = JacksonUtils.getObjectMapper();
		 * objectMapper.setDateFormat(new SimpleDateFormat("YYYY-MM-dd"));
		 * </per>
		 *
		 * @author leigq
		 * @date 2020 -07-24 11:14:28
		 */
		public static ObjectMapper newInstance() {
			ObjectMapper objectMapper = new ObjectMapper();
			/* 一些配置 配置参考：http://www.imooc.com/wenda/detail/425280*/
			objectMapper
					// 设置时区
					.setTimeZone(TimeZone.getTimeZone("GMT+8"))
					// Date 对象的格式，非 java8 时间
					.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMATTER))
					// 默认忽略值为 null 的属性，暂时不忽略，放开注释即不会序列化为 null 的属性
//				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
					// 禁止打印时间为时间戳
					.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
					// 禁止使用科学记数法
					.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);

			// 自定义Java8的时间兼容模块
			JavaTimeModule javaTimeModule = new JavaTimeModule();

			// 序列化配置,针对java8 时间
			javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)));
			javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
			javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMATTER)));

			// 反序列化配置,针对java8 时间，使用自定义 LocalDateTimeDeserializer
			javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER)));
			javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMATTER)));
			javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMATTER)));

			/*注册模块*/
			objectMapper
					// Java8的时间兼容模块
					.registerModule(javaTimeModule)
					// Jdk8Module() -> 注册jdk8模块
					.registerModule(new Jdk8Module())
					// new ParameterNamesModule() ->
					.registerModule(new ParameterNamesModule());
			return objectMapper;
		}

	}

	private static class JacksonUtilsException extends RuntimeException {
		public JacksonUtilsException(Throwable cause) {
			super(cause);
		}
	}

}
