package com.demo.design.pattern.state;

/**
 * 状态接口
 * @Author: wangzongyu
 * @Date: 2020/4/13 23:41
 */
public interface IMario {
	String getName();
	/**
	 * 获得蘑菇
	 */
	void obtainMushroom(MarioStateFSM marioState);

	/**
	 * 获得 斗篷
	 * @param marioState
	 */
	void obtainCape(MarioStateFSM marioState);

	/**
	 * 获得 火焰
	 * @param marioState
	 */
	void obtainFireFlower(MarioStateFSM marioState);

	/**
	 *
	 * @param marioState
	 */
	void meetMonster(MarioStateFSM marioState);

}
