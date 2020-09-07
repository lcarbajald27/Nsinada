package pe.gob.oefa.apps.sinada.bean.maestros;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;


/**
 * BEAN 		: DepartamentoBean
 * RESPONSABLE 	: MGARCIA
 * FECHA 		: 15/09/2017
 * DESCRIPCI�N 	: CLASE CON LOS CAMPOS QUE TENDR� LA ENTIDAD Departamento PARA MANEJAR LA INFORMACI�N DE UNA
 * 				  MANERA MAS ORDENADA
 **/

public class Departamento extends BaseBean{

//	private static final long serialVersionUID = 1L;

	private String codigoDepartamento;
	private String descripcionDepartamento;
	private String codDep;
	public String getCodigoDepartamento() {
		return codigoDepartamento;
	}
	public void setCodigoDepartamento(String codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}
	public String getDescripcionDepartamento() {
		return descripcionDepartamento;
	}
	public void setDescripcionDepartamento(String descripcionDepartamento) {
		this.descripcionDepartamento = descripcionDepartamento;
	}
	public String getCodDep() {
		return codDep;
	}
	public void setCodDep(String codDep) {
		this.codDep = codDep;
	}
	
	
	
		
}
