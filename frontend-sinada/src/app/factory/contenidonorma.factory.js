(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('ContenidoNormaFactory', ContenidoNormaFactory);

	/* @ngInject */
	function ContenidoNormaFactory(API_CONFIG, APIFactory,$q,$http ) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			
				model:model,
				listar:listar,
				buscar : buscar
			/*modelProducto : modelProducto*/
			
		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {	


		 	 		idContenidoNorma		: 0,
					idNorma 				: 0,
				    numeroNorma 			: '',
				    numeroContenidoNorma 	: '',
				    idTitulo 				: 0,
				    idCapitulo 				: 0,
				    idSubcapitulo 			: 0,
				    idSeccion 				: 0,
				    idArticulo 				: 0,
				    fechaVigencia 			: '',
				    estado 					: 0,
				    nombreEstado 			: '',
				    numeroTitulo 			: '',
				    descripcionTitulo 		: '',
				    numeroCapitulo 			: '',
				    descripcionCapitulo 	: '',
				    numeroSubCapitulo 		: '',
				    descripcionSubCapitulo	: '',
				    numeroSeccion 			: '',
				    descripcionSeccion 		: '',
				    numeroArticulo 			: '',
				    descripcionArticulo 	: '',
				    idCasoEfa 				: 0

			};
		}

		/*function listar() {
			return APIFactory.listar(API_CONFIG.url+'efa' );
		}*/
		

		function listar(prmContenido) {
			return APIFactory.listar(API_CONFIG.url+'contenidonorma/listar', prmContenido);
		}

	    function buscar(idcontenido) {
			return APIFactory.consultaPOST(API_CONFIG.url+'contenidonorma/buscarPorId/',idcontenido);
			
		}
		/*fin de factory*/
	}
})();