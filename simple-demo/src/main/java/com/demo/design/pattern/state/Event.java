package com.demo.design.pattern.state;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/13 23:42
 */
public enum Event {
	GOT_MUSHROOM(0),
	GOT_FIRE(1),
	GOT_CAPE(2),
	MET_MONSTER(3);

	private int value;

	Event(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
