(function() {
	'use strict';

	angular
		.module('spapp.producto')
		.controller('ProductoListDialogController', ProductoListDialogController);

	/* @ngInject */
	function ProductoListDialogController($state, $controller, $scope, ProductoFactory, toastr, ngDialog) {
		var vm = this;

		/*declaracion de variables globales*/
		vm.listaProducto = [];
		vm.prmProducto = ProductoFactory.modelProducto();

		/*variables de paginacion*/
		
        vm.opcionesPaginacion = {
        	itemsPorPag : 10,
        	cantidades : [10,25,50,100]
        };
		
		/*declaración de metodos enlazados a la vista*/
        vm.limpiar = limpiar;
		vm.buscar = buscar;
		vm.seleccionar = seleccionar;

		/*implementación de metodos*/
		
        function buscar() {
			ProductoFactory
			.listar(angular.copy(vm.prmProducto))
			.then(function (response) {
				if (response.valido) {
					vm.listaProducto= response.data;
				}
				else {
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) {
				toastr.error('Error al consultar');
			});
		}

		function limpiar() {
			vm.prmProducto= ProductoFactory.modelProducto();
			vm.prmProducto.nombre = '';
		}

		function seleccionar(itemProducto) {
        	$scope.closeThisDialog({producto : angular.copy(itemProducto)});
        }

		function init() {
			limpiar();
			
		}
		
		init();


		/*fin de metodos*/
	}
})();