package com.zeasn.union.db;

public abstract class TableFieldType{
    private String fieldName;
    private String typeName;
    private boolean nullAble;
    private boolean primaryKey;

    public TableFieldType(String fieldName,String typeName, boolean nullAble, boolean primaryKey) {
        this.fieldName = fieldName;
        this.typeName = typeName;
        this.nullAble = nullAble;
        this.primaryKey = primaryKey;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isNullAble() {
        return nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        if(!isNullAble()){
            sb.append(" not NULL");
        }
        return sb.toString();
    }

    protected void toString(StringBuilder sb){
        sb.append(String.format("%s %s",getFieldName(),getTypeName()));
    }
}
