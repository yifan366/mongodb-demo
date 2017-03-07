package com.zhongtai.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zhongtai.modle.UserGroup;
import com.zhongtai.modle.VideosInfo;
import com.zhongtai.service.IUserServie;
import com.zhongtai.service.IVideosInfoService;

/**
 * 上传控制器
 * 
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/ul")
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private IVideosInfoService mongodbService;

	@Autowired
	private IUserServie userService;

	@RequestMapping(value = "v", method = RequestMethod.POST)
	public void upload(@RequestParam(value = "file", required = false) MultipartFile file, String username,
			String userpwd, String filename, String aliases, String fileid, String cameraUid, Integer currentPart,
			Integer countPart, String createTime, String durationtime, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		LOGGER.info("新文件上传开始.....");
		long a = System.currentTimeMillis();

		boolean state = false;
		Integer userid = null;
		Integer groupid = null;

		try {
			userid = userService.validateUser(username, userpwd);
			if (userid != null) {
				state = true;
				groupid = userService.getGroupid(userid);
			}

		} catch (Exception e) {
			LOGGER.error("与鉴权系统链接失败");
			// out.write("{errcode:-1}");
		}

		if (groupid != null) {
			if (state && !mongodbService.getPartFilesIsUpload(fileid, currentPart,groupid)) {
				VideosInfo fileInfo = new VideosInfo();

				fileInfo.setFilename(filename);
				fileInfo.setAliases(aliases);
				fileInfo.setFileid(fileid);
				fileInfo.setFile(file.getInputStream());
				fileInfo.setCurrentPart(currentPart);
				fileInfo.setCountPart(countPart);
				fileInfo.setCameraUid(cameraUid);
				fileInfo.setUserId(userid);

				fileInfo.setCreateTime(createTime);
				fileInfo.setDurationtime(durationtime);

				fileInfo.setShare(true);
				fileInfo.setGroupId(groupid);

				mongodbService.addFiles(fileInfo);
			}else {
				// out.write("{errcode:0}");
			}
		} 

		// out.write("{errcode:1}");
		out.flush();
		out.close();

		LOGGER.debug("\r<br>执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");

	}

}
