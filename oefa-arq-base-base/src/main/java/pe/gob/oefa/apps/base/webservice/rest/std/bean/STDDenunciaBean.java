package pe.gob.oefa.apps.base.webservice.rest.std.bean;

public class STDDenunciaBean {
	
	private String CODIGOSINADA;
	private String DESCRIPCION;
	private String REMITENTE;
	private String EXPEDIENTE;
	private String FLGEXITO;
	private String HOJANUEVA;
	
	public STDDenunciaBean() {
		super();
	}

	public String getDESCRIPCION() {
		return DESCRIPCION;
	}

	public void setDESCRIPCION(String dESCRIPCION) {
		DESCRIPCION = dESCRIPCION;
	}

	public String getREMITENTE() {
		return REMITENTE;
	}

	public void setREMITENTE(String rEMITENTE) {
		REMITENTE = rEMITENTE;
	}

	public String getFLGEXITO() {
		return FLGEXITO;
	}

	public void setFLGEXITO(String fLGEXITO) {
		FLGEXITO = fLGEXITO;
	}

	public String getHOJANUEVA() {
		return HOJANUEVA;
	}

	public void setHOJANUEVA(String hOJANUEVA) {
		HOJANUEVA = hOJANUEVA;
	}

	public String getEXPEDIENTE() {
		return EXPEDIENTE;
	}

	public void setEXPEDIENTE(String eXPEDIENTE) {
		EXPEDIENTE = eXPEDIENTE;
	}

	public String getCODIGOSINADA() {
		return CODIGOSINADA;
	}

	public void setCODIGOSINADA(String cODIGOSINADA) {
		CODIGOSINADA = cODIGOSINADA;
	}

	@Override
	public String toString() {
		return "STDDenunciaBean [CODIGOSINADA=" + CODIGOSINADA
				+ ", DESCRIPCION=" + DESCRIPCION + ", REMITENTE=" + REMITENTE
				+ ", EXPEDIENTE=" + EXPEDIENTE + ", FLGEXITO=" + FLGEXITO
				+ ", HOJANUEVA=" + HOJANUEVA + "]";
	}


	
	
}
