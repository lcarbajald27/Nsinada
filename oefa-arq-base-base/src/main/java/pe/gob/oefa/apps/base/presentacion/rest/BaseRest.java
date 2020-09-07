package pe.gob.oefa.apps.base.presentacion.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pe.gob.oefa.apps.base.presentacion.exception.ErrorMessage;
import pe.gob.oefa.apps.base.presentacion.exception.InternalServerErrorException;
import pe.gob.oefa.apps.base.presentacion.exception.ResourceNotFoundException;
import pe.gob.oefa.apps.base.presentacion.exception.UnauthorizedException;
import pe.gob.oefa.apps.base.util.UtilConverter;
import pe.gob.oefa.apps.base.util.UtilValidator;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

@Controller
public class BaseRest
{
	@RequestMapping(value="/error", produces="application/json")
    @ResponseBody
    public Map<String, Object> handle(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        int status = (int)request.getAttribute("javax.servlet.error.status_code");
        map.put("status", status);
        String reason = "";
        switch (status)
		{
			case 400:
				reason = "Información incorrecta.";
				break;
			case 401: 
				reason = UtilConverter.toString(request.getAttribute("javax.servlet.error.message"));
				break;
			case 404 :
				reason = "Recurso solicitado no encontrado.";
				break;
			case 500:
				reason = UtilConverter.toString(request.getAttribute("javax.servlet.error.exception"));
			default:
				break;
		}
        map.put("reason", reason);
        /*
        //Enumeration enumeration = request.getParameterNames();
        Enumeration enumeration = request.getAttributeNames();
        Map<String, Object> modelMap = new HashMap<>();
        while(enumeration.hasMoreElements())
        {
            String atName = (String) enumeration.nextElement();
            //modelMap.put(atName, request.getAttribute(atName));
            System.out.println("atName ===:"+atName+"|");
            System.out.println("atName-value ===:"+request.getAttribute(atName)+"|");
        }
        //System.out.println("modelMap");
        //System.out.println(modelMap.toString());
        */
        return map;
    }
	/*
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)//401
	public ErrorMessage handleUnauthorizedException(UnauthorizedException e, HttpServletRequest req) {
	    return new ErrorMessage(e);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)//404
	public ErrorMessage handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest req) {
	    return new ErrorMessage(e);
	}

	@ExceptionHandler(InternalServerErrorException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//500
	public ErrorMessage handleInternalServerErrorException(InternalServerErrorException e, HttpServletRequest req) {
	    return new ErrorMessage(e);
	}*/

}
