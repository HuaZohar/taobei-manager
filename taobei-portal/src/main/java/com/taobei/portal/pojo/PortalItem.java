package com.taobei.portal.pojo;

import com.taobei.pojo.TbItem;

public class PortalItem extends TbItem {
	public String[] getImages(){
		String image = this.getImage();
		if(image!=null && !"".equals(image)){
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
