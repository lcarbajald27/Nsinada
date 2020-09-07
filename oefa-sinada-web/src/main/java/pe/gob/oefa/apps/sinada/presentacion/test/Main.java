package pe.gob.oefa.apps.sinada.presentacion.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pe.gob.oefa.apps.sinada.presentacion.util.AlfrescoUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.ResourceUtil;
import pe.gob.oefa.apps.sinada.presentacion.util.VO;

public class Main {
	
	
	public static List<String> genericArchivoAlfresco(List<String> multipartFileArray,String folder)
			throws Exception {
		try {
			List<String> lstUiidArchivo = new ArrayList<String>();

			for (String objArchivo : multipartFileArray) {

				// Obtiene el nombre del archivo
//				String tmpNombreArchivo = objArchivo.getOriginalFilename();
				MultipartFile archivo = null;
				/** EL SIGUIENTE CODIGO ES LA INTEGRACION CON WSALFRESCO **/
				String uiid = AlfrescoUtil
						.grabarArchivoAlfresco(archivo, folder);

				if (VO.isNullOrEmpty(uiid)) {

					return null;
				}

				lstUiidArchivo.add(uiid);

			}
			return lstUiidArchivo;
			
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
		

	}

	public static void main(String[] args) {
		
		String[] attachFiles = new String[3];
        attachFiles[0] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (29).pdf";
        attachFiles[1] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (30).pdf";
        attachFiles[2] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (31).pdf";
        
        
      List<String> listData = new ArrayList<String>();
      listData.add("adsaads");
      listData.add("adsaads");
		int flagMetodoProceso=0;
		
		String flagAlfresco = ResourceUtil.getKey("file.alfreso");
		List<String> lstArchivoGenericoAlfresco = new ArrayList<String>();
		if(listData!=null && listData.size()!=0 && !flagAlfresco.equals("0")){
			/********* Ruta ****************/
			String folderGeneric = ResourceUtil.getKey("file.path.informe-atencion");
			folderGeneric = folderGeneric.replace("{id}", String.valueOf(0));
			/********* Ruta ****************/
			try {
				lstArchivoGenericoAlfresco =	 genericArchivoAlfresco(listData,folderGeneric);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(lstArchivoGenericoAlfresco==null || lstArchivoGenericoAlfresco.size()==0){
//				respuesta.setMensaje("Hubo un error al registrar el archivo, intentelo mas tarde.");
				flagMetodoProceso=1;
//				return respuesta;
				
			}
			
		}
//		StringBuffer sb= new StringBuffer("\"xxxPK_eIdUsuario\"");
//		sb.append(":158,");
//		sb.append("\"uCodUsuario\"");
//		sb.append(":");
//		sb.append("\"ANOVOA\",");
//		System.out.println(sb.toString());
//		String s=sb.toString();
//		System.out.println(s);
//
//		int indIdUsu= sb.indexOf("PK_eIdUsuario")+15;
//		int indC1= sb.indexOf(",",indIdUsu+1);
//		System.out.println(sb.substring((indIdUsu), indC1));
//			
//		int indIdCod= sb.indexOf("uCodUsuario")+13;
//		int indC2= sb.indexOf(",",indIdCod);
//		System.out.println(sb.substring((indIdCod), indC2));
		
		//"PK_eIdUsuario":158,"uCodUsuario":"ANOVOA"
		//System.out.println("PK_eIdUsuario".length());
	}	
}