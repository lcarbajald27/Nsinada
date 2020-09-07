package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;

public class AtencionDenuncia extends BaseBean{

	private long 	idAtencionDenuncia;				// id Atencion denuncia
	private BandejaDetalle detalleBandeja;
//	private Maestro tipoEntidadCompetente;			// Tipo Entidad Compenten EFA : 2 - OEFA : 1
//	private Efa 	efa;							
//	private PersonaOefa personaOefa;
	private Maestro tipoAtencion;				// TipoAtencionDenuncia --
	
	private Maestro motivo;
	private String fechaRegistro;
	private Maestro estado;					
	private String flagActivo;
	private Denuncia denuncia;   // denuncia
	private String hojaTramite;
	
	private String descripcionMotivo;
	
	private List<ArchivoAtencion> lstArchivoAtencion;
		

	
	public long getIdAtencionDenuncia() {
		return idAtencionDenuncia;
	}
	public void setIdAtencionDenuncia(long idAtencionDenuncia) {
		this.idAtencionDenuncia = idAtencionDenuncia;
	}

	public Maestro getTipoAtencion() {
		return tipoAtencion;
	}
	public void setTipoAtencion(Maestro tipoAtencion) {
		this.tipoAtencion = tipoAtencion;
	}
	
	public Maestro getMotivo() {
		return motivo;
	}
	public void setMotivo(Maestro motivo) {
		this.motivo = motivo;
	}
//	public Maestro getTipoEntidadCompetente() {
//		return tipoEntidadCompetente;
//	}
//	public void setTipoEntidadCompetente(Maestro tipoEntidadCompetente) {
//		this.tipoEntidadCompetente = tipoEntidadCompetente;
//	}
//	public Efa getEfa() {
//		return efa;
//	}
//	public void setEfa(Efa efa) {
//		this.efa = efa;
//	}
//	public PersonaOefa getPersonaOefa() {
//		return personaOefa;
//	}
//	public void setPersonaOefa(PersonaOefa personaOefa) {
//		this.personaOefa = personaOefa;
//	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Maestro getEstado() {
		if(estado==null){
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
	public Denuncia getDenuncia() {
		if(denuncia == null){
			denuncia = new Denuncia();
		}
		return denuncia;
	}
	public void setDenuncia(Denuncia denuncia) {
		this.denuncia = denuncia;
	}

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}
	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
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
	public BandejaDetalle getDetalleBandeja() {
		if(detalleBandeja==null){
			detalleBandeja = new BandejaDetalle();
		}
		return detalleBandeja;
	}
	public void setDetalleBandeja(BandejaDetalle detalleBandeja) {
		this.detalleBandeja = detalleBandeja;
	}
	public String getHojaTramite() {
		return hojaTramite;
	}
	public void setHojaTramite(String hojaTramite) {
		this.hojaTramite = hojaTramite;
	}
	
	
	
}
