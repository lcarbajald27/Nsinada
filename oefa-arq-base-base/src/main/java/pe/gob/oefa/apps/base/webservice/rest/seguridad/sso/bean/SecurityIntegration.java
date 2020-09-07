package pe.gob.oefa.apps.base.webservice.rest.seguridad.sso.bean;

public class SecurityIntegration {
	
	private String wa;
	private String wresult;
	private String wctx;
	
	public SecurityIntegration(){}

	public String getWa() {
		return wa;
	}

	public void setWa(String wa) {
		this.wa = wa;
	}

	public String getWresult() {
		return wresult;
	}

	public void setWresult(String wresult) {
		this.wresult = wresult;
	}

	public String getWctx() {
		return wctx;
	}

	public void setWctx(String wctx) {
		this.wctx = wctx;
	}

	@Override
	public String toString()
	{
		return "SecurityIntegration ["
				+ (wa != null ? "wa=" + wa + ", " : "")
				+ (wresult != null ? "wresult=" + wresult + ", " : "")
				+ (wctx != null ? "wctx=" + wctx : "") + "]";
	}
	
	
}
