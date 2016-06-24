package com.test.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.util.Base64;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.test.entity.SkuPicture;
import com.test.service.TestExcelService;
import com.test.service.impl.PropertiesService;
import com.test.utils.DateUtils;

@Controller
@RequestMapping("testExcel")
public class TestExcelController {

	@Autowired
	private TestExcelService testExcelService;
	@Autowired
	private PropertiesService propertiesService;

	@RequestMapping("import")
	@ResponseBody
	public String testExcelImport(@RequestParam("file") MultipartFile fileImport, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		response.setContentType("UTF-8");
		String originalFilename = fileImport.getOriginalFilename();
		String[] split = originalFilename.split("\\.");
		String fileName = split[0] + "导入结果" + DateUtils.formatDateTime(new Date(), DateUtils.FORMAT_FOUR) + ".xslx";
		// File file = new File("E:\\images\\"+originalFilename);
		// fileImport.transferTo(file);
		ImportParams params = new ImportParams();

		params.setVerifyHanlder(new IExcelVerifyHandler<SkuPicture>() {

			@Override
			public ExcelVerifyHanlderResult verifyHandler(SkuPicture skuPicture) {
				StringBuffer sb = new StringBuffer();
				int count = testExcelService.selectSkuPictureOne(skuPicture);
				if (count > 0) {
					sb.append("数据已存在！");
				}
				return new ExcelVerifyHanlderResult(false, sb.toString());
			}

		});

		params.setNeedVerfiy(true);
		try {
			ExcelImportResult<SkuPicture> importResult = ExcelImportUtil.importExcelVerify(fileImport.getInputStream(), SkuPicture.class, params);

			String agent = (String) request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("Firefox") != -1) {
				// 火狐浏览器特殊处理
				fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
			if (importResult.isVerfiyFail()) {
				FileOutputStream fos = new FileOutputStream(propertiesService.FILE_PATH + File.separator + fileName);
				importResult.getWorkbook().write(fos);
				fos.close();
				
			}
			// List<SkuPicture> list = importResult.getList();
			// if (list != null && list.size() > 0) {
			// testExcelService.excelImport(list);
			// } else {
			// return "导入数据为空";
			// }
			return "导入成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "导入异常";
		}
	}

	@RequestMapping("export")
	@ResponseBody
	public String testExcelExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("UTF-8");
		ExportParams params = new ExportParams("SKU图片", null, ExcelType.XSSF);
		List<SkuPicture> list = testExcelService.selectSkuPictureList();
		Workbook workbook = ExcelExportUtil.exportExcel(params, SkuPicture.class, list);
		String fileName = "SKU图片数据.xlsx";
		try {
			String agent = (String) request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("Firefox") != -1) {
				// 火狐浏览器特殊处理
				fileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
			} else {
				fileName = URLEncoder.encode(fileName, "UTF8");
			}
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";target=_blank");
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("download")
	@ResponseBody
	public String testExcelDownload(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("UTF-8");
		String fileName = "SKU图片导入模板.xlsx";
		String fileName1 = null;
		try {
			String agent = (String) request.getHeader("USER-AGENT");
			if (agent != null && agent.indexOf("Firefox") != -1) {
				// 火狐浏览器特殊处理
				fileName1 = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
			} else {
				fileName1 = URLEncoder.encode(fileName, "UTF8");
			}
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName1 + "\";target=_blank");
			IOUtils.copy(TestExcelController.class.getClassLoader().getResourceAsStream("template/" + fileName), response.getOutputStream());
			// OutputStream os = response.getOutputStream();
			// os.write(FileUtils.readFileToByteArray(file));
			// os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
