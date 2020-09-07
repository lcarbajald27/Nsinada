(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('ReporteFactory', ReporteFactory);

	/* @ngInject */
	function ReporteFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			buscarPorAnio : buscarPorAnio
		};

		return factory;

		/* implementacion de metodos */

		function buscarPorAnio(prmAnio) {
			return APIFactory.listar(API_CONFIG.url+'pedido/reporte-anio?anio='+prmAnio);
		}
		
		/*fin de factory*/
	}
})();