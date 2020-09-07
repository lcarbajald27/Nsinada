package pe.gob.oefa.apps.base.presentacion.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JwtFilter extends GenericFilterBean
{

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException
	{
		// TODO Auto-generated method stub
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final String authHeader = request.getHeader("Authorization");
		if (authHeader==null || !authHeader.startsWith("Basic "))
		{
			throw new ServletException("Cabecera de Autorizacion invalida o no encontrada.");
		}
		
		final String token = authHeader.substring(6);//La parte siguiente a 'Bearer '
													 //						'Basic '
		try
		{
			final Claims claims = Jwts
									.parser()
									.setSigningKey("claveSecreta")
									.parseClaimsJws(token)
									.getBody();
			
			request.setAttribute("claims", claims);
		}
		catch (final SignatureException se)
		{
			// TODO: handle exception
			se.printStackTrace();
			throw new ServletException("Token invalido.");
		}
		
		filterChain.doFilter(servletRequest, servletResponse);
	}

}
