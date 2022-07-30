package com.microservices.eureka.client.dto;

import com.microservices.eureka.client.ProjectConfig;

public class ProjectConfigDto extends ProjectConfig {

	public ProjectConfigDto() { }

	public ProjectConfigDto(ProjectConfig projectConfig) {
		this.setTitle(projectConfig.getTitle());
		this.setLastUpdated(projectConfig.getLastUpdated());
		this.setContacts(projectConfig.getContacts());
		this.setPhones(projectConfig.getPhones());
	}
}
