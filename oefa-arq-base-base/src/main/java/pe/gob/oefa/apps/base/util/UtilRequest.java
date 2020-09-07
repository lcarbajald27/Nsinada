package pe.gob.oefa.apps.base.util;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UtilRequest
{
	public static String getClientIpAddress(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;
	}
	
	public static String getBasicPath() {
		String rs = "";
		try {
			ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = sra.getRequest();
			URL reconstructedURL = new URL(request.getScheme(),
	                request.getServerName(),
	                request.getServerPort(),
	                //"");
					request.getContextPath());
			
			rs = reconstructedURL.toString();
		} catch (Exception e) {
			e.getMessage();
			rs = null;
		}
		return rs;
	}
	
}
