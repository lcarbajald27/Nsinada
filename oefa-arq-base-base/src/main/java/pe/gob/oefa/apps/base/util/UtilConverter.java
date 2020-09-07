package pe.gob.oefa.apps.base.util;

public class UtilConverter
{
	public static int toInt(String obj)
	{
		if (UtilValidator.isNullOrEmpty(obj)) return 0;
		return Integer.valueOf(obj);
	}
	
	public static String toString(Object obj)
	{
		if (UtilValidator.isNullOrEmpty(obj)) return "";
		return String.valueOf(obj);
	}
	
}
