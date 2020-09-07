package pe.gob.oefa.apps.base.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;

import java.util.ResourceBundle;

public class UtilProperties {

//	private static InputStream is = UtilProperties.class.getResourceAsStream("/config.properties");
	private static final String BUNDLE_NAME = "config";
	private static final ResourceBundle RESOURCE_BUNDLE_NAME = ResourceBundle.getBundle(BUNDLE_NAME);

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE_NAME.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getKey(String key){		
		Properties props = new Properties();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream resourceStream = loader.getResourceAsStream("config.properties");
			props.load(resourceStream);//is);
			return props.getProperty(key);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getKey(String key,String cfg){		
		Properties props = new Properties();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream resourceStream = loader.getResourceAsStream(cfg);
			props.load(resourceStream);//is);
			return props.getProperty(key);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}