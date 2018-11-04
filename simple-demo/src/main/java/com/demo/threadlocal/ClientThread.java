package com.demo.threadlocal;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientThread extends Thread {

    private Sequence sequence;

    public ClientThread(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            log.debug(String.format("%s => %d", Thread.currentThread().getName(), sequence.getNumber()));
        }

    }

}
