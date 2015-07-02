package com.unlam.uno.code;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private String user = "-";
	private FileWriter file = null;
	private PrintWriter pw = null;
	private static Logger instance = new Logger();
		
	//Constructor privado para singleton
	private Logger() {
	}
	
	//Instancia del singleton
	public static Logger getInstance() {
		return instance;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	/** Set message in file
	 * 
	 * @param type specific type of message (Info, warn, error)
	 * @param msg Message put to file
	 */
	private void logMessage(String type, String msg) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Date date = new Date();
		String message = type + " [" + dateFormat.format(date) + "] [" + this.user + "] " + msg;
		try {
			this.file = new FileWriter("resources/log", true);
			this.pw = new PrintWriter(file);
			pw.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null)
				pw.close();
		}
	}
	
	/** Information message
	 * 
	 * @param msg Message put to file
	 */
	public void info(String msg) {
		logMessage("INFO", msg);
	}

	/** Warning message
	 * 
	 * @param msg Message put to file
	 */
	public void warn(String msg) {
		logMessage("WARN", msg);
	}
	
	/** Error message
	 * 
	 * @param msg Message put to file
	 */
	public void error(String msg) {
		logMessage("ERROR", msg);
	}
}
