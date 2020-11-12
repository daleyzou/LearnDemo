package com.daleyzou.demo.disruptor;

/**
 * ObjectEvent
 * @description TODO
 * @author daleyzou
 * @date 2020年11月12日 20:12
 * @version 1.3.1
 */
public class ObjectEvent<T> {
    private T obj;

    public ObjectEvent() {

    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
