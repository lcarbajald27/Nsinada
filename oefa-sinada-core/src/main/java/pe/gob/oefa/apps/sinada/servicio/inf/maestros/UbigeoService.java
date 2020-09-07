package pe.gob.oefa.apps.sinada.servicio.inf.maestros;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.maestros.CentroPoblado;
import pe.gob.oefa.apps.sinada.bean.maestros.Departamento;
import pe.gob.oefa.apps.sinada.bean.maestros.Distrito;
import pe.gob.oefa.apps.sinada.bean.maestros.Provincia;
import pe.gob.oefa.apps.sinada.bean.maestros.Ubigeo;

public interface UbigeoService  extends BaseService<Ubigeo, Long>{

	public List<Ubigeo> listarDepartamento() throws ServicioException;
	
	public List<Ubigeo> listarProvincia(Ubigeo prmData) throws ServicioException;
	
	public List<Ubigeo> listarDistrito(Ubigeo prmData) throws ServicioException;
	
	public List<CentroPoblado> listarCentroPoblado(CentroPoblado prmData) throws ServicioException;
	
	
}
