(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('ProductoFactory', ProductoFactory);

	/* @ngInject */
	function ProductoFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			modelProducto : modelProducto,
			listar : listar,
		    registrar : registrar,
			obtener : obtener,
			actualizar : actualizar,
			eliminar : eliminar,
			buscarPorNombre : buscarPorNombre
		};

		return factory;

		/* implementacion de metodos */

		function modelProducto() {
			return {				
				idProducto : 0,
				nombre : null,
				precio : null,
				stock : null,
				flagActivo : '0',
				idUsuario : 0,
			};
		}

		function listar(prmProducto) {
			return APIFactory.listar(API_CONFIG.url+'producto', prmProducto);
		}
		
		function registrar(prmProducto) {
			return APIFactory.registrar(API_CONFIG.url+'producto/registrar', prmProducto);
		}

		function obtener(idProducto) {
			return APIFactory.obtener(API_CONFIG.url+'producto/', idProducto);
		}

		function actualizar(prmProducto) {
			return APIFactory.actualizar(API_CONFIG.url+'producto/actualizar', prmProducto);
		}

		function eliminar(prmProducto) {
			return APIFactory.eliminar(API_CONFIG.url+'producto/eliminar', prmProducto);
		}

	    function buscarPorNombre(prmProducto) {
			return APIFactory.consultaPOST(API_CONFIG.url+'producto/buscar-x-nombre',prmProducto);
			
		}
		/*fin de factory*/
	}
})();