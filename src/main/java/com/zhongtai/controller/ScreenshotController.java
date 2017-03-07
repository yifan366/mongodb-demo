package com.zhongtai.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zhongtai.modle.Screenshot;
import com.zhongtai.modle.UserGroup;
import com.zhongtai.service.IScreenshotService;
import com.zhongtai.service.IUserServie;

/**
 * 截屏控制器
 * 
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/ss")
public class ScreenshotController {

	@Autowired
	private IScreenshotService screenshotService;
	
	@Autowired
	private IUserServie userService;

	/**
	 * 上传截屏
	 * @throws IOException 
	 */
	@RequestMapping("ul")
	public void upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletResponse response,String cameraUid, String username,String userpwd,String fileid,String createTime,String durationtime,String filename ) throws IOException{
		
	//	response.setContentType("text/html;charset=UTF-8");
		boolean state = false;
		Integer userid = null ;
		Integer groupid = null;
		try {
			userid = userService.validateUser(username, userpwd);
			if (userid !=null) {
				groupid = userService.getGroupid(userid);
				if (groupid != null) {
					state = true;
				}
			}
			
		} catch (Exception e) {
			//out.write("{errcode:-1}");
		}
		
		PrintWriter out = response.getWriter();
		try {
			
			if (state&&!screenshotService.isUpload(fileid,groupid)) {
				
				List<UserGroup> list = userService.listGroup(userid);
				
				Screenshot screenshot = new Screenshot();
				screenshot.setFile(file.getInputStream());
				screenshot.setUserid(userid);
				screenshot.setFileid(fileid);
				screenshot.setCreateTime(createTime);
				screenshot.setFilename(filename);
				screenshot.setCameraUid(cameraUid);
				screenshot.setGroupid(groupid);
				
				screenshotService.addFile(screenshot);
				
				out.write("{errcode:1}");
			}else{
				out.write("{errcode:-1}");
			}
		} catch (Exception e) {
			// TODO: handle exception
			out.write("{errcode:0}");
		}
		
		
	}

	/**
	 * 获取截屏列表
	 * @throws IOException 
	 */
	@RequestMapping("list")
	public void getScreenList(String username,String userpwd,String cameraUid, HttpServletResponse response) throws IOException {

		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		Integer userid = userService.validateUser(username, userpwd);
		
		if (userid!=null) {
			List<Screenshot> list = screenshotService.list(userid,cameraUid);
			JSONArray json = new JSONArray(list);
			out.write(json.toString());
		}else{
			out.write("{errcode:-1}");
		}
		
		out.flush();
		out.close();
	}

	/**
	 * 获取截屏列表数目
	 * @throws IOException 
	 */
	@RequestMapping("count")
	public void getScreenCount(String username,String userpwd,String cameraUid, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		
		Integer userid = userService.validateUser(username, userpwd);
		
		if (userid!=null) {
			List<Screenshot> list = screenshotService.list(userid, cameraUid);
			out.write(list.size());
		}
		
		out.flush();
		out.close();
		
	}

	/**
	 * 下载截屏
	 * @throws IOException 
	 */
	@RequestMapping("dl")
	public void download(String uid, HttpServletResponse response) throws IOException {

		
		
		Screenshot screenshot = screenshotService.load(uid);
		
		if (screenshot!=null) {
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();
			InputStream inStream = screenshot.getFile();
			
			byte[] buf = new byte[1024];
			while (inStream.read(buf) != -1) {
				out.write(buf);
			}
			out.flush();
			out.close();
		}else{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter outp = response.getWriter();
			outp.write("{errcode:-1}");
		}
	}
	
	
	/**
	 * 查询改截屏是否已经上传
	 * @throws IOException 
	 */
	@RequestMapping("isupload")
	public void isUpload(String fileid,String username,String userpwd, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		Integer groupid = userService.getGroupid(username, userpwd);
		
		boolean result = screenshotService.isUpload(fileid,groupid);
		
		if (result) {
			out.write("{errcode:1}");
		}else{
			out.write("{errcode:0}");
		}
		
		
		out.flush();
		out.close();
	}
	
	/**
	 * 删除截屏
	 * @throws IOException 
	 */
	@RequestMapping("delete")
	public void delete(String uids, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String[] uidss = uids.split(",");
		
		int count = 0;
		for (int i = 0; i < uidss.length; i++) {
			boolean result = screenshotService.delete(uidss[i]);
			if (result) {
				count++;
			}
		}
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("count", count);
		
		out.write(new JSONObject(map).toString());
		
		out.flush();
		out.close();
	}
	
	/**
	 * 查询某些截屏是否已经上传
	 * @throws IOException 
	 */
	@RequestMapping("isuploads")
	public void isUploads(String fileids,String username,String userpwd, HttpServletResponse response) throws IOException{
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		Integer groupid = userService.getGroupid(username, userpwd);
		
		String[] fileidss = fileids.split(",");
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		
		for (int i = 0; i < fileidss.length; i++) {
			boolean result = screenshotService.isUpload(fileidss[i],groupid);
			
			map.put(fileidss[i], result==true?1:0);
		}
		
		JSONObject jsonObject = new JSONObject(map);
		out.write(jsonObject.toString());
		
		
		out.flush();
		out.close();
	}
	
}
