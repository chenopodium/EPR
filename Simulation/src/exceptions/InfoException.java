package exceptions;

import tools.log.LogUtil;

public class InfoException extends RuntimeException{
	private static LogUtil log = new LogUtil(InfoException.class);
	
	public InfoException(String msg) {
		super("Info: "+msg);
		log.info(msg);
	}

}
