(function() {
	'use strict';

	angular
		.module('spapp.pedido')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.pedidos',{
				url : '/pedidos',
				templateUrl : 'app/modules/pedido/pedido.html',
				controller : 'PedidoController',
				controllerAs : 'vm'
			})
			.state('spapp.home.pedidos.listado',{
				url : '/listado',
				templateUrl : 'app/modules/pedido/listado/pedido-listado.html',
				controller : 'PedidoListadoController',
				controllerAs : 'vm'
			})
			.state('spapp.home.pedidos.registro',{
				url : '/registro',
				templateUrl : 'app/modules/pedido/registro/pedido-registro.html',
				controller : 'PedidoRegistroController',
				controllerAs : 'vm'
			});
		/*fin de route*/
	}
})();