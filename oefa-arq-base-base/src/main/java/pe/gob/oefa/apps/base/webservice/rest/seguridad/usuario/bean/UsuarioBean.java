package pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean;

import java.util.Arrays;
import java.util.Date;

import pe.gob.oefa.apps.base.webservice.rest.base.bean.BaseBean;

public class UsuarioBean extends BaseBean {

	private boolean EsEdicion;
	private String MensajeError;
	private String TotalVirtual;
	private String ClaveTexto;
	private String FK_elIdEmpresa;
	private String NombreCompleto;
	private String ObservacionAnulacion;
	private String PK_eIdPerfil;
	private String PK_eIdUsuario;
	private String SegSSOMPerfil;
	private String Situacion_Descripcion;
	private String TipoAutenticacion_Descripcion;
	private String TipoPersona_Descripcion;
	private String[] bClave;
	private boolean cFlgActivo;
	private boolean cFlgCambiaClave;
	private boolean cFlgEliminado;
	private boolean cFlgInterno;
	private boolean cTieneExpiracionAutomatica;
	private boolean condicion;
	private String eCambioSituacion;
	private String eDiasCaducaClave;
	private String eDiasCaducaCuenta;
	private String eIdSesionIngresa;
	private String eIdSesionModifica;
	private String eIdTipoAutenticacionUsuario;
	private String eTipoMotivoSuspension;
	private String eTipoMotivoSuspensionTexto;
	private String eTipoPersonaboolean;
	private String fFechaExpiraCuenta;
	private String fFechaIngreso;
	private String fFechaModiifica;
	private String uCodUsuario;
	private String uCorElectronico;
	private String uNombrePerfil;
	
	public UsuarioBean() {
	}

	public boolean isEsEdicion() {
		return EsEdicion;
	}

	public void setEsEdicion(boolean esEdicion) {
		EsEdicion = esEdicion;
	}

	public String getMensajeError() {
		return MensajeError;
	}

	public void setMensajeError(String mensajeError) {
		MensajeError = mensajeError;
	}

	public String getTotalVirtual() {
		return TotalVirtual;
	}

	public void setTotalVirtual(String totalVirtual) {
		TotalVirtual = totalVirtual;
	}

	public String getClaveTexto() {
		return ClaveTexto;
	}

	public void setClaveTexto(String claveTexto) {
		ClaveTexto = claveTexto;
	}

	public String getFK_elIdEmpresa() {
		return FK_elIdEmpresa;
	}

	public void setFK_elIdEmpresa(String fK_elIdEmpresa) {
		FK_elIdEmpresa = fK_elIdEmpresa;
	}

	public String getNombreCompleto() {
		return NombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		NombreCompleto = nombreCompleto;
	}

	public String getObservacionAnulacion() {
		return ObservacionAnulacion;
	}

	public void setObservacionAnulacion(String observacionAnulacion) {
		ObservacionAnulacion = observacionAnulacion;
	}

	public String getPK_eIdPerfil() {
		return PK_eIdPerfil;
	}

	public void setPK_eIdPerfil(String pK_eIdPerfil) {
		PK_eIdPerfil = pK_eIdPerfil;
	}

	public String getPK_eIdUsuario() {
		return PK_eIdUsuario;
	}

	public void setPK_eIdUsuario(String pK_eIdUsuario) {
		PK_eIdUsuario = pK_eIdUsuario;
	}

	public String getSegSSOMPerfil() {
		return SegSSOMPerfil;
	}

	public void setSegSSOMPerfil(String segSSOMPerfil) {
		SegSSOMPerfil = segSSOMPerfil;
	}

	public String getSituacion_Descripcion() {
		return Situacion_Descripcion;
	}

	public void setSituacion_Descripcion(String situacion_Descripcion) {
		Situacion_Descripcion = situacion_Descripcion;
	}

	public String getTipoAutenticacion_Descripcion() {
		return TipoAutenticacion_Descripcion;
	}

