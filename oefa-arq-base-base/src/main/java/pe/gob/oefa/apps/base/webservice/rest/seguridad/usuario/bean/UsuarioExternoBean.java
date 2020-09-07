package pe.gob.oefa.apps.base.webservice.rest.seguridad.usuario.bean;

public class UsuarioExternoBean {
	
	private int IdUsuario;
	private String CodUsuario;
	private String NombreUsuario;
	private String CorreoElectronico;
	private String Clave;
	private int IdSesion;
	private String Observacion="--";
	private int tipo;			//1:Persona, 2: Entidad
	private String nroDoc;
	private int IdPerfil=0;
	
	
	public UsuarioExternoBean() {
		super();
	}

	public int getIdUsuario() {
		return IdUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		IdUsuario = idUsuario;
	}

	public String getCodUsuario() {
		return CodUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		CodUsuario = codUsuario;
	}

	public String getNombreUsuario() {
		return NombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		NombreUsuario = nombreUsuario;
	}

	public String getCorreoElectronico() {
		return CorreoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		CorreoElectronico = correoElectronico;
	}

	public String getClave() {
		return Clave;
	}

	public void setClave(String clave) {
		Clave = clave;
	}

	public int getIdSesion() {
		return IdSesion;
	}

	public void setIdSesion(int idSesion) {
		IdSesion = idSesion;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNroDoc() {
		return nroDoc;
	}

	public void setNroDoc(String nroDoc) {
		this.nroDoc = nroDoc;
	}

	public int getIdPerfil() {
		return IdPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		IdPerfil = idPerfil;
	}

	@Override
	public String toString() {
		return "UsuarioExternoBean [IdUsuario=" + IdUsuario + ", CodUsuario="
				+ CodUsuario + ", NombreUsuario=" + NombreUsuario
				+ ", CorreoElectronico=" + CorreoElectronico + ", Clave="
				+ Clave + ", IdSesion=" + IdSesion + ", Observacion="
				+ Observacion + ", tipo=" + tipo + ", nroDoc=" + nroDoc
				+ ", IdPerfil=" + IdPerfil + "]";
	}
	
	
}
