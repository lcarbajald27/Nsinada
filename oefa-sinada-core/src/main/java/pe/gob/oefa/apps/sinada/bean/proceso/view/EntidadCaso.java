package pe.gob.oefa.apps.sinada.bean.proceso.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.proceso.NormaCaso;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public class EntidadCaso extends BaseBean{

	private long 	idCasoEntidad;
	private int 	tipoEntidad;
	private String 	nombreTipoEntidad;
	private long 	idEntidad;
	private String 	nombreEntidad;
	private int 	tipoAsignacion;
	private String  nombretipoAsignacion;
	private long 	idCaso;
	private List<Normas> lstNormas;
	
	private List<NormaCaso> lstNormaCaso;


	public EntidadCaso() {
		super();
	}



	public long getIdCasoEntidad() {
		return idCasoEntidad;
	}



	public void setIdCasoEntidad(long idCasoEntidad) {
		this.idCasoEntidad = idCasoEntidad;
	}



	public int getTipoEntidad() {
		return tipoEntidad;
	}



	public void setTipoEntidad(int tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}



	public String getNombreTipoEntidad() {
		return nombreTipoEntidad;
	}



	public void setNombreTipoEntidad(String nombreTipoEntidad) {
		this.nombreTipoEntidad = nombreTipoEntidad;
	}



	public long getIdEntidad() {
		return idEntidad;
	}



	public void setIdEntidad(long idEntidad) {
		this.idEntidad = idEntidad;
	}



	public String getNombreEntidad() {
		return nombreEntidad;
	}



	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}



	public int getTipoAsignacion() {
		return tipoAsignacion;
	}



	public void setTipoAsignacion(int tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
	}



	public String getNombretipoAsignacion() {
		return nombretipoAsignacion;
	}



	public void setNombretipoAsignacion(String nombretipoAsignacion) {
		this.nombretipoAsignacion = nombretipoAsignacion;
	}



	public long getIdCaso() {
		return idCaso;
	}



	public void setIdCaso(long idCaso) {
		this.idCaso = idCaso;
	}



	public List<Normas> getLstNormas() {
		return lstNormas;
	}



	public void setLstNormas(List<Normas> lstNormas) {
		this.lstNormas = lstNormas;
	}



	public List<NormaCaso> getLstNormaCaso() {
		return lstNormaCaso;
	}



	public void setLstNormaCaso(List<NormaCaso> lstNormaCaso) {
		this.lstNormaCaso = lstNormaCaso;
	}


	
	
}
