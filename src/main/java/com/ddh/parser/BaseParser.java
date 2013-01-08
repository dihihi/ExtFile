/**
 * BaseParser.java 
 * 对象转换
 */
package com.ddh.parser;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 数据转化基类
 * @version V1.0<br>
 */
@SuppressWarnings("unchecked")
public class BaseParser {
	/**
	 * 创建jsonConfig对象
	 * @param excludes
	 * @return
	 */
	public static JsonConfig createJsonConfig(String ... excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		List<String> excludeList = new ArrayList<String>();
		excludeList.add("handler");
		excludeList.add("hibernateLazyInitializer");
		if (excludes!=null && excludes.length>0) {
			for (String excludeStr : excludes) {
				excludeList.add(excludeStr);
			}
		}
		excludes = new String[excludeList.size()];
		int i=0;
		for (String str : excludeList) {
			excludes[i++] = str;
		}
		jsonConfig.setExcludes(excludes); 
		jsonConfig.registerJsonValueProcessor(Float.class, new FloatJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Double.class, new DoubleJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
		return jsonConfig;
	}
	
	/**
	 * 将数据转化成层级的树形结构json数据
	 * @param list 需要转化的数据
	 * @param out respone的输出流<br>
	 *//*
	public static void parserListToLevleTree(List list,PrintWriter out){
		Tree tree = new Tree(list,1);
		String jsonStirng = tree.toJsonTree();
		out.print(jsonStirng);
	}
	
	*//**
	 * 将数据转化成层级的树形结构json数据
	 * @param list 需要转化的数据
	 * @param out respone的输出流<br>
	 *//*
	public static void parserListToCheckLevleTree(List list,PrintWriter out){
		Tree tree = new Tree(list,2);
		String jsonStirng = tree.toJsonTree();
		out.print(jsonStirng);
	}
	
	*//**
	 * 将数据转化成层级的树形结构json数据
	 * @param list 需要转化的数据
	 * @param out respone的输出流<br>
	 *//*
	public static void parserOrgListToCheckLevleTree(List list,PrintWriter out){
		OrgTree tree = new OrgTree(list);
		String jsonStirng = tree.toJsonTree();
		out.print(jsonStirng);
	}
	
	*//**
	 * 将数据转化成简单的树形结构json数据
	 * @param list 需要转化的数据
	 * @param out respone的输出流<br>
	 *//*
	public static void parserListToSimpleTree(List list,PrintWriter out){
		Tree tree = new Tree(list,0);
		String jsonStirng = tree.toJsonTree();
		out.print(jsonStirng);
	}*/
	
	/**
	 * 将数组转化为JSON字符串
	 * @param list 需要转化的数字
	 * @param out
	 * @param out excludes 要排除的属性<br>
	 */
	public static void parserArrayToJson(Object obj,PrintWriter out, String ... excludes){
		JsonConfig jsonConfig = createJsonConfig(excludes);
		JSONArray jsonArray = JSONArray.fromObject(obj, jsonConfig);
		out.print(jsonArray.toString());
	}
	
	
	/**
	 * 将分页数组转化为JSON字符串
	 * @param list 需要转化的数字
	 * @param out<br>
	 *//*
	public static void parserPageListToJson(QueryResult qr,PrintWriter out){
		String retrunStr = "";
		List<Object> list = qr.getResultlist();
		
		JsonConfig cfg = createJsonConfig();
		
		JSONArray jsonArray = JSONArray.fromObject(list, cfg);
		retrunStr = "{start:"+qr.getStartResult()+",limit:"+qr.getPageSize()+",totalProperty:"+qr.getTotalrecord()+",data:"+jsonArray.toString()+"}";
		out.print(retrunStr);
	}*/
	
	/**
	 * 将对象转化为JSON字符串
	 * @param o 需要转化的对象
	 * @param out
	 * @param out excludes 要排除的属性<br>
	 */
	public static void parserObjectToJson(Object o,PrintWriter out, String ... excludes){
		JsonConfig jsonConfig = createJsonConfig(excludes);
		JSONObject jsonObject = JSONObject.fromObject(o, jsonConfig);
		out.print("["+jsonObject.toString()+"]");
	}
	
	/**
	 * 将json的字符串转化为对象
	 * @param json json字符串
	 * @param clazz 目的对象
	 * @return 对象<br>
	 */
	public static Object parserJsonToObject(String json,Class clazz){
		JSONObject jsonObject = JSONObject.fromObject(json);
		Object o =JSONObject.toBean(jsonObject, clazz);
		return o;
	}
	
	/**
	 * 将Map转化为json字符串
	 * @param map 
	 * @param out<br>
	 */
	public static void parserMapToJson(Map map,PrintWriter out){
		JSONObject json = JSONObject.fromObject(map);  
		out.print(json.toString());
		
	}
	
	/**
	 * 将Map转化为json字符串
	 * @param map 
	 * @param out<br>
	 */
	public static void parserEntAndListToJson(Map map,PrintWriter out){
		Object obj = map.get("entity");
		List<Object> list = (List<Object>) map.get("list");
		String retrunStr = ""; 
		JSONObject jsonObject = JSONObject.fromObject(obj);
		JSONArray jsonArray = JSONArray.fromObject(list);
		retrunStr="{root:"+jsonObject.toString()+",data:"+jsonArray.toString()+"}";
		out.print(retrunStr);
		
	}
	
	/**
	 * 将实现特定接口的list转化为下拉数据
	 * @param list
	 * @param out<br>
	 */
	/*public static void parserListToCombData(List list,PrintWriter out){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		Iterator ite = list.iterator();
		while(ite.hasNext()){
			IComboBox combo = (IComboBox) ite.next();
			Map<String,Object> map = combo.getComboMap();
			sb.append("['"+map.get("value")+"',"+"'"+map.get("text").toString()+"'],");
		}
		String data = sb.toString();
		data = data.substring(0,data.length()-1)+"]";
		out.print(data);
	}
	public static void parserObjectToCombData(Object obj,PrintWriter out){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		IComboBox combo = (IComboBox) obj;
		Map<String,Object> map = combo.getComboMap();
		sb.append("['"+map.get("value")+"',"+"'"+map.get("text").toString()+"'],");
		String data = sb.toString();
		data = data.substring(0,data.length()-1)+"]";
		out.print(data);
	}*/
	
	/**
	 * 将实现特定接口的map转化为列表
	 * @param list
	 * @param out<br>
	 */
	/*public static void parserListToSchedulesData(List<Object> list ,PrintWriter out){
		StringBuffer sb = new StringBuffer();
		Iterator ite = list.iterator();
		while(ite.hasNext()){
			ISchedulesDescribeDTO dto = (ISchedulesDescribeDTO) ite.next();
			Map<String,Object> schedulesmap = dto.getDescribeMap();
			System.out.println("待办事项详细数据项描述:"+schedulesmap.get("describe"));
			sb.append(JSONObject.fromObject(schedulesmap).toString()+",");
		}
		String data = sb.toString();
		data = "["+data.substring(0,data.length()-1)+"]";
		System.out.println("待办事项详细数据项:"+data);
		out.print(data);
	}*/

}