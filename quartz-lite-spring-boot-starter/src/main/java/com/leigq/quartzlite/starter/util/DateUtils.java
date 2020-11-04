package com.leigq.quartzlite.starter.util;


import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 基于 java8 封装的时间工具类
 * <p>
 *
 * @author leiguoqing
 * @date 2020 -07-25 20:17:16
 */
public class DateUtils {

	private DateUtils() {
	}

	/**
	 * 获取 Java8 时间需要的 DateTimeFormatter
	 *
	 * @param format the format
	 * @return the format
	 * @author leiguoqing
	 * @date 2020 -07-25 20:32:59
	 */
	public static DateTimeFormatter getFormat(java.lang.String format) {
		return DateTimeFormatter.ofPattern(format);
	}

	/**
	 * 获取 LocalDate
	 *
	 * @return the local date
	 * @author leiguoqing
	 * @date 2020 -07-25 20:32:55
	 */
	private static LocalDate localDate() {
		return LocalDate.now();
	}

	/**
	 * 获取 LocalTime
	 *
	 * @return the local time
	 * @author leiguoqing
	 * @date 2020 -07-25 20:32:56
	 */
	private static LocalTime localTime() {
		return LocalTime.now();
	}

	/**
	 * 获取 LocalDateTime
	 *
	 * @return the local date time
	 * @author leiguoqing
	 * @date 2020 -07-25 20:32:57
	 */
	private static java.time.LocalDateTime localDateTime() {
		return java.time.LocalDateTime.now();
	}

	/**
	 * 获取字符串时间相关
	 *
	 * @author leiguoqing
	 * @date 2020 -07-25 20:37:18
	 */
	public static class String {
		/**
		 * Instantiates a new Str.
		 *
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:28
		 */
		private String() {
		}


		/**
		 * 当前时间，格式："yyyy-MM-dd HH:mm:ss"
		 *
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:37:33
		 */
		public static java.lang.String currDateTime() {
			return current(Format.DATE_TIME_FORMAT);
		}

		/**
		 * 当前时间，格式："yyyy-MM-dd"
		 *
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:38:34
		 */
		public static java.lang.String currDate() {
			return current(Format.DATE_FORMAT);
		}

		/**
		 * 当前时间，自定义格式
		 *
		 * @param format 时间格式
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:38:54
		 */
		public static java.lang.String current(java.lang.String format) {
			return localDateTime().format(getFormat(format));
		}


		/**
		 * Date to String，默认格式："yyyy-MM-dd HH:mm:ss"
		 *
		 * @param date the date
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:40:27
		 */
		public static java.lang.String from(java.util.Date date) {
			return String.from(date, Format.DATE_TIME_FORMAT);
		}

		/**
		 * Date to String，自定义格式
		 *
		 * @param date   the date
		 * @param format the format
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:41:27
		 */
		public static java.lang.String from(java.util.Date date, java.lang.String format) {
			return LocalDateTime.from(date).format(getFormat(format));
		}


		/**
		 * LocalDateTime to String，默认格式："yyyy-MM-dd HH:mm:ss"
		 *
		 * @param localDateTime the local date time
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:41:47
		 */
		public static java.lang.String from(java.time.LocalDateTime localDateTime) {
			return String.from(localDateTime, Format.DATE_TIME_FORMAT);
		}

		/**
		 * LocalDateTime to String，自定义格式
		 *
		 * @param localDateTime the local date time
		 * @param format        the format
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:41:49
		 */
		public static java.lang.String from(java.time.LocalDateTime localDateTime, java.lang.String format) {
			return localDateTime.format(getFormat(format));
		}


		/**
		 * LocalDate to String，默认格式："yyyy-MM-dd"
		 *
		 * @param date the date
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:42:28
		 */
		public static java.lang.String from(LocalDate date) {
			return String.from(date, Format.DATE_FORMAT);
		}

		/**
		 * LocalDate to String，自定义格式
		 *
		 * @param date   the date
		 * @param format the format
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:42:30
		 */
		public static java.lang.String from(LocalDate date, java.lang.String format) {
			return date.format(getFormat(format));
		}

		/**
		 * LocalTime to String，默认格式："HH:mm:ss"
		 *
		 * @param date the date
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:42:58
		 */
		public static java.lang.String from(LocalTime date) {
			return String.from(date, Format.TIME_FORMAT);
		}

		/**
		 * LocalTime to String，自定义格式
		 *
		 * @param date   the date
		 * @param format the format
		 * @return the string
		 * @author leiguoqing
		 * @date 2020 -07-25 20:42:58
		 */
		public static java.lang.String from(LocalTime date, java.lang.String format) {
			return date.format(getFormat(format));
		}

