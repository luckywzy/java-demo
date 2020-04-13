package com.demo.design.pattern.state;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/13 23:54
 */
public class SmallMario implements IMario {
	@Override
	public String getName() {
		return State.SMALL.name();
	}

	@Override
	public void obtainMushroom(MarioStateFSM marioState) {
		marioState.setMario(new SuperMario());
		marioState.setScore(marioState.getScore() + 10);
	}

	@Override
	public void obtainCape(MarioStateFSM marioState) {

	}

	@Override
	public void obtainFireFlower(MarioStateFSM marioState) {

	}

	@Override
	public void meetMonster(MarioStateFSM marioState) {

	}
}
