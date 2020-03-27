package com.zeasn.union.data;

public class DataMgr {
	private static DataMgr mInstance = new DataMgr();
	private LauncherProject mLauncherProject;
	
	public static DataMgr getInstance() {
		return mInstance;
	}

	private DataMgr() {
		
	}

	public LauncherProject getProject() {
		return mLauncherProject;
	}

	public void setLauncherProject(LauncherProject launcherProject) {
		this.mLauncherProject = launcherProject;
	}
}
