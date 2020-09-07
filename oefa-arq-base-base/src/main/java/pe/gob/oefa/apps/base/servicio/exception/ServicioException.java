package pe.gob.oefa.apps.base.servicio.exception;

public class ServicioException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public ServicioException(){
		
	}
	
	public ServicioException(String message) {
		super(message);
	}
	
	public ServicioException(Throwable cause){
		super(cause);
		System.err.println("ServiceException:\n"+cause.getMessage());
	}
	
	public ServicioException(String message,Throwable cause){
		super(message, cause);
	}

}

