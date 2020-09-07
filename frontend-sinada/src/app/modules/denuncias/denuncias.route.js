(function() {
	'use strict';

	angular
		.module('spapp.denuncias')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.denuncias',{
				url : '/denuncias',
				templateUrl : 'app/modules/denuncias/denuncias.html',
				controller : 'DenunciasController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.casos',{
				url : '/casos',
				templateUrl : 'app/modules/denuncias/casos/casos-registro.html',
				controller : 'CasosRegistroController',
				controllerAs : 'vm'
			})
			/*.state('spapp.home.denuncias.listado',{
				url : '/listado',
				templateUrl : 'app/modules/denuncias/denuncias/listado/denuncias-listado.html',
				controller : 'DenunciasListadoController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro',{
				url : '/registro',
				templateUrl : 'app/modules/denuncias/denuncias/registro/denuncias-registro.html',
				controller : 'DenunciasRegistroController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso1',{
				url : '/paso-1',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-1.html',
				controller : 'DenunciasRegPaso1Controller',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso2',{
				url : '/paso-2',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-2.html',
				controller : 'DenunciasRegPaso2Controller',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso3',{
				url : '/paso-3',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-3.html',
				controller : 'DenunciasRegPaso3Controller',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso4',{
				url : '/paso-4',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-4.html',
				controller : 'DenunciasRegPaso4Controller',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso5',{
				url : '/paso-5',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-5.html',
				controller : 'DenunciasRegPaso5Controller',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso6',{
				url : '/paso-6',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-6.html',
				controller : 'DenunciasRegPaso6Controller',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso7',{
				url : '/paso-7',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-7.html',
				controller : 'DenunciasRegPaso7Controller',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.registro.paso8',{
				url : '/paso-8',
				templateUrl : 'app/modules/denuncias/denuncias/registro/pasos/denuncias-reg-paso-8.html',
				controller : 'DenunciasRegPaso8Controller',
				controllerAs : 'vm'
			})*/
			.state('spapp.home.denuncias.seguimiento',{
				url : '/seguimiento',
				templateUrl : 'app/modules/denuncias/seguimiento/seguimiento-registro.html',
				controller : 'SeguimientoRegistroController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.historial-general-denuncia',{
				url : '/historial-denuncia-general',
				templateUrl : 'app/modules/denuncias/seguimiento/historial-acciones-general-especialista/historial-accion-general-especialista.html',
				controller : 'HistorialAccionGeneralEspecialistaController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.historial-seguimiento',{
				url : '/historial-seguimiento',
				templateUrl : 'app/modules/denuncias/seguimiento/historial-acciones/historial-accion-seguimiento.html',
				controller : 'HistorialAccionSeguimientoController',
				controllerAs : 'vm'
			})
			/********* MSB ******/
			.state('spapp.home.denuncias.estado',{
				url : '/estado-general',
				templateUrl : 'app/modules/denuncias/estado/estado.html',
				controller : 'EstadoController',
				controllerAs : 'vm'
			})/*
			.state('spapp.home.denuncias.bandeja',{
				url : '/bandeja',
				templateUrl : 'app/modules/denuncias/bandeja/bandeja.html',
				controller : 'BandejaController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.bandeja.asignadas',{
				url : '/asignadas',
				templateUrl : 'app/modules/denuncias/bandeja/asignadas/asignadas.html',
				controller : 'AsignadasController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.bandeja.asignacion',{
				url : '/asignacion',
				templateUrl : 'app/modules/denuncias/bandeja/asignacion/asignacion.html',
				controller : 'AsignacionController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.bandeja.denunciante',{
				url : '/denunciante',
				templateUrl : 'app/modules/denuncias/bandeja/denunciante/denunciante.html',
				controller : 'DenuncianteController',
				controllerAs : 'vm'
			})*/
			.state('spapp.home.denuncias.acciones',{
				url : '/acciones',
				templateUrl : 'app/modules/denuncias/acciones/acciones.html',
				controller : 'AccionesController',
				controllerAs : 'vm'
			})
			.state('spapp.home.denuncias.acciones.denunciante',{
				url : '/denunciante',
				templateUrl : 'app/modules/denuncias/acciones/revisarEfa/revisarEfa.html',
				controller : 'RevisarEfaController',
				controllerAs : 'vm'
			});
			/********* MSB *****/
		/*fin de route*/
	}
})();