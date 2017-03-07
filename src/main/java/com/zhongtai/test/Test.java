package com.zhongtai.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhongtai.dao.IVideoDao;
import com.zhongtai.modle.Screenshot;
import com.zhongtai.service.IUserServie;
import com.zhongtai.service.IVideosInfoService;

import junit.framework.TestCase;

public class Test extends TestCase {

	IVideosInfoService videosInfoService;
	
	IVideoDao videoDao;
	
	IUserServie userServie;

	public void init() {

		videosInfoService = (IVideosInfoService) new ClassPathXmlApplicationContext("applicationContext.xml").getBean("videosInfoService");
		
		//userServie = (IUserServie) new ClassPathXmlApplicationContext("applicationContext.xml").getBean("userServie");
		
	}
	
	public void test(){
		init();
		
		//System.out.println(videosInfoService.getLength("5d7b8742027135411deeccef92e686bd", 15231));
		//System.out.println(videosInfoService.getPartFilesIsUpload("5d7b8742027135411deeccef92e686bd", 1, null));
		
		System.out.println(videosInfoService.listByFileid("05b8aa05-7636-4442-add0-a403e3815bc8",15253));
		
		//System.out.println(videosInfoService.listByFileid("5d7b8742027135411deeccef92e686bd", 15211).size());
		
		//System.out.println(userServie.getGroupid(349));
		
	}
	
}
