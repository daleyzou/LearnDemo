package com.daleyzou.demo.lru.cache;


import java.util.HashMap;
import java.util.Map;

/**
 * LruMap
 * @description TODO
 * @author daleyzou
 * @date 2020年10月19日 20:38
 * @version 1.3.1
 */
public class LruMap<K, V> {
    private final Map<K, V> cacheMap = new HashMap<>();

    private int cacheSize;

    private  int nodeCount;

    private Node<K, V> header;

    Node<K, V> tailer;

    public LruMap(int cacheSize) {
        this.cacheSize = cacheSize;
        header = new Node<>();
        tailer = new Node<>();

        header.next = tailer;
        header.tail = tailer;

        tailer.next = header;
        tailer.tail = header;
    }

    public void put(K key, V value){
        cacheMap.put(key, value);
        if (cacheSize == nodeCount){
            delTail();
        }
        Node<K, V> newNode = new Node<>(key, value);
        addHead(newNode);
    }

    public V get(K key){
        // 获取后将对应的节点转移到链表头去
        Node<K, V> tempHeader = this.header;
        Node<K, V> reusultNode = null;
        while (tempHeader != tailer){
            if (tempHeader.key == key){
                reusultNode = tempHeader;
            }
        }
        // 1.删除节点
        // 2.转移到头部节点去
        if (reusultNode != null){
            // 中间节点
            Node<K, V> kvNode = new Node<>(reusultNode.key, reusultNode.value);
            if (reusultNode.next != null){
                  reusultNode.tail.next = reusultNode.next;
                  reusultNode.next.tail = reusultNode.tail;
                  nodeCount--;
            }else {
                return reusultNode.value;
            }
            moveToHead(kvNode);
        }
        return cacheMap.get(key);
    }

    private void moveToHead(Node<K,V> kvNode) {
        addHead(kvNode);
    }

    private void delTail() {
        Node<K, V> endPointNode = tailer.next;
        Node<K, V> endNode =tailer;
        endNode.tail.next = endNode.next;
        endNode.next.tail = endNode.tail;

        tailer = endPointNode;
        cacheMap.remove(endNode.key);
    }

    private void addHead(Node<K, V> newNode) {
        newNode.tail = header;
        header.next = newNode;
        header = newNode;
        nodeCount++;

        // 总共为两个节点，需要删除多余的头结点、尾结点
        if (nodeCount == 2){
            tailer.next.next.tail = null;
            tailer = tailer.next.next;
        }
    }

    private class Node<K, V> {
        K key;
        V value;
        Node<K, V> tail;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {
        }

    }
}
