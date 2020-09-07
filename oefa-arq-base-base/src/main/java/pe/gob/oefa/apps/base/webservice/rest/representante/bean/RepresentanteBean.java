package pe.gob.oefa.apps.base.webservice.rest.representante.bean;

import pe.gob.oefa.apps.base.webservice.rest.base.bean.BaseBean;

public class RepresentanteBean extends BaseBean{
	
    private int 	CodCargo;			//ID_CARGO
    private int 	IdPersona;	//ID_REPRESENTANTE
    private String 	NombreCargo;			//NOMBRE_CARGO
    private String 	NombreCompleto;	//NOMBRE_REPRESENTANTE
    
	private String ApellidoMaterno;
	private String ApellidoPaterno;
	private String Nombres;
	private String NroDocumento;
	private String TipoDocumento;
	private String Ubigeo;
	private String Direccion;
	
	
	public RepresentanteBean() {
		super();
		this.setNombreCompleto("");
		this.setNombreCargo("");
	}


	public int getCodCargo() {
		return CodCargo;
	}


	public void setCodCargo(int codCargo) {
		CodCargo = codCargo;
	}


	public int getIdPersona() {
		return IdPersona;
	}


	public void setIdPersona(int idPersona) {
		IdPersona = idPersona;
	}


	public String getNombreCargo() {
		return NombreCargo;
	}


	public void setNombreCargo(String nombreCargo) {
		NombreCargo = nombreCargo;
	}


	public String getNombreCompleto() {
		return NombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		NombreCompleto = nombreCompleto;
	}


	@Override
	public String toString() {
		return "RepresentanteBean [CodCargo=" + CodCargo + ", IdPersona="
				+ IdPersona + ", NombreCargo=" + NombreCargo
				+ ", NombreCompleto=" + NombreCompleto + "]";
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


	public String getTipoDocumento() {
		return TipoDocumento;
	}


	public void setTipoDocumento(String tipoDocumento) {
		TipoDocumento = tipoDocumento;
	}


	public String getUbigeo() {
		return Ubigeo;
	}


	public void setUbigeo(String ubigeo) {
		Ubigeo = ubigeo;
	}


	public String getDireccion() {
		return Direccion;
	}


	public void setDireccion(String direccion) {
		Direccion = direccion;
	}

}
