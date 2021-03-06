package com.zeasn.union.data;

public class TextStyle {
    public enum TypeFace{
        Normal,Bold,Italic;
    }
    private String name;
    private TypeFace typeFace;
    private int textSize;
    private String textColor;
    private int letterSpacing;
    private int lineSpacing;

    public TextStyle(String name, TypeFace typeFace, int textSize, String textColor, int letterSpacing, int lineSpacing) {
        this.name = name;
        this.typeFace = typeFace;
        this.textSize = textSize;
        this.textColor = parseColor(textColor);
        this.letterSpacing = letterSpacing;
        this.lineSpacing = lineSpacing;
    }

    private String parseColor(String textColor) {
        if(textColor!=null){
            if(!textColor.startsWith("#")) {
                if (textColor.length() == 1) {
                    return "#"+textColor + textColor + textColor+textColor + textColor + textColor;
                }
                if (textColor.length() == 2) {
                    return "#"+textColor + textColor + textColor;
                }
                if (textColor.length() == 3) {
                    return "#"+textColor + textColor;
                }
                if (textColor.length() == 6) {
                    return "#"+textColor;
                }
            }
        }
        return textColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeFace getTypeFace() {
        return typeFace;
    }

    public void setTypeFace(TypeFace typeFace) {
        this.typeFace = typeFace;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getLetterSpacing() {
        return letterSpacing;
    }

    public void setLetterSpacing(int letterSpacing) {
        this.letterSpacing = letterSpacing;
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    @Override
    public String toString() {
        return String.format("%s [%d,%s,%s]",name,textSize,textColor,typeFace.name());
//        return String.format("%s 字号：%d\t 颜色：%s\t %s",name,textSize,textColor,typeFace.name());
    }
}
