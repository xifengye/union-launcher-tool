package com.zeasn.union.translater.model;

public enum Lan {
    zh_rCN("中文"), en("英文"), ru_rRU("俄文"), es_rES("西班牙文"),
    pt_rBR("葡萄牙文"), th("泰文"), am_rET("埃塞俄比亚阿姆哈拉文"),
    ar_rEG("埃及阿拉伯文"), ar_rIL("以色列阿拉伯文"), fr_rFR("法国法文"),
    hi_rIN("印地文"), in_rID("印尼文"), ms_rMY("马来西亚文"),
    my_rMM("缅甸文"), sw_rTZ("斯瓦西里文"), vi_rVN("越南文)"),
    zh_rHK("繁体中文"), zh_rTW("繁体中文");
    String value;

    Lan(String value) {
        this.value = value;
    }

    public static Lan get(String language) {
        for (Lan lan : Lan.values()) {
            if (lan.name().equalsIgnoreCase(language)) {
                return lan;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
    }
