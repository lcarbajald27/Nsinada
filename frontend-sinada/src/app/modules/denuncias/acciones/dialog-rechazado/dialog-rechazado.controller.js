(function() {
	'use strict';

	angular
		.module('spapp.denuncias')
		.controller('AccionesRechazadoDialogController', AccionesRechazadoDialogController);

	/* @ngInject */
	function AccionesRechazadoDialogController($state, $controller, $scope, toastr, ngDialog) {
		var vm = this;

		/*declaracion de variables globales*/
		/*vm.listaProducto = [];
		vm.prmProducto = ProductoFactory.modelProducto();*/

		/*variables de paginacion*/
		
        vm.opcionesPaginacion = {
        	itemsPorPag : 10,
        	cantidades : [10,25,50,100]
        };
		
		/*declaración de metodos enlazados a la vista*/
      /*  vm.limpiar = limpiar;
		vm.buscar = buscar;
		vm.seleccionar = seleccionar;
*/
		/*implementación de metodos*/
		
    /*

		function limpiar() {
			vm.prmProducto= ProductoFactory.modelProducto();
			vm.prmProducto.nombre = '';
		}*/

    
    	
	
		function init() {
		/*	limpiar();*/
			
		}
		
		init();


		/*fin de metodos*/
	}
})();