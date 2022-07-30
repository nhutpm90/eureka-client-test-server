package com.microservices.eureka.client.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.eureka.client.ProjectConfig;
import com.microservices.eureka.client.dto.ProjectConfigDto;
import com.microservices.eureka.client.service.ConfigClientFeignClient;

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
	public String testConfigClientLiveCheck() {
		return configClientFeignClient.liveCheck();
	}
	
	@GetMapping("/test-config-client/configuration")
	public ProjectConfigDto testConfigClientProjectConfig() {
		return configClientFeignClient.getProjectConfig();
	}
}
