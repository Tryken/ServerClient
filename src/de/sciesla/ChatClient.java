package de.sciesla;

import java.util.Scanner;

import de.sciesla.client.Client;
import de.sciesla.datapackage.MessageDataPackage;

public class ChatClient extends Client{

	public ChatClient(String host, int port) {
		super(host, port);
	}

	@Override
	public void onConnected() {

	}

	@Override
	public void onDisconnected() {

	}

	@SuppressWarnings("resource")
	@Override
	public String onPasswordRequired() {
		
		System.out.print("Server Kennwort: ");
		return new Scanner(System.in).nextLine();
	}

	@Override
	public void onAuthentificated() {

		while(true) {
			
			@SuppressWarnings("resource")
			String message = new Scanner(System.in).nextLine();
			sendDataPackage(new MessageDataPackage(message));
		}
	}
}
