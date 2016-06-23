package com.test.mapper;

import java.util.List;

import com.test.entity.Sku;
import com.test.entity.SkuPicture;

public interface TestExcelMapper {

	public List<Sku> selectSkuList();

	public List<SkuPicture> selectSkuPictureList();

	public void insertSkuPicture(SkuPicture skuPicture);
	
	public int selectSkuPictureOne(SkuPicture skuPicture);
}
