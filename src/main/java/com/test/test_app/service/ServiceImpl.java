package com.test.test_app.service;

import com.test.test_app.domain.TrackInfo;
import com.test.test_app.repository.TrackInfoRepository;
import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@Service
public class ServiceImpl implements MainService {
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 0;

    private final static AtomicInteger COUNTER = new AtomicInteger(START_VALUE);

    private final ThreadKeeper producers = new ThreadKeeper("Producer",0,
                                                        n -> ((AtomicInteger)n).incrementAndGet());

    private final ThreadKeeper consumers = new ThreadKeeper("Consumer",0,
                                                        n -> ((AtomicInteger)n).decrementAndGet());


    private final TrackInfoRepository trackInfoRepository;

    @Autowired
    ServiceImpl(TrackInfoRepository trackInfoRepository) {
        this.trackInfoRepository = trackInfoRepository;
    }

    @Override
    public void setState(Integer producerCntIncBy, Integer consumerCntIncBy) {

        producers.addThreads(producerCntIncBy);
        consumers.addThreads(consumerCntIncBy);

        saveData(producers.threadsCnt,consumers.threadsCnt, producerCntIncBy, consumerCntIncBy);
    }

    @Override
    public void setCounter(Integer cntValue) {
        COUNTER.set(cntValue);
    }


    private void saveData(Integer producerCurCnt, Integer producerCntIncBy,
                          Integer consumerCurCnt, Integer consumerCntIncBy) {
        TrackInfo item = TrackInfo.builder()
                .producerCurCnt(producerCurCnt)
                .producerCntIncBy(producerCntIncBy)
                .consumerCurCnt(consumerCurCnt)
                .consumerCntIncBy(consumerCntIncBy)
                .build();

        trackInfoRepository.save(item);
    }

    private class ThreadKeeper {
        private final String name;
        private int threadsCnt;
        private final Function<Number,Integer> consumer;
        private Range<Integer> validRange = Range.between(MIN_VALUE + 1, MAX_VALUE - 1);

        ThreadKeeper(String name, int threadsCnt, Function<Number,Integer> consumer) {
            this.name = name;
            this.threadsCnt = threadsCnt;
            this.consumer = consumer;
        }

        void addThreads(int cnt) {
            while (cnt > 0) {
                cnt--;
                addThread();
            }
        }

        private void addThread() {
            threadsCnt++;

            Runnable runnable = () -> {
                Integer curVal = 0;
                while(validRange.contains(ServiceImpl.COUNTER.intValue())) {
                    synchronized (ThreadKeeper.class){
                        if(validRange.contains(ServiceImpl.COUNTER.intValue())){
                            curVal = consumer.apply(ServiceImpl.COUNTER);

                            //use err output to avoid buffering
                            System.err.println(
                                    String.format("The value has been changed: %d  by thread named: %s",
                                            curVal,
                                            name + Thread.currentThread().getName())
                            );

                            if (curVal.equals(0))
                                saveData(producers.threadsCnt,0,consumers.threadsCnt,0);
                        }
                    }

                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                threadsCnt--;
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }

    }
}
