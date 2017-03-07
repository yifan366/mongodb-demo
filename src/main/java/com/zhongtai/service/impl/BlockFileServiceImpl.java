package com.zhongtai.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.zhongtai.service.BlockFileService;

@Service("blockFileService")
public class BlockFileServiceImpl implements BlockFileService {

	@Override
	public void blockFile(InputStream inputStream, String filename) {
		// TODO Auto-generated method stub

		String path = this.getClass().getClassLoader().getResource("/").getPath();
		System.out.println("path:" + path);

		String tempath = path + "tem/" + filename;
		File file = new File(tempath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}

		try {

			int partFileLength = 1024 * 1024;
			File targetFile = null;
			InputStream ips = null;
			OutputStream ops = null;
			OutputStream configOps = null;// 该文件流用于存储文件分割后的相关信息，包括分割后的每个子文件的编号和路径,以及未分割前文件名
			Properties partInfo = null;// properties用于存储文件分割的信息
			byte[] buffer = null;
			int partNumber = 1;
			ips = inputStream;// 找到读取源文件并获取输入流
			configOps = new FileOutputStream(new File(path + "tem/" + filename + "/config.properties"));
			buffer = new byte[partFileLength];// 开辟缓存空间
			int tempLength = 0;
			partInfo = new Properties();// key:1开始自动编号 value:文件路径

			while ((tempLength = ips.read(buffer, 0, partFileLength)) != -1) {
				String targetFilePath = path + "tem/" + filename + "/part_" + partNumber;// 分割后的文件路径+文件名
				partInfo.setProperty((partNumber++) + "", targetFilePath);// 将相关信息存储进properties
				targetFile = new File(targetFilePath);
				ops = new FileOutputStream(targetFile);// 分割后文件
				ops.write(buffer, 0, tempLength);// 将信息写入碎片文件

				ops.close();// 关闭碎片文件
			}

			partInfo.setProperty("name", filename);// 存储源文件名
			partInfo.store(configOps, "ConfigFile");// 将properties存储进实体文件中

			ips.close();// 关闭源文件流
			configOps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
