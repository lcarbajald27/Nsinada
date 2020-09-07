package pe.gob.oefa.apps.sinada.servicio.inf.proceso;


import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.Caso;


public interface CasoService extends BaseService<Caso, Long>{
	
	public long generarCodigoCaso() throws ServicioException;
	public Caso validaCasoRegistrado(Caso prmCaso)throws ServicioException;
	
	
}
