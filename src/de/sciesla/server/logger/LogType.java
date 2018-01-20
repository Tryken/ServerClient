package de.sciesla.server.logger;

public enum LogType {
	
	INFO("Info"),
	ERROR("Error"),
	DEBUG("Debug");
	
	private String name;
	
	LogType(String name) {
		this.name = name;
	}
	
	String getName() {
		return name;
	}
}
