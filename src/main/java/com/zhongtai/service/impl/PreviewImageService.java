package com.zhongtai.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongtai.dao.IPreviewImageDao;
import com.zhongtai.modle.PreviewImage;
import com.zhongtai.modle.VideosInfo;
import com.zhongtai.service.IPreviewImageService;

@Service("previewImageService")
public class PreviewImageService implements IPreviewImageService {

	@Autowired
	IPreviewImageDao previewImageDao;
	
	
	@Override
	public boolean addFile(PreviewImage previewImage) {
		// TODO Auto-generated method stub
		Map<String, Object> document = new HashMap<String, Object>();
		document.put("fileid", previewImage.getFileid());
		
		return previewImageDao.insertFile(previewImage.getImage(), document);
	}

	@Override
	public PreviewImage load(String fileid) {
		Map<String, Object> document = new HashMap<String, Object>();
		document.put("fileid", fileid);
		
		List<Map<String, Object>> result = previewImageDao.queryFiles(document);
		PreviewImage previewImage = null;
		if (result.size() > 0) {
			previewImage = new PreviewImage();
			InputStream file = (InputStream) result.get(0).get("$file");
			previewImage.setImage(file);
		}
		
		return previewImage;
	}

}
