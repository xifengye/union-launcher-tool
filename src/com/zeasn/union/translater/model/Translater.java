package com.zeasn.union.translater.model;

import java.util.Collection;
import java.util.List;

public class Translater {
    private Collection<Lan> lanList;
    private List<TranslateItem> translateItemList;

    public Translater(Collection<Lan> lanList, List<TranslateItem> translateItemList) {
        this.lanList = lanList;
        this.translateItemList = translateItemList;
    }

    public Collection<Lan> getLanList() {
        return lanList;
    }

    public void setLanList(List<Lan> lanList) {
        this.lanList = lanList;
    }

    public List<TranslateItem> getTranslateItemList() {
        return translateItemList;
    }

    public void setTranslateItemList(List<TranslateItem> translateItemList) {
        this.translateItemList = translateItemList;
    }
}
