package com.daleyzou.demo.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * ObjectEventFactory
 * @description TODO
 * @author daleyzou
 * @date 2020年11月12日 20:21
 * @version 1.3.1
 */
public class ObjectEventFactory<T> implements EventFactory<ObjectEvent<T>> {
    public ObjectEventFactory() {
    }

    @Override
    public ObjectEvent<T> newInstance() {
        return new ObjectEvent();
    }
}
