package pe.gob.oefa.apps.sinada.bean.general;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Entidad extends BaseBean{

	private long 	idEntidad;
    private String 	razonSocial;
    private String 	ruc;
    
    private int 	cargo;
    private Persona representanteLegal;
    private String  ubigeo;
    private String  departamento;
    private String  provincia;
    private String  distrito;
    private String  direccion;
    private String 	referencia;
    private int 	flagActivo;
    
    private String nombreDepartamento;
    private String nombreProvincia;
    private String nombreDistrito;
    
    private String 	nomCargo;			//NOMBRE_CARGO
    private String 	nomRepresentante;	//NOMBRE_REPRESENTANTE
    
  
	public Entidad() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(long idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public int getCargo() {
		return cargo;
	}
	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
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
	public int getFlagActivo() {
		return flagActivo;
	}
	public void setFlagActivo(int flagActivo) {
		this.flagActivo = flagActivo;
	}
	public Persona getRepresentanteLegal() {
		if(representanteLegal==null){
			representanteLegal = new Persona();
		}
		return representanteLegal;
	}
	public void setRepresentanteLegal(Persona representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
	public String getUbigeo() {
		return ubigeo;
	}
	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}
	public String getNomCargo() {
		return nomCargo;
	}
	public void setNomCargo(String nomCargo) {
		this.nomCargo = nomCargo;
	}
	public String getNomRepresentante() {
		return nomRepresentante;
	}
	public void setNomRepresentante(String nomRepresentante) {
		this.nomRepresentante = nomRepresentante;
	}
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	public String getNombreDistrito() {
		return nombreDistrito;
	}
	public void setNombreDistrito(String nombreDistrito) {
		this.nombreDistrito = nombreDistrito;
	}
	
    
}
