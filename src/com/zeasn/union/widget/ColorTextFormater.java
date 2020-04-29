package com.zeasn.union.widget;

import com.zeasn.union.utils.Log;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;

import javax.management.relation.Relation;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorTextFormater extends TextFormatter<Color> {
    public ColorTextFormater() {
        super((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText == null || newText.length() == 0) {
                return change;
            }

            if (newText.length() < 6) {
                String regEx = String.format("^([0-9a-fA-F]{%d})+$",newText.length());
                // 编译正则表达式
                Pattern pattern = Pattern.compile(regEx);
                // 忽略大小写的写法
                // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(newText);
                // 字符串是否与正则表达式相匹配
                if (matcher.matches()) {
                    return change;
                }else{
                    return null;
                }
            }else if(newText.length()==6){
                try {
                    Color.web("#" + newText);
                    return change;
                } catch (Exception e) {
                    return null;
                }
            }else{
                return null;
            }
        });
    }
}
