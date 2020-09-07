(function() {
	'use strict';

	angular
	.module('spapp.usuario')
	.controller('UsuarioController',UsuarioController);


	/** @ngInject */
	function UsuarioController($state, INTERNAL_HOME_PAGE) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.itemTabActivo = 1;//Primer Tab por defecto
		
		/*declaración de metodos enlazados a la vista*/

		/*implementación de metodos*/

		/*fin de metodos*/
	}
})();