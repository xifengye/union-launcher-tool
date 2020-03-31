package com.zeasn.union.db;

public class BooleanType extends TableFieldType {
    public BooleanType(String fieldName,boolean nullAble, boolean primaryKey) {
        super(fieldName,"BOOLEAN", nullAble, primaryKey);
    }

}
