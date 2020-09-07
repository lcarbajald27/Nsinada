package pe.gob.oefa.apps.base.webservice.rest.base.bean;

public class BaseBean {

	private String FechaRegistro;

	public BaseBean() {

	}

	public String getFechaRegistro() {
		return FechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		FechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "BaseBean [FechaRegistro=" + FechaRegistro + "]";
	}

}
