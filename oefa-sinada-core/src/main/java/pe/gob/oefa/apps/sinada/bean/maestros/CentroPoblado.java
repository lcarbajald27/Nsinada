package pe.gob.oefa.apps.sinada.bean.maestros;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class CentroPoblado extends BaseBean {
	
	private long   objectid;
	private String codigoCentroPoblado;
	private String nombreCentroPoblado;
	private String codigoDistrito;
	
	public long getObjectid() {
		return objectid;
	}
	public void setObjectid(long objectid) {
		this.objectid = objectid;
	}
	public String getCodigoCentroPoblado() {
		return codigoCentroPoblado;
	}
	public void setCodigoCentroPoblado(String codigoCentroPoblado) {
		this.codigoCentroPoblado = codigoCentroPoblado;
	}
	public String getNombreCentroPoblado() {
		return nombreCentroPoblado;
	}
	public void setNombreCentroPoblado(String nombreCentroPoblado) {
		this.nombreCentroPoblado = nombreCentroPoblado;
	}
	public String getCodigoDistrito() {
		return codigoDistrito;
	}
	public void setCodigoDistrito(String codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}
	
	
	
	
}
