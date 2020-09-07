package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;
import pe.gob.oefa.apps.sinada.bean.maestros.Maestro;
import pe.gob.oefa.apps.sinada.bean.sirin.ContenidoNorma;
import pe.gob.oefa.apps.sinada.bean.sirin.Normas;

public class NormaCaso extends BaseBean{
	
	private long 	idNormaCaso;
	private long 	idCasoEfa;
	private long 	idCasoOefa;
	private Maestro 	tipoNormaCaso;
	private ContenidoNorma    contenidoNorma;
    private String 	flagActivo;

	public NormaCaso() {
		super();
	}

	public long getIdNormaCaso()
	{
		return idNormaCaso;
	}

	public void setIdNormaCaso(long idNormaCaso)
	{
		this.idNormaCaso = idNormaCaso;
	}

	public long getIdCasoEfa()
	{
		return idCasoEfa;
	}

	public void setIdCasoEfa(long idCasoEfa)
	{
		this.idCasoEfa = idCasoEfa;
	}

	public String getFlagActivo()
	{
		return flagActivo;
	}

	public void setFlagActivo(String flagActivo)
	{
		this.flagActivo = flagActivo;
	}
	
	public ContenidoNorma getContenidoNorma() {
		if(contenidoNorma == null){
			contenidoNorma = new ContenidoNorma();
		}
		return contenidoNorma;
	}

	public void setContenidoNorma(ContenidoNorma contenidoNorma) {
		this.contenidoNorma = contenidoNorma;
	}
	

	public long getIdCasoOefa() {
		return idCasoOefa;
	}

	public void setIdCasoOefa(long idCasoOefa) {
		this.idCasoOefa = idCasoOefa;
	}

	public Maestro getTipoNormaCaso() {
		return tipoNormaCaso;
	}

	public void setTipoNormaCaso(Maestro tipoNormaCaso) {
		this.tipoNormaCaso = tipoNormaCaso;
	}

	@Override
	public String toString() {
		return "NormaCaso [idNormaCaso=" + idNormaCaso + ", idCasoEfa="
				+ idCasoEfa + ", idCasoOefa=" + idCasoOefa + ", tipoNormaCaso="
				+ tipoNormaCaso + ", contenidoNorma=" + contenidoNorma
				+ ", flagActivo=" + flagActivo + ", getIdNormaCaso()="
				+ getIdNormaCaso() + ", getIdCasoEfa()=" + getIdCasoEfa()
				+ ", getFlagActivo()=" + getFlagActivo()
				+ ", getContenidoNorma()=" + getContenidoNorma()
				+ ", getIdCasoOefa()=" + getIdCasoOefa()
				+ ", getTipoNormaCaso()=" + getTipoNormaCaso()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}



}
