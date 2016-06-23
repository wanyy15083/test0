package com.test.entity;

import java.io.Serializable;

public class SkuGmzxPicture implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;// 主键

	private Integer skuId;// skuId

	private Integer inx;// 图片序号

	private String url;// 图片地址

	private Integer location;// 图片位置，1 商品主图，2 商品详情介绍图，3 电子说明书，4 保修卡

	private String lastUpdate;// 维护时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public Integer getInx() {
		return inx;
	}

	public void setInx(Integer inx) {
		this.inx = inx;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLocation() {
		return location;
	}

	public void setLocation(Integer location) {
		this.location = location;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
