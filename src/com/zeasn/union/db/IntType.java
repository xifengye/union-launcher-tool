package com.zeasn.union.db;

public class IntType extends TableFieldType {
    public IntType(String fieldName,boolean nullAble, boolean primaryKey) {
        super(fieldName,"INTEGER", nullAble, primaryKey);
    }

}
