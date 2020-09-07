package pe.gob.oefa.apps.sinada.presentacion.util;

public class FormatoCorreo_1 {
	
	
	public String formatoCorreoOrganosCompetentes(String codigo){
		
		 String data = "<html>"
		 		+ " <body>"
		 		+ " <div align='center'> <h3>Denuncia Generada Para su Atencion</h3>"
		 		+ " </div>"
		 		+ "<div align='center'>"
		 		+ "<font face='Candara' size='6'> "
		 		+ "Estimado Usuario le Informamos que se ha generado la denuncia " + codigo + " para su Atencion"
		 				+ "<br><br>"
		 				+ " </font>"
		 				+ "</div>"
		 				+ " </body>"
		 				+ "</html>";
		 
	

		return data;
		
	}
	

}
