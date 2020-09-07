package pe.gob.oefa.apps.sinada.persistencia.inf.sirefa;

import java.util.List;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.sinada.bean.sirefa.Efa;

public interface EfaDAO extends BaseDAO<Efa, Long>
{
	public List<Efa> buscarPorFiltros(Efa efa) throws PersistenciaException;
	
	public List<Efa> listarEfaDerivar(Efa efa) throws PersistenciaException;
	
	public Efa buscarEfaPorIdEntidad(Efa efa) throws PersistenciaException;
	
	public List<Efa> buscarPorIdEfaPorUbigeo(Efa efa) throws PersistenciaException;
	public boolean existeOD(String codOD) throws PersistenciaException;
	public boolean existeALA(String codALA) throws PersistenciaException;
	public boolean existeANP(String codANP) throws PersistenciaException;
	public boolean existeZA(String codZA) throws PersistenciaException;
	public int buscarCodigoUnidadOrganicaPorOD(String codOD) throws PersistenciaException;
	public int buscarCodigoUnidadOrganicaPorALA(String codALA) throws PersistenciaException;
	public int buscarCodigoUnidadOrganicaPorANP(String codANP) throws PersistenciaException;
	public int buscarCodigoUnidadOrganicaPorZA(String codZA) throws PersistenciaException;
}