	public void setTipoAutenticacion_Descripcion(String tipoAutenticacion_Descripcion) {
		TipoAutenticacion_Descripcion = tipoAutenticacion_Descripcion;
	}

	public String getTipoPersona_Descripcion() {
		return TipoPersona_Descripcion;
	}

	public void setTipoPersona_Descripcion(String tipoPersona_Descripcion) {
		TipoPersona_Descripcion = tipoPersona_Descripcion;
	}

	public String[] getbClave() {
		return bClave;
	}

	public void setbClave(String[] bClave) {
		this.bClave = bClave;
	}

	public boolean iscFlgActivo() {
		return cFlgActivo;
	}

	public void setcFlgActivo(boolean cFlgActivo) {
		this.cFlgActivo = cFlgActivo;
	}

	public boolean iscFlgCambiaClave() {
		return cFlgCambiaClave;
	}

	public void setcFlgCambiaClave(boolean cFlgCambiaClave) {
		this.cFlgCambiaClave = cFlgCambiaClave;
	}

	public boolean iscFlgEliminado() {
		return cFlgEliminado;
	}

	public void setcFlgEliminado(boolean cFlgEliminado) {
		this.cFlgEliminado = cFlgEliminado;
	}

	public boolean iscFlgInterno() {
		return cFlgInterno;
	}

	public void setcFlgInterno(boolean cFlgInterno) {
		this.cFlgInterno = cFlgInterno;
	}

	public boolean iscTieneExpiracionAutomatica() {
		return cTieneExpiracionAutomatica;
	}

	public void setcTieneExpiracionAutomatica(boolean cTieneExpiracionAutomatica) {
		this.cTieneExpiracionAutomatica = cTieneExpiracionAutomatica;
	}

	public boolean isCondicion() {
		return condicion;
	}

	public void setCondicion(boolean condicion) {
		this.condicion = condicion;
	}

	public String geteCambioSituacion() {
		return eCambioSituacion;
	}

	public void seteCambioSituacion(String eCambioSituacion) {
		this.eCambioSituacion = eCambioSituacion;
	}

	public String geteDiasCaducaClave() {
		return eDiasCaducaClave;
	}

	public void seteDiasCaducaClave(String eDiasCaducaClave) {
		this.eDiasCaducaClave = eDiasCaducaClave;
	}

	public String geteDiasCaducaCuenta() {
		return eDiasCaducaCuenta;
	}

	public void seteDiasCaducaCuenta(String eDiasCaducaCuenta) {
		this.eDiasCaducaCuenta = eDiasCaducaCuenta;
	}

	public String geteIdSesionIngresa() {
		return eIdSesionIngresa;
	}

	public void seteIdSesionIngresa(String eIdSesionIngresa) {
		this.eIdSesionIngresa = eIdSesionIngresa;
	}

	public String geteIdSesionModifica() {
		return eIdSesionModifica;
	}

	public void seteIdSesionModifica(String eIdSesionModifica) {
		this.eIdSesionModifica = eIdSesionModifica;
	}

	public String geteIdTipoAutenticacionUsuario() {
		return eIdTipoAutenticacionUsuario;
	}

	public void seteIdTipoAutenticacionUsuario(String eIdTipoAutenticacionUsuario) {
		this.eIdTipoAutenticacionUsuario = eIdTipoAutenticacionUsuario;
	}

	public String geteTipoMotivoSuspension() {
		return eTipoMotivoSuspension;
	}

	public void seteTipoMotivoSuspension(String eTipoMotivoSuspension) {
		this.eTipoMotivoSuspension = eTipoMotivoSuspension;
	}

	public String geteTipoMotivoSuspensionTexto() {
		return eTipoMotivoSuspensionTexto;
	}

	public void seteTipoMotivoSuspensionTexto(String eTipoMotivoSuspensionTexto) {
		this.eTipoMotivoSuspensionTexto = eTipoMotivoSuspensionTexto;
	}

