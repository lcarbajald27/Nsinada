(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('InformeAccionFactory', InformeAccionFactory);

	/* @ngInject */
	function InformeAccionFactory(API_CONFIG, APIFactory, MaestroFactory,PersonaOefaFactory, DenunciaFactory, EntidadFactory, PersonaFactory,CasoEfaFactory,BandejaFactory ) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			listar 		: listar,
		    registrar 	: registrar,
			actualizar 	: actualizar,
			buscarPorId : buscarPorId,
			eliminar 	: eliminar,
			listarSegundoNivelBandejaEspecialista : listarSegundoNivelBandejaEspecialista

		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {		
					  idInformeAccion  		:0,
					  denuncia 				: DenunciaFactory.model(),

					  detalleBandeja		:BandejaFactory.detalleBandeja(),

					  tipoAccion 			: MaestroFactory.model(),
					  tipoSupervicion 		: MaestroFactory.model(),
					  descripcionAccion 	:'',
					  estado 				: MaestroFactory.model(),
					  flagActivo 			: '',
					  fechaRegistro 		:'',
					  tipoInforme			:MaestroFactory.model(),
					  lstArchivoInformeAccion:[],
					  idInformeCorregido      : 0
			};
		}


		/*	function modelArchivoAdjunto() {
			return {		
					  idArchivoInforme 			:0,
					  informeAccion 			:model(),
					  archivoInforme 			:'',
					  rutaArchivoInforme 		:'',
					  estado					:MaestroFactory.model(),
					  flagActivo 				:''
			};
		}*/




/* ************************************CASO**************************************** */
		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'informe-accion/listar', prmData);
		}
		
			function registrar(files, prmData) {
			
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
			return APIFactory.guardarArchivo(API_CONFIG.url+'informe-accion/registrar', formData);
		}

		function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'informe-accion/actualizar', prmData);
		}

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'informe-accion/eliminar', prmData);
		}
		
		function buscarPorId(prmData) {
			return APIFactory.listar(API_CONFIG.url+'informe-accion/buscarPorId', prmData);
		}

		
/* ******************************************************************************** */

/****************************************  VISTA ENTIDAD DENUNCIA  ********************************************/
	
	function listarSegundoNivelBandejaEspecialista(prmData) {
			return APIFactory.listar(API_CONFIG.url+'informe-accion/listar-entidad-denuncia', prmData);
		}
		
/**************************************************************************************************************/

	
		/*fin de factory*/
	}
})();