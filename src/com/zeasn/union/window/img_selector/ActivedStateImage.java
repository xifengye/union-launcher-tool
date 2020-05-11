package com.zeasn.union.window.img_selector;

public class ActivedStateImage extends StateImage {
    public ActivedStateImage(String filePath) {
        super(filePath);
    }

    @Override
    public String getCreateString() {
        return "<item android:drawable=\"@mipmap/%s\" android:state_activated=\"true\"/>";
    }

}
