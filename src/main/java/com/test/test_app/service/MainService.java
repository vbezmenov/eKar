package com.test.test_app.service;

public interface MainService {

    int START_VALUE = 50;

    void setState(Integer producerCnt, Integer consumerCnt);
    void setCounter(Integer cntValue);
}
