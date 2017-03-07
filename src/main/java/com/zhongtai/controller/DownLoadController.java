package com.zhongtai.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongtai.modle.VideosInfo;
import com.zhongtai.service.IUserServie;
import com.zhongtai.service.IVideosInfoService;
import com.zhongtai.service.impl.VideosInfoServiceImpl;

/**
 * 下载
 * 
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/dl")
public class DownLoadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DownLoadController.class);
	
	@Autowired
	IVideosInfoService videoInfoService;
	
	@Autowired
	private IUserServie userService;
	
	@RequestMapping("v")
	public void downLoadVideo(HttpServletRequest request, HttpServletResponse response,String fileid,String username, String userpwd) throws IOException {

		LOGGER.debug("下载文件。。");
		
		Integer userid = userService.validateUser(username, userpwd);
		
		if (userid!=null) {
			Integer groupid = userService.getGroupid(userid);
			try {
				response.setContentType("application/octet-stream");
				OutputStream out = response.getOutputStream();
				
				List<VideosInfo> list = videoInfoService.listByFileid(fileid,groupid);
				
				for (VideosInfo f : list) {
					InputStream inStream = f.getFile();
					
					byte[] buf = new byte[1024];
					while (inStream.read(buf) != -1) {
						out.write(buf);
					}
				}
				
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//outp.write("{errcode:0}");
		}
		

	}
	
	
}
