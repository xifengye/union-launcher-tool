package com.zeasn.union.db;

public class StringType extends TableFieldType {
    private int length;
    public StringType(String fieldName, boolean nullAble, boolean primaryKey) {
        super(fieldName,"VARCHAR", nullAble, primaryKey);
        this.length = 50;
    }

    public StringType(String fieldName,boolean nullAble, boolean primaryKey, int length) {
        super(fieldName,"VARCHAR", nullAble, primaryKey);
        this.length = length;
    }

    @Override
    protected void toString(StringBuilder sb) {
        sb.append(String.format("%s %s(%d)",getFieldName(),getTypeName(),length));
    }
}
