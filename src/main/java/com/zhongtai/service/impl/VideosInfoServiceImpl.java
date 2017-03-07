package com.zhongtai.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongtai.dao.IVideoDao;
import com.zhongtai.modle.ComparatorFileInfo;
import com.zhongtai.modle.UserGroup;
import com.zhongtai.modle.VideosInfo;
import com.zhongtai.service.IUserServie;
import com.zhongtai.service.IVideosInfoService;

@Service("videosInfoService")
public class VideosInfoServiceImpl implements IVideosInfoService {

	@Autowired
	IVideoDao videodao;
	
	@Autowired
	IUserServie userServie;


	@Override
	public VideosInfo load(String uid) {
		VideosInfo fileInfo = null;
		
		Map<String, Object> document = new HashMap<String, Object>();
		document.put("uid", uid);
		List<Map<String, Object>> result = videodao.queryFiles(document);

		if (result.size() > 0) {

			uid = (String) result.get(0).get("uid");
			String filename = (String) result.get(0).get("filename");
			String aliases = (String) result.get(0).get("aliases");
			Integer userId = (Integer) result.get(0).get("userId");
			InputStream file = (InputStream) result.get(0).get("$file");
			fileInfo = new VideosInfo(uid, filename, aliases, userId, file);
		}

		return fileInfo;
	}

	@Override
	public boolean addFiles(VideosInfo fileInfo) {

		Map<String, Object> document = new HashMap<String, Object>();
		
		document.put("uid", fileInfo.getUid());
		document.put("filename", fileInfo.getFilename());
		document.put("aliases", fileInfo.getAliases());
		document.put("userId", fileInfo.getUserId());

		document.put("fileid", fileInfo.getFileid());
		document.put("cameraUid", fileInfo.getCameraUid());
		document.put("currentPart", fileInfo.getCurrentPart());
		document.put("countPart", fileInfo.getCountPart());

		document.put("createTime", fileInfo.getCreateTime());
		document.put("durationtime", fileInfo.getDurationtime());
		
		document.put("groupid", fileInfo.getGroupId());
		
		document.put("isShare", fileInfo.isShare());
		
		Boolean result = videodao.insertFile(fileInfo.getFile(), document);

		return result;
	}


	@Override
	public long countFiles(Integer userId) {

		Map<String, Object> document = new HashMap<String, Object>();
		document.put("userId", userId);

		return videodao.countFile(document);
	}

	@Override
	public boolean deleteFile(String uid) {

		Map<String, Object> document = new HashMap<String, Object>();
		document.put("uid", uid);

		return videodao.deleteFile(document);
	}

	@Override
	public List<VideosInfo> listByFileid(String fileid,Integer groupid) {

		Map<String, Object> document = new HashMap<String, Object>();
		document.put("fileid", fileid);
		document.put("groupid", groupid);

		List<Map<String, Object>> listmap = videodao.queryFiles(document);

		List<VideosInfo> listfile = new ArrayList<VideosInfo>();

		if (listmap!=null) {
			for (Map<String, Object> map : listmap) {

				VideosInfo fileInfo = new VideosInfo();
				fileInfo.setUid(map.get("uid").toString());
				fileInfo.setAliases(map.get("aliases")!=null?map.get("aliases").toString():"");
				fileInfo.setFilename(map.get("filename")!=null?map.get("filename").toString():"");
				fileInfo.setUserId((Integer) map.get("userId"));
				fileInfo.setFile((InputStream) map.get("$file"));
				fileInfo.setCurrentPart((Integer) map.get("currentPart"));
				fileInfo.setFileid(map.get("fileid")!=null?map.get("fileid").toString():"");
				
				fileInfo.setGroupId(map.get("groupid")!=null?(Integer)map.get("groupid"):null);
				
				fileInfo.setCreateTime(map.get("createTime")!=null?map.get("createTime").toString():"");
				fileInfo.setDurationtime(map.get("durationtime")!=null?map.get("durationtime").toString():"");
				
				fileInfo.setLength((long)map.get("length"));

				listfile.add(fileInfo);
			}

			Comparator<VideosInfo> cmp = new ComparatorFileInfo();
			Collections.sort(listfile, cmp);
		}
		
		return listfile;
	}

