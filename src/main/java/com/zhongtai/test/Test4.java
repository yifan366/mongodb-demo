package com.zhongtai.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.tools.ant.taskdefs.email.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Test4 {
	class DTask implements Runnable {
		private CountDownLatch downLatch;

		private String name;

		public DTask(CountDownLatch downLatch, String name) {
			this.downLatch = downLatch;
			this.name = name;
		}

		public void run() {
			if (name.equals("A"))
				try {
					this.downLatch.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			for (int i = 0; i < 100; i++) {
				System.out.println(Thread.currentThread().getName() + "====>" + i);
			}

			// if(name.equals("B"))
			this.downLatch.countDown();
		}

	}

	public static void test1() {
		ExecutorService service = Executors.newFixedThreadPool(2);
		Object obj = new Object();
		Test4 tt = new Test4();
		CountDownLatch downLatch = new CountDownLatch(1);
		
		service.execute(tt.new DTask(downLatch, "A"));
		service.execute(tt.new DTask(downLatch, "B"));
		service.shutdown();

	}

	public static void main(String[] args) {
		
		for (int i = 0; i < 5; i++) {
			System.out.println("当前："+i);
			int c =0;
			
			if (c==100) {
				System.out.println("c:"+c);
				continue;
			}
			
		}

	}
	
	public static void updateVideo(File file, AsyncHttpResponseHandler handler)  {
		RequestParams params = new RequestParams();
		try {
			params.put("file", file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//client.post("http://192.168.1.146:8080/fileSystems/ul/v", params, handler);
	}

}
