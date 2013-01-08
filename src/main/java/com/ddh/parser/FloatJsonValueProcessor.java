package com.ddh.parser;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class FloatJsonValueProcessor implements JsonValueProcessor {

	public Object processArrayValue(Object obj, JsonConfig jsonConfig) {
		return process(obj);
	}

	public Object processObjectValue(String str, Object obj, JsonConfig jsonConfig) {
		return process(obj);
	}
	
	private Object process(Object obj) {
		if (obj == null) {
			return "0";
		}
		return obj.toString();
	}
}


