//package com.microservices.eureka.client.api;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.microservices.eureka.client.ProjectConfig;
//import com.microservices.eureka.client.dto.ProjectConfigDto;
//import com.microservices.eureka.client.service.ConfigClientFeignClient;
//
//import io.github.resilience4j.bulkhead.annotation.Bulkhead;
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController
//public class LiveApiBK {
//
//	@Autowired
//	private Environment environment;
//
//	@Autowired
//	private ProjectConfig projectConfig;
//	
//	@GetMapping(value = {"/", "/live-check"})
//	public Map<String, Object> liveCheck(@RequestParam(required = false) List<String> envs) {
//		log.info("liveCheck:: envs:: " + envs);
//		if (envs == null) {
//			envs = new ArrayList<>();
//		}
//		envs.addAll(Arrays.asList("spring.application.name", "server.port"));
//		Map<String, Object> ret = this.getEnvironmentConfig(new HashSet<>(envs));
//		ret.put("projectConfig", new ProjectConfigDto(projectConfig));
//
//		return ret;
//	}
//
//	public Map<String, Object> getEnvironmentConfig(Set<String> envs) {
//		Map<String, Object> ret = envs.stream()
//				.collect(Collectors.toMap(
//						k -> k, 
//						v -> environment.getProperty(v)));
//		return ret;
//	}
//	
//	@Autowired
//	private ConfigClientFeignClient configClientFeignClient;
//	
//	@GetMapping("/test-config-client/live-check")
//	@CircuitBreaker(
//			name = "configClientTestServerLiveCheck",
//			fallbackMethod = "configClientLiveCheckFallBack")
//	public String testConfigClientLiveCheck(@RequestParam(required = false) Integer sleep,
//			@RequestHeader("my-app-correlation-id") String correlationId) {
//		log.info("testConfigClientLiveCheck:: " + sleep);
//		return configClientFeignClient.liveCheck(sleep, correlationId);
//	}
//	
//	private String configClientLiveCheckFallBack(Integer sleep, Throwable t) {
//		log.info("configClientLiveCheckFallBack:: " + sleep + " - error:: " + t.getMessage());
//		return "something went wrong on configClientServer";
//	}
//	
//	@GetMapping("/test-config-client/configuration")
//	public ProjectConfigDto testConfigClientProjectConfig() {
//		return configClientFeignClient.getProjectConfig();
//	}
//	
//	int count = 0;
//	
////	@GetMapping("/hello")
////	@RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
////	public String sayHello(@RequestParam(required = false) Integer sleep) throws InterruptedException {
////		if(sleep != null) {
////			Thread.sleep(sleep*1000);
////		}
////		count++;
////		return "Hello from Eureka Client Test Server:: " + count;
////	}
//	
//	@GetMapping("/hello")
//	@Bulkhead(name = "sayHello", fallbackMethod = "sayHelloFallback")
//	public String sayHello(@RequestParam(required = false) String name,
//			@RequestParam(required = false) Integer sleep) throws InterruptedException {
//		System.out.println("sayHello:: " + name + " - sleep:: " + sleep);
//		if(sleep != null) {
//			Thread.sleep(sleep*1000);
//		}
//		count++;
//		return "Hello from Eureka Client Test Server:: " + count;
//	}
//
//	public String sayHelloFallback(Throwable t) {
//		return "something went wrong";
//	}
//}
