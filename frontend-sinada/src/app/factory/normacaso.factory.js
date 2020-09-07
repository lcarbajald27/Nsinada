(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('NormaCasoFactory', NormaCasoFactory);

	/* @ngInject */
	function NormaCasoFactory(MaestroFactory,API_CONFIG, APIFactory,ContenidoNormaFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
				model : model,
				registrar:registrar,
				listar:listar,
				actualizar:actualizar,
				eliminar:eliminar
			/*modelProducto : modelProducto*/
			
		};

		return factory;

		/* implementacion de metodos */
		
	
		function model() {
			return {	
				  	idNormaCaso	    :0,
				  	idCasoEfa 		:0,
				  	idCasoOefa 		:0,
				  	tipoNormaCaso:MaestroFactory.model(),
				     contenidoNorma :ContenidoNormaFactory.model(),
			      	flagActivo :''



			};
		}

		/*function listar() {
			return APIFactory.listar(API_CONFIG.url+'efa' );
		}*/
		
		function registrar(prmNormas) {
			return APIFactory.registrar(API_CONFIG.url+'normaCaso/registrar', prmNormas);
		}

		function listar(prmNormas) {
			return APIFactory.listar(API_CONFIG.url+'normaCaso/listar', prmNormas);
		}

		function actualizar(prmNormas) {
			return APIFactory.actualizar(API_CONFIG.url+'normaCaso/actualizar', prmNormas);
		}

		function eliminar(prmNormas) {
			return APIFactory.eliminar(API_CONFIG.url+'normaCaso/eliminar', prmNormas);
		}

	  
		/*fin de factory*/
	}
})();