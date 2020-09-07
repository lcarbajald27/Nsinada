package pe.gob.oefa.apps.sinada.bean.seguridad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.general.ContactoPersona;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.proceso.Bandeja;
import pe.gob.oefa.apps.sinada.bean.proceso.Notificaciones;
import pe.gob.oefa.apps.sinada.bean.proceso.PersonaOefa;

public class Usuario extends BaseBean{

	private long 	idUsuario;
    private int 	tipoPersona;
    private long 	idPersona;
    private Persona 	persona;
    private Entidad 	entidad;
    private String 	nombreUsuario;
//    private String 	clave;
//    private Perfil 	perfil;
    private int 	estado;
    private String 	flagActivo;
    
    /***************************/
    private Bandeja bandeja;
    private int 	tipoEntidadCompetente;
    private long	idEfa;
    private long    idPersonaOefa;
    private Bandeja bandejaEntidad;
    /***************************/
    
    private int validaExiste;
    
    
    /**** filtro Usuarios  ******/
    private int flagFiltroUsuario;
    private long idDenuncia;
    /********************/
    private List<Notificaciones> lstNotificaciones = null;
    private List<ContactoPersona> lstContactoPersona = new ArrayList<ContactoPersona>();
    
    
    /********************/
    
    private PersonaOefa personaOefa;
    
    private String nombrePersona;
    private long idPerfil;
    
    private String claveAntigua;
    private String claveNueva;
    
    /*********************/

	public Bandeja getBandeja() {
		return bandeja;
	}

	public void setBandeja(Bandeja bandeja) {
		this.bandeja = bandeja;
	}



	public long getIdPersonaOefa() {
		return idPersonaOefa;
	}

	public void setIdPersonaOefa(long idPersonaOefa) {
		this.idPersonaOefa = idPersonaOefa;
	}

	public Usuario() {
		super();
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(int tipoPersona) {
		this.tipoPersona = tipoPersona;
	}



	public Persona getPersona() {
		if(persona==null){
			persona = new Persona();
		}
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}



	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getFlagActivo() {
		return flagActivo;
	}

	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public long getIdEfa() {
		return idEfa;
	}

	public void setIdEfa(long idEfa) {
		this.idEfa = idEfa;
	}

	public int getTipoEntidadCompetente() {
		return tipoEntidadCompetente;
	}

	public void setTipoEntidadCompetente(int tipoEntidadCompetente) {
		this.tipoEntidadCompetente = tipoEntidadCompetente;
	}

	public Bandeja getBandejaEntidad() {
		if(bandejaEntidad==null){
			bandejaEntidad = new Bandeja();
		}
		return bandejaEntidad;
	}

	public void setBandejaEntidad(Bandeja bandejaEntidad) {
		this.bandejaEntidad = bandejaEntidad;
	}

	public List<Notificaciones> getLstNotificaciones() {
		if(lstNotificaciones==null){
			lstNotificaciones = new ArrayList<Notificaciones>();
		}
		return lstNotificaciones;
	}

	public void setLstNotificaciones(List<Notificaciones> lstNotificaciones) {
		this.lstNotificaciones = lstNotificaciones;
	}

	public long getIdDenuncia() {
		return idDenuncia;
	}

	public void setIdDenuncia(long idDenuncia) {
		this.idDenuncia = idDenuncia;
	}

	public int getFlagFiltroUsuario() {
		return flagFiltroUsuario;
	}

	public void setFlagFiltroUsuario(int flagFiltroUsuario) {
		this.flagFiltroUsuario = flagFiltroUsuario;
	}

	public List<ContactoPersona> getLstContactoPersona() {
		return lstContactoPersona;
	}

	public void setLstContactoPersona(List<ContactoPersona> lstContactoPersona) {
		this.lstContactoPersona = lstContactoPersona;
	}

	public String getNombrePersona() {
		return nombrePersona;
	}

	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public PersonaOefa getPersonaOefa() {
		return personaOefa;
	}

	public void setPersonaOefa(PersonaOefa personaOefa) {
		this.personaOefa = personaOefa;
	}

	public int getValidaExiste() {
		return validaExiste;
	}

	public void setValidaExiste(int validaExiste) {
		this.validaExiste = validaExiste;
	}

	public String getClaveAntigua() {
		return claveAntigua;
	}

	public void setClaveAntigua(String claveAntigua) {
		this.claveAntigua = claveAntigua;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}



	
	
}
