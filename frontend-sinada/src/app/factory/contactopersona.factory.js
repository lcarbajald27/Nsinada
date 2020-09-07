(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('ContactoPersonaFactory', ContactoPersonaFactory);

	/* @ngInject */
	function ContactoPersonaFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model : model,
			listar : listar,
		    registrar : registrar,
			obtener : obtener,
			actualizar : actualizar,
			eliminar : eliminar,
			validarContactoPersona : validarContactoPersona
		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {
              
   	 	 		idContactoPersona	:0,
		 	 	tipoContacto		:0,
		 	 	valor 				:'',
		 	 	tipoPersona			:0,
		 	 	idPersona 			:0,
		 	 	estado 				:0,
		 	 	flagActivo			:''

			};
		}
		
		

		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'contactoPersona/listar', prmData);
		}
		
		function registrar(prmData) {
			return APIFactory.registrar(API_CONFIG.url+'contactoPersona/registrar', prmData);
		}


		function validarContactoPersona(prmData) {
			return APIFactory.consultaPOST(API_CONFIG.url+'contactoPersona/validarContacto', prmData);
		}


		function obtener(idContactoPersona) {
			return APIFactory.obtener(API_CONFIG.url+'contactoPersona/buscar-id/', idContactoPersona);
		}

		function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'contactoPersona/actualizar', prmData);
		}

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'contactoPersona/eliminar', prmData);
		}

		/*fin de factory*/
		
	}
})();