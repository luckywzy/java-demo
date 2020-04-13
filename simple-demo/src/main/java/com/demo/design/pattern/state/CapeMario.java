package com.demo.design.pattern.state;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/13 23:58
 */
public class CapeMario implements IMario {
	@Override
	public String getName() {
		return null;
	}

	@Override
	public void obtainMushroom(MarioStateFSM marioState) {
		marioState.setMario(new SuperMario());
		marioState.setScore(+30);
	}

	@Override
	public void obtainCape(MarioStateFSM marioState) {
		//do nothing
	}

	@Override
	public void obtainFireFlower(MarioStateFSM marioState) {

	}

	@Override
	public void meetMonster(MarioStateFSM marioState) {

	}
}
