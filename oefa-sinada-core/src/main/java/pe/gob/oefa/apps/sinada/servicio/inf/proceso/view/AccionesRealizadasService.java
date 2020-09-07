package pe.gob.oefa.apps.sinada.servicio.inf.proceso.view;


import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;


public interface AccionesRealizadasService extends BaseService<AccionesRealizadas, Long>{

	public List<AccionesRealizadas> listarAccionesPorIdBandeja(AccionesRealizadas prmData)throws ServicioException;
	
	public AccionesRealizadas buscarPorTipoTablaIdAccion(AccionesRealizadas prmData) throws ServicioException;
	
	public List<AccionesRealizadas> listarAccionesRealizadasPorEstados(AccionesRealizadas prmData)throws ServicioException;
	
	
	
}
