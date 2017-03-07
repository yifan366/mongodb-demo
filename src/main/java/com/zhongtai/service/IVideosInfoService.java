package com.zhongtai.service;

import java.util.List;
import java.util.Map;

import com.zhongtai.modle.VideosInfo;

public interface IVideosInfoService {

	/**
	 * 根据文件uid获取文件
	 */
	public VideosInfo load(String uid);
	
	/**
	 * 根据userId查询改用户的所有文件数量
	 */
	public long countFiles(Integer userId);
	
	/**
	 * 添加文件
	 */
	public boolean addFiles(VideosInfo fileInfo);
	
	
	/**
	 * 删除文件
	 */
	public boolean deleteFile(String uid);
	
	/**
	 * 根据fileid查询文件列表
	 */
	public List<VideosInfo> listByFileid(String fileid,Integer groupid);
	
	/**
	 * 查询某段文件是否已经上传
	 */
	public boolean getPartFilesIsUpload(String fileid,int partId,Integer groupid);
	
	/**
	 * 查询该文件是否已经上传完成
	 */
	public boolean getFilesIsUpload(String fileid);
	
	
	/**
	 * 根据用户id查询该用户上传所有视频列表
	 */
	public List<VideosInfo> listByUserId(int userid);
	
	/**
	 * 根据用户id查询所有可看视频列表
	 */
	public List<VideosInfo> listAllByUserId(int userid);
	
	/**
	 * 根据fileid查询某个视频总长度
	 */
	public long getLength(String fileid,Integer groupid);
	
	/**
	 * 更改某个视频是否上传状态
	 */
	//public void updateFileIsuplad(String fileid);
	
	
}
