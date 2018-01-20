package de.sciesla.server.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface Logger {

	public static void log(LogType type, String message) {
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		System.out.println(time + " " + type.getName() + " " + message);
	}
}
