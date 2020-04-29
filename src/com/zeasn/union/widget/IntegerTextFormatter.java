package com.zeasn.union.widget;

import com.zeasn.union.utils.Log;
import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

public class IntegerTextFormatter extends TextFormatter<Integer> {

    public IntegerTextFormatter() {
        super((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            Log.d("newText=%s",newText);
            if(newText==null || newText.length()==0){
                return change;
            }
            try{
                Integer.valueOf(newText);
                return change;
            }catch (Exception e) {
                return null;
            }
        });
    }


}
