package com.hilox.mall.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hilox
 * @description 测试Controller
 * @date 2019-04-25 01:45
 */
@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/actuator/info")
    public String info() {
        ServiceInstance localServiceInstance = discoveryClient.getInstances("user-server").get(0);
        String info = String.format("host: %s, post: %s", localServiceInstance.getHost(), localServiceInstance.getPort());
        return info;
    }
}
