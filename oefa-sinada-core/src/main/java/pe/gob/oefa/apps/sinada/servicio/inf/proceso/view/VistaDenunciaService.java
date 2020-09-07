package pe.gob.oefa.apps.sinada.servicio.inf.proceso.view;


import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.bean.proceso.view.VistaDenuncia;


public interface VistaDenunciaService extends BaseService<VistaDenuncia, Long>{

	public VistaDenuncia buscarFichaDenunciaPorId(VistaDenuncia prmData) throws ServicioException;
	
	public VistaDenuncia generarVisualizacionPreliminarFichaDenuncia(VistaDenuncia prmData) throws ServicioException;
	
}
