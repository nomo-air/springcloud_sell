package com.imooc.order.controller;

import com.imooc.order.client.ProductClient;
import com.imooc.order.dataobject.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        // 1.第一种方式（直接使用restTemplate，url写死）
        RestTemplate restTemplate = new RestTemplate();
        // String response = restTemplate.getForObject("http://localhost:8082/msg", String.class);

        // 2.第二种方式（利用loadBalancerClient通过应用名获取url，然后再使用restTemplate）
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort() + "/msg");
        // String response = restTemplate.getForObject(url, String.class);

        // 3.第三种方式（利用@LoadBalanced，可在restTemplate里使用应用的名字）
        // String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);

        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }

    @PostMapping("/getProductList")
    public String getProductList() {
        List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
        log.info("response={}", productInfoList);
        return "ok";
    }
}
