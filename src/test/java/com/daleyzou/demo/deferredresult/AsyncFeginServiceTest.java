package com.daleyzou.demo.deferredresult;

import com.daleyzou.demo.LearnDemoApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AsyncFeginServiceTest
 * @description TODO
 * @author daleyzou
 * @date 2020年08月14日 22:31
 * @version 1.3.1
 */
public class AsyncFeginServiceTest extends LearnDemoApplicationTests {

    @Autowired
    AsyncFeginService asyncFeginService;

    @Test
    public void longPolling() throws IOException {
        ExecutorService executorService= Executors.newFixedThreadPool(4);
        for (int i=0;i<=3;i++){
            executorService.execute(()->{
                String kl=asyncFeginService.longPolling();
                System.err.println("收到响应："+kl);
            });
        }
        System.in.read();
    }

    @Test
    public void returnLongPollingValue() {
        asyncFeginService.returnLongPollingValue();
        System.out.println("out");
    }
}