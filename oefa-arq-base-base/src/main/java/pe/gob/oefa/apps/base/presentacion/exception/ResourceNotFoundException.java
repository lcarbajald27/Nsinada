package pe.gob.oefa.apps.base.presentacion.exception;

public class ResourceNotFoundException extends RuntimeException 
{
	
	private static final long serialVersionUID = 449971587231274127L;

	public ResourceNotFoundException() 
	{
		this("Resource not found!");
	}

	public ResourceNotFoundException(String message) 
	{
		this(message, null);
	}

	public ResourceNotFoundException(String message, Throwable cause) 
	{
		super(message, cause);
	}
	
}