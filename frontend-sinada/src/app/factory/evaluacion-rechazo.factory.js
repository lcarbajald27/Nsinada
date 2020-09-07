(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('EvaluacionRechazoFactory', EvaluacionRechazoFactory);

	/* @ngInject */
	function EvaluacionRechazoFactory(API_CONFIG, APIFactory,AtencionDenuncia, MaestroFactory,PersonaOefaFactory, DenunciaFactory, EntidadFactory, PersonaFactory,CasoEfaFactory,BandejaFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			listar 		: listar,
		    registrar 	: registrar,
			actualizar 	: actualizar,
			buscarPorId : buscarPorId,
			eliminar 	: eliminar,
			modelDerivacionDenuncia : modelDerivacionDenuncia,
			listarEfaDerivar :listarEfaDerivar,
			derivarDenuncia : derivarDenuncia,
			buscarPorAtencion : buscarPorAtencion

		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {		
				 	 idEvaluacionRechazo	:0,					//id Evaluacion Rechazo
					 tipoEvaluacionRechazo 	:MaestroFactory.model(),// Reiterar - derivar - Archivar
					 atencionDenuncia 		:AtencionDenuncia.model(),
					 motivoDescripcion 		:'',		// Motivo campo de texto
					 motivo 				:MaestroFactory.model(),
					 estado 				:MaestroFactory.model(),
					 flagActivo 			:'',
					 lstArchivoAtencion 	:[],
					 lstDerivacionDenuncia  :[]
			};
		}



		function modelDerivacionDenuncia(){
			return {
					 idDerivacionDenuncia		:0,
					 evaluacionRechazo 			:model(),
					 tipoDestinatario 			:MaestroFactory.model(),
					 efa 						:CasoEfaFactory.getEfa(),
					 direccionSupervicion 		:MaestroFactory.model(),
					 subDireccion 				:MaestroFactory.model(),
					 coordinacion 				:MaestroFactory.model(),
					 estado 					:MaestroFactory.model(),
					 flagActivo 				:''
			};
		}
		




/* ************************************CASO**************************************** */
		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'evaluacion-rechazo/listar', prmData);
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
			return APIFactory.guardarArchivo(API_CONFIG.url+'evaluacion-rechazo/registrar', formData);
		}

		function derivarDenuncia(prmData) {
		
			return APIFactory.registrar(API_CONFIG.url+'evaluacion-rechazo/derivarDenuncia', prmData);
		}

		

		function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'evaluacion-rechazo/actualizar', prmData);
		}

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'evaluacion-rechazo/eliminar', prmData);
		}
		
		function buscarPorId(prmData) {
			return APIFactory.consultaPOST(API_CONFIG.url+'evaluacion-rechazo/buscarPorId', prmData);
		}

		function buscarPorAtencion(prmData) {
			return APIFactory.listar(API_CONFIG.url+'evaluacion-rechazo/buscar-por-atencion', prmData);
		}

		/* Listar Efa */

		function listarEfaDerivar(prmData) {
			return APIFactory.consultaPOST(API_CONFIG.url+'efa/listar-efas-derivar', prmData);
		}
		
/* ******************************************************************************** */


		/*fin de factory*/
	}
})();