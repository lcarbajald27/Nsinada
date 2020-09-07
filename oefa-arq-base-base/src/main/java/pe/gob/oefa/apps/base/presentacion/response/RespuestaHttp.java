package pe.gob.oefa.apps.base.presentacion.response;

import java.io.Serializable;

public class RespuestaHttp implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean valido;
	private Object data;
	private String mensaje;
	private Object objData;
	
	public RespuestaHttp(){}
	
	public RespuestaHttp(boolean valido, Object data, String mensaje)
	{
		this.valido = valido;
		this.data = data;
		this.mensaje = mensaje;
	}
	
	public boolean isValido()
	{
		return valido;
	}
	public void setValido(boolean valido)
	{
		this.valido = valido;
	}
	public Object getData()
	{
		return data;
	}
	public void setData(Object data)
	{
		this.data = data;
	}
	public String getMensaje()
	{
		return mensaje;
	}
	public void setMensaje(String mensaje)
	{
		this.mensaje = mensaje;
	}


	public Object getObjData() {
		return objData;
	}

	public void setObjData(Object objData) {
		this.objData = objData;
	}

	@Override
	public String toString()
	{
		return "RespuestaHttp [valido=" + valido + ", "
				+ (data != null ? "data=" + data + ", " : "")
				+ (mensaje != null ? "mensaje=" + mensaje : "") + "]";
	}
	
	

}
