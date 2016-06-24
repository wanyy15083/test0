package com.test.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.entity.Sku;
import com.test.entity.SkuPicture;
import com.test.mapper.TestExcelMapper;
import com.test.service.TestExcelService;

@Service("testExcelService")
public class TestExcelServiceImpl implements TestExcelService {

	private static final Logger logger = Logger.getLogger(TestExcelServiceImpl.class);

	@Autowired
	private TestExcelMapper testExcelMapper;

	@Override
	public List<Sku> selectSkuList() {
		return testExcelMapper.selectSkuList();
	}

	@Override
	public List<SkuPicture> selectSkuPictureList() {
		return testExcelMapper.selectSkuPictureList();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void excelImport(List<SkuPicture> list) {
		for (SkuPicture skuPicture : list) {
			testExcelMapper.insertSkuPicture(skuPicture);
		}
	}

	@Override
	public int selectSkuPictureOne(SkuPicture skuPicture) {
		return testExcelMapper.selectSkuPictureOne(skuPicture);
	}

}
