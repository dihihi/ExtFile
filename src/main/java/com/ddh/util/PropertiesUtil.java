package com.ddh.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertiesUtil {
	private static Log log=LogFactory.getLog(PropertiesUtil.class);
	private static Hashtable hash = new Hashtable();
	private final static String FILENAME = "companyweb.properties";

	private PropertiesUtil() {

	}

	/**
	 * 得到文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	private static Properties getProperties(String fileName) {
		InputStream input = null;
		Properties prop = null;
		try {
			prop = (Properties) hash.get(fileName);
			if (prop == null) {

				try {
					input = new FileInputStream(fileName);// 全路径
				} catch (Exception ex) {
					if (fileName.startsWith("/")) {
						input = PropertiesUtil.class
								.getResourceAsStream(fileName);// 只有/+文件名字
					} else {
						input = PropertiesUtil.class.getResourceAsStream("/"
								+ fileName);// 只有文件名字的
					}
				}
				prop = new Properties();
				prop.load(input);
				if (input != null) {
					input.close();
				}
			}
			hash.put(fileName, prop);
		} catch (Exception e) {
			log.error("读取配置文件："+fileName+"错误",e);
			return null;
		}
		return prop;
	}

	/**
	 * 配置文件中取值
	 * @param key 键值
	 * @param defaultValue 没有值时默认值
	 * @param filename 文件名
	 * @return
	 */
	public static String getProperty(String key,String defaultValue,String filename) {
		try {
			if(filename == null || "".equals(filename)){
				filename = FILENAME;
			}
			Properties prop = PropertiesUtil.getProperties(filename);
			if (null == prop)
				return null;
			return prop.getProperty(key, defaultValue);
		} catch (Exception ex) {
			log.error(ex);
			return null;
		}
	}
}
