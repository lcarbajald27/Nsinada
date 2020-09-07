package pe.gob.oefa.ws.util;

import java.util.ResourceBundle;

public class ResourceUtil {

	private static final String DEFAULT_BUNDLE_NAME = "config";
	
	public static  String getKey(String key, String bundle) {
		ResourceBundle rb = ResourceBundle.getBundle(bundle);
		String valor = rb.getString(key);
		return valor;
	}
	
	public static  String getKey(String key) {
		return getKey(key, DEFAULT_BUNDLE_NAME);
	}
}
