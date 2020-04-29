package com.zeasn.union.widget;


import javafx.event.EventHandler;
import javafx.scene.control.TextField;



public class IntegerTextField extends TextField {
    public IntegerTextField() {
        setTextFormatter(new IntegerTextFormatter());
    }
}
