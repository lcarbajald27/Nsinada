(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('UbigeoFactory', UbigeoFactory);

	/* @ngInject */
	function UbigeoFactory(API_CONFIG, APIFactory,MaestroFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			departamento : departamento,
			provincia : provincia,
			distrito : distrito,
			listarDepartamento : listarDepartamento,
			listarProvincia : listarProvincia,
			listarDistrito  : listarDistrito,
			centroPoblado   : centroPoblado,
			registrar : registrar,
			/*obtener : obtener,*/
			actualizar : actualizar,
			eliminar : eliminar,
			listarCentroPoblado : listarCentroPoblado
		/*	loginUsuario : loginUsuario,
			cambiarClave : cambiarClave*/
		};

		return factory;

		/* implementacion de metodos */
			

		function departamento() {
			return {

				 	 codigoDepartamento			:'',
				 	 descripcionDepartamento	:''
			};
		}


		function provincia() {
			return {

			 		 codigoDepartamento		:'',
			 		 codigoProvincia		:'',
			 		 descripcion 			:''
			};
		}

		function distrito() {
			return {

			 		  codigoDepartamento	:'',
					  codigoProvincia		:'',
					  codigoDistrito 		:'',
					  descripcionDistrito 	:''
			};
		}
		
		function centroPoblado() {
			return {

					  objectid					:0,
					  codigoCentroPoblado		:'',
					  nombreCentroPoblado 		:'',
					  codigoDistrito 			:''
			};
		}


		function listarDepartamento(prmUbigeo) {
			return APIFactory.listar(API_CONFIG.url+'ubigeo/listar-departamento',prmUbigeo);
		}

		function listarProvincia(prmUbigeo) {
			return APIFactory.listar(API_CONFIG.url+'ubigeo/listar-provincia',prmUbigeo);
		}

		function listarDistrito(prmUbigeo) {
			return APIFactory.listar(API_CONFIG.url+'ubigeo/listar-distrito',prmUbigeo);
		}

		function listarCentroPoblado(prmUbigeo) {
			return APIFactory.listar(API_CONFIG.url+'ubigeo/listar-centro-poblado', prmUbigeo);
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