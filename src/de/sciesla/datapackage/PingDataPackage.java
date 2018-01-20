package de.sciesla.datapackage;

import de.sciesla.sender.Sender;

public class PingDataPackage extends DataPackage{

	private static final long serialVersionUID = 3882915236106582186L;

	public PingDataPackage() {
		super("_Ping");
	}

	@Override
	public void onServer(Sender sender) {
		sender.sendDataPackage(new PongDataPackage(System.currentTimeMillis()));
	}

	@Override
	public void onClient(Sender sender) {
		sender.sendDataPackage(new PongDataPackage(System.currentTimeMillis()));
	}
}
