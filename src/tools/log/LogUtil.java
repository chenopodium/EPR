package tools.log;

import java.io.File;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;


public class LogUtil {
	//protected static String consolePattern_ = "%d{HH:mm} %5p: %m%n";
	//protected static String logPattern_ = "%d{MM/dd/yy HH:mm} %5p [%t]: %m%n";
	protected static String consolePattern_ = "%5p:";
	protected static String logPattern_ = "%d{MM/dd/yy HH:mm}: %m%n";
	
	protected static String logMaxFileSize_ = "100KB";
	protected static String logMaxBackupIndex_ = "1";
	static protected final int STACKSIZE = 10;
	private Logger logger;
	private String name;
	private static ILogListener listener;
	

	private static ConsoleAppender consoleAppender_;
	private static Logger root = LogManager.getRootLogger();

	static {
		configure();
	}

	public LogUtil(String classname) {
		if (logger == null) {
			logger = Logger.getLogger(classname);
			init();
			
		}
	}
	private void init() {
		String full = logger.getName();
		int dot = full.lastIndexOf(".");
		if (dot > 0) name = full.substring(dot+1);
		else name = full;
		if (name.length()>10) {
			name = name.substring(0, 10);
		}
		//System.out.println("Full name:"+full+", name:" +name);
	}
	public LogUtil(Class clazz) {
		if (logger == null) {
			logger = Logger.getLogger(clazz);
			init();
			
		}
	}
	public void info(String msg) {
		logger.info(name+":"+msg);
		if (listener != null) listener.log(" "+name+":"+msg);
	}
	public void warning(String msg) {
		logger.warn(name+":"+msg);
		if (listener != null) listener.log("?"+name+":"+msg);
	}
	public void error(String msg) {
		logger.error(name+":"+msg);
		if (listener != null) listener.log("!"+name+":"+msg);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public ILogListener getListener() {
		return listener;
	}
	public void setListener(ILogListener listener) {
		this.listener = listener;
	}
	private static void basicConfigure() {
		BasicConfigurator.configure();
		java.util.Enumeration appenders = root.getAllAppenders();
		while (appenders.hasMoreElements()) {
			Appender appender = (Appender) appenders.nextElement();
			if (appender instanceof ConsoleAppender) {
				appender.setLayout(new PatternLayout(consolePattern_));
				consoleAppender_ = (ConsoleAppender) appender;
			}
		}
	}

	private static void configure() {

		// basicConfigure();
		String configfile = "config/log4j.properties";

		if (configfile != null) {
			File f = new File(configfile);
			if (f.exists()) {
				PropertyConfigurator.configure(configfile);
			}
		}

		// rootLogger_.removeAppender(consoleAppender_);
		if (consoleAppender_ == null) {
			consoleAppender_ = new ConsoleAppender(new PatternLayout(
					consolePattern_));
		}
	}

	public void showAppenders() {
		Enumeration en = logger.getAllAppenders();
		out("Appenders for " + logger.getName());
		for (; en.hasMoreElements();) {
			AppenderSkeleton app = (AppenderSkeleton) en.nextElement();
			out(app.getClass().getName() + " -> " + app.getName());
		}
	}

	private static void out(String s) {
		System.out.println("Log: " + s);
	}
}
