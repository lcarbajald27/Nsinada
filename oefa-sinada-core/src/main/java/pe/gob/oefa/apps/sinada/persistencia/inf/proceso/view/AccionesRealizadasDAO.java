package pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view;


import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;


public interface AccionesRealizadasDAO extends BaseDAO<AccionesRealizadas, Long>{

	public List<AccionesRealizadas> listarAccionesPorIdBandeja(AccionesRealizadas prmData)throws PersistenceException;
	
	public AccionesRealizadas buscarPorTipoTablaIdAccion(AccionesRealizadas prmData) throws PersistenceException;
	
	public List<AccionesRealizadas> listarAccionesRealizadasPorEstados(AccionesRealizadas prmData)throws PersistenceException;
	
	
	
}
