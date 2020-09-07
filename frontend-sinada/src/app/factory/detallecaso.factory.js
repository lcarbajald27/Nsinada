(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('DetalleCasoFactory', DetalleCasoFactory);

	/* @ngInject */
	function DetalleCasoFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			listar 		: listar,
		    registrar 	: registrar,
			actualizar 	: actualizar,
			eliminar 	: eliminar,
			buscarPorId:buscarPorId,
			listarDetalleCasoRegistrado : listarDetalleCasoRegistrado
		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {		

		 	 	idDetalleCaso		:0,
	     	 	tipoCaso			:0,
	     	 	descripcion			:'',
	     	 	codigoPadre			:0,
	     	 	tipoNivel			:0,
	     	 	estado				:0,
	     	 	flagActivo			:''	
			};
		}
	

/* ************************************CASO**************************************** */
		function listarDetalleCasoRegistrado(prmData) {
			return APIFactory.listar(API_CONFIG.url+'detalle-caso/listar-detalle-caso-registrado', prmData);
		}

		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'detalle-caso/listar', prmData);
		}
		
		
		function registrar(prmData) {
			return APIFactory.registrar(API_CONFIG.url+'detalle-caso/registrar', prmData);
		}

		function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'detalle-caso/actualizar', prmData);
		}

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'detalle-caso/eliminar', prmData);
		}
		
		function buscarPorId(prmData) {
			return APIFactory.listar(API_CONFIG.url+'detalle-caso/buscarPorId', prmData);
		}
/* ******************************************************************************** */

		/*fin de factory*/
	}
})();