package com.zeasn.union.translater.model;

public class StringValue {
    public String key;
    public String value;

    public StringValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
