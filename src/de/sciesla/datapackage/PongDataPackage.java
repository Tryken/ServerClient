package de.sciesla.datapackage;

import de.sciesla.sender.Sender;

public class PongDataPackage extends DataPackage{

	private static final long serialVersionUID = 3882915236106582186L;

	public PongDataPackage(long time) {
		super("_Pong", time);
	}

	@Override
	public void onServer(Sender sender) {
		System.out.println("Pong");
	}

	@Override
	public void onClient(Sender sender) {
		System.out.println("Pong");
	}
}
