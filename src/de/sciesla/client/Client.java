package de.sciesla.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import de.sciesla.datapackage.DataPackage;
import de.sciesla.sender.Sender;
import de.sciesla.sender.SenderType;

public abstract class Client {

	private static Client instance;
	
	private String host;
	private int port;

	private boolean authentificated = false;
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	private ClientState clientState;
	
	public Client(String host, int port) {
		
		Client.instance = this;
		
		this.host = host;
		this.port = port;
		
		init();
	}
	
	public abstract void onConnected();
	public abstract void onDisconnected();
	public abstract String onPasswordRequired();
	public abstract void onAuthentificated();
	
	private void init() {
		clientState = ClientState.DISCONNECTED;
	}
	
	public void connect() {
		if(clientState != ClientState.DISCONNECTED) {
			System.out.println("Client ist bereits verbunden!");
			return;
		}
		
		new Thread() {
			@Override
			public void run() {
		
				try {
					socket = new Socket(host, port);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					out = new PrintWriter(socket.getOutputStream(), true);
					
					new Thread() {
						@Override
						public void run() {		
							onConnected();
						}
					}.start();
					
					String line;
					while((line = in.readLine()) != null) {
		                DataPackage dataPackage = DataPackage.fromString(line);
		                if(dataPackage != null) // ERRORhandling server verlassen?
		                	dataPackage.onClient(new Sender(SenderType.SERVER));
					}
					
				} catch (IOException e) {

				} finally {
					try {
						socket.close();
						socket = null;
						System.out.println("Verbindung verloren!");
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NullPointerException e2) {
						System.out.println("Konnte keine Verbindung zum Server herstellen!");
					}
					
					new Thread() {
						@Override
						public void run() {		
							onDisconnected();
						}
					}.start();					
				}
			}
		}.start();

	}
	
	public void sendDataPackage(DataPackage datapackage) {
		if(out != null)
			out.println(datapackage.toString());
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
	}

	public static Client getInstance() {
		return instance;
	}
}
