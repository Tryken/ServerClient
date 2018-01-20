package de.sciesla.sender;

import de.sciesla.client.Client;
import de.sciesla.datapackage.DataPackage;
import de.sciesla.server.Server;
import de.sciesla.server.connection.Connection;

public class Sender {

	private SenderType senderType;
	private Connection connection;
	
	public Sender(SenderType senderType) {
		this.senderType = senderType;
	}
	
	public Sender(Connection connection, SenderType senderType) {
		this.senderType = senderType;
		this.connection = connection;
	}
	
	public void sendDataPackage(DataPackage datapackage) {
		switch(senderType) {
			case CLIENT:
				Server.getInstance().sendDataPackage(connection, datapackage);
				break;
			case SERVER:
				Client.getInstance().sendDataPackage(datapackage);
				break;
		}
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		switch(senderType) {
			case CLIENT:
				return connection.getUserName();
			case SERVER:
				return "Console";
		}
		return "";
	}
	
	/**
	 * @return the userName
	 */
	public Connection getConnection() {
		switch(senderType) {
			case CLIENT:
				return connection;
			case SERVER:
				return null;
		}
		return null;
	}
}
