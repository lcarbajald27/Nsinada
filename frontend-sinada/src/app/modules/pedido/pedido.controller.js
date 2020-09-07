(function() {
	'use strict';

	angular
	.module('spapp.pedido')
	.controller('PedidoController',PedidoController);


	/** @ngInject */
	function PedidoController($state,toastr,$rootScope , INTERNAL_HOME_PAGE) {
		var vm = this;
		
		/*declaracion de variables globales*/
		$rootScope.itemTabActivo = 1;//Primer Tab por defecto
		vm.irHacia = irHacia;
		
		function irHacia(item){
			if(angular.isDefined(localStorage.tmpIdPersonal)){
				$state.go('spapp.home.pedidos.'+item);
				$rootScope.itemTabActivo = 3;
			}else{
				toastr.info('Debe registrar al personal');
			}
		}
		/*declaración de metodos enlazados a la vista*/

		/*implementación de metodos*/

		/*fin de metodos*/
	}
})();