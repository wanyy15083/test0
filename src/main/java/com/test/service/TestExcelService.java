package com.test.service;

import java.util.List;

import com.test.entity.Sku;
import com.test.entity.SkuPicture;

public interface TestExcelService {

	public List<Sku> selectSkuList();

	public List<SkuPicture> selectSkuPictureList();

	public void excelImport(List<SkuPicture> list);

	public int selectSkuPictureOne(SkuPicture skuPicture);
}
