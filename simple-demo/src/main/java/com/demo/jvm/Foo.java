package com.demo.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wangzongyu
 * @Date: 2019/2/22 20:27
 */
public class Foo {
	static boolean boolValue;

	public static void main(String[] args) {

	/*	boolValue = true; // 将这个 true 替换为 2 或者 3，再看看打印结果
		if (boolValue) System.out.println("Hello, Java!");
		if (boolValue == true) System.out.println("Hello, JVM!");*/

		boolean n = false;
		Integer one = 1;
		List list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		Object collect = list.stream().filter(k -> n == one.equals(k)).distinct().collect(Collectors.toList());
		System.out.println(collect);

	}
}
