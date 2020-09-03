package com.daleyzou.demo.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRUDemo
 * @description TODO
 * @author daleyzou
 * @date 2020年09月03日 9:11
 * @version 1.3.1
 */
public class LruDemo<K, V> extends LinkedHashMap<K, V> {
    private final int CACHE_SIZE;
    public LruDemo(int cacheSize) {
        super((int)Math.ceil(cacheSize / 0.75f) + 1, 0.75f, true);
        CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当map中的数据量大于指定的数量时，就自动删除最老的数据
        return this.size() > CACHE_SIZE;
    }

    public static void main(String[] args){
        LruDemo<Integer, Integer> lruDemo = new LruDemo<>(5);
        for (int i = 0; i < 5; i++) {
            lruDemo.put(i, i);
        }
        outPutMap(lruDemo);
        lruDemo.get(0);
        lruDemo.put(7,7);
        outPutMap(lruDemo);
    }

    private static void outPutMap(LruDemo<Integer, Integer> lruDemo) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
        lruDemo.forEach((key, value)->{
            System.out.println(key + ":" + value);
        });
    }
}
