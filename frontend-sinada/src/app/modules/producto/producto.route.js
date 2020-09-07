(function() {
	'use strict';

	angular
		.module('spapp.producto')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.productos',{
				url : '/productos',
				templateUrl : 'app/modules/producto/producto.html',
				controller : 'ProductoController',
				controllerAs : 'vm'
			})
			.state('spapp.home.productos.listado',{
				url : '/listado',
				templateUrl : 'app/modules/producto/listado/producto-listado.html',
				controller : 'ProductoListadoController',
				controllerAs : 'vm'
			})
			.state('spapp.home.productos.registro',{
				url : '/registro',
				templateUrl : 'app/modules/producto/registro/producto-registro.html',
				controller : 'ProductoRegistroController',
				controllerAs : 'vm'
			});
		/*fin de route*/
	}
})();