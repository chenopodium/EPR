package tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import tools.exceptions.FatalException;
import tools.exceptions.WarningException;
import tools.log.LogUtil;

public class FileUtils {

	private static LogUtil log = new LogUtil(FileUtils.class);
	
	public static void copyFile(String srFile, String dtFile){
		 File f1 = new File(srFile);
	      File f2 = new File(dtFile);
	      copyFile(f1, f2);
	}
	
	
	public static String getDir(String title, File val) {
		JFileChooser cc = new JFileChooser();
		if (val != null) {
			cc.setSelectedFile(val);
			cc.setCurrentDirectory(val);
		
		}
		cc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		cc.setDialogTitle(title);
		cc.setAcceptAllFileFilterUsed(false);
		cc.setVisible(true);
		String res = val.toString();
		
		int ans=cc.showOpenDialog(null);
		if (ans == JOptionPane.OK_OPTION) {
			File dir = cc.getSelectedFile();			
			if (!dir.isDirectory()) {
				JOptionPane.showMessageDialog(null, dir+" does not look like a directory - please select a directory");
				//f.get
			}
			else {
				res  = dir.getAbsolutePath();
				if (res.endsWith("/")) res += "/";				
			}			
		}
		return res;
	}
	public static boolean copyFile(File f1, File f2){
		p("Copying file "+f1+" to "+f2);
	    try{
	      
	      InputStream in = new FileInputStream(f1);
	      
	      //For Append the file.
//	      OutputStream out = new FileOutputStream(f2,true);

	      //For Overwrite the file.
	      OutputStream out = new FileOutputStream(f2);

	      byte[] buf = new byte[1024];
	      int len;
	      while ((len = in.read(buf)) > 0){
	        out.write(buf, 0, len);
	      }
	      in.close();
	      out.close();
	      p("File copied.");
	    }
	    catch(FileNotFoundException ex){
	      ex.printStackTrace();
	      return false;
	      
	    }
	    catch(IOException e){
	      e.printStackTrace();
	      return false;
	    }
	    return true;
	}
	    
	
	/** Returns the content of a file as a string.
	
	 */
	public static String getFileAsString(String filename) {
		//	p("getFileAsString: File is "+filename);
	    if (filename == null) {
	        new WarningException("Filename is null");
	        return null;
	    }
		StringBuffer res = new StringBuffer();

		try {

			BufferedReader in = new BufferedReader(new FileReader(filename));

			while (in.ready()) {
				// res.append((char)in.read());
				res.append(in.readLine());
				res.append("\n");
			}
			in.close();
			//		p("getFileAsString: Result is:\n"+res);
		} catch (FileNotFoundException e) {
			e.printStackTrace();			
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return res.toString();
	}
	public static ArrayList<String> getFileAsArray(String filename) {
		//	p("getFileAsString: File is "+filename);
	    if (filename == null) {
	        new WarningException("Filename is null");
	        return null;
	    }
	    ArrayList<String> res = new  ArrayList<String> ();
	  
		try {

			BufferedReader in = new BufferedReader(new FileReader(filename));

			while (in.ready()) {
				// res.append((char)in.read());
				String line = in.readLine();
				if (line != null) line = line.trim();
				else line = "";
				res.add(line);
				
			}
			in.close();
			//		p("getFileAsString: Result is:\n"+res);
		} catch (FileNotFoundException e) {
			e.printStackTrace();			
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return res;
	}
	public static ArrayList<String> getFileAsArray(URL filename) {
		//	p("getFileAsString: File is "+filename);
	    if (filename == null) {
	        new WarningException("URL is null");
	        return null;
	    }
	    ArrayList<String> res = new  ArrayList<String> ();
	  
		try {
			URLConnection uc= filename.openConnection();
			InputStreamReader input = new InputStreamReader(uc.getInputStream());
			BufferedReader in = new BufferedReader(input);

			while (in.ready()) {
				// res.append((char)in.read());
				String line = in.readLine();
				if (line != null) line = line.trim();
				else line = "";
				res.add(line);
				
			}
			in.close();
			//		p("getFileAsString: Result is:\n"+res);
		} catch (FileNotFoundException e) {
			e.printStackTrace();			
		} catch (IOException e) {
			e.printStackTrace();			
		}
		return res;
	}
	public static boolean writeStringToFile(String filename, String content) {
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			fout.print(content);
			fout.flush();
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		return false;
	}
	public static boolean writeStringToFile(String filename, StringBuffer content) {
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			fout.print(content);
			fout.flush();
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		return false;
	}
	public static boolean writeArrayToFile(String filename, ArrayList<String> content) {
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			for (int i = 0; i < content.size(); i++) {
				fout.println(content.get(i));
			}
			fout.flush();
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		return false;
	}
	
	public static boolean writeArrayToFile(String filename, int[] content) {
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			for(int i = 0; i < content.length; i++) {
				fout.print(content[i]+" ");
			}
			fout.flush();
			fout.close();
			return true;
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " not found");
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		return false;
	}
	public static byte[] getFileAsBytes(String filename) {
		
		File f = new File(filename);
		byte[] res = new byte[(int) f.length()];

		try {

			BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
			int i = 0;
			while (in.available()>0) {
				
				res[i++] = (byte) in.read();
			
			}
			in.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return res;
	}
	// **********************************************************************
	// FILE INPUT AND OUTPUT
	// **********************************************************************

	public static String readLine(BufferedReader reader) {
		StringBuffer buf = new StringBuffer();

		try {
			return reader.readLine();
		} catch (Exception e) {}
		return null;
	}

	public static BufferedReader openFileToRead(String filename) {
		BufferedReader reader = null;
		if (filename == null) {
			return null;
		}
		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reader;
	}


	public static boolean appendStringToFile(String filename, String content) {
	    BufferedWriter out = null ;
		try {
		     out = new BufferedWriter(new FileWriter(filename, true));	
		     out.write(content);
		     out.flush();
			 out.close();
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}
	public static boolean appendStringToFile(PrintWriter out, String content) {
		if (out == null || content == null) {
		    err("No content or no out writer");
			return false;
		}
		try {
			out.print(content);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static PrintWriter openFileToWrite(String filename) {
		PrintWriter out = null ;
		try {
			try {
				out = new PrintWriter( 
				        new BufferedWriter( new FileWriter (filename, true)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return out;
	}

	

	public static int[] getFileAsInts(File f) {
	//	p("getFileAsInts: File is "+f);
		//File f = new File(filename);
		int[] res = new int[(int) f.length()];

		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			int i = 0;
			while (in.ready()) {
				// res.append((char)in.read());
				String line = in.readLine();
				
				if (line != null && line.length()>0) {
					int integer = 0;
					try { integer = Integer.parseInt(line.trim()); }
					catch (Exception e) {
						e.printStackTrace();
					
					}
					res[i++] = integer;
				//	if (i % 10000 == 0) p("read line: "+line+" -> int "+res[i-1]);
				}
				
			}
			in.close();
		//	p("getFileAsInt: Result is:\n"+res);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return res;
	}
	
	
	public static String getFileAsString(BufferedReader in) {
	    if (in == null) return null;
		//	p("getFileAsString: File is "+filename);
		StringBuffer res = new StringBuffer();

		try {

			
			while (in.ready()) {
				// res.append((char)in.read());
				res.append(in.readLine());
				res.append("\n");
			}
			in.close();
			//		p("getFileAsString: Result is:\n"+res);
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO Exception");
		}
		return res.toString();
	}
	public static void closeFile(PrintWriter out) {
		try {
			out.flush();
			out.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private static void warn(String msg) {
		 new WarningException(msg);			
	}
	private static void p(String msg) {
		log.info(msg);
	}
	private static void err(String msg) {
		new FatalException(msg);
	}
	private static void fatal(String msg) {
		throw new FatalException(msg);
	}
	public static void closeFile(BufferedWriter out) {
		try {
			out.flush();
			out.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}

}
