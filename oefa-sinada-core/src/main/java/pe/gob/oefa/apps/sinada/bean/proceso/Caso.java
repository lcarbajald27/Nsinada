package pe.gob.oefa.apps.sinada.bean.proceso;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class Caso extends BaseBean{
	
	private long		idCaso;
    private String 			numeroCaso;
    private DetalleCaso 	problematica = new DetalleCaso();
    
    private DetalleCaso 	debidoA1;
    private DetalleCaso 	debidoA2;
    private DetalleCaso 	debidoA3;
    
    private DetalleCaso 	zonasuceso1;
    private DetalleCaso 	zonasuceso2;
    private DetalleCaso 	zonasuceso3;
    
    private int 	estado;
    private String 	flagActivo;

	public Caso() {
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

	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	public String getNumeroCaso() {
		return numeroCaso;
	}

	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}

	public DetalleCaso getProblematica() {
		return problematica;
	}

	public void setProblematica(DetalleCaso problematica) {
		this.problematica = problematica;
	}

	public DetalleCaso getDebidoA1() {
		return debidoA1;
	}

	public void setDebidoA1(DetalleCaso debidoA1) {
		this.debidoA1 = debidoA1;
	}

	public DetalleCaso getDebidoA2() {
		return debidoA2;
	}

	public void setDebidoA2(DetalleCaso debidoA2) {
		this.debidoA2 = debidoA2;
	}

	public DetalleCaso getDebidoA3() {
		return debidoA3;
	}

	public void setDebidoA3(DetalleCaso debidoA3) {
		this.debidoA3 = debidoA3;
	}

	public DetalleCaso getZonasuceso1() {
		return zonasuceso1;
	}

	public void setZonasuceso1(DetalleCaso zonasuceso1) {
		this.zonasuceso1 = zonasuceso1;
	}

	public DetalleCaso getZonasuceso2() {
		return zonasuceso2;
	}

	public void setZonasuceso2(DetalleCaso zonasuceso2) {
		this.zonasuceso2 = zonasuceso2;
	}

	public DetalleCaso getZonasuceso3() {
		return zonasuceso3;
	}

	public void setZonasuceso3(DetalleCaso zonasuceso3) {
		this.zonasuceso3 = zonasuceso3;
	}

	
	
}
