package com.zeasn.union.db;

public class FloatType extends TableFieldType {
    public FloatType(String fieldName,boolean nullAble, boolean primaryKey) {
        super(fieldName,"FLOAT", nullAble, primaryKey);
    }

}
