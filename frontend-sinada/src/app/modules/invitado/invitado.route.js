(function() {
	'use strict';

	angular
		.module('spapp.invitado')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('invitado.inicio',{
				url : '/inicio',
				templateUrl : 'app/modules/invitado/inicio/invitado-inicio.html',
				controller : 'InvitadoInicioController',
				controllerAs : 'vm'
			})
			.state('invitado.registro',{
				url : '/registro',
				templateUrl : 'app/modules/invitado/registro/denuncias-invitado-registro.html',
				controller : 'DenunciasInvitadoRegistroController',
				controllerAs : 'vm'
			})
			.state('invitado.nuevo-usuario',{
				url : '/nuevo-usuario',
				templateUrl : 'app/modules/invitado/usuario/usuario-nuevo-registro.html',
				controller : 'UsuariosNuevoRegistroController',
				controllerAs : 'vm'
			})
			.state('invitado.listado',{
				url : '/listado',
				templateUrl : 'app/modules/invitado/listado/denuncias-invitado-listado.html',
				controller : 'DenunciasInvitadoListadoController',
				controllerAs : 'vm'
			})
			.state('invitado.historial',{
				url : '/historial',
				templateUrl : 'app/modules/invitado/listado/historial-denuncia-invitado/historial-denuncia-invitado.html',
				controller : 'HistorialDenunciaInvitadoController',
				controllerAs : 'vm'
			})
			
			.state('invitado.registro.paso1',{
				url : '/paso-1',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-1.html',
				controller : 'DenunciasInvitadoRegPaso1Controller',
				controllerAs : 'vm'
			})
			.state('invitado.registro.paso2',{
				url : '/paso-2',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-2.html',
				controller : 'DenunciasInvitadoRegPaso2Controller',
				controllerAs : 'vm'
			})
			.state('invitado.registro.paso3',{
				url : '/paso-4',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-3.html',
				controller : 'DenunciasInvitadoRegPaso3Controller',
				controllerAs : 'vm'
			})
			.state('invitado.registro.paso4',{
				url : '/paso-3',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-4.html',
				controller : 'DenunciasInvitadoRegPaso4Controller',
				controllerAs : 'vm'
			})
			.state('invitado.registro.paso5',{
				url : '/paso-5',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-5.html',
				controller : 'DenunciasInvitadoRegPaso5Controller',
				controllerAs : 'vm'
			})
			.state('invitado.registro.paso6',{
				url : '/paso-6',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-6.html',
				controller : 'DenunciasInvitadoRegPaso6Controller',
				controllerAs : 'vm'
			})
			.state('invitado.registro.paso7',{
				url : '/paso-7',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-7.html',
				controller : 'DenunciasInvitadoRegPaso7Controller',
				controllerAs : 'vm'
			})
			.state('invitado.registro.paso8',{
				url : '/paso-8',
				templateUrl : 'app/modules/invitado/registro/pasos/denuncias-invitado-reg-paso-8.html',
				controller : 'DenunciasInvitadoRegPaso8Controller',
				controllerAs : 'vm'
			})
			.state('invitado.encuesta',{
				url : '/encuesta',
				templateUrl : 'app/modules/invitado/registro/encuesta/encuesta-registro-denuncia.html',
				controller : 'EncuestaRegistroDenunciaController',
				controllerAs : 'vm'
			})
			/*.state('invitado.registro',{
				url : '/registro',
				templateUrl : 'app/modules/producto/listado/producto-listado.html',
				controller : 'ProductoListadoController',
				controllerAs : 'vm'
			})*/
			/*.state('spapp.home.invitado.registro',{
				url : '/registro',
				templateUrl : 'app/modules/producto/registro/producto-registro.html',
				controller : 'ProductoRegistroController',
				controllerAs : 'vm'
			});*/
		/*fin de route*/
	}
})();