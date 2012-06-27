/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.File;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.tools.java.ClassFile;
/*
 *
 * @author Chantal Roth
 */


public class InMemoryClassfile extends ClassFile
{
   private String filename;
   private String text;
 
   public InMemoryClassfile(String filename, String text) {
      super(new File(filename));
      this.filename = filename;
      this.text = text;
   }
 
   public String getAbsoluteName() {
      return filename;
   }
 
   public boolean exists() {
      return true;
   }
 
   public InputStream getInputStream() {
      return new StringBufferInputStream(text);
   }
 
   public String getName() {
      return filename;
   }
 
   public String getPath() {
      return "";
   }
 
   public boolean isDirectory() {
      return false;
   }
 
   public boolean isZipped() {
      return false;
   }
  
   public long lastModified() {
      return new Date().getTime();
   }
 
   public long length() {
      return text.length();
   }
 
   public String toString() {
      return filename;
   }
}
