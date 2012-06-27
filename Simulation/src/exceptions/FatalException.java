package exceptions;

import tools.log.LogUtil;

public class FatalException extends RuntimeException{
private static LogUtil log = new LogUtil(WarningException.class);
	
	public FatalException(String msg) {
		super("Fatal: "+msg);
		log.error(msg);
		this.printStackTrace();
		System.exit(99);
	}
}
