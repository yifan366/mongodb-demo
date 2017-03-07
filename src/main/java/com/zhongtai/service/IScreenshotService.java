package com.zhongtai.service;

import java.util.List;

import com.zhongtai.modle.Screenshot;

public interface IScreenshotService {

	public boolean addFile(Screenshot screenshot);

	public List<Screenshot> list(Integer userid,String cameraUid);
	
	public Screenshot load(String uid);
	
	public boolean isUpload(String fileid,Integer groupid);
	
	public boolean delete(String uid);
	
}
