package com.zeasn.union.translater.model;

public class StringKeyMap {
    public String key;
    public String targetKey;

    public StringKeyMap(String key, String targetKey) {
        this.key = key;
        this.targetKey = targetKey;
    }

    @Override
    public String toString() {
        return "{" +
                "key='" + key + '\'' +
                ", targetKey='" + targetKey + '\'' +
                '}';
    }
}
