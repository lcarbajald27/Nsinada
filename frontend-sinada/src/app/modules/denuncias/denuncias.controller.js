(function() {
	'use strict';

	angular
	.module('spapp.denuncias')
	.controller('DenunciasController',DenunciasController);


	/** @ngInject */
	function DenunciasController($state, INTERNAL_HOME_PAGE) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.itemTabActivo = 1;//Primer Tab por defecto
		
		/*declaración de metodos enlazados a la vista*/

		/*implementación de metodos*/

		/*fin de metodos*/
	}
})();