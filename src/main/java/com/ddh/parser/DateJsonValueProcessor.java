package com.ddh.parser;

import java.text.SimpleDateFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {

	public Object processArrayValue(Object obj, JsonConfig jsonConfig) {
		return process(obj);
	}

	public Object processObjectValue(String str, Object obj, JsonConfig jsonConfig) {
		return process(obj);
	}
	
	private Object process(Object obj) {
		if (obj == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(obj);
	}
}


