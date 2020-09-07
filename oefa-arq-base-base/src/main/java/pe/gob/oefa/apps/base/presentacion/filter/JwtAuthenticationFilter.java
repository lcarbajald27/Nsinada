package pe.gob.oefa.apps.base.presentacion.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.gob.oefa.apps.base.util.UtilValidator;

//@WebFilter(value="/rest/api/*")
public class JwtAuthenticationFilter implements Filter
{

	private static final java.util.logging.Logger LOG = Logger.getLogger( JwtAuthenticationFilter.class.getName() );

    private static final String AUTH_HEADER_KEY = "Authorization";
    private static final String AUTH_HEADER_VALUE_PREFIX = "Basic "; // with trailing space to separate token

    private static final int STATUS_CODE_UNAUTHORIZED = 401;


	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		LOG.info( "JwtAuthenticationFilter initialized" );
		
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, 
						 final ServletResponse servletResponse,
						 final FilterChain filterChain) throws IOException, ServletException
	{
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		
		boolean loggedIn = false;
		
		try
		{
			String token = getBasicToken(httpRequest);
			if (!UtilValidator.isEmpty(token))
			{
				httpRequest.login(token, "");
				loggedIn = true;
				LOG.info( "Logged in using JWT" );
			}
			else
			{
				LOG.info( "No JWT provided, go on unauthenticated" );
			}
			filterChain.doFilter(servletRequest, servletResponse);
			
			if (loggedIn)
			{
				httpRequest.logout();
				LOG.info( "Logged out" );
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			LOG.log( Level.WARNING, "Failed logging in with security token", e );
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setContentLength( 0 );
            httpResponse.setStatus( STATUS_CODE_UNAUTHORIZED );
		}
	}
	
	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		LOG.info( "JwtAuthenticationFilter destroyed" );
	}

	private String getBasicToken(HttpServletRequest request)
	{
		String authHeader = request.getHeader(AUTH_HEADER_KEY);
		if (authHeader!=null && authHeader.startsWith(AUTH_HEADER_VALUE_PREFIX))
		{
			return authHeader.substring(AUTH_HEADER_VALUE_PREFIX.length());
		}
		return null;
	}
}
