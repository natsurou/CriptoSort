/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.natsurou.criptosort;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.nio.file.StandardOpenOption.*;
import net.natsurou.criptosort.lib.Decoder;
import net.natsurou.criptosort.lib.Encoder;
import net.natsurou.criptosort.lib.errors.SemanticError;
import net.natsurou.criptosort.lib.structures.EncodedFile;
import net.natsurou.criptosort.lib.parser.Parser;
import net.natsurou.criptosort.lib.parser.Scanner;
import net.natsurou.criptosort.lib.structures.SourceFile;

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
        int i;
        switch (args[0]) {
            case "d":
                i = 1;
                outputFileName = noExt(args[args.length-1])+".crs";
                while(i<args.length) {
                    if(args[i].startsWith("-")) {
                        if (args[i].equals("-o")) {
                            i++;
                            if(i<args.length&&isSane(args[i])) {
                                outputFileName = args[i];
                            } else {
                                System.out.println(messages.getString("bad_syntax"));
                                System.out.format(messages.getString("usage"), getAppName());
                            }
                        }
                    } else {
                        parse(args[i]);
                    }
                    i++;
                }
                break;
            case "e":
                i = 1;
                outputFileName = noExt(args[args.length-1])+".out";
                while(i<args.length) {
                    if(args[i].startsWith("-")) {
                        if (args[i].equals("-o")) {
                            i++;
                            if(i<args.length&&isSane(args[i])) {
                                outputFileName = args[i];
                            } else {
                                System.out.println(messages.getString("bad_syntax"));
                                System.out.format(messages.getString("usage"), getAppName());
                            }
                        }
                    } else {
                        encode(args[i]);
                    }
                    i++;
                }
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
        
        appProps = new Properties(defaultProps);
        try{
            in = new FileInputStream(appPropsPath);
            appProps.load(in);
            in.close();
        } catch (IOException ex) {
            System.err.println(messages.getString("app_props_not_found"));
        }

    }
    
    private static void saveProperties(String comment) throws IOException {
        try (FileOutputStream out = new FileOutputStream(appPropsPath)) {
            appProps.store(out, comment);
        }
    }
    
    private static void saveProperties() throws IOException {
        saveProperties("---No Comment---");
    }
    
    private static String outputFileName;
    private static String appPropsPath = System.getProperty("user.dir")
            +System.getProperty("file.separator") +"CriptoSort.properties";
    private static ResourceBundle messages;
    private static Properties defaultProps,appProps;

    private static void parse(String filename) {
        SourceFile sourceFile = new SourceFile(filename);
        Scanner scanner = new Scanner(sourceFile);
        Parser parser = new Parser(scanner);
        EncodedFile efile = parser.parseCode();
        Decoder decoder = new Decoder(efile);
        decoder.decode();
        String buffer = "";
        for (int i = 0; i < decoder.getNumberOfLines(); i++) {
            buffer+=decoder.getLine(i)+System.lineSeparator();
        }
        try {
            Files.write(Paths.get(outputFileName),
                    buffer.getBytes(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.WRITE);
        } catch (IOException ex) {
            Logger.getLogger(CriptoSort.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private static void encode(String filename) {
        SourceFile sourceFile = new SourceFile(filename);
        Encoder encoder = new Encoder(sourceFile);
        try {
            Files.write(Paths.get(outputFileName),
                    encoder.encode().dump().toString().getBytes(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.WRITE);
        } catch (SemanticError | IOException ex) {
            Logger.getLogger(CriptoSort.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private static boolean isSane(String string) {
        return true; //not yet implemented
    }

    private static String noExt(String string) {
        int i;
        for (i = string.length()-1; i> 0; i--) {
            if(string.charAt(i) == '.') {
                break;
            }
        }
        return string.substring(0, i);
    }
}
