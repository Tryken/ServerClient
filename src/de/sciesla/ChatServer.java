package de.sciesla;

import de.sciesla.server.Server;

public class ChatServer extends Server{

	public ChatServer(int port, String password, int maxClients) {
		super(port, maxClients, password);
	}

}
