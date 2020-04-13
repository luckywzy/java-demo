package com.demo.design.pattern.state;

/**
 * @Author: wangzongyu
 * @Date: 2020/4/13 23:48
 */
public enum State {
	SMALL(0),
	SUPER(1),
	FIRE(2),
	CAPE(3);
	private int value;

	private State(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
