package com.zeasn.union.translater.model;

public enum Lan {

    Zh("中文"),EN("英文"),RU("俄文");
    String value;
    Lan(String value){
        this.value = value;
    }

    public static Lan get(String language) {
        for(Lan lan:Lan.values()){
            if(lan.name().equalsIgnoreCase(language)){
                return lan;
            }
        }
        return EN;
    }

    public String getValue() {
        return value;
    }
}
