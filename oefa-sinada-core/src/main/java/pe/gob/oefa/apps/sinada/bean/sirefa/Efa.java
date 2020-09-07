package pe.gob.oefa.apps.sinada.bean.sirefa;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;

public class Efa extends BaseBean{
	
    private long 	idEfa;
    private long 	idUbigeo;
    private long    idEntidad;
    private String 	ruc;
    private String 	nombre;
    private int 	tipoNivel;
    private String  nombreTipoNivel;
    private int 	tipoGobierno;
    private String  nombreTipoGobierno;
    private String 	paginaWeb;
    private String 	correo;
    private String 	telefono;
    private String 	departamento;
    private String 	provincia;
    private String 	distrito;
    private String 	direccion;
    private String 	referencia;
    private int 	flagActivo;
    private String 	titular;
    private String 	cargo;
    private String  nivel;
    private int		tipoCargo;
    private String 	documento;
    private long 	idProblematica;
    private long 	idDebido;
    private long 	idZonaSuceso;
    private long    idCaso;
    
    private Denuncia denuncia;
  
    
	public Efa() {
		super();
	}

	public long getIdEfa()
	{
		return idEfa;
	}

	public void setIdEfa(long idEfa)
	{
		this.idEfa = idEfa;
	}

	public long getIdUbigeo()
	{
		return idUbigeo;
	}

	public void setIdUbigeo(long idUbigeo)
	{
		this.idUbigeo = idUbigeo;
	}

	public String getRuc()
	{
		return ruc;
	}

	public void setRuc(String ruc)
	{
		this.ruc = ruc;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public int getTipoNivel()
	{
		return tipoNivel;
	}

	public void setTipoNivel(int tipoNivel)
	{
		this.tipoNivel = tipoNivel;
	}

	public int getTipoGobierno()
	{
		return tipoGobierno;
	}

	public void setTipoGobierno(int tipoGobierno)
	{
		this.tipoGobierno = tipoGobierno;
	}

	public String getPaginaWeb()
	{
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb)
	{
		this.paginaWeb = paginaWeb;
	}

	public String getCorreo()
	{
		return correo;
	}

	public void setCorreo(String correo)
	{
		this.correo = correo;
	}

	public String getTelefono()
	{
		return telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getDepartamento()
	{
		return departamento;
	}

	public void setDepartamento(String departamento)
	{
		this.departamento = departamento;
	}

	public String getProvincia()
	{
		return provincia;
	}

	public void setProvincia(String provincia)
	{
		this.provincia = provincia;
	}

	public String getDistrito()
	{
		return distrito;
	}

	public void setDistrito(String distrito)
	{
		this.distrito = distrito;
	}

	public String getDireccion()
	{
		return direccion;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public String getReferencia()
	{
		return referencia;
	}

	public void setReferencia(String referencia)
	{
		this.referencia = referencia;
	}

	public int getFlagActivo()
	{
		return flagActivo;
	}

	public void setFlagActivo(int flagActivo)
	{
		this.flagActivo = flagActivo;
	}

	public String getTitular()
	{
		return titular;
	}

	public void setTitular(String titular)
	{
		this.titular = titular;
	}

	public String getCargo()
	{
		return cargo;
	}

	public void setCargo(String cargo)
	{
		this.cargo = cargo;
	}

	public String getNivel()
	{
		return nivel;
	}

	public void setNivel(String nivel)
	{
		this.nivel = nivel;
	}

	public int getTipoCargo()
	{
		return tipoCargo;
	}

	public void setTipoCargo(int tipoCargo)
	{
		this.tipoCargo = tipoCargo;
	}

	public String getDocumento()
	{
		return documento;
	}

	public void setDocumento(String documento)
	{
		this.documento = documento;
	}

	public long getIdProblematica() {
		return idProblematica;
	}

	public void setIdProblematica(long idProblematica) {
		this.idProblematica = idProblematica;
	}

	public long getIdDebido() {
		return idDebido;
	}

	public void setIdDebido(long idDebido) {
		this.idDebido = idDebido;
	}

	public long getIdZonaSuceso() {
		return idZonaSuceso;
	}

	public void setIdZonaSuceso(long idZonaSuceso) {
		this.idZonaSuceso = idZonaSuceso;
	}

	
	
	public String getNombreTipoNivel() {
		return nombreTipoNivel;
	}

	public void setNombreTipoNivel(String nombreTipoNivel) {
		this.nombreTipoNivel = nombreTipoNivel;
	}

	public String getNombreTipoGobierno() {
		return nombreTipoGobierno;
	}

	public void setNombreTipoGobierno(String nombreTipoGobierno) {
		this.nombreTipoGobierno = nombreTipoGobierno;
	}

	public long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(long idCaso) {
		this.idCaso = idCaso;
	}

	
	
	public long getIdEntidad() {
		return idEntidad;
	}

	public void setIdEntidad(long idEntidad) {
		this.idEntidad = idEntidad;
	}

	
	
	public Denuncia getDenuncia() {
		return denuncia;
	}

	public void setDenuncia(Denuncia denuncia) {
		this.denuncia = denuncia;
	}

	@Override
	public String toString()
	{
		return "Efa [idEfa=" + idEfa + ", idUbigeo=" + idUbigeo + ", ruc=" + ruc + ", nombre=" + nombre + ", tipoNivel="
				+ tipoNivel + ", tipoGobierno=" + tipoGobierno + ", paginaWeb=" + paginaWeb + ", correo=" + correo
				+ ", telefono=" + telefono + ", departamento=" + departamento + ", provincia=" + provincia
				+ ", distrito=" + distrito + ", direccion=" + direccion + ", referencia=" + referencia + ", flagActivo="
				+ flagActivo + ", titular=" + titular + ", cargo=" + cargo + ", nivel=" + nivel + ", tipoCargo="
				+ tipoCargo + ", documento=" + documento + "]";
	}
	
}
