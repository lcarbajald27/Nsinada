package pe.gob.oefa.apps.sinada.bean.proceso;

import java.util.ArrayList;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class EvaluacionRechazo extends BaseBean{

	private long idEvaluacionRechazo;					//id Evaluacion Rechazo
	private Maestro tipoEvaluacionRechazo;		// Reiterar - derivar - Archivar
	private AtencionDenuncia atencionDenuncia;
	private String motivoDescripcion; 			// Motivo campo de texto
	private Maestro motivo;
	private Maestro estado;
	private String flagActivo;
	private List<ArchivoAtencion> lstArchivoAtencion;
	
	private List<DerivacionDenuncia> lstDerivacionDenuncia;

	
	public Maestro getTipoEvaluacionRechazo() {
		return tipoEvaluacionRechazo;
	}
	public void setTipoEvaluacionRechazo(Maestro tipoEvaluacionRechazo) {
		this.tipoEvaluacionRechazo = tipoEvaluacionRechazo;
	}
	public AtencionDenuncia getAtencionDenuncia() {
		return atencionDenuncia;
	}
	public void setAtencionDenuncia(AtencionDenuncia atencionDenuncia) {
		this.atencionDenuncia = atencionDenuncia;
	}
	public String getMotivoDescripcion() {
		return motivoDescripcion;
	}
	public void setMotivoDescripcion(String motivoDescripcion) {
		this.motivoDescripcion = motivoDescripcion;
	}

	public Maestro getMotivo() {
		return motivo;
	}
	public void setMotivo(Maestro motivo) {
		this.motivo = motivo;
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
	public long getIdEvaluacionRechazo() {
		return idEvaluacionRechazo;
	}
	public void setIdEvaluacionRechazo(long idEvaluacionRechazo) {
		this.idEvaluacionRechazo = idEvaluacionRechazo;
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

	
	
}
