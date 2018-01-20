package de.sciesla.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import de.sciesla.datapackage.DataPackage;
import de.sciesla.datapackage.KickDataPackage;
import de.sciesla.server.connection.Connection;
import de.sciesla.server.logger.LogType;
import de.sciesla.server.logger.Logger;

public class Server {

	private static Server instance;
	
	private int port;
	private int maxClients;
	private String password;
	
	private ServerState serverState;
	
	private ServerSocket listener;
	private ArrayList<Connection> connections;
	
	public Server(int port, int maxClients, String password) {
		
		instance = this;
		
		this.port = port;
		this.maxClients = maxClients;
		this.password = password;
		
		serverState = ServerState.STOPPED;
		
		init();
	}
	
	private void init() {
		Logger.log(LogType.INFO, "Server Init");
		
		connections = new ArrayList<>();
	}
	
	public void start() {
		if(serverState != ServerState.STOPPED) {
			Logger.log(LogType.ERROR, "Server läuft bereits!");
			return;
		}
		
		setServerState(ServerState.STARTING);
		
		
		setServerState(ServerState.STARTED);
		new Thread() {
			@Override
			public void run() {
				try {
					listener = new ServerSocket(port);
					Logger.log(LogType.INFO, "Listening on Port " + port);
				
					while (true) {
							
							Socket socket = listener.accept();
							Connection connection = new Connection(socket, password.equalsIgnoreCase(""));
							connections.add(connection);
							connection.start();
	
					}
				} catch (IOException e) {
					
					Logger.log(LogType.ERROR, "Ein Server läuft bereits unter diesem");
					Server.getInstance().stop();
				} finally {
		            
		        }

			}
		}.start();

	}
	
	public void stop() {
		if(serverState != ServerState.STARTED && serverState != ServerState.RESTARTING) {
			Logger.log(LogType.ERROR, "Server läuft nicht!");
			return;
		}
		
		setServerState(ServerState.STOPPING);

		try {
			listener.close();
			listener = null;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

		}
		
		setServerState(ServerState.STOPPED);
	}
	
	public void restart() {
		if(serverState != ServerState.STARTED && serverState != ServerState.RESTARTING) {
			Logger.log(LogType.ERROR, "Server läuft nicht!");
			return;
		}
		
		setServerState(ServerState.RESTARTING);
		
		init();
		stop();
		start();
	}
	
	private void setServerState(ServerState state) {
		this.serverState = state;
		Logger.log(LogType.INFO, "Server " + state.getName());
	}
	
	public void removeConnection(Connection connection) {
		connections.remove(connection);
	}
	
	public void removeConnection(Connection connection, String reason) {
		
		Logger.log(LogType.INFO, "Kick " + connection.getUserName() + " reason: " + reason);
		connection.setAuthentificated(false);
		sendDataPackage(connection, new KickDataPackage(reason));
		removeConnection(connection);
	}
	
	public int getConnectionAmount() {
		return connections.size();
	}
	
	public boolean isConnectionLimitReached() {
		return (getMaxClients() != -1 && getConnectionAmount() > getMaxClients());
	}
	
	/**
	 * @return the maxClients
	 */
	public int getMaxClients() {
		return maxClients;
	}

	/**
	 * @param maxClients the maxClients to set
	 */
	public void setMaxClients(int maxClients) {
		this.maxClients = maxClients;
	}

	public boolean isPasswordCorrect(String password) {
		return (this.password.equalsIgnoreCase("") || password.equalsIgnoreCase(this.password));
	}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public void sendDataPackage(Connection connection, DataPackage datapackage) {
		connection.sendDataPackage(datapackage);
	}
	
	public void broadcastDataPackage(DataPackage datapackage) {
		for(Connection connection : connections)
			connection.sendDataPackage(datapackage);
	}
	
	public static Server getInstance() {
		return instance;
	}
}
