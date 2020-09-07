package pe.gob.oefa.apps.sinada.persistencia.inf.proceso;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.persistencia.inf.BaseDAO;
import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;


public interface DenunciaDAO extends BaseDAO<Denuncia, Long>{

	public int generarCodigo(Denuncia prmDenuncia) throws PersistenciaException;
	
	public int cambiarEstado(Denuncia prmDenuncia) throws PersistenciaException;
	
	public int subirHojaTramite(Denuncia prmDenuncia) throws PersistenciaException;
	
	public int subirFichaDenuncia(Denuncia prmDenuncia) throws PersistenciaException;
	
	public List<Denuncia> buscarDenunciaInvitado(Denuncia prmBean) throws PersistenciaException;
	
	
	/*********************************** no Transaccional ***********************************************************/
	


	Long insertarDenunciaNoTransaccional(Denuncia prmBean)throws PersistenciaException;

	int generarCodigoDenunciaNoTransaccional(Denuncia prmDenuncia)throws PersistenciaException;

	int subirHojaTramiteDenunciaNoTransaccional(Denuncia prmDenuncia)
			throws PersistenciaException;

	int subirFichaDenunciaNoTransaccional(Denuncia prmDenuncia)
			throws PersistenciaException;
	
	
	public int bloquearTablaParaTransaccion() throws PersistenciaException;
	
}
