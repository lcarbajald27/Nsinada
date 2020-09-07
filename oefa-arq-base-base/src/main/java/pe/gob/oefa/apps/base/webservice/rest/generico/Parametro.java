package pe.gob.oefa.apps.base.webservice.rest.generico;

public class Parametro {
	private String prm1;
	private String prm2;
	private String prm3;
	private String prm4;
	private String prm5;
	
	public Parametro() {
		super();
	}

	public String getPrm1() {
		return prm1;
	}

	public void setPrm1(String prm1) {
		this.prm1 = prm1;
	}

	public String getPrm2() {
		return prm2;
	}

	public void setPrm2(String prm2) {
		this.prm2 = prm2;
	}

	public String getPrm3() {
		return prm3;
	}

	public void setPrm3(String prm3) {
		this.prm3 = prm3;
	}

	public String getPrm4() {
		return prm4;
	}

	public void setPrm4(String prm4) {
		this.prm4 = prm4;
	}

	public String getPrm5() {
		return prm5;
	}

	public void setPrm5(String prm5) {
		this.prm5 = prm5;
	}

	@Override
	public String toString() {
		return "Parametro [prm1=" + prm1 + ", prm2=" + prm2 + ", prm3=" + prm3
				+ ", prm4=" + prm4 + ", prm5=" + prm5 + "]";
	}
	
	
}
