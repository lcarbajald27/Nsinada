(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('MaestroFactory', MaestroFactory);

	/* @ngInject */
	function MaestroFactory(API_CONFIG, APIFactory) {

		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
				model 	   	 		 : model,
				listar 				 : listar,
				buscarXCodigoMaestro : buscarXCodigoMaestro,
			    registrar 			 : registrar,
				actualizar 			 : actualizar,
				eliminar 			 : eliminar,
				buscarMaestros 		 : buscarMaestros,
				listarHijos			 : listarHijos,
				buscarRegistroHijo   : buscarRegistroHijo,
				obtenerParametros 	 : obtenerParametros
		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {

				  	idMaestro			:0,
    			  	codigoMaestro 		:'',
    			  	codigoRegistro 		:0,
    			  	descripcion 		:'',
    			  	abreviatura 		:'',
    			  	codigoRegistroPadre	:0,
    			  	flagActivo 			:'1',
    			  	codigoNivel 		:0,
    			  	valorGeneral01 		:'',
    			  	valorGeneral02 		:'',
    			  	valorGeneral03 		:'',
    			  	valorGeneral04 		:'',
    			  	valorGeneral05		:''

			};
		}
		function buscarRegistroHijo(prmMaestro) {
			return APIFactory.listar(API_CONFIG.url+'maestro/listar-registro-hijo', prmMaestro);
		}

		function listarHijos(prmMaestro) {
			return APIFactory.listar(API_CONFIG.url+'maestro/listarHijos', prmMaestro);
		}

		function listar() {
			return APIFactory.listar(API_CONFIG.url+'maestro/listar-codigo-maestro');
		}
		function buscarXCodigoMaestro(prmMaestro) {
			return APIFactory.listar(API_CONFIG.url+'maestro/buscar-maestro', prmMaestro);
		}

		function registrar(prmMaestro) {
			// debugger;
			return APIFactory.registrar(API_CONFIG.url+'maestro/registrar', prmMaestro);
		}

	/*	function obtener(idMaestro) {
			return APIFactory.obtener(API_CONFIG.url+'maestro/obtener/', idAutor);
		}*/



		function actualizar(prmMaestro) {
			return APIFactory.actualizar(API_CONFIG.url+'maestro/actualizar', prmMaestro);
		}

		function eliminar(prmMaestro) {
			return APIFactory.eliminar(API_CONFIG.url+'maestro/eliminar', prmMaestro);
		}

		function buscarMaestros(prmListaMaestros) {
			return APIFactory.listar(API_CONFIG.url+'maestro/buscarMaestros', prmListaMaestros);
		}
		
		function obtenerParametros(){
			return APIFactory.listar(API_CONFIG.url+'maestro/obtenerParametros');
		}
	}
})();