	@Override
	public boolean getPartFilesIsUpload(String fileid, int partId,Integer groupid) {

		Map<String, Object> document = new HashMap<String, Object>();
		document.put("fileid", fileid);
		document.put("currentPart", partId);
		document.put("groupid", groupid);

		List<Map<String, Object>> listmap  = videodao.queryFiles(document);

		if (listmap !=null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean getFilesIsUpload(String fileid) {
		
		Map<String, Object> document = new HashMap<String, Object>();
		document.put("fileid", fileid);

		List<Map<String, Object>> list = videodao.queryFiles(document);
		if (list.size() > 0) {
			int dbcount = (int) list.get(0).get("countPart");
			int count = list.size();
			if (dbcount == count) {
				return true;
			}
		}

		return false;
	}


	@Override
	public List<VideosInfo> listByUserId(int userid) {
		
		Map<String, Object> document = new HashMap<String, Object>();
		
		document.put("userId", userid);
		document.put("isShare", true);
		
		List<Map<String, Object>> list = videodao.queryFiles(document);

		Map<String, VideosInfo> resultmap = new HashMap<String, VideosInfo>();
		
		if (list != null) {
			for (Map<String, Object> m : list) {
				String fileid = (String) m.get("fileid");
				String fileName = (String) m.get("filename");
				

				VideosInfo fileInfo = new VideosInfo();
				fileInfo.setUserId(userid);
				fileInfo.setFileid(fileid);
				fileInfo.setFilename(fileName);
				fileInfo.setCreateTime(m.get("createTime")!=null?m.get("createTime").toString():"");
				fileInfo.setDurationtime(m.get("durationtime")!=null?m.get("durationtime").toString():"");

				resultmap.put(fileid, fileInfo);
			}

			List<VideosInfo> resultList = new ArrayList<VideosInfo>();

			for (Map.Entry<String, VideosInfo> entry : resultmap.entrySet()) {
				resultList.add(entry.getValue());
			}
			return resultList;
		}

		return null;
	}

	@Override
	public List<VideosInfo> listAllByUserId(int userid) {
		
		Integer groupid = userServie.getGroupid(userid);
		
		if (groupid!=null) {
			Map<String, Object> document = new HashMap<String, Object>();
			
			document.put("groupid", groupid);
			
			List<Map<String, Object>> list = videodao.queryFiles(document);

			Map<String, VideosInfo> resultmap = new HashMap<String, VideosInfo>();
			
			if (list != null) {
				for (Map<String, Object> m : list) {
					String fileid = (String) m.get("fileid");
					String fileName = (String) m.get("filename");

					VideosInfo fileInfo = new VideosInfo();
					fileInfo.setUserId(m.get("userId")!=null?(Integer)m.get("userId"):null);
					fileInfo.setFileid(fileid);
					fileInfo.setFilename(fileName);
					fileInfo.setCreateTime(m.get("createTime")!=null?m.get("createTime").toString():"");
					fileInfo.setDurationtime(m.get("durationtime")!=null?m.get("durationtime").toString():"");

					fileInfo.setCameraUid(m.get("cameraUid")!=null?m.get("cameraUid").toString():"");
					
					fileInfo.setLength(this.getLength(fileid,groupid));
					
					resultmap.put(fileid, fileInfo);
				}

				List<VideosInfo> resultList = new ArrayList<VideosInfo>();

				for (Map.Entry<String, VideosInfo> entry : resultmap.entrySet()) {
					
					if (this.getFilesIsUpload(entry.getValue().getFileid())) {resultList.add(entry.getValue());}
				}
				
				Collections.sort(resultList,new VideosInfo());
				
				return resultList;
			}
		}
		
		return null;
		
	}
	
	/**
	 * 根据fileid查询某个视频总长度
	 */
	public long getLength(String fileid,Integer groupid){
		
		long length = 0;
		
		List<VideosInfo> list = this.listByFileid(fileid,groupid);
		
		for (VideosInfo vf : list ) {
			length = length+vf.getLength();
		}
		
		return length;
		
	}
	/*
	*//**
	 * 更改某个视频是否上传状态
	 *//*
	public void updateFileIsuplad(String fileid){
		
		Map<String, Object> document = new HashMap<String, Object>();
		
		document.put("isUpload", true);
		
		Map<String, Object> whereClause = new HashMap<String, Object>();
		
		whereClause.put("fileid", fileid);
		
		videodao.update(document,whereClause);
		
		
	}*/

	
}
