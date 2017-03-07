package com.zhongtai.service;

import java.io.InputStream;

public interface BlockFileService {

	/**
	 * 分割文件流
	 * @param inputStream
	 */
	public void blockFile(InputStream inputStream,String filename);
}
