(function() {
	'use strict';

	angular
		.module('spapp.maestro')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.maestros',{
				url : '/maestros',
				templateUrl : 'app/modules/maestro/maestro.html',
				controller : 'MaestroController',
				controllerAs : 'vm'
			})
			.state('spapp.home.maestros.listado',{
				url : '/listado',
				templateUrl : 'app/modules/maestro/listado/maestro-listado.html',
				controller : 'MaestroListadoController',
				controllerAs : 'vm'
			});
		/*fin de route*/
	}
})();