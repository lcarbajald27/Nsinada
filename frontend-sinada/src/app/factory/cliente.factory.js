(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('ClienteFactory', ClienteFactory);

	/* @ngInject */
	function ClienteFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			modelCliente : modelCliente,
			listar : listar,
		    registrar : registrar,
			obtener : obtener,
			actualizar : actualizar,
			eliminar : eliminar,
			buscarPorRUC : buscarPorRUC
		};

		return factory;

		/* implementacion de metodos */

		function modelCliente() {
			return {				
				idCliente : 0,
				direccion : null,
				razonSocial : null,
				ruc : null,
				flagActivo : '0',
			};
		}

		function listar(prmCliente) {
			return APIFactory.listar(API_CONFIG.url+'cliente', prmCliente);
		}
		
		function registrar(prmCliente) {
			return APIFactory.registrar(API_CONFIG.url+'cliente/registrar', prmCliente);
		}

		function obtener(idCliente) {
			return APIFactory.obtener(API_CONFIG.url+'cliente/', idCliente);
		}

		function actualizar(prmCliente) {
			return APIFactory.actualizar(API_CONFIG.url+'cliente/actualizar', prmCliente);
		}

		function eliminar(prmCliente) {
			return APIFactory.eliminar(API_CONFIG.url+'cliente/eliminar', prmCliente);
		}

		function buscarPorRUC(prmCliente) {
			return APIFactory.consultaPOST(API_CONFIG.url+'cliente/buscar-x-ruc',prmCliente);
			
		}
		/*fin de factory*/
	}
})();