		/**
		 * 时间戳转 String 类型时间，默认格式：yyyy-MM-dd HH:mm:ss
		 *
		 * @param epochSecond the epoch second
		 * @return the java . lang . string
		 */
		public static java.lang.String from(long epochSecond) {
			return from(epochSecond, Format.DATE_TIME_FORMAT);
		}

		/**
		 * 时间戳转 String 类型时间，自定义格式
		 *
		 * @param timestamp the epoch second
		 * @param format    the format
		 * @return the java . lang . string
		 */
		public static java.lang.String from(long timestamp, java.lang.String format) {
			final java.time.LocalDateTime localDateTime = LocalDateTime.from(timestamp);
			return from(localDateTime, format);
		}
	}

	/**
	 * The type T date.
	 *
	 * @author leiguoqing
	 * @date 2020 -07-25 21:15:28
	 */
	public static class Date {
		/**
		 * Instantiates a new T date.
		 *
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		private Date() {
		}

		/**
		 * String to Date，默认格式：yyyy-MM-dd HH:mm:ss
		 *
		 * @param date the date
		 * @return the date
		 * @author leiguoqing
		 * @date 2020 -07-25 21:07:53
		 */
		public static java.util.Date from(java.lang.String date) {
			return Date.from(LocalDateTime.from(date));
		}


		/**
		 * String to Date，自定义格式
		 *
		 * @param date   the date
		 * @param format the format
		 * @return the date
		 * @author leiguoqing
		 * @date 2020 -07-25 21:08:28
		 */
		public static java.util.Date from(java.lang.String date, java.lang.String format) {
			return Date.from(LocalDateTime.from(date, format));
		}


		/**
		 * LocalDateTime to Date.
		 *
		 * @param localDateTime the local date time
		 * @return the date
		 * @author leiguoqing
		 * @date 2020 -07-25 21:08:45
		 */
		public static java.util.Date from(java.time.LocalDateTime localDateTime) {
			return java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		}


		/**
		 * LocalDate to Date. 时分秒将填充 "00:00:00"
		 *
		 * @param localDate localDate
		 * @return date
		 * @author leiguoqing
		 * @date 2020 -07-25 21:53:48
		 */
		public static java.util.Date from(LocalDate localDate) {
			return Date.from(LocalDateTime.from(localDate));
		}

		/**
		 * LocalTime to Date. 年月日将填充当前年月日
		 *
		 * @param localTime localTime
		 * @return date
		 * @author leiguoqing
		 * @date 2020 -07-25 21:53:49
		 */
		public static java.util.Date from(LocalTime localTime) {
			return Date.from(LocalDateTime.from(localTime));
		}

	}

	/**
	 * TLocalDateTime 时间相关
	 *
	 * @author leiguoqing
	 * @date 2020 -07-25 20:43:40
	 */
	public static class LocalDateTime {
		/**
		 * Instantiates a new T local date time.
		 *
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		private LocalDateTime() {
		}

		/**
		 * String to LocalDateTime. 默认格式：yyyy-MM-dd HH:mm:ss
		 * <p>
		 *
		 * @param date date类型时间
		 * @return LocalDateTime local date time
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		public static java.time.LocalDateTime from(java.lang.String date) {
			return LocalDateTime.from(date, Format.DATE_TIME_FORMAT);
		}


		/**
		 * String to LocalDateTime. 自定义格式
		 *
		 * @param date   the date
		 * @param format the format
		 * @return the local date time
		 * @author leiguoqing
		 * @date 2020 -07-25 20:49:19
		 */
		public static java.time.LocalDateTime from(java.lang.String date, java.lang.String format) {
			return java.time.LocalDateTime.parse(date, getFormat(format));
		}

		/**
		 * Date to LocalDateTime
		 * <p>
		 *
		 * @param date date类型时间
		 * @return LocalDateTime local date time
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		public static java.time.LocalDateTime from(java.util.Date date) {
			return java.time.LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		}


		/**
		 * LocalDate to LocalDateTime. 时分秒将填充 "00:00:00"
		 *
		 * @param localDate localDate
		 * @return date
		 * @author leiguoqing
		 * @date 2020 -07-25 21:53:48
		 */
		public static java.time.LocalDateTime from(LocalDate localDate) {
			return localDate.atStartOfDay();
		}

		/**
		 * LocalTime to LocalDateTime. 年月日将填充当前年月日
		 *
		 * @param localTime localTime
		 * @return date
		 * @author leiguoqing
		 * @date 2020 -07-25 21:53:49
		 */
		public static java.time.LocalDateTime from(LocalTime localTime) {
			return java.time.LocalDateTime.of(localDate(), localTime);
		}


		/**
		 * 时间戳 转 LocalDateTime 类型时间
		 *
		 * @param timestamp the timestamp
		 * @return the java . lang . string
		 */
		public static java.time.LocalDateTime from(long timestamp) {
			return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
		}
	}

