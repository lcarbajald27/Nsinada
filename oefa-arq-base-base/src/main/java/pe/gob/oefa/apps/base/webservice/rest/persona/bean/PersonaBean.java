package pe.gob.oefa.apps.base.webservice.rest.persona.bean;

import pe.gob.oefa.apps.base.webservice.rest.base.bean.BaseBean;

public class PersonaBean extends BaseBean{
	
	private String Id_Persona;	
	private String NombreCompleto;
	private String ApellidoMaterno;
	private String ApellidoPaterno;
	private String Codigo;
	private String Direccion;
	private boolean EsValido;
	private String EstadoCivil;
	private String FechaNacimiento;
	private String Genero;
	private String Mensaje;
	private String Nombres;
	private String NroDocumento;
	private String RUC;
	private String TipoDocumento;
	private String TipoPersona;
	private String Ubigeo;
	
	public PersonaBean() {
		super();
	}

	public String getId_Persona() {
		return Id_Persona;
	}

	public void setId_Persona(String id_Persona) {
		Id_Persona = id_Persona;
	}

	public String getNombreCompleto() {
		return NombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		NombreCompleto = nombreCompleto;
	}

	public String getApellidoMaterno() {
		return ApellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		ApellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return ApellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		ApellidoPaterno = apellidoPaterno;
	}

	public String getCodigo() {
		return Codigo;
	}

	public void setCodigo(String codigo) {
		Codigo = codigo;
	}

	public String getDireccion() {
		return Direccion;
	}

	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

	public boolean isEsValido() {
		return EsValido;
	}

	public void setEsValido(boolean esValido) {
		EsValido = esValido;
	}

	public String getEstadoCivil() {
		return EstadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		EstadoCivil = estadoCivil;
	}

	public String getFechaNacimiento() {
		return FechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return Genero;
	}

	public void setGenero(String genero) {
		Genero = genero;
	}

	public String getMensaje() {
		return Mensaje;
	}

	public void setMensaje(String mensaje) {
		Mensaje = mensaje;
	}

	public String getNombres() {
		return Nombres;
	}

	public void setNombres(String nombres) {
		Nombres = nombres;
	}

	public String getNroDocumento() {
		return NroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		NroDocumento = nroDocumento;
	}

	public String getRUC() {
		return RUC;
	}

	public void setRUC(String rUC) {
		RUC = rUC;
	}

	public String getTipoDocumento() {
		return TipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		TipoDocumento = tipoDocumento;
	}

	public String getTipoPersona() {
		return TipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		TipoPersona = tipoPersona;
	}

	public String getUbigeo() {
		return Ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		Ubigeo = ubigeo;
	}

	@Override
	public String toString() {
		return "EntidadBean [Id_Persona=" + Id_Persona + ", NombreCompleto=" + NombreCompleto + ", ApellidoMaterno="
				+ ApellidoMaterno + ", ApellidoPaterno=" + ApellidoPaterno + ", Codigo=" + Codigo + ", Direccion="
				+ Direccion + ", EsValido=" + EsValido + ", EstadoCivil=" + EstadoCivil + ", FechaNacimiento="
				+ FechaNacimiento + ", Genero=" + Genero + ", Mensaje=" + Mensaje + ", Nombres=" + Nombres
				+ ", NroDocumento=" + NroDocumento + ", RUC=" + RUC + ", TipoDocumento=" + TipoDocumento
				+ ", TipoPersona=" + TipoPersona + ", Ubigeo=" + Ubigeo + "]"
				+ super.toString();
	}
	
}
