package com.zhongtai.modle;

public class IsUploadModel {

	private String fileid;
	
	private Integer state;

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public IsUploadModel(String fileid, Integer state) {
		super();
		this.fileid = fileid;
		this.state = state;
	}
	
	
}
