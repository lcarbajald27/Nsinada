package pe.gob.oefa.apps.base.webservice.rest.generico;

public class Acceso {
	private String elemento;
	private long padre;
	
	public Acceso() {
		super();
	}
	public Acceso(String elemento) {
		super();
		this.elemento = elemento;

	}

	public Acceso(String elemento, long padre) {
		super();
		this.elemento = elemento;
		this.padre = padre;
	}
	public String getElemento() {
		return elemento;
	}
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

	public long getPadre() {
		return padre;
	}

	public void setPadre(long padre) {
		this.padre = padre;
	}
	@Override
	public String toString() {
		return "Acceso [elemento=" + elemento + ", padre=" + padre + "]";
	}
	
	
}
