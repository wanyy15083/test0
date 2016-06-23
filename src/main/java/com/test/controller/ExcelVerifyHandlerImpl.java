package com.test.controller;

import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.entity.SkuPicture;
import com.test.mapper.TestExcelMapper;

public class ExcelVerifyHandlerImpl implements IExcelVerifyHandler<SkuPicture> {

	@Autowired
	private TestExcelMapper testExcelMapper;

	@Override
	public ExcelVerifyHanlderResult verifyHandler(SkuPicture skuPicture) {
		StringBuffer sb = new StringBuffer();
		int count = testExcelMapper.selectSkuPictureOne(skuPicture);
		if (count > 0) {
			sb.append("数据已存在！");
		}
		return new ExcelVerifyHanlderResult(false, sb.toString());
	}

}
