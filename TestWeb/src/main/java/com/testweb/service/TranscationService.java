package com.testweb.service;

import demo.com.annotation.MyService;
import demo.com.annotation.MyTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MyService
public class TranscationService {

	@MyTransaction
	public String tranA(String a) {
		log.debug("tranA with args:{}", a);

		return a;
	}
}
