package com.zhongtai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongtai.modle.VideosInfo;
import com.zhongtai.service.IUserServie;
import com.zhongtai.service.IVideosInfoService;

/**
 * 操作文件控制器
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/ov")
public class OperationVideoController {

	@Autowired
	private IVideosInfoService ms;
	
	@Autowired
	private IUserServie userService;
	
	/**
	 * 根据用户id查询视频列表
	 * @param response
	 * @param request
	 * @param userid
	 * @throws IOException
	 */
	@RequestMapping(value="list")
	public void list(HttpServletResponse response ,HttpServletRequest request ,int userid) throws IOException{

		
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		
		List<VideosInfo> list = ms.listAllByUserId(userid);
		
		JSONArray json = new JSONArray(list);
		
		
		out.write(json.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("count")
	public void count(HttpServletResponse response ,HttpServletRequest request ,int userid) throws IOException{
		
		PrintWriter out = response.getWriter();
		
		List<VideosInfo> list = ms.listAllByUserId(userid);
		
		out.write(list.size());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("delete")
	public void deleteFile(HttpServletResponse response ,String fileid,String username, String userpwd) throws IOException{
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		Integer userid = userService.validateUser(username, userpwd);
		
		if (userid != null) {
			Integer groupid = userService.getGroupid(userid);
			for (VideosInfo fileInfo : ms.listByFileid(fileid,groupid)) {
				ms.deleteFile(fileInfo.getUid());
			}
			
			out.write("{errcode:1}");
		}else{
			out.write("{errcode:0}");
		}
		
		out.flush();
		out.close();
		
	}
	
	/**
	 * 查询该段文件是否上传
	 * @throws IOException 
	 */
	@RequestMapping("isul")
	public void getPartIsUpload(HttpServletResponse response ,String fileid,String username,String userpwd,int currentPart) throws IOException{
		
		PrintWriter out = response.getWriter();
		
		Integer groupid = userService.getGroupid(username, userpwd);
		
		boolean result = ms.getPartFilesIsUpload(fileid, currentPart,groupid);
		
		if (result) {
			out.write("1");
		}else{
			out.write("0");
		}
		
		out.flush();
		out.close();
	}
	
	
}
