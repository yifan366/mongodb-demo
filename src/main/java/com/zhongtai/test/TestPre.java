package com.zhongtai.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhongtai.dao.IPreviewImageDao;
import com.zhongtai.service.IPreviewImageService;
import com.zhongtai.service.IScreenshotService;

import junit.framework.TestCase;

public class TestPre extends TestCase{

	IPreviewImageDao previewImageDao;
	
	Map<String, Object> document = new HashMap<String, Object>();

	public void init() {

		previewImageDao = (IPreviewImageDao) new ClassPathXmlApplicationContext("applicationContext.xml").getBean("previewImageDao");
	}
	
	public void	list(){
		
		init();
		
		List<Map<String, Object>> listmap =  previewImageDao.queryFiles(document);
		for (Map<String, Object> map : listmap) {
			System.out.println(map.get("fileid"));
		}
	}
}
