(function() {
	'use strict';

	angular
	.module('spapp')
	.controller('DialogMensajeDenunciadoController',DialogMensajeDenunciadoController);


	/** @ngInject */
	function DialogMensajeDenunciadoController($rootScope,ngDialog, $scope) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.titulo = null;
		vm.mensaje = null;
		$rootScope.tipoOperacionDenunciado = 0;
		vm.operacionDenunciado=operacionDenunciado;
		
		/*declaración de métodos enlazados a la vista*/
			function operacionDenunciado(item){

					$rootScope.tipoOperacionDenunciado = item;
					ngDialog.close();

			}


		

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