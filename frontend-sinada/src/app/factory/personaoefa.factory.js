(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('PersonaOefaFactory', PersonaOefaFactory);

	/* @ngInject */
	function PersonaOefaFactory(API_CONFIG, APIFactory, MaestroFactory, DenunciaFactory, EntidadFactory, PersonaFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			listar 		: listar,
		    registrar 	: registrar,
			buscarPorId : buscarPorId,
			eliminar 	: eliminar,
			listarUsuarioSinada : listarUsuarioSinada

		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {	


			 idPersonaOefa 	:0,
			 direccion 		:MaestroFactory.model(),
			 subDireccion 	:MaestroFactory.model(),
			 persona 		:PersonaFactory.model(),
			 estado 		:MaestroFactory.model(),
			 flagActivo 	:''	

			};
		}



/* ************************************CASO**************************************** */
		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'persona-oefa/listar', prmData);
		}
		
		function registrar(prmData) {
			return APIFactory.registrar(API_CONFIG.url+'persona-oefa/registrar', prmData);
		}

		/*function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'persona-oefa/actualizar', prmData);
		}*/

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'persona-oefa/eliminar', prmData);
		}
		
		function buscarPorId(prmData) {
			return APIFactory.listar(API_CONFIG.url+'persona-oefa/buscarPorId', prmData);
		}


/**************************************************************************************/

		function listarUsuarioSinada(prmData) {
			return APIFactory.listar(API_CONFIG.url+'persona-oefa/listar-usuario-sinada', prmData);
		}
		
/* ******************************************************************************** */


	
		/*fin de factory*/
	}
})();