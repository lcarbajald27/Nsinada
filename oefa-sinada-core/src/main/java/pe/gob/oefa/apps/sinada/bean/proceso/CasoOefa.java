package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class CasoOefa extends BaseBean{
	
	private long 		idCasoOefa;
	private	Maestro     direccionSupervision;
	private Maestro 	direccionEvaluacion;
	private Maestro     estado;
	private Caso		caso;
	private Maestro 	tipoAsignacion;
	private int 	swEditable = 0;
    private String 		flagActivo;

	public CasoOefa() {
		super();
	}

	public long getIdCasoOefa() {
		return idCasoOefa;
	}

	public void setIdCasoOefa(long idCasoOefa) {
		this.idCasoOefa = idCasoOefa;
	}

	public Maestro getDireccionSupervision() {
		return direccionSupervision;
	}

	public void setDireccionSupervision(Maestro direccionSupervision) {
		this.direccionSupervision = direccionSupervision;
	}

	public Maestro getDireccionEvaluacion() {
		return direccionEvaluacion;
	}

	public void setDireccionEvaluacion(Maestro direccionEvaluacion) {
		this.direccionEvaluacion = direccionEvaluacion;
	}

	public Maestro getEstado() {
		return estado;
	}

	public void setEstado(Maestro estado) {
		this.estado = estado;
	}

	public Caso getCaso() {
		if(caso==null){
			caso = new Caso();
		}
		return caso;
	}

	public void setCaso(Caso caso) {
		this.caso = caso;
	}

	public Maestro getTipoAsignacion() {
		return tipoAsignacion;
	}

	public void setTipoAsignacion(Maestro tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
	}

	public String getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	
	public int getSwEditable() {
		return swEditable;
	}

	public void setSwEditable(int swEditable) {
		this.swEditable = swEditable;
	}

	@Override
	public String toString() {
		return "CasoOefa [idCasoOefa=" + idCasoOefa + ", direccionSupervision="
				+ direccionSupervision + ", direccionEvaluacion="
				+ direccionEvaluacion + ", estado=" + estado + ", caso=" + caso
				+ ", tipoAsignacion=" + tipoAsignacion + ", flagActivo="
				+ flagActivo + "]";
	}

	
	
}
