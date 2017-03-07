package com.zhongtai.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zhongtai.modle.PreviewImage;
import com.zhongtai.service.IPreviewImageService;
import com.zhongtai.service.IUserServie;
import com.zhongtai.service.IVideosInfoService;

/**
 * 视频缩缩图控制器
 * 
 * @author zhangfan
 *
 */
@Controller
@RequestMapping("/pi")
public class PreviewImageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreviewImageController.class);

	@Autowired
	private IUserServie userService;

	@Autowired
	private IPreviewImageService previewImageService;
	
	@Autowired
	private IVideosInfoService videosInfoService;

	/**
	 * 上传图片
	 * 
	 * @throws IOException
	 */
	@RequestMapping("ul")
	public void upload(@RequestParam(value = "file", required = false) MultipartFile file, String fileid,
			String username, String userpwd,String lfength, HttpServletResponse response) throws IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			Integer userid = userService.validateUser(username, userpwd);

			if (userid != null) {
				PreviewImage previewImage = new PreviewImage();
				previewImage.setFileid(fileid);
				previewImage.setImage(file.getInputStream());

				previewImageService.addFile(previewImage);
				
				//out.write("{errcode:1,isup:"+(videosInfoService.getFilesIsUpload(fileid)==true?1:0)+"}");
			}else{
				//out.write("{errcode:0}");
			}
		} catch (Exception e) {
			LOGGER.error("上传缩略图错误....");
		}

	}

	/**
	 * 下载
	 */
	@RequestMapping("dl")
	public void download(String fileid, HttpServletResponse response) {
		
		try {
			response.setContentType("application/octet-stream");
			OutputStream out = response.getOutputStream();

			PreviewImage previewImage = previewImageService.load(fileid);

			InputStream inStream = previewImage.getImage();

			byte[] buf = new byte[1024];
			while (inStream.read(buf) != -1) {
				out.write(buf);
			}

			out.flush();
			out.close();
		} catch (Exception e) {
			LOGGER.error("下载缩略图错误....");
		}
		
	}
	
}
