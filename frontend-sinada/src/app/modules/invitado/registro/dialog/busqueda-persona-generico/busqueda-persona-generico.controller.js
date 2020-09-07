(function() {
	'use strict';

	angular
	.module('spapp')
	.controller('BusquedaPersonaGenericoController',BusquedaPersonaGenericoController);


	/** @ngInject */
	function BusquedaPersonaGenericoController(toastr, $rootScope,ngDialog, $scope,PersonaFactory) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.titulo = "Búsqueda de persona";
		vm.mensaje = null;
		vm.dataPersonaSSO = PersonaFactory.personaSSOmodel();
		vm.listaPersonaSSO = [];
		vm.obtenerPersona=obtenerPersona;
		vm.listarPersona=listarPersona;
		$scope.config = {
				    itemsPerPage: 10,
				    fillLastPage: true,
				    current:1
		};

		function obtenerPersona(data){

			$rootScope.dataPersonaSSOObtenida = data;
			ngDialog.close();

		}
	
		/*declaración de métodos enlazados a la vista*/
	

		function listarPersona(){
		
			PersonaFactory
						.listarPersonaSSO(vm.dataPersonaSSO)
						.then(function (response) {
							if (response.valido) {

								vm.listaPersonaSSO = response.data;
						
							
								
							}
							
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {

						});

		}

		

		/*implementación de métodos*/
		
		function init() {

			
			if(angular.isDefined($rootScope.tipoPersona)){
				vm.dataPersonaSSO.tipoPersona=angular.copy($rootScope.tipoPersona);
			}
			if(angular.isDefined($rootScope.tituloBusqueda)){
				vm.titulo =angular.copy($rootScope.tituloBusqueda);
			}
			
			//listarPersona();
			/*if (angular.isDefined($scope.ngDialogData)) {
				vm.titulo = angular.copy($scope.ngDialogData.Titulo);
				vm.mensaje = angular.copy($scope.ngDialogData.Mensaje);
			}*/
		}

		init();
	}
})();