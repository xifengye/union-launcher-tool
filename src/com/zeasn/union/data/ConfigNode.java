package com.zeasn.union.data;

import java.util.List;

public class ConfigNode {
    private String name;
    private String icon;
    List<ConfigNode> children;

    public ConfigNode(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public ConfigNode(String name) {
        this.name = name;
    }

    public List<ConfigNode> getChildren() {
        return children;
    }

    public void setChildren(List<ConfigNode> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
