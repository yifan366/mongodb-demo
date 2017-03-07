package com.zhongtai.service;

import com.zhongtai.modle.PreviewImage;

public interface IPreviewImageService {

	public boolean addFile(PreviewImage previewImage);
	
	public PreviewImage load(String fileid);
}
