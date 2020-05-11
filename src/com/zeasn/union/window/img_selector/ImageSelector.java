package com.zeasn.union.window.img_selector;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageSelector {
    String name;
    List<StateImage> stateImageList = new ArrayList<>();

    public ImageSelector(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStateImage(StateImage stateImage){
        stateImageList.add(stateImage);
    }

    public void create(String parentFilePath) {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">\n");

        for(StateImage stateImage:stateImageList){
            sb.append("\t\t").append(stateImage.create()).append("\n");
        }
        sb.append("</selector>");
        try {
            FileUtils.write(new File(parentFilePath+File.separator+getName()+".xml"),sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
