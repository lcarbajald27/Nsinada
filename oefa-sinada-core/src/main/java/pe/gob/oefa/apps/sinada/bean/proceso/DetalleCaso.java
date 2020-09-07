package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class DetalleCaso extends BaseBean{
	
	private long 	idDetalleCaso;
    private int 	tipoCaso;
    private String 	descripcion;
    private long 	codigoPadre;
    private int 	tipoNivel;
    private int 	estado;
    private String 	flagActivo;

	public DetalleCaso() {
		super();
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

	public int getTipoCaso() {
		return tipoCaso;
	}

	public void setTipoCaso(int tipoCaso) {
		this.tipoCaso = tipoCaso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(long codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	public int getTipoNivel() {
		return tipoNivel;
	}

	public void setTipoNivel(int tipoNivel) {
		this.tipoNivel = tipoNivel;
	}

	public long getIdDetalleCaso() {
		return idDetalleCaso;
	}

	public void setIdDetalleCaso(long idDetalleCaso) {
		this.idDetalleCaso = idDetalleCaso;
	}
	
	
}
