package pe.gob.oefa.apps.sinada.persistencia.inf.proceso.view;


import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.proceso.ArchivoAtencion;
import pe.gob.oefa.apps.sinada.bean.proceso.view.AccionesRealizadas;
import pe.gob.oefa.apps.sinada.bean.proceso.view.VistaDenuncia;


public interface VistaDenunciaDAO extends BaseDAO<VistaDenuncia, Long>{

	public VistaDenuncia buscarFichaDenunciaPorId(VistaDenuncia prmData) throws PersistenceException;
	
	public VistaDenuncia generarVisualizacionPreliminarFichaDenuncia(VistaDenuncia prmData) throws PersistenceException;
	
}
