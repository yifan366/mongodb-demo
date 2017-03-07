package com.zhongtai.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongtai.dao.IScreenshotDao;
import com.zhongtai.modle.Screenshot;
import com.zhongtai.modle.UserGroup;
import com.zhongtai.service.IScreenshotService;
import com.zhongtai.service.IUserServie;

@Service("screenshotService")
public class ScreenshotServiceImpl implements IScreenshotService{

	@Autowired
	IScreenshotDao screenshotDao;
	
	@Autowired
	IUserServie userServie;
	
	
	
	@Override
	public boolean addFile(Screenshot screenshot) {
		// TODO Auto-generated method stub
		Map<String, Object> document = new HashMap<String, Object>();
		document.put("userid", screenshot.getUserid());
		document.put("uid", UUID.randomUUID().toString().replaceAll("-", ""));
		
		document.put("fileid", screenshot.getFileid()!=null?screenshot.getFileid():"");
		document.put("createTime", screenshot.getCreateTime()!=null?screenshot.getCreateTime():"");
		
		document.put("filename", screenshot.getFilename()!=null?screenshot.getFilename():"");
		
		document.put("cameraUid", screenshot.getCameraUid()!=null?screenshot.getCameraUid():"");
		document.put("groupid", screenshot.getGroupid()!=null?screenshot.getGroupid():null);
		
		return screenshotDao.insertFile(screenshot.getFile(), document);
	}

	@Override
	public List<Screenshot> list(Integer userid,String cameraUid) {
		// TODO Auto-generated method stub
		Map<String, Object> document = new HashMap<String, Object>();
		List<Screenshot> reList = new ArrayList<>();
		
		try {
			if (!cameraUid.equals("")&&cameraUid!=null) {
				document.put("cameraUid", cameraUid);
			}
		} catch (Exception e) {
		}
		
		List<UserGroup> groups = userServie.listGroup(userid);
		
		if (groups.size()>0) {
			document.put("groupid", groups.get(0).getGroupid());
		}else{
			document.put("userid", userid);
		}
		
		List<Map<String, Object>> listmap = screenshotDao.queryFiles(document);
		
		if (listmap!=null) {
			for (Map<String, Object> map : listmap) {
				Screenshot ss = new Screenshot();
				
				ss.setUid(map.get("uid").toString());
				ss.setUserid((Integer)map.get("userid"));
				
				ss.setCreateTime(map.get("createTime")!=null?map.get("createTime").toString():"");
				ss.setFileid(map.get("fileid")!=null?map.get("fileid").toString():"");
				
				ss.setFilename(map.get("filename")!=null?map.get("filename").toString():"");
				
				ss.setCameraUid(map.get("cameraUid")!=null?map.get("cameraUid").toString():"");
				
				ss.setLength((long)map.get("length"));
				
				reList.add(ss);
			}
		}
		
		Collections.sort(reList, new Screenshot());
		
		
		
		return reList;
	}

	@Override
	public Screenshot load(String uid) {
		// TODO Auto-generated method stub
		Map<String, Object> document = new HashMap<String, Object>();
		document.put("uid", uid);
		
		List<Map<String, Object>> listmap = screenshotDao.queryFiles(document);
		Screenshot ss = null;
		if (listmap!=null) {
			ss = new Screenshot();
			
			ss.setUid(listmap.get(0).get("uid").toString());
			ss.setUserid((Integer)listmap.get(0).get("userid"));
			ss.setFile((InputStream)listmap.get(0).get("$file"));
		} 
		
		return ss;
	}

	@Override
	public boolean isUpload(String fileid,Integer groupid) {
		
		Map<String, Object> document = new HashMap<String, Object>();
		List<Screenshot> reList = new ArrayList<>();
		
		document.put("fileid", fileid);
		document.put("groupid", groupid);
		
		List<Map<String, Object>> listmap = screenshotDao.queryFiles(document);
		
		if (listmap!=null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(String uid) {
		// TODO Auto-generated method stub
		
		Map<String, Object> document = new HashMap<String, Object>();
		
		document.put("uid", uid);
		
		return screenshotDao.deleteFile(document);
	}

}
