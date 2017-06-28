package com.downjoy.logindownjoy1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Login {
	private CloseableHttpClient closeableHttpClient = null;
	static String token = null;
	public Login() {
		this.closeableHttpClient = HttpClients.createDefault();
	}
	// 取得cookie
	public void login() throws Exception {
//		你需要访问登陆的网站
		HttpPost post = new HttpPost("XXX");
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//		添加到表格参数里
		formparams.add(new BasicNameValuePair("用户名对应的id", "你的帐号"));
		formparams.add(new BasicNameValuePair("password", "你的密码"));
		CloseableHttpResponse httpResponse = null;
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post.setEntity(entity);
//		获取response
		httpResponse = closeableHttpClient.execute(post);
//		获取状态行
		System.out.println(httpResponse.getStatusLine());
	}
//	利用jsoup解析html获取token
	private static String getToken(String html) {
		Document document = Jsoup.parse(html);
		return document.select("input[name=token]").attr("value");
	}

	// 获取token
	public  void getNet1() throws ParseException, IOException {
		HttpGet httpGet1 = new HttpGet("二级网页");
		HttpResponse response2 = closeableHttpClient.execute(httpGet1);
		HttpEntity result2 = response2.getEntity();
		System.out.println("status2:" + response2.getStatusLine());
		String str2 = EntityUtils.toString(result2);
		token = getToken(str2);
	}

	// 发送token进入第三级页面
	public  void postNet() throws ClientProtocolException, IOException {
		HttpPost post2 = new HttpPost("三级页面");
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("token", token));
		CloseableHttpResponse httpResponse = null;
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
		post2.setEntity(entity);
		httpResponse = closeableHttpClient.execute(post2);
		System.out.println("status3:" + httpResponse.getStatusLine());
//		System.out.println(EntityUtils.toString(httpResponse.getEntity()));
	}

	// 获取第三级页面数据，可以写入本地文件
	public  void getData() throws ClientProtocolException, IOException {
		HttpGet get2 = new HttpGet(
				"查询具体数据，并读取出来");
		HttpResponse response5 = closeableHttpClient.execute(get2);
		HttpEntity result5 = response5.getEntity();
		System.out.println("status4:" + response5.getStatusLine());
		String str5 = EntityUtils.toString(result5);
		Document document2 = Jsoup.parse(str5);
		Elements trs = document2.select("table").select("tbody").get(1).select("td");
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < 45; j = j + 9) {
			String temp = trs.get(j).text();
			sb.append(temp + "\t" + "\t");
		}
		sb.append("\n");
		for (int i = 3; i < 48; i = i + 9) {
			String temp = trs.get(i).text();
			sb.append(temp + "\t" + "\t" + "\t");
		}
		sb.append("\n");
		for (int i = 7; i < 52; i = i + 9) {
			String temp = trs.get(i).text();
			sb.append(temp + "     ");
		}
		System.out.println(sb.toString());
		closeableHttpClient.close();
	}
}
