package de.sciesla.datapackage;

import de.sciesla.sender.Sender;

public class KickDataPackage extends DataPackage{


	private static final long serialVersionUID = 172899783019486620L;

	public KickDataPackage(String reason) {
		super("_Kick", reason);
	}

	@Override
	public void onServer(Sender sender) {

	}

	@Override
	public void onClient(Sender sender) {
		System.out.println(getString(0));
	}
}
