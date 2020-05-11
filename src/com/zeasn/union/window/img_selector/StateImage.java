package com.zeasn.union.window.img_selector;

public abstract class StateImage {
    public abstract String getCreateString();

    private String fileName;

    public StateImage(String fileName) {
        this.fileName = fileName;
    }

    public String create() {
        return String.format(getCreateString(), fileName);
    }
}
