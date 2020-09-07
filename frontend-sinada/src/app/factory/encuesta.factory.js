(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('EncuestaFactory', EncuestaFactory);

	/* @ngInject */
	function EncuestaFactory(API_CONFIG, APIFactory,MaestroFactory,BandejaFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
				model 	   	 		 : model,
				listar 				 : listar,
				registrar 			 : registrar
		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {

					  idEncuesta 		:0,
					  idDenuncia 		:0,
					  pregunta1 		:MaestroFactory.model(),
					  pregunta2 		:MaestroFactory.model(),
					  pregunta3 		:MaestroFactory.model(),
					  estado 			:0,
					  tipoEncuesta 		:MaestroFactory.model(), 
					  bandejaDetalle 	:BandejaFactory.detalleBandeja()

			};
		}
	

		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'encuesta/listar', prmData);
		}
		
		function registrar(prmData) {
		
			return APIFactory.registrar(API_CONFIG.url+'encuesta/registrar', prmData);
		}

	/*	function obtener(idMaestro) {
			return APIFactory.obtener(API_CONFIG.url+'maestro/obtener/', idAutor);
		}*/

	
		
	/*	function actualizar(prmMaestro) {
			return APIFactory.actualizar(API_CONFIG.url+'maestro/actualizar', prmMaestro);
		}

		function eliminar(prmMaestro) {
			return APIFactory.eliminar(API_CONFIG.url+'maestro/eliminar', prmMaestro);
		}*/

		/*function buscarMaestros(prmListaMaestros) {
			return APIFactory.listar(API_CONFIG.url+'maestro/buscarMaestros', prmListaMaestros);
		}*/
	}
})();