	/**
	 * 时间格式 字符串
	 *
	 * @author leiguoqing
	 * @date 2020 -07-25 19:34:11
	 */
	public static class Format {
		/**
		 * <span color='red'>年-月-日</span>的时间格式
		 * <br/>
		 * 返回 "yyyy-MM-dd" 字符串
		 */
		public static final java.lang.String DATE_FORMAT = "yyyy-MM-dd";
		/**
		 * <span color='red'>时:分:秒</span>的时间格式
		 * <br/>
		 * 返回 "HH:mm:ss" 字符串
		 */
		public static final java.lang.String TIME_FORMAT = "HH:mm:ss";
		/**
		 * <span color='red'>年-月-日 时:分:秒</span>的时间格式
		 * <br/>
		 * 返回 "yyyy-MM-dd HH:mm:ss" 字符串
		 */
		public static final java.lang.String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
		/**
		 * <span color='red'>年月日时分秒</span>的时间格式（无符号）
		 * <br/>
		 * 返回 "yyyyMMddHHmmss" 字符串
		 */
		public static final java.lang.String DATETIME_FORMAT = "yyyyMMddHHmmss";

		/**
		 * Instantiates a new Format.
		 *
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		private Format() {
		}

		/**
		 * @param mss 要转换的毫秒数
		 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
		 */
		public static java.lang.String formatDuring(long mss) {
			long days = mss / (1000 * 60 * 60 * 24);
			long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
			long seconds = (mss % (1000 * 60)) / 1000;
			return days + " days " + hours + " hours " + minutes + " minutes "
					+ seconds + " seconds ";
		}

	}

	/**
	 * 时间戳
	 *
	 * @author leiguoqing
	 * @date 2020 -07-25 19:32:12
	 */
	public static class Timestamp {

		/**
		 * Instantiates a new Timestamp.
		 *
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		private Timestamp() {
		}

		/**
		 * 获取秒级时间戳
		 *
		 * @return the long
		 * @author leiguoqing
		 * @date 2020 -07-25 19:31:59
		 */
		public static Long epochSecond() {
			return localDateTime().toEpochSecond(ZoneOffset.of("+8"));
		}

		/**
		 * 获取毫秒级时间戳
		 *
		 * @return the long
		 * @author leiguoqing
		 * @date 2020 -07-25 19:32:00
		 */
		public static Long epochMilli() {
			return localDateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		}
	}

	/**
	 * 时间间隔相关
	 *
	 * @author leiguoqing
	 * @date 2020 -07-25 20:17:18
	 */
	public static class Interval {
		/**
		 * Instantiates a new Interval.
		 *
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		private Interval() {
		}

		/**
		 * 获取 endDate-startDate 时间间隔天数
		 *
		 * @param start the start
		 * @param end   the end
		 * @return 时间间隔天数 long
		 * @author leiguoqing
		 * @date 2020 -07-25 20:16:48
		 */
		public static Long days(java.time.LocalDateTime start, java.time.LocalDateTime end) {
			return start.toLocalDate().toEpochDay() - end.toLocalDate().toEpochDay();
		}
	}

	/**
	 * 当前时间相关
	 *
	 * @author leiguoqing
	 * @date 2020 -07-25 19:45:00
	 */
	public static class Current {
		/**
		 * Instantiates a new Current.
		 *
		 * @author leiguoqing
		 * @date 2020 -07-25 21:15:29
		 */
		private Current() {
		}


		/**
		 * 获取当前年
		 *
		 * @return the integer
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:53
		 */
		public static Integer year() {
			return localDate().getYear();
		}

		/**
		 * 获取当前月
		 *
		 * @return the int
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:52
		 */
		public static int month() {
			return localDate().getMonthValue();
		}

		/**
		 * 获取当前年中的日
		 *
		 * @return the integer
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:50
		 */
		public static Integer dayOfYear() {
			return localDate().getDayOfYear();
		}

		/**
		 * 获取当前月中的日
		 *
		 * @return the integer
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:48
		 */
		public static Integer dayOfMonth() {
			return localDate().getDayOfMonth();
		}

		/**
		 * 获取当前星期中的日
		 *
		 * @return the integer
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:47
		 */
		public static Integer dayOfWeek() {
			return localDate().getDayOfWeek().getValue();
		}

		/**
		 * 获取当前小时
		 *
		 * @return the integer
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:45
		 */
		public static Integer hour() {
			return localTime().getHour();
		}

		/**
		 * 获取当前分钟
		 *
		 * @return the integer
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:44
		 */
		public static Integer minute() {
			return localTime().getMinute();
		}

		/**
		 * 获取当前秒
		 *
		 * @return the integer
		 * @author leiguoqing
		 * @date 2020 -07-25 20:34:43
		 */
		public static Integer second() {
			return localTime().getSecond();
		}
	}
}