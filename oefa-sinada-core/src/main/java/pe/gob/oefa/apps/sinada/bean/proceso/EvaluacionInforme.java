package pe.gob.oefa.apps.sinada.bean.proceso;

import java.util.ArrayList;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class EvaluacionInforme extends BaseBean{

	private long idEvaluacionInforme;					//id Evaluacion Rechazo
	private Maestro tipoInforme;		// Informe de Accion - Informe de Atencion
	private InformeAccion informeAccion;
	private Maestro tipoOpcion; 			// Observar - aprobar
	private String motivoDescripcion;
	private Maestro estado;
	private String flagActivo;
	private List<ArchivoAtencion> lstArchivoAtencion;
	
	private List<DerivacionDenuncia> lstDerivacionDenuncia;

	

	public long getIdEvaluacionInforme() {
		return idEvaluacionInforme;
	}
	public void setIdEvaluacionInforme(long idEvaluacionInforme) {
		this.idEvaluacionInforme = idEvaluacionInforme;
	}
	public String getMotivoDescripcion() {
		return motivoDescripcion;
	}
	public void setMotivoDescripcion(String motivoDescripcion) {
		this.motivoDescripcion = motivoDescripcion;
	}

	
	public Maestro getEstado() {
		return estado;
	}
	public void setEstado(Maestro estado) {
		this.estado = estado;
	}
	public String getFlagActivo() {
		return flagActivo;
	}
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	public List<ArchivoAtencion> getLstArchivoAtencion() {
		if(lstArchivoAtencion==null){
			lstArchivoAtencion = new ArrayList<ArchivoAtencion>();
		}
		return lstArchivoAtencion;
	}
	public void setLstArchivoAtencion(List<ArchivoAtencion> lstArchivoAtencion) {
		this.lstArchivoAtencion = lstArchivoAtencion;
	}
	public List<DerivacionDenuncia> getLstDerivacionDenuncia() {
		if(lstDerivacionDenuncia == null){
			lstDerivacionDenuncia = new ArrayList<DerivacionDenuncia>();
		}
		return lstDerivacionDenuncia;
	}
	public void setLstDerivacionDenuncia(
			List<DerivacionDenuncia> lstDerivacionDenuncia) {
		this.lstDerivacionDenuncia = lstDerivacionDenuncia;
	}
	
	public Maestro getTipoInforme() {
		
		if(tipoInforme==null){
			tipoInforme = new Maestro();
		}
		return tipoInforme;
	}
	public void setTipoInforme(Maestro tipoInforme) {
		this.tipoInforme = tipoInforme;
	}
	public InformeAccion getInformeAccion() {
		if(informeAccion==null){
			informeAccion = new InformeAccion();
		}
		return informeAccion;
	}
	public void setInformeAccion(InformeAccion informeAccion) {
		this.informeAccion = informeAccion;
	}
	public Maestro getTipoOpcion() {
		if(tipoOpcion==null){
			tipoOpcion = new  Maestro();
		}
		return tipoOpcion;
	}
	public void setTipoOpcion(Maestro tipoOpcion) {
		this.tipoOpcion = tipoOpcion;
	}

	
	
}
