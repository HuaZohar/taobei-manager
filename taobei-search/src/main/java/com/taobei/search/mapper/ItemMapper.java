package com.taobei.search.mapper;

import java.util.List;

import com.taobei.search.pojo.SearchItem;

public interface ItemMapper {
	List<SearchItem> getItemList() throws Exception;
}
