package com.firefly.tools;

import java.sql.Timestamp;

public class DateUtil {

	private DateUtil(){
		
	}
	
	/**
	 * 返回时间字符串  格式 yyyy-MM-dd
	 * @param time
	 * @return
	 */
	public static String long2String(long time){
		Timestamp t = new Timestamp(time);
		return t.toString().substring(0,10);
	}
	
	/**
	 * 返回时间字符串 格式 yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String long2StringWithTime(long time){
		Timestamp t = new Timestamp(time);
		return t.toString().substring(0,19);
	}
	
	/**
	 * 返回时间字符串 格式 yyyy-MM-dd HH:mm
	 * @param time
	 * @return
	 */
	public static String long2StringWithTime1(long time){
		Timestamp t = new Timestamp(time);
		return t.toString().substring(0,16);
	}
	
	/**
	 * 把时间字符串转换成数值 格式：yyyy-MM-dd HH --> yyyy-MM-dd HH:00:00
	 * @param str
	 * @return
	 */
	public static long string2LongBegin1(String str){
		Timestamp t = Timestamp.valueOf(str+"00:00");
		return t.getTime();
	}
	
	/**
	 * 把时间字符串转换成数值 格式：yyyy-MM-dd HH --> yyyy-MM-dd HH:59:59
	 * @param str
	 * @return
	 */
	public static long string2LongEnd1(String str){
		Timestamp t = Timestamp.valueOf(str+"59:59");
		return t.getTime();
	}
	
	/**
	 * 把时间字符串转换成数值 格式：yyyy-MM-dd --> yyyy-MM-dd 00:00:00
	 * @param str
	 * @return
	 */
	public static long string2LongBegin(String str){
		Timestamp t = Timestamp.valueOf(str+" 00:00:00");
		return t.getTime();
	}
	
	/**
	 * 把时间字符串转换成数值 格式：yyyy-MM-dd --> yyyy-MM-dd 23:59:59
	 * @param str
	 * @return
	 */
	public static long string2LongEnd(String str){
		Timestamp t = Timestamp.valueOf(str+" 23:59:59");
		return t.getTime();
	}
	
	/**
	 * 把时间字符串转换成数值 格式：yyyy-MM-dd HH:mm --> yyyy-MM-dd HH:mm:00
	 * @param str
	 * @return
	 */
	public static long string2Long(String str){
		Timestamp t = Timestamp.valueOf(str+":00");
		return t.getTime();
	}
	
	/**�ո�
	 * html
	 * \n -- <br>
	 * �ո�“”'' -->&nbsp;
	 * @param str
	 * @return
	 */
	public static String transTextArea(String str){
		str = str.replaceAll("\n", "<br>");
		str = str.replaceAll(" ", "&nbsp;");
		return str;
	}
	
	/**
	 * 获得当前时间毫秒数
	 * @return
	 */
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}
}
