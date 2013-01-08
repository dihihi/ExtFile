package com.ddh.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Ip {
	/**
	 * http://www.yodao.com/smartresult-xml/search.s?type=id&q=身份证号码
	 * http://www.yodao.com/smartresult-xml/search.s?type=ip&q=221.123.123.123
	 * http://www.yodao.com/smartresult-xml/search.s?type=mobile&q=13564370000
	 * @param ip
	 * @return
	 */
	public static String getIpAddress(String ip){
		try {
			URL url = new URL("http://www.youdao.com/smartresult-xml/search.s?type=ip&q=" + ip);
			InputStream s = url.openStream();
			InputStreamReader ss = new InputStreamReader(s,"gbk");
			BufferedReader sss= new BufferedReader(ss);
			String temp="";
			StringBuffer ipaddress=new StringBuffer();
			while((temp=sss.readLine()) != null){
				ipaddress.append(temp);
			}
			Pattern p = Pattern.compile("<location>.*</location>");
			Matcher m = p.matcher(ipaddress.toString());
			m.find();
			temp = m.group();
			if(temp==null)temp="";
			temp = temp.replaceAll("<location>", "");
			temp = temp.replaceAll("</location>", "");
			return temp;
		} catch (Exception e) {
			return "";
		}
	}
}
