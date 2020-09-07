(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('EvaluacionInformeFactory', EvaluacionInformeFactory);

	/* @ngInject */
	function EvaluacionInformeFactory(API_CONFIG, APIFactory,AtencionDenuncia, MaestroFactory,PersonaOefaFactory, DenunciaFactory, EntidadFactory, PersonaFactory,CasoEfaFactory,BandejaFactory,InformeAccionFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			listar 		: listar,
		    registrar 	: registrar,
			actualizar 	: actualizar,
			buscarPorId : buscarPorId,
			eliminar 	: eliminar,
			aprobarAccion : aprobarAccion,
			buscarPorIdInformeAccion : buscarPorIdInformeAccion
		

		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {		
				  idEvaluacionInforme 		: 0,					//id Evaluacion Rechazo
				  tipoInforme 				: MaestroFactory.model(),		// Informe de Accion - Informe de Atencion
				  informeAccion 			: InformeAccionFactory.model(),
				  tipoOpcion 				: MaestroFactory.model(),		// Observar - aprobar
				  motivoDescripcion 		: '',
				  estado 					: MaestroFactory.model(),
				  flagActivo 				: '',
				  lstArchivoAtencion 		: []
			};
		}



/* ************************************ Evaluacion Informe **************************************** */
		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'evaluacion-informe/listar', prmData);
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
			return APIFactory.guardarArchivo(API_CONFIG.url+'evaluacion-informe/registrar', formData);
		}

			function aprobarAccion(prmData) {
		
			return APIFactory.registrar(API_CONFIG.url+'evaluacion-informe/aprobarAccion', prmData);
		}


	

		function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'evaluacion-informe/actualizar', prmData);
		}

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'evaluacion-informe/eliminar', prmData);
		}
		
		function buscarPorId(prmData) {
			return APIFactory.consultaPOST(API_CONFIG.url+'evaluacion-informe/buscarPorId', prmData);
		}

		function buscarPorIdInformeAccion(prmData) {
			return APIFactory.consultaPOST(API_CONFIG.url+'evaluacion-informe/buscarPorIdInformeAccion', prmData);
		}

		/* Listar Efa */

	
/* ******************************************************************************** */


		/*fin de factory*/
	}
})();