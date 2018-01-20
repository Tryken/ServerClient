package de.sciesla.server.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

import de.sciesla.datapackage.AuthentificatedPackage;
import de.sciesla.datapackage.AuthentificationPackage;
import de.sciesla.datapackage.DataPackage;
import de.sciesla.datapackage.MessageDataPackage;
import de.sciesla.sender.Sender;
import de.sciesla.sender.SenderType;
import de.sciesla.server.Server;
import de.sciesla.server.logger.LogType;
import de.sciesla.server.logger.Logger;

public class Connection extends Thread{
	
	private Socket socket;
	private String userName;
	private UUID uuid;
	private boolean authentificated;
	
	private BufferedReader in;
	private PrintWriter out;
	
	public Connection(Socket socket, boolean authentificated) {
		this.socket = socket;
		this.userName = "User-" + String.format("%04d", (int)(Math.random() * 9999));
		this.uuid = UUID.randomUUID();
		this.authentificated = authentificated;
		
		Logger.log(LogType.INFO, userName + socket.getInetAddress().toString() + " versucht sich zu verbinden!");
	}
	
	@Override
	public void run() {
		
        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            Server server = Server.getInstance();
            if(server.isConnectionLimitReached()) {
            	server.removeConnection(this, "Kick: The Server is full!");
            	return;
            }
            
            if(authentificated) {
            	sendDataPackage(new AuthentificatedPackage());
        		Logger.log(LogType.INFO, userName + " hat den Server betreten!");
        		server.broadcastDataPackage(new MessageDataPackage(userName + " hat den Server betreten!"));
            }else{
            	server.sendDataPackage(this, new AuthentificationPackage());
            }
            	
            
			String line;
			while((line = in.readLine()) != null) {
                DataPackage dataPackage = DataPackage.fromString(line);
                if(dataPackage != null) // ERRORhandling kick?
                	dataPackage.onServer(new Sender(this, SenderType.CLIENT));
            }
            
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
            
            Logger.log(LogType.INFO, userName + " hat den Server verlassen!");
            
            if(authentificated) {
            	Server.getInstance().broadcastDataPackage(new MessageDataPackage(userName + " hat den Server verlassen!"));
            }
            
            Server.getInstance().removeConnection(this);
        }
	}

	public void sendDataPackage(DataPackage datapackage) {
		if(out != null) {
			out.println(datapackage.toString());
		}
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @return the authentificated
	 */
	public boolean isAuthentificated() {
		return authentificated;
	}

	/**
	 * @param authentificated the authentificated to set
	 */
	public void setAuthentificated(boolean authentificated) {
		
		this.authentificated = authentificated;
		
		if(authentificated) {
			Server server = Server.getInstance();
			sendDataPackage(new AuthentificatedPackage());
			Logger.log(LogType.INFO, userName + " hat den Server betreten!");
			server.broadcastDataPackage(new MessageDataPackage(userName + " hat den Server betreten!"));
		}
	}
}
