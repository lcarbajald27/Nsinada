package pe.gob.oefa.apps.base.webservice.rest.sso.bean;

public class UsuarioSSO {

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", idEmpresa=" + idEmpresa
				+ ", codUsuario=" + codUsuario + ", correoElectronico="
				+ correoElectronico + "]";
	}
	private Long idUsuario;
	private Long idEmpresa;
	private String codUsuario;
	private String correoElectronico;
	
	public UsuarioSSO() {
		super();
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	
	
}
