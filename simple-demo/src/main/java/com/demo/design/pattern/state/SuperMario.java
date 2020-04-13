package com.demo.design.pattern.state;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/13 23:57
 */
public class SuperMario implements IMario {
	@Override
	public String getName() {
		return State.SUPER.name();
	}

	@Override
	public void obtainMushroom(MarioStateFSM marioState) {
		//do nothing
	}

	@Override
	public void obtainCape(MarioStateFSM marioState) {
		marioState.setMario(new CapeMario());
		marioState.setScore(+100);
	}

	@Override
	public void obtainFireFlower(MarioStateFSM marioState) {

	}

	@Override
	public void meetMonster(MarioStateFSM marioState) {

	}
}
