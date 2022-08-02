package com.microservices.eureka.client.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.eureka.client.service.ConfigClientFeignClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ConfigClientServiceApi {

	@Autowired
	private ConfigClientFeignClient configClientFeignClient;

	@GetMapping("/config-client/live-check")
	public String configClientLiveCheck() {
		log.info("configClientLiveCheck:: ");
		return configClientFeignClient.liveCheck();
	}
}
