package pe.gob.oefa.apps.base.webservice.rest.seguridad.perfil.bean;

import pe.gob.oefa.apps.base.webservice.rest.seguridad.BaseSeguridadBean;


public class PerfilBean extends BaseSeguridadBean{
   
    private long		FK_eIdAplicacion;
    private String 		Nombre_Aplicacion;
    private long		PK_eIdPerfil;
    private String 		uNombrePerfil;
    private boolean 	Existe;
    
	public PerfilBean() {
		super();
	}

	public long getFK_eIdAplicacion() {
		return FK_eIdAplicacion;
	}

	public void setFK_eIdAplicacion(long fK_eIdAplicacion) {
		FK_eIdAplicacion = fK_eIdAplicacion;
	}

	public String getNombre_Aplicacion() {
		return Nombre_Aplicacion;
	}

	public void setNombre_Aplicacion(String nombre_Aplicacion) {
		Nombre_Aplicacion = nombre_Aplicacion;
	}

	public long getPK_eIdPerfil() {
		return PK_eIdPerfil;
	}

	public void setPK_eIdPerfil(long pK_eIdPerfil) {
		PK_eIdPerfil = pK_eIdPerfil;
	}

	public String getuNombrePerfil() {
		return uNombrePerfil;
	}

	public void setuNombrePerfil(String uNombrePerfil) {
		this.uNombrePerfil = uNombrePerfil;
	}

	public boolean isExiste() {
		return Existe;
	}

	public void setExiste(boolean existe) {
		Existe = existe;
	}

	@Override
	public String toString() {
		return "PerfilBean [FK_eIdAplicacion=" + FK_eIdAplicacion
				+ ", Nombre_Aplicacion=" + Nombre_Aplicacion
				+ ", PK_eIdPerfil=" + PK_eIdPerfil + ", uNombrePerfil="
				+ uNombrePerfil + ", Existe=" + Existe + "]";
	}


   
}
