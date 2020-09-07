package pe.gob.oefa.apps.base.presentacion.exception;

public class InternalServerErrorException extends RuntimeException 
{
	
	private static final long serialVersionUID = -6374323756951607995L;

	public InternalServerErrorException() 
	{
		this("Internal Server Error was happened!");
	}

	public InternalServerErrorException(String message) 
	{
		this(message, null);
	}

	public InternalServerErrorException(String message, Throwable cause) 
	{
		super(message, cause);
	}
}