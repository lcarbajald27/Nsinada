package pe.gob.oefa.apps.sinada.bean.maestros;

import java.io.Serializable;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Maestro extends BaseBean{

	private long 	idMaestro;
    private String 	codigoMaestro;
    private int 	codigoRegistro;
    private String 	descripcion;
    private String 	abreviatura; 
    private long 	codigoRegistroPadre;
    private int 	flagActivo;
    
    private int 	codigoNivel;
    private String 	valorGeneral01;
    private String 	valorGeneral02;
    private String 	valorGeneral03;
    private String 	valorGeneral04;
    private String 	valorGeneral05;
    
    private List<Maestro> listTabla;
    
	public Maestro() {
		super();
	}
	public long getIdMaestro() {
		return idMaestro;
	}
	public void setIdMaestro(long idMaestro) {
		this.idMaestro = idMaestro;
	}
	public String getCodigoMaestro() {
		return codigoMaestro;
	}
	public void setCodigoMaestro(String codigoMaestro) {
		this.codigoMaestro = codigoMaestro;
	}
	public int getCodigoRegistro() {
		return codigoRegistro;
	}
	public void setCodigoRegistro(int codigoRegistro) {
		this.codigoRegistro = codigoRegistro;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	public long getCodigoRegistroPadre() {
		return codigoRegistroPadre;
	}
	public void setCodigoRegistroPadre(long codigoRegistroPadre) {
		this.codigoRegistroPadre = codigoRegistroPadre;
	}
	public int getFlagActivo() {
		return flagActivo;
	}
	public void setFlagActivo(int flagActivo) {
		this.flagActivo = flagActivo;
	}
	
	public List<Maestro> getLstMaestro() {
		return listTabla;
	}
	public void setLstMaestro(List<Maestro> listTabla) {
		this.listTabla = listTabla;
	}
	
	public int getCodigoNivel() {
		return codigoNivel;
	}
	public void setCodigoNivel(int codigoNivel) {
		this.codigoNivel = codigoNivel;
	}
	public String getValorGeneral01() {
		return valorGeneral01;
	}
	public void setValorGeneral01(String valorGeneral01) {
		this.valorGeneral01 = valorGeneral01;
	}
	public String getValorGeneral02() {
		return valorGeneral02;
	}
	public void setValorGeneral02(String valorGeneral02) {
		this.valorGeneral02 = valorGeneral02;
	}
	public String getValorGeneral03() {
		return valorGeneral03;
	}
	public void setValorGeneral03(String valorGeneral03) {
		this.valorGeneral03 = valorGeneral03;
	}
	public String getValorGeneral04() {
		return valorGeneral04;
	}
	public void setValorGeneral04(String valorGeneral04) {
		this.valorGeneral04 = valorGeneral04;
	}
	public String getValorGeneral05() {
		return valorGeneral05;
	}
	public void setValorGeneral05(String valorGeneral05) {
		this.valorGeneral05 = valorGeneral05;
	}
	public List<Maestro> getListTabla() {
		return listTabla;
	}
	public void setListTabla(List<Maestro> listTabla) {
		this.listTabla = listTabla;
	}
	@Override
	public String toString() {
		return "Maestro [idMaestro=" + idMaestro + ", codigoMaestro=" + codigoMaestro + ", codigoRegistro="
				+ codigoRegistro + ", descripcion=" + descripcion + ", abreviatura=" + abreviatura
				+ ", codigoRegistroPadre=" + codigoRegistroPadre + ", flagActivo=" + flagActivo + "]";
	}
}
