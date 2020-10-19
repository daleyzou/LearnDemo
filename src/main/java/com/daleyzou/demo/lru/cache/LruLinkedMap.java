package com.daleyzou.demo.lru.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * lruLinkedMap
 * @description TODO
 * @author daleyzou
 * @date 2020年10月19日 21:44
 * @version 1.3.1
 */
public class LruLinkedMap<K, V> {
    private int cacheSize;

    private LinkedHashMap<K, V> cacheMap;

    public LruLinkedMap(int cacheSize) {
        this.cacheSize = cacheSize;

        cacheMap = new LinkedHashMap<K, V>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                if (cacheSize + 1 == cacheMap.size()) {
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    public void put(K key, V value) {
        this.cacheMap.put(key, value);
    }

    public V get(K key) {
        return this.cacheMap.get(key);
    }

    public Collection<Map.Entry<K, V>> getAll() {
        return new ArrayList<>(cacheMap.entrySet());
    }
}
