package com.demo.robot;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangzongyu
 * @Date: 2019/2/27 23:02
 */
public class JikeRobot {

	public static void main(String[] args) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String url = "https://time.geekbang.org/serv/v1/article";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> paramList = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		param.put("id", "181");
		param.put("include_neighbors", "true");
		for (String key : param.keySet()) {
			paramList.add(new BasicNameValuePair(key, param.get(key)));
		}
		// 模拟表单
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
		post.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(post);
		String content = EntityUtils.toString(response.getEntity(), "utf-8");
		System.out.println(content);
		response.close();
	}
}
