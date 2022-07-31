package com.microservices.eureka.client.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.eureka.client.ProjectConfig;
import com.microservices.eureka.client.dto.ProjectConfigDto;
import com.microservices.eureka.client.service.ConfigClientFeignClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LiveApi {

	@Autowired
	private Environment environment;

	@Autowired
	private ProjectConfig projectConfig;
	
	@GetMapping("/live-check")
	public String liveCheck() throws Exception {
		Integer port = Integer.parseInt(environment.getProperty("server.port"));
		return String.format("Eureka Client Server:: %s", port);
	}

	@GetMapping("/env-check")
	public Map<String, String> testParam(@RequestParam("envs") List<String> envs) throws Exception {
		Map<String, String> ret = envs.stream()
				.collect(Collectors.toMap(
						k -> k, 
						v -> environment.getProperty(v)));
		return ret;
	}
	
	@GetMapping("/configuration")
	public ProjectConfigDto configuration() throws Exception {
		return new ProjectConfigDto(projectConfig);
	}
	
	@Autowired
	private ConfigClientFeignClient configClientFeignClient;
	
	@GetMapping("/test-config-client/live-check")
	@CircuitBreaker(
			name = "configClientTestServerLiveCheck",
			fallbackMethod = "configClientLiveCheckFallBack")
	public String testConfigClientLiveCheck(@RequestParam(required = false) Integer sleep,
			@RequestHeader("my-app-correlation-id") String correlationId) {
		log.info("testConfigClientLiveCheck:: " + sleep);
		return configClientFeignClient.liveCheck(sleep, correlationId);
	}
	
	private String configClientLiveCheckFallBack(Integer sleep, Throwable t) {
		log.info("configClientLiveCheckFallBack:: " + sleep + " - error:: " + t.getMessage());
		return "something went wrong on configClientServer";
	}
	
	@GetMapping("/test-config-client/configuration")
	public ProjectConfigDto testConfigClientProjectConfig() {
		return configClientFeignClient.getProjectConfig();
	}
	
	int count = 0;
	
//	@GetMapping("/hello")
//	@RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
//	public String sayHello(@RequestParam(required = false) Integer sleep) throws InterruptedException {
//		if(sleep != null) {
//			Thread.sleep(sleep*1000);
//		}
//		count++;
//		return "Hello from Eureka Client Test Server:: " + count;
//	}
	
	@GetMapping("/hello")
	@Bulkhead(name = "sayHello", fallbackMethod = "sayHelloFallback")
	public String sayHello(@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer sleep) throws InterruptedException {
		System.out.println("sayHello:: " + name + " - sleep:: " + sleep);
		if(sleep != null) {
			Thread.sleep(sleep*1000);
		}
		count++;
		return "Hello from Eureka Client Test Server:: " + count;
	}

	public String sayHelloFallback(Throwable t) {
		return "something went wrong";
	}
}
