package pe.gob.oefa.apps.sinada.servicio.inf.proceso;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import pe.gob.oefa.apps.base.persistencia.exception.PersistenciaException;
import pe.gob.oefa.apps.base.presentacion.response.RespuestaHttp;
import pe.gob.oefa.apps.base.servicio.exception.ServicioException;
import pe.gob.oefa.apps.base.servicio.inf.BaseService;
import pe.gob.oefa.apps.sinada.bean.proceso.Denuncia;
import pe.gob.oefa.apps.sinada.bean.proceso.view.VistaDenuncia;

public interface DenunciaService extends BaseService<Denuncia, Long>{

	public int generarCodigo(Denuncia prmDenuncia) throws ServicioException;
	
	public int cambiarEstado(Denuncia prmDenuncia) throws ServicioException;
	
	public int subirHojaTramite(Denuncia prmDenuncia) throws ServicioException;
	
	public int subirFichaDenuncia(Denuncia prmDenuncia) throws ServicioException;

	public List<Denuncia> buscarDenunciaInvitado(Denuncia prmBean) throws ServicioException;
	
	public RespuestaHttp registraDenunciaGenerico(Denuncia prmDenuncia,MultipartFile[] archivo, HttpServletRequest request) throws PersistenciaException, NumberFormatException, ServicioException, GeneralSecurityException, IOException, Exception;
	
	public VistaDenuncia obtenerDatosDenunciaGenerico(Denuncia prmData) throws ServicioException;
	
}
