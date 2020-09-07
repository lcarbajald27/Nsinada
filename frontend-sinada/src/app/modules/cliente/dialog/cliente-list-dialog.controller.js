(function() {
	'use strict';

	angular
		.module('spapp.cliente')
		.controller('ClienteListDialogController', ClienteListDialogController);

	/* @ngInject */
	function ClienteListDialogController($state, $controller, $scope, ClienteFactory, toastr, ngDialog) {
		var vm = this;

		/*declaracion de variables globales*/
		vm.listaCliente = [];
		vm.prmCliente = ClienteFactory.modelCliente();

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
			ClienteFactory
			.listar(angular.copy(vm.prmCliente))
			.then(function (response) {
				if (response.valido) {
					vm.listaCliente = response.data;
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
			vm.prmCliente= ClienteFactory.modelCliente();
			vm.prmCliente.razonSocial = '';
			vm.prmCliente.ruc = '';
		}

		function seleccionar(itemCliente) {
        	$scope.closeThisDialog({cliente : angular.copy(itemCliente)});
        }

		function init() {
			limpiar();
			
		}
		
		init();


		/*fin de metodos*/
	}
})();