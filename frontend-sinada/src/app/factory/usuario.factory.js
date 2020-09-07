(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('UsuarioFactory', UsuarioFactory);

	/* @ngInject */
	function UsuarioFactory(API_CONFIG, APIFactory,PerfilFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			modelUsuario : modelUsuario,
			listar : listar,
		    registrar : registrar,
			obtener : obtener,
			actualizar : actualizar,
			eliminar : eliminar,
			loginUsuario : loginUsuario,
			validarUsuario : validarUsuario,
			validarDatosUsuarioPersona : validarDatosUsuarioPersona
		};

		return factory;

		/* implementacion de metodos */

		function modelUsuario() {
			return {
                idUsuario	  			: 0,
                tipoPersona   			: 0,
                idPersona	  			: 0,
                persona 	  			:{},    
                entidad		  			:{},
				nombreUsuario 			: '',
//				clave 		  			: '',
//              perfil 	      			: PerfilFactory.model(),
                estado		  			: 0,
                flagActivo 	  			: '',
                bandeja  	  			:{},
                tipoEntidadCompetente 	: 0,
                idEfa					: 0,
            	idPersonaOefa			:0,
            	personaOefa 			:{}
			};
		}
		
		

		function listar(prmUsuario) {
			return APIFactory.listar(API_CONFIG.url+'usuario/listar', prmUsuario);
		}
		
		function registrar(prmUsuario) {
			return APIFactory.registrar(API_CONFIG.url+'usuario/registrar', prmUsuario);
		}

		function obtener(idUsuario) {
			return APIFactory.obtener(API_CONFIG.url+'usuario/buscar-id/', idUsuario);
		}

		function actualizar(prmUsuario) {
			return APIFactory.actualizar(API_CONFIG.url+'usuario/actualizar', prmUsuario);
		}

		function eliminar(prmUsuario) {
			return APIFactory.eliminar(API_CONFIG.url+'usuario/eliminar', prmUsuario);
		}

		function loginUsuario(prmUsuario){
			return APIFactory.consultaPOST(API_CONFIG.url+'usuario/login', prmUsuario);
		}

		function validarDatosUsuarioPersona(ref){
			return APIFactory.listar(API_CONFIG.url+'usuario/validar-datos-usuario-persona', {'prm1':ref});
		}

		function validarUsuario(prmUsuario){
			return APIFactory.consultaPOST(API_CONFIG.url+'usuario/cambiarClave', prmUsuario);
		}
		/*fin de factory*/
		
	}
})();