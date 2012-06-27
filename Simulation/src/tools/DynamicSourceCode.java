/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.SimpleJavaFileObject;

/**
 *
 * @author Chantal Roth
 */
public    class DynamicSourceCode extends SimpleJavaFileObject{
    private String qualifiedName ;
    private String sourceCode ;
 
    /**
     * Converts the name to an URI, as that is the format expected by JavaFileObject
     *
     *
     * @param fully qualified name given to the class file
     * @param code the source code string
     */
    protected DynamicSourceCode(String name, String code) {
        super(URI.create("string:///" +name.replaceAll(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.qualifiedName = name ;
        this.sourceCode = code ;
    }
 
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        return sourceCode ;
    }
 
    public String getQualifiedName() {
        return qualifiedName;
    }
 
    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
 
    public String getSourceCode() {
        return sourceCode;
    }
 
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    /** ================== LOGGING ===================== */
    private static void err(String msg, Exception ex) {
        Logger.getLogger(DynamicSourceCode.class.getName()).log(Level.SEVERE, msg, ex);
    }
    
    private void err(String msg) {
        
        Logger.getLogger(DynamicSourceCode.class.getName()).log(Level.SEVERE, msg);
    }
    
    private static void warn(String msg) {
        Logger.getLogger(DynamicSourceCode.class.getName()).log(Level.WARNING, msg);
    }
    
    private static void p(String msg) {
        System.out.println("DynamicSourceCode: " + msg);
        //Logger.getLogger( DynamicSourceCode.class.getName()).log(Level.INFO, msg);
    }
}
