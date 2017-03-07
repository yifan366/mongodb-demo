package com.zhongtai.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) throws Exception {
		String result = "";
		BufferedReader in = null;
		String urlNameString = url + "?" + param;
		URL realUrl = new URL(urlNameString);
		// 打开和URL之间的连接
		URLConnection connection = realUrl.openConnection();
		// 设置通用的请求属性
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.setReadTimeout(5000);
		connection.setConnectTimeout(5000);
		// 建立实际的连接
		connection.connect();
		// 获取所有响应头字段
		Map<String, List<String>> map = connection.getHeaderFields();
		// 遍历所有的响应头字段
		for (String key : map.keySet()) {
			System.out.println(key + "--->" + map.get(key));
		}
		// 定义 BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		URL realUrl = new URL(url);
		// 打开和URL之间的连接
		URLConnection conn = realUrl.openConnection();
		// 设置通用的请求属性
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setReadTimeout(5000);
		conn.setConnectTimeout(5000);
		// 获取URLConnection对象对应的输出流
		out = new PrintWriter(conn.getOutputStream());
		// 发送请求参数
		out.print(param);
		// flush输出流的缓冲
		out.flush();
		// 定义BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		return result;
	}

	public static void main(String[] args) throws Exception {

		/* String username = "chenxp@163.com";
         String userpwd = "2";
         String filename = "filename";
         String aliases = "aliases";
         String fileid = "3TPGU44V2845VW3V111A111";
         String cameraUid = "3TPGU44V2845VW3V111A";
         int currentPart = 1;
         int countPart = 2;*/
		
		//String param = "username="+username+"&userpwd="+userpwd+"&filename="+filename+"&aliases="+aliases+"&fileid="+fileid+"&cameraUid="+cameraUid+"&currentPart="+currentPart+"&countPart="+countPart;
		
		String param = "{1,2}";
		
		String result = HttpRequest.sendPost(
				"http://192.168.1.146:8080/fileSystems/ss/delete",
				param);

		System.out.println(result);

		

	}
}
