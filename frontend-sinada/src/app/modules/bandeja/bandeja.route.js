(function() {
	'use strict';
	angular
		.module('spapp.bandeja')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.bandeja',{
				url : '/bandeja',
				templateUrl : 'app/modules/bandeja/bandeja.html',
				controller : 'BandejaController',
				controllerAs : 'vm'
			})
			.state('spapp.home.bandeja.asignadas',{
				url : '/asignadas',
				templateUrl : 'app/modules/bandeja/asignadas/asignadas-bandeja.html',
				controller : 'AsignadasController',
				controllerAs : 'vm'
			})
			.state('spapp.home.bandeja.asignadas-detalle',{
				url : '/asignadas-detalle',
				templateUrl : 'app/modules/bandeja/asignadas/asignadas-detalle/asignada-detalle.html',
				controller : 'AsignadaDetalleController',
				controllerAs : 'vm'
			})
			.state('spapp.home.bandeja.asignadas-informar',{
				url : '/asignadas-informar',
				templateUrl : 'app/modules/bandeja/asignadas/asignadas-informar/asignada-informar.html',
				controller : 'AsignadaInformarController',
				controllerAs : 'vm'
			})
			.state('spapp.home.bandeja.asignacion',{
				url : '/asignacion',
				templateUrl : 'app/modules/bandeja/asignacion/asignacion.html',
				controller : 'AsignacionController',
				controllerAs : 'vm'
			})
			.state('spapp.home.bandeja.denunciante',{
				url : '/denunciante',
				templateUrl : 'app/modules/bandeja/denunciante/denunciante.html',
				controller : 'DenuncianteController',
				controllerAs : 'vm'
			})
			
			.state('spapp.home.bandeja.historial-denunciante',{
				url : '/historial-denunciante',
				templateUrl : 'app/modules/bandeja/denunciante/historial-acciones-denunciante/historial-accion-denunciante.html',
				controller : 'HistorialAccionDenuncianteController',
				controllerAs : 'vm'
			})
			.state('spapp.home.bandeja.historial-coordinador',{
				url : '/historial-coordinador',
				templateUrl : 'app/modules/bandeja/asignacion/historial/historial-acciones-denuncias-coordinador.html',
				controller : 'HistorialAccionesDenunciasCoordinadorController',
				controllerAs : 'vm'
			})
			.state('spapp.home.bandeja.historial',{
				url : '/historial',
				templateUrl : 'app/modules/bandeja/asignadas/historial-acciones/historial-accion.html',
				controller : 'HistorialAccionController',
				controllerAs : 'vm'
			});
			/*
			.state('spapp.home.acciones',{
				url : '/acciones',
				templateUrl : 'app/modules/acciones/acciones.html',
				controller : 'AccionesController',
				controllerAs : 'vm'
			})
			.state('spapp.home.acciones.denunciante',{
				url : '/denunciante',
				templateUrl : 'app/modules/acciones/revisarEfa/revisarEfa.html',
				controller : 'RevisarEfaController',
				controllerAs : 'vm'
			});*/
		/*fin de route*/
	}
})();