package com.microservices.eureka.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservices.eureka.client.dto.ProjectConfigDto;

@FeignClient("test-config")
public interface ConfigClientFeignClient {

	@RequestMapping(method = RequestMethod.GET, value = "live-check", consumes = "application/json")
	String liveCheck(@RequestParam(required = false) Integer sleep);
	
	@RequestMapping(method = RequestMethod.GET, value = "configuration", consumes = "application/json")
	ProjectConfigDto getProjectConfig();
}

