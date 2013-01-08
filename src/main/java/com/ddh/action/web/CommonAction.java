package com.ddh.action.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ddh.bean.Page;
import com.opensymphony.xwork2.ActionSupport;

public class CommonAction extends ActionSupport {
	private String basePath;
	protected Page page;
	protected String id;

	public String getBasePath() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+ (path.equals("/")?"":"/");
		return basePath;
	}

	/**
	 * json 返回
	 * @throws IOException 
	 */
	public void jsonReturn(String jsonStr) throws IOException {
		HttpServletResponse res = ServletActionContext.getResponse();
		PrintWriter out = res.getWriter();
		res.setContentType("text/json;charset=utf-8");
		out.write(jsonStr);
		out.close();
	}
	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
