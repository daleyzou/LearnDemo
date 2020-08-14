package com.daleyzou.demo.deferredresult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * AsyncFeginService
 * @description TODO
 * @author daleyzou
 * @date 2020年08月14日 21:59
 * @version 1.3.1
 */
@FeignClient(url = "localhost:8080", name = "async")
public interface AsyncFeginService {
    @GetMapping("/async/longPolling")
    String longPolling();

    @GetMapping("/async/returnLongPollingValue")
    void returnLongPollingValue();
}
