package dispositivo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MySimpleLogger {
	
	public static final int sender_spacing = 17;
	
	public static void info(String sender, String msg) {
		System.out.println(putCurrentTimestamp() + " INFO  [" + formatWithSpaces(sender,sender_spacing) + "] " + msg);
	}

	public static void debug(String sender, String msg) {
		System.out.println(putCurrentTimestamp() + " DEBUG [" + formatWithSpaces(sender,sender_spacing) + "] " + msg);
	}

	public static void warn(String sender, String msg) {
		System.out.println(putCurrentTimestamp() + " WARN  [" + formatWithSpaces(sender,sender_spacing) + "] " + msg);
	}

	public static void error(String sender, String msg) {
		System.out.println(putCurrentTimestamp() + " ERROR [" + formatWithSpaces(sender,sender_spacing) + "] " + msg);
	}

	public static void trace(String sender, String msg) {
		System.out.println(putCurrentTimestamp() + " TRACE [" + formatWithSpaces(sender,sender_spacing) + "] " + msg);
	}

	protected static String formatWithSpaces(String msg, int size) {
		if ( size <= msg.length() )
			return msg;

		int n = size - msg.length();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < n; i++) builder.append(" ");
		builder.append(msg);

		return builder.toString();
	}
	
	protected static String putCurrentTimestamp() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
	}
}
