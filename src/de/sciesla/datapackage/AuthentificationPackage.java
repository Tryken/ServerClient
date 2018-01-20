package de.sciesla.datapackage;

import de.sciesla.client.Client;
import de.sciesla.sender.Sender;
import de.sciesla.server.Server;

public class AuthentificationPackage extends DataPackage{


	private static final long serialVersionUID = 172899783019486620L;

	public AuthentificationPackage() {
		super("_Authentification");
	}
	
	public AuthentificationPackage(String password) {
		super("_Authentification", password);
	}

	@Override
	public void onServer(Sender sender) {
		if(getLength() == 1) {
			
			Server server = Server.getInstance();
			if(server.isPasswordCorrect(getString(0))) {
				
				sender.getConnection().setAuthentificated(true);
			} else {
				
				sender.sendDataPackage(new AuthentificationPackage());
			}
		} else {
			
			sender.sendDataPackage(new AuthentificationPackage());
		}
	}

	@Override
	public void onClient(Sender sender) {
		Client client = Client.getInstance();
		String password = client.onPasswordRequired();
		sender.sendDataPackage(new AuthentificationPackage(password));
	}
}
