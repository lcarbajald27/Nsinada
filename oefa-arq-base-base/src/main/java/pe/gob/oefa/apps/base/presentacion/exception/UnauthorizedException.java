package pe.gob.oefa.apps.base.presentacion.exception;

public class UnauthorizedException extends RuntimeException 
{
	
	private static final long serialVersionUID = 7243449882242228019L;

	public UnauthorizedException() 
	{
		this("Unauthorized!");
	}

	public UnauthorizedException(String message) 
	{
		this(message, null);
	}

	public UnauthorizedException(String message, Throwable cause) 
	{
		super(message, cause);
	}
	
}