package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;

public class DerivacionDenuncia extends BaseBean{

	private long idDerivacionDenuncia;
	private EvaluacionRechazo evaluacionRechazo;
	private Maestro tipoDestinatario;  // 2 : EFA -- 1 : OEFA
	private Efa efa;
	private Maestro direccionSupervicion;
	private Maestro subDireccion;
	private Maestro coordinacion;
	private String 	nombreEntidad;
	private Maestro estado;
	private String flagActivo;



	public long getIdDerivacionDenuncia() {
		return idDerivacionDenuncia;
	}

	public void setIdDerivacionDenuncia(long idDerivacionDenuncia) {
		this.idDerivacionDenuncia = idDerivacionDenuncia;
	}

	public EvaluacionRechazo getEvaluacionRechazo() {
		if(evaluacionRechazo==null){
			evaluacionRechazo = new EvaluacionRechazo();
		}
		return evaluacionRechazo;
	}

	public void setEvaluacionRechazo(EvaluacionRechazo evaluacionRechazo) {
		this.evaluacionRechazo = evaluacionRechazo;
	}

	public Maestro getTipoDestinatario() {
		if(tipoDestinatario == null){
			tipoDestinatario = new Maestro();
		}
		return tipoDestinatario;
	}

	public void setTipoDestinatario(Maestro tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public Efa getEfa() {
		if(efa == null){
			
			efa = new Efa();
			
		}
		
		return efa;
	}

	public void setEfa(Efa efa) {
		this.efa = efa;
	}

	public Maestro getDireccionSupervicion() {
		if(direccionSupervicion==null){
			direccionSupervicion = new Maestro();
			
		}
		
		return direccionSupervicion;
	}

	public void setDireccionSupervicion(Maestro direccionSupervicion) {
		this.direccionSupervicion = direccionSupervicion;
	}

	public Maestro getSubDireccion() {
		if(subDireccion == null){
			subDireccion = new Maestro();
		}
		return subDireccion;
	}

	public void setSubDireccion(Maestro subDireccion) {
		this.subDireccion = subDireccion;
	}

	public Maestro getCoordinacion() {
		if(coordinacion == null){
			coordinacion = new Maestro();
		}
		return coordinacion;
	}

	public void setCoordinacion(Maestro coordinacion) {
		this.coordinacion = coordinacion;
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

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	
	
}
