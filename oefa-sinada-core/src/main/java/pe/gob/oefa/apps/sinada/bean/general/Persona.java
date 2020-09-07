package pe.gob.oefa.apps.sinada.bean.general;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Persona extends BaseBean{

	private long idPersona; 
    private String documento; 
    private String primerNombre; 
    private String segundoNombre; 
    private String nombres; 
    private String apellidoPaterno; 
    private String apellidoMaterno;
    private String nombreCompleto;
    private String ubigeo;
    private String departamento;
    private String provincia;
    private String distrito;
    private String direccion;
    private String referencia;
    
    private String nombreDepartamento;
    private String nombreProvincia;
    private String nombreDistrito;
    
	public Persona() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	public String getUbigeo() {
		return ubigeo;
	}
	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}
	public String getDepartamento() {
		if(departamento==null){
			departamento = "";
		}
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		if(provincia==null){
			provincia = "";
		}
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		if(distrito==null){
			distrito = "";
		}
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	public String getNombreCompleto() {
		
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	
	public String getNombres() {
		if(nombres==null){
			nombres = primerNombre+" "+segundoNombre;
		}
		
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	
	public String getNombreDepartamento() {
		if(nombreDepartamento==null){
			nombreDepartamento = "";
		}
		return nombreDepartamento;
	}
	
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	
	public String getNombreProvincia() {
		if(nombreProvincia==null){
			nombreProvincia = "";
		}
		return nombreProvincia;
	}
	
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	
	public String getNombreDistrito() {
		if(nombreDistrito==null){
			nombreDistrito = "";
		}
		return nombreDistrito;
	}
	
	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}
	
	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", documento=" + documento + ", primerNombre=" + primerNombre
				+ ", segundoNombre=" + segundoNombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno="
				+ apellidoMaterno + "]";
	} 
	
    
}
