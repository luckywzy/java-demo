package com.testweb.service;

import demo.com.annotation.MyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MyService
public class TranscationService {

    public String tranA(String a){
        log.debug("tranA with args:{}", a);

        return a;
    }
}
