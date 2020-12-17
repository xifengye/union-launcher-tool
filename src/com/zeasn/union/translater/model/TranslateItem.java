package com.zeasn.union.translater.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.Map;

import static com.zeasn.union.translater.parser.ExcelReader.START_COLUMN;

public class TranslateItem {
    private String key;
    private Map<String, LanTranslate> lanTranslateMap = new HashMap<>();

    public TranslateItem(Row row, Map<Integer, Lan> lanMap) {
        Cell keyCell = row.getCell(START_COLUMN);
        if(keyCell!=null) {
            String keyValue = keyCell.getStringCellValue();
            key = (keyValue==null?"":keyValue.trim());
            System.out.println("key="+keyValue);
            int lastColumn = row.getLastCellNum();
            for (int column = START_COLUMN+1; column < lastColumn; column++) {
                if (lanMap.containsKey(column)) {
                    Lan lan = lanMap.get(column);
                    Cell cell = row.getCell(column);
                    if (cell != null) {
                        String translate = cell.getStringCellValue();
                        if (translate != null && !translate.isEmpty()) {
                            lanTranslateMap.put(lan.name(), new LanTranslate(lan, translate));
                        }
                    }
                }
            }
        }
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "TranslateItem{" +
                "key='" + key + '\'' +
                ", lanTranslateMap=" + translateToString() +
                '}';
    }

    private String translateToString(){
        StringBuilder sb = new StringBuilder();
        for(LanTranslate lanTranslate:lanTranslateMap.values()){
            sb.append(lanTranslate.toString()).append(",");
        }
        return sb.toString();
    }

    public String getTranslateByLan(String lan){
        if(lanTranslateMap.containsKey(lan)){
            return lanTranslateMap.get(lan).getTranslate();
        }
        return "";
    }
}
