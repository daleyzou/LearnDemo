package com.daleyzou.demo.lru.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LRUAbstrackMap
 * @description https://crossoverjie.top/%2F2018%2F04%2F07%2Falgorithm%2FLRU-cache%2F
 * @author daleyzou
 * @date 2020年10月16日 13:07
 * @version 1.3.1
 */
public class LruAbstrackMap extends AbstractMap {
    private final static Logger LOGGER = LoggerFactory.getLogger(LruAbstrackMap.class);

    private ExecutorService checkTimePoll;

    private final static int MAX_SIZE = 1024;

    private static int DEFAULT_ARRAY_SIZE = 1024;

    private int arraySize;

    private Object[] arrays;

    private volatile boolean flag = true;

    private final static Long EXPIRE_TIME = 60 * 60 * 1000L;

    private volatile AtomicInteger size;

    private final static ArrayBlockingQueue<Node> BLOCKING_QUEUE = new ArrayBlockingQueue<Node>(MAX_SIZE);

    public LruAbstrackMap() {
        arraySize = DEFAULT_ARRAY_SIZE;
        arrays = new Object[arraySize];
        executeClearTask();
    }

    private void executeClearTask() {

        ThreadFactory threadNameFactory = r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        };

        checkTimePoll = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(1), threadNameFactory,
                new ThreadPoolExecutor.AbortPolicy());
        checkTimePoll.execute(new CheckTimeTask());
    }

    @Override
    public Object put(Object key, Object value) {
        int hash = hash(key);
        int index = hash % arraySize;
        Node currentNode = (Node)arrays[index];
        if (currentNode == null){
            Node newNode = new Node(null, key, value, new Date(), null);
            sizeUp();
            BLOCKING_QUEUE.offer(newNode);
            arrays[index] = newNode;
            return newNode;
        }
        if (currentNode.key == key){
            currentNode.value = value;
            return currentNode;
        }
        // 存在重复的
        Node tempNode = currentNode;
        while (tempNode.next != null){
            if (tempNode.key == key){
                tempNode.value = value;
                return  tempNode;
            }
            tempNode = tempNode.next;
        }
        if (tempNode.key == key){
            tempNode.value = value;
            return tempNode;
        }else {
            Node newNode = new Node(null, key, value, new Date(), null);
            sizeUp();
            BLOCKING_QUEUE.offer(newNode);
            tempNode.next = newNode;
            return  newNode;
        }
    }

    @Override
    public Object get(Object key) {
        int hash = hash(key);
        int index = hash % arraySize;
        Node nodeByKey = (Node) arrays[index];
        if (nodeByKey == null){
            return null;
        }
        if (nodeByKey.key == key){
            nodeByKey.setUpdateTime(new Date());
            return nodeByKey;
        }
        while (nodeByKey.next != null){
            if (nodeByKey.key == key){
                nodeByKey.setUpdateTime(new Date());
                return nodeByKey;
            }
            nodeByKey = nodeByKey.next;
        }
        if (nodeByKey.key == key){
            nodeByKey.setUpdateTime(new Date());
            return nodeByKey;
        }
        return super.get(key);
    }

    private void sizeUp() {
        flag = true;
        if (this.size == null){
            this.size = new AtomicInteger();
        }

        int size = this.size.incrementAndGet();
        if (size > MAX_SIZE){
            Node currentNode = BLOCKING_QUEUE.poll();
            if (currentNode == null){
                throw new RuntimeException("data err, get data is empty");
            }
            remove(currentNode.getKey());
            lruCallBack(currentNode);
        }
    }

    private void lruCallBack(Node currentNode) {
        LOGGER.info("lru callback!!, key = " + currentNode.getKey());
    }

    @Override
    public Object remove(Object key) {
        int hash = hash(key);
        int index = hash % arraySize;
        Node node = (Node)arrays[index];
        if (node == null){
            return null;
        }
        if (node.getKey() == key){
            sizeDown();
            arrays[index] = null;
            BLOCKING_QUEUE.poll();
            return node;
        }
        Node tempNode = node;
        while (tempNode.next != null){
            if (tempNode.key == key){
                sizeDown();
                tempNode.pre.next = tempNode.next;
                tempNode = null;
                BLOCKING_QUEUE.poll();
                return null;
            }
            node = node.next;
        }
        return super.remove(key);
    }

    private void sizeDown() {
        if (BLOCKING_QUEUE.size() == 0){
            flag = false;
        }
        this.size.decrementAndGet();
    }

    private int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    @Override
    public Set<Entry> entrySet() {
        return super.keySet();
    }

    private class Node {
        private Node pre;

        private Object key;

        private Object value;

        private Date updateTime;

        private Node next;

        public Node(Node pre, Object key, Object value, Date updateTime, Node next) {
            this.pre = pre;
            this.key = key;
            this.value = value;
            this.updateTime = updateTime;
            this.next = next;
        }

        public Date getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
            this.updateTime = updateTime;
        }

        public Object getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }
    }

    private class CheckTimeTask implements Runnable {
        @Override
        public void run() {
            while (flag) {
                try {
                    Node node = BLOCKING_QUEUE.poll();
                    if (node == null) {
                        continue;
                    }
                    Date nodeUpdateTime = node.getUpdateTime();
                    if (System.currentTimeMillis() - nodeUpdateTime.getTime() > EXPIRE_TIME) {
                        // 数据过期了
                        remove(node.getKey());
                    }
                } catch (Exception exception) {
                    LOGGER.error("something error!", exception);
                }
            }
        }
    }
}
