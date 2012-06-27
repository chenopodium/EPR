package tools.exceptions;

import tools.log.LogUtil;


public class WarningException extends RuntimeException{
	private static LogUtil log = new LogUtil(WarningException.class);
	
	public WarningException(String msg) {
		super("Warning: "+msg);
		log.warning(msg);
		this.printStackTrace();
	}

}
