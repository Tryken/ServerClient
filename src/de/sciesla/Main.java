package de.sciesla;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		
		if(new Scanner(System.in).nextLine().equals("s")){
		
			ChatServer server = new ChatServer(25565, "test1234", 32);
			server.start();
		}else{

			ChatClient client = new ChatClient("localhost", 25565);
			client.connect();
		}
	}

}
