package com.zeasn.union.widget;


import javafx.scene.control.TextField;


public class ColorTextField extends TextField {
    public ColorTextField() {
        setTextFormatter(new ColorTextFormater());
    }
}
