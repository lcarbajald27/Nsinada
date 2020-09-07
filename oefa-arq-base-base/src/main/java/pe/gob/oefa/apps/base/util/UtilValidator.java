package pe.gob.oefa.apps.base.util;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class UtilValidator
{
	public static boolean isNullOrEmpty(Object obj) {
		return isNull(obj) || isEmpty(obj);
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	public static boolean isEmpty(Object obj) {
		return (obj.toString().trim().length() == 0);
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isEmptyList(List obj) {
		return CollectionUtils.isEmpty(obj);
		//return (obj == null) || (obj.size() == 0);
	}	
	
	public static boolean isObjectsEquals(Object obj,Object object) {
		return obj.equals(object);
	}
	public static boolean isEquals(String obj,String object) {
		return obj.equals(object);
	}
	
	public static boolean isValidId(Object id) {
		if (id instanceof Long) {
			return !isNull(id) && (Long)id>0;	
		}
		if (id instanceof Integer) {
			return !isNull(id) && (Integer)id>0;
		}
		if (id instanceof String) {
			return !isNull(id) && (((String)id).trim()!="0" && ((String)id).trim()!="");
		}
		return false;
	}
}
