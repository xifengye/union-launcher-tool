package com.zeasn.union.translater.model;

public class LanTranslate {
    private Lan lan;
    private String translate;

    public LanTranslate(Lan lan, String translate) {
        this.lan = lan;
        this.translate = (translate==null?"":translate.trim()).replace(" ","");
    }

    public Lan getLan() {
        return lan;
    }

    public String getTranslate() {
        return translate;
    }

    @Override
    public String toString() {
        return "[" +lan + "-" + translate +"]";
    }
}
