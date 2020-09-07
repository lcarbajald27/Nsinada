(function() {
	'use strict';

	angular
		.module('spapp.reporte')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.reportes',{
				url : '/reportes',
				templateUrl : 'app/modules/reporte/reporte.html',
				controller : 'ReporteController',
				controllerAs : 'vm'
			})
			.state('spapp.home.reportes.anio',{
				url : '/anio',
				templateUrl : 'app/modules/reporte/anio/reporte-anio.html',
				controller : 'ReporteAnioController',
				controllerAs : 'vm'
			});
		/*fin de route*/
	}
})();