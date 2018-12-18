/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Sergio
 */
public class PropertyUtils {
    
    private static Properties prop = null; 
    
    public static String get(String key){
        try{
            if(prop == null){
                prop = new Properties();
                String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                String configPath = rootPath + "config.properties";
                InputStream input = new FileInputStream( configPath );

		prop.load(input);
            }            
        }catch(IOException e){
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
    
}
