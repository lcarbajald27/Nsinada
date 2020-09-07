(function() {
	'use strict';

	angular
		.module('spapp.ficha')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.ficha',{
				url : '/ficha',
				templateUrl : 'app/modules/ficha-denuncia/ficha.html',
				controller : 'FichaController',
				controllerAs : 'vm'
			});
		/*fin de route*/
	}
})();