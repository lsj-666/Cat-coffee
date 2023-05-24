package com.example.petparent.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeqGenerateUtil {
	
	/**
	 * 主键生成工具类 毫秒数+6位随机数
	 * @return
	 */
	public static Long seqGenerate(){
		//获取当前时间毫秒数
		long mTime = System.currentTimeMillis();
		//随机生成6位随机数
		long radomNum = (int)((Math.random()*9+1)*100000);
		long seq = mTime*1000000+radomNum;
		return seq;
	}
	
	/**
	 * 日志表主键生成
	 * @return
	 */
	public static Long logSeqGenerate(){
		Date date = new Date(System.currentTimeMillis());
	    SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
	    String sd = format.format(date);
	    long seq = (Long.parseLong(sd))*10000+ (int)((Math.random()*9+1)*100000);
		return seq;
	}

	/**
	 * 生成12位的时间戳需求编号
	 * @return
	 */
	public static String generateDemandNumb() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis())) + (((int) (Math.random() * 9000 + 1000)) + "");
	}
	
	public static void main(String[] args) {
		System.out.println(generateDemandNumb());
	}

}
