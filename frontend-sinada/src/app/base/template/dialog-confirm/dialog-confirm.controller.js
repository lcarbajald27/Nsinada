(function() {
	'use strict';

	angular
	.module('spapp')
	.controller('DialogConfirmController',DialogConfirmController);


	/** @ngInject */
	function DialogConfirmController(ngDialog, $scope) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.titulo = null;
		vm.mensaje = null;
		/*declaración de métodos enlazados a la vista*/
			
		

		/*implementación de métodos*/
		
		function init() {
			
			if (angular.isDefined($scope.ngDialogData)) {
				vm.titulo = angular.copy($scope.ngDialogData.Titulo);
				vm.mensaje = angular.copy($scope.ngDialogData.Mensaje);
			}
		}

		init();
	}
})();