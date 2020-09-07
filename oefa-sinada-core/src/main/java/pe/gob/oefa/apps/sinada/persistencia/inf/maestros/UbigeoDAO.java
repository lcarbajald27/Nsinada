package pe.gob.oefa.apps.sinada.persistencia.inf.maestros;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.maestros.CentroPoblado;
import pe.gob.oefa.apps.sinada.bean.maestros.Departamento;
import pe.gob.oefa.apps.sinada.bean.maestros.Distrito;
import pe.gob.oefa.apps.sinada.bean.maestros.Provincia;
import pe.gob.oefa.apps.sinada.bean.maestros.Ubigeo;

public interface UbigeoDAO extends BaseDAO<Ubigeo, Long>{

	public List<Ubigeo> listarDepartamento() throws PersistenciaException;
	
	public List<Ubigeo> listarProvincia(Ubigeo prmData) throws PersistenciaException;
	
	public List<Ubigeo> listarDistrito(Ubigeo prmData) throws PersistenciaException;
	
	public List<CentroPoblado> listarCentroPoblado(CentroPoblado prmData) throws PersistenciaException;
	
}
