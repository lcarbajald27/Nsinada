package pe.gob.oefa.apps.base.webservice.rest.seguridad;

import pe.gob.oefa.apps.base.bean.BaseBean;

public class BaseSeguridadBean extends BaseBean{

    private String 		MensajeError;
    private long		TotalVirtual;
    
    private int			OptionSelected;
    
    private String 		cFlgActivo;
    private String 		cFlgEliminado;
    
    private long		eIdSesionIngresa;
    private long		eIdSesionModifica;
    
    private boolean 	EsEdicion;
    
	public BaseSeguridadBean() {
		super();
	}

	public boolean isEsEdicion() {
		return EsEdicion;
	}

	public void setEsEdicion(boolean esEdicion) {
		EsEdicion = esEdicion;
	}

	public String getMensajeError() {
		return MensajeError;
	}

	public void setMensajeError(String mensajeError) {
		MensajeError = mensajeError;
	}

	public long getTotalVirtual() {
		return TotalVirtual;
	}

	public void setTotalVirtual(long totalVirtual) {
		TotalVirtual = totalVirtual;
	}



	public int getOptionSelected() {
		return OptionSelected;
	}

	public void setOptionSelected(int optionSelected) {
		OptionSelected = optionSelected;
	}

	
	public String getcFlgActivo() {
		return cFlgActivo;
	}

	public void setcFlgActivo(String cFlgActivo) {
		this.cFlgActivo = cFlgActivo;
	}

	public String getcFlgEliminado() {
		return cFlgEliminado;
	}

	public void setcFlgEliminado(String cFlgEliminado) {
		this.cFlgEliminado = cFlgEliminado;
	}

	public long geteIdSesionIngresa() {
		return eIdSesionIngresa;
	}

	public void seteIdSesionIngresa(long eIdSesionIngresa) {
		this.eIdSesionIngresa = eIdSesionIngresa;
	}

	public long geteIdSesionModifica() {
		return eIdSesionModifica;
	}

	public void seteIdSesionModifica(long eIdSesionModifica) {
		this.eIdSesionModifica = eIdSesionModifica;
	}

	@Override
	public String toString() {
		return "BaseSeguridadBean [EsEdicion=" + EsEdicion + ", MensajeError="
				+ MensajeError + ", TotalVirtual=" + TotalVirtual
				+ ", OptionSelected=" + OptionSelected + ", cFlgActivo="
				+ cFlgActivo + ", cFlgEliminado=" + cFlgEliminado
				+ ", eIdSesionIngresa=" + eIdSesionIngresa
				+ ", eIdSesionModifica=" + eIdSesionModifica + "]";
	}
   
}
