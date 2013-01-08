package com.ddh.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;

public class ContextUtil {
	
	private static final Log log = LogFactory.getLog(ContextUtil.class);
	
	private String pageName="page";
	
	public void setPageName(String pageName){
		this.pageName=pageName;
	}
	public String getPageName(){
		return pageName;
	}
	
	/**
	 * @author QZY
	 * @param params
	 * @说明 返回查询条件
	 */
	private String getQuery(Map<String, Object> params){
		String query="";
		
		if(params==null)
			return query;
		
		Iterator<String> iter=params.keySet().iterator();
		while(iter.hasNext()){
			String key=iter.next();
		
			// 不对 PAGENAME 做参数拼接
			if(!key.equals(getPageName())){
				String value=ParamUtil.getArray(params, key);
				if(null!=value && !"".equals(value)){
					try {
						query+="&"+key+"="+java.net.URLEncoder.encode(value,"UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if(query.length()>1)
			query=query.substring(1);
		
		return query;
	}
	
	public static String getQueryString(Map<String, Object> params){
		ContextUtil util=new ContextUtil();
		return util.getQuery(params);
	}
	/**
	 * 
	 * @param str 原来的HTML格式的文本
	 * @param length 如果是NULL 则取全部，获取长度
	 * @return
	 */
	public static String parseHtml(String str,Integer length){
		if(str != null || !"".equals(str)){
			StringBuffer temp = new StringBuffer();
			Parser parser;
			NodeIterator nodes;
			parser = Parser.createParser(str, "GBK");
			try {
				nodes = parser.elements();
				while (nodes.hasMoreNodes()) {
					Node textnode = (Node) nodes.nextNode();
					temp.append(textnode.toPlainTextString());
				}
			} catch (Exception e) {
			}
			String temp_string = temp.toString();
			if(length == null || length<0){
				return temp_string;
			}
			if(temp_string != null && !"".equals(temp_string) && temp_string.length()>length){
				temp_string = temp_string.substring(0,length);
			}
			return temp_string;
		}else{
			return null;
		}
	}
	/**
	 * 生成按照日期编码
	 * @return
	 */
	public static String builderTradeNo(){
		Date date=new Date();
		Calendar  c = java.util.Calendar.getInstance(); 		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

		c.setTime(date);
		String mill=new Integer(c.get(Calendar.MILLISECOND)).toString();
		StringBuffer buffer=new StringBuffer(mill);
		
		for(int i=0;i<(3-mill.length());i++){
			buffer.insert(0, new StringBuffer("0"));
		}
		
		if(mill.equals("0"))
			buffer.append("0");
		buffer.insert(0, format.format(date));
		return buffer.toString();
	}
	/**
	 * 发送邮件模板
	 * @param filename
	 * @param items
	 * @return
	 */
	public static String getTemplateMessage(String filename, List items) {
		try{
			InputStream in=new FileInputStream(filename);
			InputStreamReader r = new InputStreamReader(in,"UTF-8");
			BufferedReader rr = new BufferedReader(r);
			String inputLine;
			StringBuffer str=new StringBuffer();
			while((inputLine = rr.readLine()) != null){
				str.append(inputLine);
				str.append("\n");
			}
			
			
			return formatText(items,str.toString());
		}catch(Exception e){
			log.error(e);
		}
		return null;
	}
	/**
	 * 格式化发送邮件模板
	 * @param items
	 * @param text
	 * @return
	 */
	public static String formatText(List items, String text){
		String key="";
		for(int i=0;i<items.size();i++){
			key="{"+i+"}";
			text=text.replace(key, (String)items.get(i));			
		}
		return text;
	}
	
	public static void main(String[] args) {
		System.out.println(builderTradeNo());
	}
}
