package pe.gob.oefa.apps.base.webservice.rest.seguridad.opcion.bean;

import pe.gob.oefa.apps.base.webservice.rest.seguridad.BaseSeguridadBean;


public class OpcionBean extends BaseSeguridadBean{
   
    private long		FK_eIdAplicacion;
    private long		PerfilOpcionID;
    private long		PK_eIdOpcion;
    private String 		uNombreOpcion;
    private String 		NombreGrupo;
    private String 		uDireccionOpcion;
    private String 		uTituloOpcion;
    private String 		uUrl;
    private String 		Accion;
    private String 		Area;
    private String 		Controladora;
    private long		eNroOrden;
    private boolean 	cEsVisible;
    private boolean 	Existe;

	public OpcionBean() {
		super();
	}

	public long getFK_eIdAplicacion() {
		return FK_eIdAplicacion;
	}

	public void setFK_eIdAplicacion(long fK_eIdAplicacion) {
		FK_eIdAplicacion = fK_eIdAplicacion;
	}

	
	public boolean isExiste() {
		return Existe;
	}

	public void setExiste(boolean existe) {
		Existe = existe;
	}

	public long getPerfilOpcionID() {
		return PerfilOpcionID;
	}

	public void setPerfilOpcionID(long perfilOpcionID) {
		PerfilOpcionID = perfilOpcionID;
	}

	public long getPK_eIdOpcion() {
		return PK_eIdOpcion;
	}

	public void setPK_eIdOpcion(long pK_eIdOpcion) {
		PK_eIdOpcion = pK_eIdOpcion;
	}

	public String getuNombreOpcion() {
		return uNombreOpcion;
	}

	public void setuNombreOpcion(String uNombreOpcion) {
		this.uNombreOpcion = uNombreOpcion;
	}

	public String getNombreGrupo() {
		return NombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		NombreGrupo = nombreGrupo;
	}

	public String getuDireccionOpcion() {
		return uDireccionOpcion;
	}

	public void setuDireccionOpcion(String uDireccionOpcion) {
		this.uDireccionOpcion = uDireccionOpcion;
	}

	public String getuTituloOpcion() {
		return uTituloOpcion;
	}

	public void setuTituloOpcion(String uTituloOpcion) {
		this.uTituloOpcion = uTituloOpcion;
	}

	public String getuUrl() {
		return uUrl;
	}

	public void setuUrl(String uUrl) {
		this.uUrl = uUrl;
	}

	public String getAccion() {
		return Accion;
	}

	public void setAccion(String accion) {
		Accion = accion;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getControladora() {
		return Controladora;
	}

	public void setControladora(String controladora) {
		Controladora = controladora;
	}

	public long geteNroOrden() {
		return eNroOrden;
	}

	public void seteNroOrden(long eNroOrden) {
		this.eNroOrden = eNroOrden;
	}

	public boolean iscEsVisible() {
		return cEsVisible;
	}

	public void setcEsVisible(boolean cEsVisible) {
		this.cEsVisible = cEsVisible;
	}

	@Override
	public String toString() {
		return "OpcionBean [FK_eIdAplicacion=" + FK_eIdAplicacion
				+ ", PerfilOpcionID=" + PerfilOpcionID + ", PK_eIdOpcion="
				+ PK_eIdOpcion + ", uNombreOpcion=" + uNombreOpcion
				+ ", NombreGrupo=" + NombreGrupo + ", uDireccionOpcion="
				+ uDireccionOpcion + ", uTituloOpcion=" + uTituloOpcion
				+ ", uUrl=" + uUrl + ", Accion=" + Accion + ", Area=" + Area
				+ ", Controladora=" + Controladora + ", eNroOrden=" + eNroOrden
				+ ", cEsVisible=" + cEsVisible + ", Existe=" + Existe + "]";
	}
}
