package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;

public class CasoEfa extends BaseBean{
	
	private long 	idCasoEfa;
	private	Efa     efa;
	private Caso 	caso;
	private Maestro 	tipoAsignacion;

    private String 	flagActivo;
    private String 	swEditable;
    
    private List<Efa> lstEfa;
    
	public CasoEfa() {
		super();
	}

	public long getIdCasoEfa()
	{
		return idCasoEfa;
	}

	public void setIdCasoEfa(long idCasoEfa)
	{
		this.idCasoEfa = idCasoEfa;
	}

	public Efa getEfa() {
		if(efa==null){
			efa = new Efa();
		}
		return efa;
	}

	public void setEfa(Efa efa) {
		this.efa = efa;
	}

	public String getFlagActivo()
	{
		return flagActivo;
	}

	public void setFlagActivo(String flagActivo)
	{
		this.flagActivo = flagActivo;
	}

	public String getSwEditable()
	{
		return swEditable;
	}

	public void setSwEditable(String swEditable)
	{
		this.swEditable = swEditable;
	}

	public Caso getCaso() {
		if(caso == null){
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

	public List<Efa> getLstEfa() {
		return lstEfa;
	}

	public void setLstEfa(List<Efa> lstEfa) {
		this.lstEfa = lstEfa;
	}
	
	
}
