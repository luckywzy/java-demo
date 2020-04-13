package com.demo.design.pattern.state;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/13 23:47
 */
public class MarioStateFSM {

	private int score;
	private IMario mario;


	public MarioStateFSM() {
		this.score = 0;
		this.mario = new SmallMario();
	}

	public void obtainMushRoom() {
		mario.obtainMushroom(this);
	}

	public void obtainCape() {
		mario.obtainCape(this);
	}

	public void obtainFireFlower() {
		mario.obtainFireFlower(this);
	}

	public void meetMonster() {
		mario.meetMonster(this);
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public IMario getMario() {
		return mario;
	}

	public void setMario(IMario mario) {
		this.mario = mario;
	}

}
