package pe.gob.oefa.apps.sinada.bean.maestros;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;

/**
 * BEAN 		: DistritoBean
 * RESPONSABLE 	: MGARCIA
 * FECHA 		: 15/09/2017
 * DESCRIPCI�N 	: CLASE CON LOS CAMPOS QUE TENDR� LA ENTIDAD Distrito PARA MANEJAR LA INFORMACI�N DE UNA
 * 				  MANERA MAS ORDENADA
 **/

public class Distrito extends BaseBean {

//	private static final long serialVersionUID = 1L;
	
	private String codigoDepartamento;
	private String codigoProvincia;
	private String codigoDistrito;
	private String descripcionDistrito;
	private String codReg;
	

	private String abrev;
	private String codDep;
	private String codDist;
	private String codPro;

	private String codZona;
	
	private String flagActivo;
	
	
	
	
	public Distrito() {
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

	public String getCodigoDistrito() {
		return codigoDistrito;
	}

	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}

	public String getDescripcionDistrito() {
		return descripcionDistrito;
	}

	public void setDescripcionDistrito(String descripcionDistrito) {
		this.descripcionDistrito = descripcionDistrito;
	}

	public String getCodReg() {
		return codReg;
	}

	public void setCodReg(String codReg) {
		this.codReg = codReg;
	}

	public String getAbrev() {
		return abrev;
	}

	public void setAbrev(String abrev) {
		this.abrev = abrev;
	}

	public String getCodDep() {
		return codDep;
	}

	public void setCodDep(String codDep) {
		this.codDep = codDep;
	}

	public String getCodDist() {
		return codDist;
	}

	public void setCodDist(String codDist) {
		this.codDist = codDist;
	}

	public String getCodPro() {
		return codPro;
	}

	public void setCodPro(String codPro) {
		this.codPro = codPro;
	}

	public String getCodZona() {
		return codZona;
	}

	public void setCodZona(String codZona) {
		this.codZona = codZona;
	}
	
	public String getFlagActivo() {
		return flagActivo;
	}
	
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}
	


	
	
}
