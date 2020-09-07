(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('AtencionDenuncia', AtencionDenuncia);

	/* @ngInject */
	function AtencionDenuncia(API_CONFIG, APIFactory, MaestroFactory,PersonaOefaFactory, DenunciaFactory, EntidadFactory, PersonaFactory,CasoEfaFactory,BandejaFactory) {

		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			listar 		: listar,
		    registrar 	: registrar,
			actualizar 	: actualizar,
			buscarPorId : buscarPorId,
			eliminar 	: eliminar,
			rechazarDenuncia : rechazarDenuncia,
			denunciaNoAtendida : denunciaNoAtendida,
			modelArchivoAtencion : modelArchivoAtencion,
			listarDatosDenunciaRechazada :listarDatosDenunciaRechazada,
			listarAccionPorIdBandejaDetalle : listarAccionPorIdBandejaDetalle,
			listarArchivoAtencion : listarArchivoAtencion,
			listarArchivoDenuncia : listarArchivoDenuncia,
			listarArchivoCaso 	  : listarArchivoCaso,
			listarAccionPorIdDenuncia: listarAccionPorIdDenuncia


		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {
				  idAtencionDenuncia 	:0,
				  denuncia 				:DenunciaFactory.model(),
				  detalleBandeja		:BandejaFactory.detalleBandeja(),
				/*  tipoEntidadCompetente	:MaestroFactory.model(),
				  efa 					:CasoEfaFactory.getEfa(),
				  personaOefa			:PersonaOefaFactory.model(),*/


				  tipoAtencion 			:MaestroFactory.model(),
				  motivo 				:MaestroFactory.model(),
				  fechaRegistro 		:'',
				  estado 				:MaestroFactory.model(),
				  flagActivo 			:'',
				  descripcionMotivo		:'',
				  hojaTramite			:'',
				  lstArchivoAtencion	:[]
			};
		}

		function modelArchivoAtencion(){
				return {
	 			idArchivoAtencion :0,
	 			idAtencionDenuncia :0,
	 			nombreArchivo :'',
	 			rutaArchivo:'',
	 			mimeTypeArchivo :'',
	 			estado :MaestroFactory.model(),
	 			flagActivo :'',
	 			tipoArchivo :MaestroFactory.model(),
	 			tipoTabla :0, // TipoTablaArchivoAtencion
	 			descripcionArchivo :'',
	 			posicionArchivo :''

				};
		}



/* ************************************CASO**************************************** */
		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'atencion-denuncia/listar', prmData);
		}

		function registrar(prmData) {
			return APIFactory.registrar(API_CONFIG.url+'atencion-denuncia/registrar', prmData);
		}

			function rechazarDenuncia(files, prmData) {

			var formData = new FormData();
			if(files instanceof FileList)
			{
				for(var x in files)
				{
					formData.append("file",files[x]);
				}
			}
			else
			{
				formData.append("file",files);
			}


			formData.append("strContenido",angular.toJson(prmData));
			return APIFactory.guardarArchivo(API_CONFIG.url+'atencion-denuncia/rechazarDenuncia', formData);
		}


			function denunciaNoAtendida(files, prmData) {
		// debugger;
			var formData = new FormData();

			for(var x in files)
				{
					formData.append("file",files[x]);
				}
			/*if(files instanceof FileList)
			{
				for(var x in files)
				{
					formData.append("file",files[x]);
				}
			}
			else
			{
				formData.append("file",files);
			}*/


			formData.append("strContenido",angular.toJson(prmData));
			return APIFactory.guardarArchivo(API_CONFIG.url+'atencion-denuncia/no-atencion-denuncia', formData);
		}

		function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'atencion-denuncia/actualizar', prmData);
		}

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'atencion-denuncia/eliminar', prmData);
		}

		function buscarPorId(prmData) {
			return APIFactory.listar(API_CONFIG.url+'atencion-denuncia/buscarPorId', prmData);
		}

		function listarDatosDenunciaRechazada(prmData) {
			return APIFactory.consultaPOST(API_CONFIG.url+'atencion-denuncia/buscar-denuncia-rechazada', prmData);
		}




/* ******************************************************************************** */

		/*  Acciones  */

		function listarAccionPorIdBandejaDetalle(prmData) {
			return APIFactory.listar(API_CONFIG.url+'atencion-denuncia/listarAccionPorIdBandejaDetalle', prmData);
		}

		function listarAccionPorIdDenuncia(prmData) {
			return APIFactory.listar(API_CONFIG.url+'atencion-denuncia/listarAccionPorIdDenuncia', prmData);
		}


		function listarArchivoAtencion(prmData) {
			return APIFactory.listar(API_CONFIG.url+'archivo-adjunto/listar-archivo-atencion', prmData);
		}

		function listarArchivoDenuncia(prmData) {
			return APIFactory.listar(API_CONFIG.url+'archivo-adjunto/listar-archivo-denuncia', prmData);
		}

		function listarArchivoCaso(prmData) {
			return APIFactory.listar(API_CONFIG.url+'archivo-adjunto/listar-archivo-Caso', prmData);
		}


		/*fin de factory*/
	}
})();