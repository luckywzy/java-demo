package com.demo.design.pattern.state;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/14 00:01
 */
public class ApplicationDemo {

	public static void main(String[] args) {
		MarioStateFSM mario = new MarioStateFSM();
		mario.obtainMushRoom();
		int score = mario.getScore();
		IMario mario1 = mario.getMario();
		System.out.println("mario score: " + score + "; state: " + mario1.getName());

	}
}
