package com.zhongtai.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhongtai.modle.Screenshot;
import com.zhongtai.service.IScreenshotService;
import com.zhongtai.service.IUserServie;
import com.zhongtai.service.IVideosInfoService;

import junit.framework.TestCase;

public class TestScreenshot extends TestCase{

	IScreenshotService screenshotService;

	public void init() {

		screenshotService = (IScreenshotService) new ClassPathXmlApplicationContext("applicationContext.xml").getBean("screenshotService");
	}
	
	public void addFile(){
		init();
		
		InputStream file = null;
		try {
			file = new FileInputStream(new File("C:/Users/Administrator/Desktop/22.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Screenshot screenshot = new Screenshot();
		screenshot.setFile(file);
		screenshot.setUserid(349);
		screenshot.setFileid("789c5031651273dbae91f3fe60af6832");
		screenshot.setCreateTime("2017-03-03");
		screenshot.setFilename("IMG_20170303_105955.jpg");
		screenshot.setCameraUid("3TPGU44V2845VW3V111A");
		screenshot.setGroupid(15254);
		
		if (!screenshotService.isUpload("789c5031651273dbae91f3fe60af6832",15254)) {
			screenshotService.addFile(screenshot);
		}
		
	}
	
	public void list(){
		init();
		
		for (Screenshot s : screenshotService.list(349,"3TPGU44V2845VW3V111A")) {
			
			System.out.println(s.getUid());
		}
		
	}
	
	
	public void detele(){
		init();
		
		screenshotService.delete("eb42e03656e240f8b6c7f0b4f0ed7ae7");
		
	}
	
}
