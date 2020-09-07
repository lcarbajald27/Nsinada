package pe.gob.oefa.apps.base.webservice.rest.seguridad.operacion.bean;

import pe.gob.oefa.apps.base.webservice.rest.seguridad.BaseSeguridadBean;


public class OperacionBean extends BaseSeguridadBean{
   
	private long		FK_eIdOpcion;
	private long		PK_eIdOperacion;
	private long		eIdOperacionRef;
	private long		eIdTipoOperacionOpcion;
    private String 		uNombreOperacion;
    private String 		NombreNodo;
    private String 		NombreOpcion;
    
	public OperacionBean() {
		super();
	}

	public long getFK_eIdOpcion() {
		return FK_eIdOpcion;
	}

	public void setFK_eIdOpcion(long fK_eIdOpcion) {
		FK_eIdOpcion = fK_eIdOpcion;
	}

	public long getPK_eIdOperacion() {
		return PK_eIdOperacion;
	}

	public void setPK_eIdOperacion(long pK_eIdOperacion) {
		PK_eIdOperacion = pK_eIdOperacion;
	}

	public long geteIdOperacionRef() {
		return eIdOperacionRef;
	}

	public void seteIdOperacionRef(long eIdOperacionRef) {
		this.eIdOperacionRef = eIdOperacionRef;
	}

	public long geteIdTipoOperacionOpcion() {
		return eIdTipoOperacionOpcion;
	}

	public void seteIdTipoOperacionOpcion(long eIdTipoOperacionOpcion) {
		this.eIdTipoOperacionOpcion = eIdTipoOperacionOpcion;
	}

	public String getuNombreOperacion() {
		return uNombreOperacion;
	}

	public void setuNombreOperacion(String uNombreOperacion) {
		this.uNombreOperacion = uNombreOperacion;
	}

	public String getNombreNodo() {
		return NombreNodo;
	}

	public void setNombreNodo(String nombreNodo) {
		NombreNodo = nombreNodo;
	}

	public String getNombreOpcion() {
		return NombreOpcion;
	}

	public void setNombreOpcion(String nombreOpcion) {
		NombreOpcion = nombreOpcion;
	}


	@Override
	public String toString() {
		return 
				super.toString()
				+"OperacionBean [FK_eIdOpcion=" + FK_eIdOpcion
				+ ", PK_eIdOperacion=" + PK_eIdOperacion + ", eIdOperacionRef="
				+ eIdOperacionRef + ", eIdTipoOperacionOpcion="
				+ eIdTipoOperacionOpcion + ", uNombreOperacion="
				+ uNombreOperacion + ", NombreNodo=" + NombreNodo
				+ ", NombreOpcion=" + NombreOpcion +  "]";
	}

}
