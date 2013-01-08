package com.ddh.bean;

import java.util.List;

public class Page<T> {
	private boolean hasPrevPage;// 是否存在上一页

	private boolean hasNextPage;// 是否存在下一页

	private int pageSize;// 每页容量

	private int totalPage;// 总页数

	private int totalRow;// 总记录数

	private int curPage;// 当前页

	private int beginIndex;// 当前页开始索引号

	private String url;// 该页URL地址

	private String param;// 参数以&分隔为组合URL使用

	private String pageToolBar;// 分页工具条

	private Condition condition;// 条件由Condition构造,为DAO查询使用

	private List result;// 当前页结果集(记录集)

	/**
	 * 功能:初始化数据(查询前)
	 * 
	 * @param pageSize
	 * @param url
	 * @param param
	 */
	public Page() {

	}

	public Page(int pageSize, String url, String param) {
		this.pageSize = pageSize;
		this.url = (null == url) ? "" : url;
		this.param = (null == param) ? "" : param;
	}

	/**
	 * 功能:初始化数据(查询后)
	 * 
	 * @param hasPrevPage
	 * @param hasNextPage
	 * @param pageSize
	 * @param totalPage
	 * @param totalRow
	 * @param curPage
	 * @param beginIndex
	 * @param pageToolBar
	 */
	public Page(boolean hasPrevPage, boolean hasNextPage, int pageSize,
			int totalPage, int totalRow, int curPage, int beginIndex,
			String pageToolBar, Condition condition) {
		this.hasPrevPage = hasPrevPage;
		this.hasNextPage = hasNextPage;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRow = totalRow;
		this.curPage = curPage;
		this.beginIndex = beginIndex;
		this.pageToolBar = pageToolBar;
		this.condition=condition;

	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPrevPage() {
		return hasPrevPage;
	}

	public void setHasPrevPage(boolean hasPrevPage) {
		this.hasPrevPage = hasPrevPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRow() {
		return totalRow;
	}

	/**
	 * 设置总记录数
	 * 计算总页数
	 * 如果当前页大于总页数,则把当前页设为最后一页
	 * @param totalRow
	 */
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
		
		if (curPage < 1) {
			curPage = 1;
		}
		totalPage = totalRow / pageSize;
		if (totalRow % pageSize > 0) {
			totalPage++;
		}
		if (curPage > totalPage) {
			curPage = totalPage;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageToolBar() {
		return pageToolBar;
	}

	public void setPageToolBar(String pageToolBar) {
		this.pageToolBar = pageToolBar;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	//当前记录
	public int getStart() {
		return (this.curPage-1) * this.pageSize;
	}
	//终止记录
	public int getEnd() {
		return this.curPage * this.pageSize - 1;
	}

}
