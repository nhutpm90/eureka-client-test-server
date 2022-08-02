package com.microservices.eureka.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("test-config")
public interface ConfigClientFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "live-check", consumes = "application/json")
	String liveCheck();
	
}

