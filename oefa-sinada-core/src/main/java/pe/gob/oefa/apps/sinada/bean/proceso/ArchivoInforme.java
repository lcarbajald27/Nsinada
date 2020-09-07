package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class ArchivoInforme extends BaseBean{
	
	private long idArchivoInforme;
	private InformeAccion informeAccion;
	private String archivoInforme;
	private String rutaArchivoInforme;
	private Maestro estado;
	private String flagActivo;
	

	public ArchivoInforme() {
		super();
	}


	public long getIdArchivoInforme() {
		return idArchivoInforme;
	}


	public void setIdArchivoInforme(long idArchivoInforme) {
		this.idArchivoInforme = idArchivoInforme;
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


	public String getArchivoInforme() {
		return archivoInforme;
	}


	public void setArchivoInforme(String archivoInforme) {
		this.archivoInforme = archivoInforme;
	}


	public String getRutaArchivoInforme() {
		return rutaArchivoInforme;
	}


	public void setRutaArchivoInforme(String rutaArchivoInforme) {
		this.rutaArchivoInforme = rutaArchivoInforme;
	}


	public Maestro getEstado() {
		
		if(estado == null){
			estado = new Maestro();
		}
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


	
}
