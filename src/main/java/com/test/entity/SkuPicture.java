package com.test.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.jeecgframework.poi.excel.annotation.Excel;

public class SkuPicture implements Serializable {

	private static final long serialVersionUID = 1L;

	@Excel(name = "主键")
	@NotNull
	private Integer id;// 主键
	@Excel(name = "sku编码")
	@NotNull
	private Integer skuId;// skuId
	@Excel(name = "图片序号")
	@NotNull
	private Integer inx;// 图片序号
	@Excel(name = "图片地址")
	@NotBlank
	private String url;// 图片地址
	@Excel(name = "图片位置")
	@NotNull
	private Integer location;// 图片位置，1 商品主图，2 商品详情介绍图，3 电子说明书，4 保修卡
	@Excel(name = "维护时间")
	@NotBlank
	private String lastUpdate;// 维护时间

	public SkuPicture() {
	}

	public SkuPicture(Integer id, Integer skuId, Integer inx, String url, Integer location, String lastUpdate) {
		super();
		this.id = id;
		this.skuId = skuId;
		this.inx = inx;
		this.url = url;
		this.location = location;
		this.lastUpdate = lastUpdate;
	}

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

	@Override
	public String toString() {
		return "SkuPicture [id=" + id + ", skuId=" + skuId + ", inx=" + inx + ", url=" + url + ", location=" + location + ", lastUpdate=" + lastUpdate + "]";
	}

}
