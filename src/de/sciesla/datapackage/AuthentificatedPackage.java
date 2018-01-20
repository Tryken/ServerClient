package de.sciesla.datapackage;

import de.sciesla.client.Client;
import de.sciesla.sender.Sender;

public class AuthentificatedPackage extends DataPackage{


	private static final long serialVersionUID = 172899783019486620L;

	public AuthentificatedPackage() {
		super("_Authentificated");
	}

	@Override
	public void onServer(Sender sender) {

	}

	@Override
	public void onClient(Sender sender) {
		Client client = Client.getInstance();
		client.setAuthentificated(true);
		
		new Thread() {
			@Override
			public void run() {		
				client.onAuthentificated();
			}
		}.start();
	}
}
