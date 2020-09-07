(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('PerfilFactory', PerfilFactory);

	/* @ngInject */
	function PerfilFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model : model,
			listar : listar,
			registrar : registrar,
			/*obtener : obtener,*/
			actualizar : actualizar,
			eliminar : eliminar
		/*	loginUsuario : loginUsuario,
			cambiarClave : cambiarClave*/
		};

		return factory;

		/* implementacion de metodos */
			

		function model() {
			return {

				idPerfil:0,
				nombrePerfil : '',
				estado :0,
				flagActivo:''
			};
		}

		function listar() {
			return APIFactory.listar(API_CONFIG.url+'perfil/listar');
		}

		function registrar(prmPerfil) {
			return APIFactory.registrar(API_CONFIG.url+'perfil/registrar', prmPerfil);
		}
/*
		function obtener(idUsuario) {
			return APIFactory.obtener(API_CONFIG.url+'perfil/obtener/', idUsuario);
		}
*/
		function actualizar(prmPerfil) {
			return APIFactory.actualizar(API_CONFIG.url+'perfil/actualizar', prmPerfil);
		}

		function eliminar(prmPerfil) {
			return APIFactory.eliminar(API_CONFIG.url+'perfil/eliminar', prmPerfil);
		}

	/*	function loginUsuario(prmPerfil){
			return APIFactory.consultaPOST(API_CONFIG.url+'perfil/login', prmUsuario);
		}

		function cambiarClave(prmPerfil){
			return APIFactory.actualizar(API_CONFIG.url+'perfil/cambiarClave',prmUsuario);
		}
*/
	}
})();