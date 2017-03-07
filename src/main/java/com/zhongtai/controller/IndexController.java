package com.zhongtai.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongtai.modle.VideosInfo;
import com.zhongtai.service.IVideosInfoService;

@Controller
public class IndexController {

	@Autowired
	private IVideosInfoService mongodbService;
	
	
	@RequestMapping("/index")
	public String index(){
		return "index";
		
	}
	
	@RequestMapping("/listFile/{userId}")
	public void listFileByUserId(HttpServletResponse response,@PathVariable Integer userId) throws IOException{
		
		response.setContentType("text/json;charset=UTF-8");
		
		List<VideosInfo> lst = mongodbService.listByUserId(userId);
		
		JSONArray json = new JSONArray(lst);
		
		PrintWriter out = response.getWriter();
		out.write(json.toString());
	} 
	
	
}
