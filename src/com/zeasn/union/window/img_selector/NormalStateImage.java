package com.zeasn.union.window.img_selector;

public class NormalStateImage extends StateImage {
    public NormalStateImage(String filePath) {
        super(filePath);
    }

    @Override
    public String getCreateString() {
        return "<item android:drawable=\"@mipmap/%s\"/>";
    }

}
