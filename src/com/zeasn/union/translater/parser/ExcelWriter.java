package com.zeasn.union.translater.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.zeasn.union.translater.model.TranslateItem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelWriter {

	public void write(String fileName, List<TranslateItem> list) {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("StudentScore");

		// 创建Excel标题行，第一行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("姓名");
		headRow.createCell(1).setCellValue("分数");

		// 往Excel表中遍历写入数据
		for (TranslateItem studentScore : list) {
			createCell(studentScore, sheet);
		}

		File xlsFile = new File(fileName);
		try {
			// 或者以流的形式写入文件 workbook.write(new FileOutputStream(xlsFile));
			workbook.write(xlsFile);
		} catch (IOException e) {
			// TODO
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO
			}	
		}
	}

	// 创建Excel的一行数据。
	private void createCell(TranslateItem studentScore, HSSFSheet sheet) {
		HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
//		dataRow.createCell(0).setCellValue(studentScore.getName());
//		dataRow.createCell(1).setCellValue(studentScore.getScore());
	}

}