	public String geteTipoPersonaboolean() {
		return eTipoPersonaboolean;
	}

	public void seteTipoPersonaboolean(String eTipoPersonaboolean) {
		this.eTipoPersonaboolean = eTipoPersonaboolean;
	}

	public String getfFechaExpiraCuenta() {
		return fFechaExpiraCuenta;
	}

	public void setfFechaExpiraCuenta(String fFechaExpiraCuenta) {
		this.fFechaExpiraCuenta = fFechaExpiraCuenta;
	}

	public String getfFechaIngreso() {
		return fFechaIngreso;
	}

	public void setfFechaIngreso(String fFechaIngreso) {
		this.fFechaIngreso = fFechaIngreso;
	}

	public String getfFechaModiifica() {
		return fFechaModiifica;
	}

	public void setfFechaModiifica(String fFechaModiifica) {
		this.fFechaModiifica = fFechaModiifica;
	}

	public String getuCodUsuario() {
		return uCodUsuario;
	}

	public void setuCodUsuario(String uCodUsuario) {
		this.uCodUsuario = uCodUsuario;
	}

	public String getuCorElectronico() {
		return uCorElectronico;
	}

	public void setuCorElectronico(String uCorElectronico) {
		this.uCorElectronico = uCorElectronico;
	}

	public String getuNombrePerfil() {
		return uNombrePerfil;
	}

	public void setuNombrePerfil(String uNombrePerfil) {
		this.uNombrePerfil = uNombrePerfil;
	}

	@Override
	public String toString() {
		return "UsuarioBean [EsEdicion=" + EsEdicion + ", MensajeError=" + MensajeError + ", TotalVirtual="
				+ TotalVirtual + ", ClaveTexto=" + ClaveTexto + ", FK_elIdEmpresa=" + FK_elIdEmpresa
				+ ", NombreCompleto=" + NombreCompleto + ", ObservacionAnulacion=" + ObservacionAnulacion
				+ ", PK_eIdPerfil=" + PK_eIdPerfil + ", PK_eIdUsuario=" + PK_eIdUsuario + ", SegSSOMPerfil="
				+ SegSSOMPerfil + ", Situacion_Descripcion=" + Situacion_Descripcion
				+ ", TipoAutenticacion_Descripcion=" + TipoAutenticacion_Descripcion + ", TipoPersona_Descripcion="
				+ TipoPersona_Descripcion + ", bClave=" + Arrays.toString(bClave) + ", cFlgActivo=" + cFlgActivo
				+ ", cFlgCambiaClave=" + cFlgCambiaClave + ", cFlgEliminado=" + cFlgEliminado + ", cFlgInterno="
				+ cFlgInterno + ", cTieneExpiracionAutomatica=" + cTieneExpiracionAutomatica + ", condicion="
				+ condicion + ", eCambioSituacion=" + eCambioSituacion + ", eDiasCaducaClave=" + eDiasCaducaClave
				+ ", eDiasCaducaCuenta=" + eDiasCaducaCuenta + ", eIdSesionIngresa=" + eIdSesionIngresa
				+ ", eIdSesionModifica=" + eIdSesionModifica + ", eIdTipoAutenticacionUsuario="
				+ eIdTipoAutenticacionUsuario + ", eTipoMotivoSuspension=" + eTipoMotivoSuspension
				+ ", eTipoMotivoSuspensionTexto=" + eTipoMotivoSuspensionTexto + ", eTipoPersonaboolean="
				+ eTipoPersonaboolean + ", fFechaExpiraCuenta=" + fFechaExpiraCuenta + ", fFechaIngreso="
				+ fFechaIngreso + ", fFechaModiifica=" + fFechaModiifica + ", uCodUsuario=" + uCodUsuario
				+ ", uCorElectronico=" + uCorElectronico + ", uNombrePerfil=" + uNombrePerfil + "]";
	}
	
}
