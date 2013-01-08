package com.ddh.util;

import com.ddh.bean.Condition;
import com.ddh.bean.Page;


public class PageUtil {

	private final static int DEF_PAGE_SIZE = 10;// 默认当前页的容量

	/**
	 * 功能:传入查询前初始化的page实例,创建一个新的Page实例
	 * 
	 * @param page
	 * @param totalRow
	 * @return
	 */
	public static Page createPage(Page page, int totalRow) {
		return createPage(page.getCondition(), page.getUrl(), page.getParam(),
				page.getPageSize(), page.getCurPage(), totalRow);
	}

	public static Page createPage(Condition condition, String url,
			String param, int pageSize, int curPage, int totalRow) {
		pageSize = getpageSize(pageSize);
		curPage = getcurPage(curPage);
		int beginIndex = getBeginIndex(pageSize, curPage);
		int totalPage = getTotalPage(pageSize, totalRow);
		boolean hasNextPage = hasNextPage(curPage, totalPage);
		boolean hasPrePage = hasPrePage(curPage);
		String pageToolBar = getPageToolBar(url, param, hasPrePage,
				hasNextPage, pageSize, totalPage, totalRow, curPage);
		Page page = new Page(hasPrePage, hasNextPage, pageSize, totalPage, totalRow,
				curPage, beginIndex, pageToolBar, condition);
		page.setParam(param);
		return page;
	}

	private static String getPageToolBar(String url, String param,
			boolean hasPrePage, boolean hasNextPage, int pageSize,
			int totalPage, int totalRow, int curPage) {
		StringBuffer strBuf = new StringBuffer();

		boolean isHaveParam = false;

		if (null != param && !"".equals(param)) {
			isHaveParam = true;
		}

		strBuf.append("当前第" + curPage + "页/共" + totalPage + "页&nbsp;&nbsp;总记录"
				+ totalRow + "条");
		if (hasPrePage) {
			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page=1"
					+ ((isHaveParam) ? "&amp;" + param : "") + "\">首页</a>");
			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page="
					+ (curPage - 1) + ((isHaveParam) ? "&amp;" + param : "")
					+ "\">上一页</a>");
		} else {
			strBuf.append("&nbsp;&nbsp;首页&nbsp;&nbsp;上一页");
		}
		if (hasNextPage) {
			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page="
					+ (curPage + 1) + ((isHaveParam) ? "&amp;" + param : "")
					+ "\">下一页</a>");
			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page=" + totalPage
					+ ((isHaveParam) ? "&amp;" + param : "") + "\">尾页</a>");
		} else {
			strBuf.append("&nbsp;&nbsp;下一页&nbsp;&nbsp;尾页");
		}
		strBuf.append("&nbsp;&nbsp;<input id=\"pageINPUT\" type=\"text\" value=\""+curPage+"\"   size=\"4\" maxlength=\"4\" onKeyPress=\"enter();\" onMouseOver=\"this.focus();this.select();\" style=\"width:40;height:20;text-align:center\" />");
		strBuf.append("&nbsp;&nbsp;<a href=\"javascript:gotoPage();\">GO</a>");
		
		strBuf.append("<script>");
		strBuf.append("function enter(){");
		strBuf.append("if(event.keyCode==13)gotoPage();");
		strBuf.append("}");
		strBuf.append("function gotoPage(){");
		strBuf.append("var p=document.getElementById('pageINPUT').value;");
		strBuf.append("var patrn=/^\\d+$/;");
		strBuf.append("if(!patrn.test(p))return;");
		strBuf.append("p=parseInt(p);");
		strBuf.append("p=p<1?1:p;");
		strBuf.append("p=p>"+totalPage+"?"+totalPage+":p;");
		//strBuf.append("alert('" + url + "?page='+p+'" +((isHaveParam) ? "&" + param : "") +"');");
		strBuf.append("location.href='" + url + "?page='+p+'" +((isHaveParam) ? "&" + param : "") +"';");
		strBuf.append("}");
		strBuf.append("</script>");
		if(totalPage <2){
			return "";
		}
		return strBuf.toString();
	}
