package de.sciesla.server;

public enum ServerState {
	
	STARTING("Starting"),
	STARTED("Started"),
	STOPPING("Stopping"),
	STOPPED("Stopped"),
	RESTARTING("Restarting");
	
	private String name;
	
	ServerState(String name) {
		this.name = name;
	}
	
	String getName() {
		return name;
	}
}
