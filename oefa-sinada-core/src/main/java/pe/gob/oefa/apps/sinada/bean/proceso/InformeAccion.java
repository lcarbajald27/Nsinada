package pe.gob.oefa.apps.sinada.bean.proceso;

import java.util.ArrayList;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;

public class InformeAccion extends BaseBean{

	private long idInformeAccion;
	private Denuncia denuncia;
	
	private BandejaDetalle detalleBandeja;
//	private Maestro tipoEntidadCompetente;
//	private Efa 	efa;
//	private PersonaOefa personaOefa;
	
	
	private Maestro tipoAccion;
	private Maestro tipoSupervicion;
	private String descripcionAccion;
	private Maestro estado;
	private String flagActivo;
	private String fechaInicio;
	private String fechaFin;
	private String fechaRegistro;
	private Maestro tipoInforme;
	private int numeroArchivosAdjuntos;
	private long idInformeCorregido; //private InformeAccion informeCorregido;
	
	private List<ArchivoAtencion> lstArchivoInformeAccion;
	public long getIdInformeAccion() {
		return idInformeAccion;
	}
	public void setIdInformeAccion(long idInformeAccion) {
		this.idInformeAccion = idInformeAccion;
	}
	public Denuncia getDenuncia() {
		return denuncia;
	}
	public void setDenuncia(Denuncia denuncia) {
		this.denuncia = denuncia;
	}

	public Maestro getTipoAccion() {
		return tipoAccion;
	}
	public void setTipoAccion(Maestro tipoAccion) {
		this.tipoAccion = tipoAccion;
	}
	public Maestro getTipoSupervicion() {
		return tipoSupervicion;
	}
	public void setTipoSupervicion(Maestro tipoSupervicion) {
		this.tipoSupervicion = tipoSupervicion;
	}
	public String getDescripcionAccion() {
		return descripcionAccion;
	}
	public void setDescripcionAccion(String descripcionAccion) {
		this.descripcionAccion = descripcionAccion;
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
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
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
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getNumeroArchivosAdjuntos() {
		return numeroArchivosAdjuntos;
	}
	public void setNumeroArchivosAdjuntos(int numeroArchivosAdjuntos) {
		this.numeroArchivosAdjuntos = numeroArchivosAdjuntos;
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
//		if(personaOefa==null){
//			personaOefa = new PersonaOefa();
//		}
//		return personaOefa;
//	}
//	public void setPersonaOefa(PersonaOefa personaOefa) {
//		this.personaOefa = personaOefa;
//	}
	public List<ArchivoAtencion> getLstArchivoInformeAccion() {
		if(lstArchivoInformeAccion==null){
			lstArchivoInformeAccion = new ArrayList<ArchivoAtencion>();
		}
		return lstArchivoInformeAccion;
	}
	public void setLstArchivoInformeAccion(
			List<ArchivoAtencion> lstArchivoInformeAccion) {
		this.lstArchivoInformeAccion = lstArchivoInformeAccion;
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
	public long getIdInformeCorregido() {
		return idInformeCorregido;
	}
	public void setIdInformeCorregido(long idInformeCorregido) {
		this.idInformeCorregido = idInformeCorregido;
	}
	

	

	
	
}
