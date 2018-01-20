package de.sciesla.datapackage;

import de.sciesla.sender.Sender;
import de.sciesla.server.Server;
import de.sciesla.server.logger.LogType;
import de.sciesla.server.logger.Logger;

public class MessageDataPackage extends DataPackage{

	private static final long serialVersionUID = 3882915236106582186L;

	public MessageDataPackage(String msg) {
		super("_Message", msg);
	}

	@Override
	public void onServer(Sender sender) {
		if(getString(0).equalsIgnoreCase(""))
			return;
		
		String msg = sender.getUserName() + ": " +  getString(0);
		Server.getInstance().broadcastDataPackage(new MessageDataPackage(msg));
		Logger.log(LogType.INFO, msg);
	}

	@Override
	public void onClient(Sender sender) {
		System.out.println(getString(0));
	}
}
