package com.imooc.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {

/*    @HystrixCommand(commandProperties = {
            // 超时时间
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
            // 服务熔断
            @HystrixProperty(name="circuitBreaker.enabled", value = "true"), // 断路器开关
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 休眠时间窗
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "10"), // 断路器最小请求数
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value = "60"), // 错误百分比 10/60 = 60%

    })*/
    @HystrixCommand(commandKey = "a")
    @GetMapping("/getProductInfoList")
    public String getProductInfoList() {
        throw new RuntimeException();
    }

    private String fallback() {
        return "触发服务降级";
    }

    private String defaultFallback(){
        return "触发默认服务降级";
    }

}
