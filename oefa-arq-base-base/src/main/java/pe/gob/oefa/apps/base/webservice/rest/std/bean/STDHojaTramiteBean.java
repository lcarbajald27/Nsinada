package pe.gob.oefa.apps.base.webservice.rest.std.bean;

public class STDHojaTramiteBean {

	private String NROHOJA;
	private String ANIO;
	private String INTEXT;
	private STDDenunciaBean[] PCURSOR;
	
	public STDHojaTramiteBean() {
		super();
	}

	public String getNROHOJA() {
		return NROHOJA;
	}

	public void setNROHOJA(String nROHOJA) {
		NROHOJA = nROHOJA;
	}

	public String getANIO() {
		return ANIO;
	}

	public void setANIO(String aNIO) {
		ANIO = aNIO;
	}

	public String getINTEXT() {
		return INTEXT;
	}

	public void setINTEXT(String iNTEXT) {
		INTEXT = iNTEXT;
	}

	public STDDenunciaBean[] getPCURSOR() {
		return PCURSOR;
	}

	public void setPCURSOR(STDDenunciaBean[] pCURSOR) {
		PCURSOR = pCURSOR;
	}
	
	public STDDenunciaBean getDenuncia(){
		STDDenunciaBean denuncia= new STDDenunciaBean();
		if (PCURSOR!=null) {
			if (PCURSOR.length>0) {
				denuncia=PCURSOR[0];
			}
		}
		return denuncia;
	}

	@Override
	public String toString() {
		return  
				"STDBean [NROHOJA=" + NROHOJA + ", ANIO=" + ANIO + ", INTEXT="
				+ INTEXT + ", PCURSOR=" + PCURSOR + "]";
	}
	
}
