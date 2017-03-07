package com.zhongtai.modle;

import java.io.InputStream;

public class PreviewImage {

	private String fileid;
	
	private InputStream image ; //文件流

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
	
	
}
