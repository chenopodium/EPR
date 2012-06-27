/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;

/**
 *
 * @author Chantal Roth
 */
public class ByteJavaFileManager extends ForwardingJavaFileManager{

    private Map<String, JavaFileObject> store = new HashMap<String, JavaFileObject>();

    public ByteJavaFileManager(StandardJavaFileManager fileManager, Map<String, JavaFileObject> str) {
        super(fileManager);
        store = str;
    }

    public JavaFileObject getJavaFileForOutput(Location location,
            String className, Kind kind, FileObject sibling)
            throws IOException {
        try {
            ByteArrayJFO jfo = new ByteArrayJFO(className, kind);
            store.put(className, jfo);
            return jfo;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public class ByteArrayJFO extends SimpleJavaFileObject {

        private ByteArrayOutputStream baos = null;
       

        private ByteArrayJFO(String className, Kind kind) throws URISyntaxException {
             super(new URI(className), kind);
        }

        public InputStream openInputStream() throws IOException // the input stream to the java file object accepts bytes 
        {
            return new ByteArrayInputStream(baos.toByteArray());
        }

        public OutputStream openOutputStream() throws IOException // the output stream supplies bytes 
        {
            return baos = new ByteArrayOutputStream();
        }

        public byte[] getByteArray() // access the byte output stream as an array 
        {
            return baos.toByteArray();
        }
    } 
}
