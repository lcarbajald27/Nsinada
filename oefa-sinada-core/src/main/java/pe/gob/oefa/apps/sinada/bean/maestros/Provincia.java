package pe.gob.oefa.apps.sinada.bean.maestros;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;

/**
 * BEAN 		: ProvinciaBean
 * RESPONSABLE 	: MGARCIA
 * FECHA 		: 15/09/2017
 * DESCRIPCI�N 	: CLASE CON LOS CAMPOS QUE TENDR� LA ENTIDAD Provincia PARA MANEJAR LA INFORMACI�N DE UNA
 * 				  MANERA MAS ORDENADA
 **/

public class Provincia extends BaseBean {

//	private static final long serialVersionUID = 1L;
	
	private String codigoDepartamento;
	private String codigoProvincia;
	private String descripcion;
	private String codDep;
	private String codProv;
	private String flagActivo;
	
	
	
	public Provincia() {
	}



	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}



	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}



	public String getCodigoProvincia() {
		return codigoProvincia;
	}



	public void setCodigoProvincia(String codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getCodDep() {
		return codDep;
	}



	public void setCodDep(String codDep) {
		this.codDep = codDep;
	}



	public String getCodProv() {
		return codProv;
	}



	public void setCodProv(String codProv) {
		this.codProv = codProv;
	}



	public String getFlagActivo() {
		return flagActivo;
	}



	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}



	
	
	
}
