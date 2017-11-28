package com.taobei.search.pojo;

import java.util.List;

public class SearchResult {
	private List<SearchItem> searchItems;
	private Long recordCount;
	private int pageCount;
	private int CurPage;
	public List<SearchItem> getSearchItems() {
		return searchItems;
	}
	public void setSearchItems(List<SearchItem> searchItems) {
		this.searchItems = searchItems;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPage() {
		return CurPage;
	}
	public void setCurPage(int curPage) {
		CurPage = curPage;
	}
	
}
