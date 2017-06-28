package com.downjoy.logindownjoy1;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.ParseException;

public class RunDemo {

	public static void main(String[] args) {
//		每隔10分钟执行一次
		while (true) {
			Login ldj=new Login();
			System.out.println("*******************");
			try {
				ldj.login();
				ldj.getNet1();
				System.out.println("*******************");
				ldj.postNet();
				System.out.println("*******************");
				ldj.getData();
				TimeUnit.MINUTES.sleep(10);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
