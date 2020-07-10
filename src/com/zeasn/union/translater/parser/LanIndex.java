package com.zeasn.union.translater.parser;

import com.zeasn.union.translater.model.Lan;

public class LanIndex {
    int index;
    Lan lan;

    public LanIndex(int index, String language) {
        this.index = index;
        this.lan = Lan.get(language);
    }

}
