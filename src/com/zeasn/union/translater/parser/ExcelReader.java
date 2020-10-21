package com.zeasn.union.translater.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zeasn.union.translater.model.Lan;
import com.zeasn.union.translater.model.TranslateItem;
import com.zeasn.union.translater.model.Translater;
import javafx.scene.control.Label;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	public static final int START_COLUMN = 2;
	private Label mTranslationOutLabel;
	public ExcelReader(Label mTranslationOutLabel) {
		this.mTranslationOutLabel = mTranslationOutLabel;
	}

	public Translater read(String fileName) throws EncryptedDocumentException, IOException {
		mTranslationOutLabel.setText(fileName);
		if (fileName == null) return null;
		
		File xlsFile = new File(fileName);
		if (!xlsFile.exists()) return null;

		mTranslationOutLabel.setText("fileExist");
		// 工作表
		Workbook workbook = WorkbookFactory.create(xlsFile);
		// 表个数
		int numberOfSheets = workbook.getNumberOfSheets();
		mTranslationOutLabel.setText("numberOfSheets"+numberOfSheets);

//		System.out.println(numberOfSheets);
		if (numberOfSheets <= 0) return null;
		
		List<TranslateItem> list = new ArrayList<>();
		//我们的需求只需要处理一个表，因此不需要遍历
		Sheet sheet = workbook.getSheetAt(0);
		// 行数
		int rowNumbers = sheet.getLastRowNum() + 1;
//		System.out.println(rowNumbers);
		TranslateItem translateItem;
		// 读数据，第二行开始读取
		Map<Integer, Lan> lanMap = parseLanguage(sheet.getRow(0));
		for (int row = 1; row < rowNumbers; row++) {
			Row r = sheet.getRow(row);
			translateItem = new TranslateItem(r,lanMap);
			if(translateItem.getKey()!=null && !translateItem.getKey().isEmpty()) {
				list.add(translateItem);
			}
		}
		return new Translater(lanMap.values(),list);
	}

	private Map<Integer, Lan> parseLanguage(Row row){
		Map<Integer, Lan> map = new HashMap<>();
		int lastCellNum = row.getLastCellNum();
		for(int column=START_COLUMN+1;column<lastCellNum;column++){
			String header = row.getCell(column).getStringCellValue();
			int leftIndex = header.indexOf("（");
			String replaceHeader = header;
			if(leftIndex==-1){
				leftIndex = header.indexOf("(");
			}
			if(leftIndex>=0) {
				replaceHeader = header.substring(0, leftIndex).replace(" ", "").replace("-","_");
			}
			Lan lan = Lan.get(replaceHeader);
			if(lan!=null) {
				map.put(column, lan);
			}else{
				System.out.println(replaceHeader+"未找到");
			}
		}
		return map;
	}
}
