package com.zeasn.union.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LauncherProject extends ConfigNode{
    private String mRootDir;
    private String mTemplateDir;
    private List<ConfigNode> children;

    public LauncherProject(String name) {
        super(name);
    }

    public LauncherProject() {
        super("");
    }

    public String getProjectDir() {
        return mRootDir;
    }






    public void setRootDir(String projectDir) {
        this.mRootDir = projectDir;
    }



    public String getTemplateDir() {
        return mTemplateDir;
    }

    public void setTemplateDir(String mTemplateDir) {
        this.mTemplateDir = mTemplateDir;
    }

    public String getProjectFilePath() {
        return mRootDir + File.separator+ getName();
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
