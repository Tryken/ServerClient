package de.sciesla.client;

public enum ClientState {
	
	CONNECING("Connecting"),
	CONNECTED("Connected"),
	DISCONNECTING("Disconnecting"),
	DISCONNECTED("Disconnected"),
	RECONNECTING("Reconnecting");
	
	private String name;
	
	ClientState(String name) {
		this.name = name;
	}
	
	String getName() {
		return name;
	}
}
