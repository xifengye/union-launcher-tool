package com.zeasn.union.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LauncherProject {
    private String mRootDir;
    private String mName;
    private String mTemplateDir;
    private List<ConfigNode> children;

    public String getProjectDir() {
        return mRootDir;
    }


    public String getName() {
        return mName;
    }



    public void setRootDir(String projectDir) {
        this.mRootDir = projectDir;
    }


    public void setName(String projectName) {
        this.mName = projectName;
    }

    public String getTemplateDir() {
        return mTemplateDir;
    }

    public void setTemplateDir(String mTemplateDir) {
        this.mTemplateDir = mTemplateDir;
    }

    public String getProjectFilePath() {
        return mRootDir + File.separator+ mName;
    }

    public void initTreeNodes(){
        children = new ArrayList<>();
        ConfigNode styleConfig  = new ConfigNode("主题","");
        ConfigNode gobalConfig  = new ConfigNode("全局","");
        ConfigNode dialogConfig = new ConfigNode("对话框","");
        ConfigNode homeConfig   = new ConfigNode("首页","");
        ConfigNode detailConfig = new ConfigNode("详情","");
        ConfigNode searchConfig = new ConfigNode("搜索","");
        ConfigNode sortingPlayListConfig = new ConfigNode("分类、播单","");
        children.add(styleConfig );
        children.add(gobalConfig );
        children.add(dialogConfig);
        children.add(homeConfig  );
        children.add(detailConfig);
        children.add(searchConfig);
        children.add(sortingPlayListConfig);
    }

    public List<ConfigNode> getChildrenConfigNode() {
        return children;
    }
}
