/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 至朗
 */
public class CriptoSort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        init();
        if(args.length<2) {
            System.out.format(messages.getString("usage"), getAppName());
            System.exit(0);
        }
        switch (args[0]) {
            case "d":
                int i = 1;
                while(i<args.length) {
                    if(args[i].startsWith("-")) {
                    }
                }
                break;
            case "e":
                break;
            case "c":
                break;
            case "gui":
                break;
            default:
                System.out.println(messages.getString("bad_syntax"));
                System.out.format(messages.getString("usage"), getAppName());
                break;
        }
    }
    
    public static void init() {
        messages = ResourceBundle.getBundle("net.natsurou.criptosort.MessagesBundle");
        loadProperties();
    }
    
    public static String getAppName() {
        Path path;
        path = FileSystems.getDefault().getPath(
                net.natsurou.criptosort.CriptoSort.class.getProtectionDomain().
                getCodeSource().getLocation().toString().substring(8));
        String decodedPath;
        try {
            decodedPath = URLDecoder.decode(path.getFileName().toString(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            decodedPath = path.getFileName().toString();
        }
        return decodedPath;
    }

    private static void loadProperties() {
        FileInputStream in = null;
        
        try{
            defaultProps = new Properties();
            defaultProps.load(CriptoSort.class.getResourceAsStream("defaultProps.properties"));
        } catch (IOException ex) {
            Logger.getLogger(CriptoSort.class.getName()).log(
                    Level.SEVERE, messages.getString("defaultprops_load_error"), ex);
            System.exit(1);
        }
        
        applicationProps = new Properties(defaultProps);
        try{
            in = new FileInputStream(appPropsPath);
            applicationProps.load(in);
            in.close();
        } catch (IOException ex) {
            System.err.println(messages.getString("app_props_not_found"));
        }

    }
    
    private static void saveProperties() throws IOException {
        FileOutputStream out = new FileOutputStream(appPropsPath);
        applicationProps.store(out, "---No Comment---");
        out.close();
    }
    private static String appPropsPath = System.getProperty("user.dir")
            +System.getProperty("file.separator") +"CriptoSort.properties";
    private static ResourceBundle messages;
    private static Properties defaultProps,applicationProps;
}
