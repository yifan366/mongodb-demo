package com.zhongtai.modle;

import java.io.InputStream;
import java.util.Comparator;

/**
 * 视频截图实体类
 * @author zhangfan
 *
 */
public class Screenshot implements Comparator{

	private String uid;
	
	private InputStream file ; //文件流
	
	private Integer userid; //所属用户
	
	private String fileid; // 截图的唯一id
	
	private String createTime; // 截图生成时间
	
	private String filename; //文件名称
	
	private String cameraUid; //摄像机uid
	
	private Integer groupid;
	
	private long length; //截屏文件长度
	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getCameraUid() {
		return cameraUid;
	}

	public void setCameraUid(String cameraUid) {
		this.cameraUid = cameraUid;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		Screenshot sdto1= (Screenshot )o1;

		Screenshot sdto2= (Screenshot )o2;

       return sdto2.getCreateTime().compareTo(sdto1.getCreateTime());
	}
	
	
}
