package pe.gob.oefa.apps.sinada.bean.proceso.view;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class DenunciaEstado extends BaseBean{

	private long idDenuncia;
	private int totalAtenciones;
	private int denunciasAtendidas;
	private int denunciasArchivadas;
	private int denunciasNoAtendidas;
	private int denunciasPendientes;
	public long getIdDenuncia() {
		return idDenuncia;
	}
	public void setIdDenuncia(long idDenuncia) {
		this.idDenuncia = idDenuncia;
	}
	public int getTotalAtenciones() {
		return totalAtenciones;
	}
	public void setTotalAtenciones(int totalAtenciones) {
		this.totalAtenciones = totalAtenciones;
	}
	public int getDenunciasAtendidas() {
		return denunciasAtendidas;
	}
	public void setDenunciasAtendidas(int denunciasAtendidas) {
		this.denunciasAtendidas = denunciasAtendidas;
	}
	public int getDenunciasArchivadas() {
		return denunciasArchivadas;
	}
	public void setDenunciasArchivadas(int denunciasArchivadas) {
		this.denunciasArchivadas = denunciasArchivadas;
	}
	public int getDenunciasNoAtendidas() {
		return denunciasNoAtendidas;
	}
	public void setDenunciasNoAtendidas(int denunciasNoAtendidas) {
		this.denunciasNoAtendidas = denunciasNoAtendidas;
	}
	public int getDenunciasPendientes() {
		return denunciasPendientes;
	}
	public void setDenunciasPendientes(int denunciasPendientes) {
		this.denunciasPendientes = denunciasPendientes;
	}
	
	
	
	
}