//	private static String getPageToolBar(String url, String param,
//			boolean hasPrePage, boolean hasNextPage, int pageSize,
//			int totalPage, int totalRow, int curPage) {
//		StringBuffer strBuf = new StringBuffer();
//		
//		boolean isHaveParam = false;
//		
//		if (null != param && !"".equals(param)) {
//			isHaveParam = true;
//		}
//		
//		strBuf.append("当前第" + curPage + "页/共" + totalPage + "页&nbsp;&nbsp;总记录"
//				+ totalRow + "条");
//		if (hasPrePage) {
//			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page=1"
//					+ ((isHaveParam) ? "&amp;" + param : "") + "\">首页</a>");
//			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page="
//					+ (curPage - 1) + ((isHaveParam) ? "&amp;" + param : "")
//					+ "\">上一页</a>");
//		} else {
//			strBuf.append("&nbsp;&nbsp;首页&nbsp;&nbsp;上一页");
//		}
//		if (hasNextPage) {
//			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page="
//					+ (curPage + 1) + ((isHaveParam) ? "&amp;" + param : "")
//					+ "\">下一页</a>");
//			strBuf.append("&nbsp;&nbsp;<a href=\"" + url + "?page=" + totalPage
//					+ ((isHaveParam) ? "&amp;" + param : "") + "\">尾页</a>");
//		} else {
//			strBuf.append("&nbsp;&nbsp;下一页&nbsp;&nbsp;尾页");
//		}
//		strBuf.append("&nbsp;&nbsp;<input id=\"pageINPUT\" type=\"text\" value=\""+curPage+"\"   size=\"4\" maxlength=\"4\" onKeyPress=\"enter();\" onMouseOver=\"this.focus();this.select();\" style=\"width:40;height:20;text-align:center\" />");
//		strBuf.append("&nbsp;&nbsp;<a href=\"javascript:gotoPage();\">GO</a>");
//		
//		strBuf.append("<script>");
//		strBuf.append("function enter(){");
//		strBuf.append("if(event.keyCode==13)gotoPage();");
//		strBuf.append("}");
//		strBuf.append("function gotoPage(){");
//		strBuf.append("var p=document.getElementById('pageINPUT').value;");
//		strBuf.append("var patrn=/^\\d+$/;");
//		strBuf.append("if(!patrn.test(p))return;");
//		strBuf.append("p=parseInt(p);");
//		strBuf.append("p=p<1?1:p;");
//		strBuf.append("p=p>"+totalPage+"?"+totalPage+":p;");
//		//strBuf.append("alert('" + url + "?page='+p+'" +((isHaveParam) ? "&" + param : "") +"');");
//		strBuf.append("location.href='" + url + "?page='+p+'" +((isHaveParam) ? "&" + param : "") +"';");
//		strBuf.append("}");
//		strBuf.append("</script>");
//		return strBuf.toString();
//	}

	private static int getpageSize(int pageSize) {
		return pageSize == 0 ? DEF_PAGE_SIZE : pageSize;
	}

	private static int getcurPage(int curPage) {
		return curPage == 0 ? 1 : curPage;
	}

	private static int getBeginIndex(int pageSize, int curPage) {
		return (curPage - 1) * pageSize;
	}

	private static int getTotalPage(int pageSize, int totalRow) {
		int totalPage = 0;
		if (totalRow % pageSize == 0)
			totalPage = totalRow / pageSize;
		else
			totalPage = totalRow / pageSize + 1;
		return totalPage;
	}

	private static boolean hasPrePage(int curPage) {
		return curPage == 1 ? false : true;
	}

	private static boolean hasNextPage(int curPage, int totalPage) {
		return curPage == totalPage || totalPage == 0 ? false : true;
	}

}