package com.zeasn.union.data;

import java.io.File;

public class DataMgr {
	private static DataMgr mInstance = new DataMgr();
	private String mProjectDir;
	private String mProjectName;
	
    private String mTemplateProjectFilePath;


	
	public static DataMgr getInstance() {
		return mInstance;
	}

	private DataMgr() {
		
	}
	
	public String getProjectDir() {
		return mProjectDir;
	}
	

	public String getProjectName() {
		return mProjectName;
	}
	
	

	public void setProjectDir(String projectDir) {
		this.mProjectDir = projectDir;
	}
	

	public void setProjectName(String projectName) {
		this.mProjectName = projectName;
	}
	
    public String getmTemplateProjectFilePath() {
        return mTemplateProjectFilePath;
    }

    public void setmTemplateProjectFilePath(String mTemplateProjectFilePath) {
        this.mTemplateProjectFilePath = mTemplateProjectFilePath;
    }

	public String getProjectFilePath() {
		return mProjectDir+File.separator+mProjectName;
	}
}
