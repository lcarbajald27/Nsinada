(function() {
	'use strict';

	angular
		.module('spapp.usuario')
		.config(routeConfig);

	/* @ngInject */
	function routeConfig($stateProvider) {
		/*inicio de route*/
		$stateProvider
			.state('spapp.home.usuarios',{
				url : '/usuarios',
				templateUrl : 'app/modules/usuario/usuario.html',
				controller : 'UsuarioController',
				controllerAs : 'vm'
			})
			.state('spapp.home.usuarios.listado',{
				url : '/listado',
				templateUrl : 'app/modules/usuario/listado/usuario-listado.html',
				controller : 'UsuarioListadoController',
				controllerAs : 'vm'
			})
			.state('spapp.home.usuarios.registro',{
				url : '/registro',
				templateUrl : 'app/modules/usuario/registro/usuario-registro.html',
				controller : 'UsuarioRegistroController',
				controllerAs : 'vm'
			});
		/*fin de route*/
	}
})();