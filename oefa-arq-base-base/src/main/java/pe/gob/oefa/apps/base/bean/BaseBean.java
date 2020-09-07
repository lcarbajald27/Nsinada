package pe.gob.oefa.apps.base.bean;

public class BaseBean
{
	private long idUsuario;
	private String hostIp;
	private String prm1;
	private int numeroItem;
	
	public BaseBean()
	{
		
	}
	
	public long getIdUsuario()
	{
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario)
	{
		this.idUsuario = idUsuario;
	}
	public String getHostIp()
	{
		return hostIp;
	}
	public void setHostIp(String hostIp)
	{
		this.hostIp = hostIp;
	}
	@Override
	public String toString()
	{
		return "BaseBean [idUsuario=" + idUsuario + ", hostIp=" + hostIp + "]";
	}

	public String getPrm1() {
		return prm1;
	}

	public void setPrm1(String prm1) {
		this.prm1 = prm1;
	}

	public int getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}
	
	
	
}
