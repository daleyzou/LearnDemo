package com.daleyzou.demo.lru.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LRUAbstrackMap
 * @description TODO
 * @author daleyzou
 * @date 2020年10月16日 13:07
 * @version 1.3.1
 */
public class LRUAbstrackMap extends AbstractMap {
    private final static Logger LOGGER = LoggerFactory.getLogger(LRUAbstrackMap.class);
    private ExecutorService checkTimePoll;
    private final static int MAX_SIZE = 1024;
    private static int DEFAULT_ARRAY_SIZE = 1024;
    private int arraySize;
    private Object[] arrays;
    private volatile boolean flag = true;
    private final static Long EXPIRE_TIME = 60 * 60*1000L;
    private volatile AtomicInteger size;

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return null;
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
