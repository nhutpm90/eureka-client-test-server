package com.microservices.eureka.client.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.eureka.client.ProjectConfig;
import com.microservices.eureka.client.dto.ProjectConfigDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LiveApi {

	@Autowired
	private Environment environment;

	@Autowired
	private ProjectConfig projectConfig;
	
	@GetMapping(value = {"/", "/live-check"})
	public Map<String, Object> liveCheck(@RequestParam(required = false) List<String> envs) {
		log.info("liveCheck:: envs:: " + envs);
		if (envs == null) {
			envs = new ArrayList<>();
		}
		envs.addAll(Arrays.asList("spring.application.name", "server.port"));
		Map<String, Object> ret = this.getEnvironmentConfig(new HashSet<>(envs));
		ret.put("projectConfig", new ProjectConfigDto(projectConfig));

		return ret;
	}

	public Map<String, Object> getEnvironmentConfig(Set<String> envs) {
		Map<String, Object> ret = envs.stream()
				.collect(Collectors.toMap(
						k -> k, 
						v -> environment.getProperty(v)));
		return ret;
	}
}
