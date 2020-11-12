package com.daleyzou.demo.disruptor;

import java.util.Calendar;

/**
 * MyConsumer
 * @description TODO
 * @author daleyzou
 * @date 2020年11月12日 20:42
 * @version 1.3.1
 */
public class MyConsumer extends ADisruptorConsumer<String> {
    private String name;

    public MyConsumer(String name) {
        this.name = name;
    }

    @Override
    public void consume(String data) {
        System.out.println(now() + this.name + "：拿到队列中的数据：" + data);
        //等待1秒钟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 获取当前时间（分:秒）
    public String now() {
        Calendar now = Calendar.getInstance();
        return "[" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND) + "] ";
    }
}
