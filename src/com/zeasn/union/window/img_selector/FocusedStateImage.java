package com.zeasn.union.window.img_selector;

public class FocusedStateImage extends StateImage {
    public FocusedStateImage(String filePath) {
        super(filePath);
    }

    @Override
    public String getCreateString() {
        return "<item android:drawable=\"@mipmap/%s\" android:state_focused=\"true\"/>";
    }

}
