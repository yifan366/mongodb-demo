package com.zhongtai.modle;

import java.io.InputStream;
import java.util.Comparator;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 文件对象
 * 
 * @author zhangfan
 *
 */
@JsonIgnoreProperties(value = { "uid" })
public class VideosInfo implements Comparator{

	private String uid = UUID.randomUUID().toString().replaceAll("-", ""); // 用uuid生成

	private String filename; // 文件名称

	private String aliases; // 文件别名

	private Integer userId; // 上传者id

	private InputStream file; // 文件流

	private String fileid; // 文件id

	private String cameraUid; // 摄像机uid

	private Integer currentPart; // 当前段数

	private int countPart; // 总段数

	private boolean isShare; // 是否分享

	private String createTime; // 视频生成时间

	private String durationtime; // 视频总时长

	private Integer groupId; // 组id
	
	private long length; //文件长度
	
	//private boolean isUpload; //是否上传完成

	@JsonIgnore
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getCameraUid() {
		return cameraUid;
	}

	public void setCameraUid(String cameraUid) {
		this.cameraUid = cameraUid;
	}

	public Integer getCurrentPart() {
		return currentPart;
	}

	public void setCurrentPart(Integer currentPart) {
		this.currentPart = currentPart;
	}

	public int getCountPart() {
		return countPart;
	}

	public void setCountPart(int countPart) {
		this.countPart = countPart;
	}

	public boolean isShare() {
		return isShare;
	}

	public void setShare(boolean isShare) {
		this.isShare = isShare;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDurationtime() {
		return durationtime;
	}

	public void setDurationtime(String durationtime) {
		this.durationtime = durationtime;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	
	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}
	


	public VideosInfo(String uid, String filename, String aliases, Integer userId, InputStream file) {
		super();
		this.uid = uid;
		this.filename = filename;
		this.aliases = aliases;
		this.userId = userId;
		this.file = file;
	}

	public VideosInfo() {
		super();
	}


	@Override
	public String toString() {
		return "VideosInfo [uid=" + uid + ", filename=" + filename + ", aliases=" + aliases + ", userId=" + userId
				+ ", file=" + file + ", fileid=" + fileid + ", cameraUid=" + cameraUid + ", currentPart=" + currentPart
				+ ", countPart=" + countPart + ", isShare=" + isShare + ", createTime=" + createTime + ", durationtime="
				+ durationtime + ", groupId=" + groupId + ", length=" + length + "]";
	}

	@Override
    public int compare(Object o1, Object o2)
   {

		VideosInfo sdto1= (VideosInfo )o1;

		VideosInfo sdto2= (VideosInfo )o2;

       return sdto2.getCreateTime().compareTo(sdto1.getCreateTime());

   }


}
