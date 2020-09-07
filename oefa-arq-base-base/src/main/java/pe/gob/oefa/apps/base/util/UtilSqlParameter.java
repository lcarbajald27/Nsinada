package pe.gob.oefa.apps.base.util;

public class UtilSqlParameter
{
	public static String toLikeString(String parametro)
	{
		return UtilValidator.isNullOrEmpty(parametro) ? "%%" : "%"+parametro+"%";
	}
	
	public static String toNotEmptyString(Object obj)
	{
		return UtilValidator.isNullOrEmpty(obj) ? "" : obj.toString();
	}
}
