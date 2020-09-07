package pe.gob.oefa.apps.sinada.bean.proceso;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.general.Persona;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;

public class PersonaOefa extends BaseBean{

	private long 	idPersonaOefa;
	private Maestro direccion;
	private Maestro subDireccion;
	private Persona persona;
	private Maestro estado;
	private String flagActivo;
	public long getIdPersonaOefa() {
		return idPersonaOefa;
	}
	public Maestro getDireccion() {
		if(direccion==null){
			direccion = new Maestro();
		}
		return direccion;
	}
	public void setDireccion(Maestro direccion) {
		this.direccion = direccion;
	}
	public Maestro getSubDireccion() {
		if(subDireccion==null){
			subDireccion = new Maestro();
		}
		return subDireccion;
	}
	public void setSubDireccion(Maestro subDireccion) {
		this.subDireccion = subDireccion;
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
	
	public String getFlagActivo() {
		return flagActivo;
	}
	public void setFlagActivo(String flagActivo) {
		this.flagActivo = flagActivo;
	}
	public void setIdPersonaOefa(long idPersonaOefa) {
		this.idPersonaOefa = idPersonaOefa;
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
	

	
	
}
