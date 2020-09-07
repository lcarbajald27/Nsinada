package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.general.Entidad;
import pe.gob.oefa.apps.sinada.bean.general.Persona;

public class Denunciante extends BaseBean{

	private long 		idDenunciante; 
    private long  		idDenuncia; 
    private int  		tipoPersona; 
    private long 		idPersona;
    private Persona 	personaDenunciante; 
    private Entidad 	entidadDenunciante; 
    private int  		estado; 
    private String  	flagActivo;

    
	public Denunciante() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getIdDenunciante() {
		return idDenunciante;
	}


	public void setIdDenunciante(long idDenunciante) {
		this.idDenunciante = idDenunciante;
	}




	public long getIdDenuncia() {
		return idDenuncia;
	}


	public void setIdDenuncia(long idDenuncia) {
		this.idDenuncia = idDenuncia;
	}


	public int getTipoPersona() {
		return tipoPersona;
	}


	public void setTipoPersona(int tipoPersona) {
		this.tipoPersona = tipoPersona;
	}


	public Persona getPersonaDenunciante() {
		return personaDenunciante;
	}


	public void setPersonaDenunciante(Persona personaDenunciante) {
		this.personaDenunciante = personaDenunciante;
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


	public Entidad getEntidadDenunciante() {
		return entidadDenunciante;
	}


	public void setEntidadDenunciante(Entidad entidadDenunciante) {
		this.entidadDenunciante = entidadDenunciante;
	}


	public long getIdPersona() {
		return idPersona;
	}


	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}



	
    
}
