package com.daleyzou.demo.deferredresult;

import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

/**
 * AsyncController
 * @description TODO
 * @author daleyzou
 * @date 2020年08月14日 21:47
 * @version 1.3.1
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    final Map<Integer, DeferredResult> deferredResultMap = new ConcurrentReferenceHashMap<>();

    @GetMapping("/longPolling")
    public DeferredResult DefferendResultPolling(){
        DeferredResult deferredResult = new DeferredResult(0L);
        deferredResultMap.put(deferredResult.hashCode(), deferredResult);
        deferredResult.onCompletion(()->{
            deferredResultMap.remove(deferredResult.hashCode());
            System.err.println("还剩" + deferredResultMap.size() + "个deferredResult未响应");
        });
        return deferredResult;
    }

    @GetMapping("/returnLongPollingValue")
    public void retrurnLongPollingValue(){
        for (Map.Entry<Integer, DeferredResult> entry : deferredResultMap.entrySet()) {
            entry.getValue().setResult("k1");
        }
    }
}
