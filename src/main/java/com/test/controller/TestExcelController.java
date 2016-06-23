package com.test.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.util.Base64;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.entity.SkuPicture;
import com.test.service.TestExcelService;

@Controller
@RequestMapping("testExcel")
public class TestExcelController {

	@Autowired
	private TestExcelService testExcelService;

	@RequestMapping("import")
	@ResponseBody
	public String testExcelImport(@RequestParam("file") MultipartFile fileImport, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		String originalFilename = fileImport.getOriginalFilename();
		String fileName = originalFilename + "导入结果.xslx";
		// File file = new File("E:\\images\\"+originalFilename);
		// fileImport.transferTo(file);
		ImportParams params = new ImportParams();
		params.setVerifyHanlder(new ExcelVerifyHandlerImpl());
		params.setNeedVerfiy(true);
		OutputStream outputStream = null;
		BufferedOutputStream bufo = null;
		BufferedInputStream bufi = null;
		try {
			ExcelImportResult<SkuPicture> importResult = ExcelImportUtil.importExcelVerify(fileImport.getInputStream(), SkuPicture.class, params);
			outputStream = new FileOutputStream(new File("E:\\images\\" + fileName));
			importResult.getWorkbook().write(outputStream);
			String agent = (String) request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("Firefox") != -1) {
				// 火狐浏览器特殊处理
				fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";target=_blank");
			bufo = new BufferedOutputStream(response.getOutputStream());
			bufi = new BufferedInputStream(new FileInputStream(new File("E:\\images\\" + fileName)));
			IOUtils.copy(bufi, bufo);
			List<SkuPicture> list = importResult.getList();
			testExcelService.excelImport(list);
			return "导入成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "导入异常";
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	@RequestMapping("download")
	@ResponseBody
	public String testExcelDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedOutputStream outputStream = null;
		BufferedInputStream inputStream = null;
		String fileName = "SKU图片模板.xls";
		try {
			String agent = (String) request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("Firefox") != -1) {
				// 火狐浏览器特殊处理
				fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";target=_blank");
			outputStream = new BufferedOutputStream(response.getOutputStream());
			inputStream = new BufferedInputStream(TestExcelController.class.getClassLoader().getResourceAsStream("template/" + fileName + ".xslx"));
			IOUtils.copy(inputStream, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
		return null;
	}
}
