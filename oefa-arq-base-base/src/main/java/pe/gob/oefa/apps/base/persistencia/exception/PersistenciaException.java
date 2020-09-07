package pe.gob.oefa.apps.base.persistencia.exception;

public class PersistenciaException extends Exception{

	private static final long serialVersionUID = 1L;

	public PersistenciaException() {
		
	}

	public PersistenciaException(String arg0) {
		super(arg0);
		
	}

	public PersistenciaException(Throwable arg0) {
		super(arg0);
		System.err.println("----PersistenciaException----:\n"+arg0.getMessage());
	}

	public PersistenciaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}
}

