(function() {
	'use strict';

	angular
	.module('spapp.denuncias')
	.controller('CasoDondeSucedeDialogController',CasoDondeSucedeDialogController);


	/** @ngInject */
	function CasoDondeSucedeDialogController(toastr, $scope, ngDialog,DetalleCasoFactory) {
		var vm = this;
		
		/*declaracion de variables globales*/
 		vm.dataDondeSucede=DetalleCasoFactory.model();
		
		/*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.guardar=guardar;
        vm.confirmarEliminarDondeSucede=confirmarEliminarDondeSucede;

		/*implementación de metodos*/



				function confirmarEliminarDondeSucede() 
		{
			ngDialog
				.openConfirm({
					template : 'app/base/template/dialog-confirm/dialog-confirm.html',
					controller : 'DialogConfirmController',
					controllerAs : 'vm',
					data : {
						Titulo : 'Confirmar eliminación',
						Mensaje : '¿Está seguro de eliminar el dato de dónde sucede: '+vm.dataDondeSucede.descripcion +'?,  ya que podria afectar a los casos registrados. '
					},
					width : '380px'
				})
				.then(
					function (okValue) 
					{
						eliminarDondeSucede(vm.dataDondeSucede);
					}
				);
		}


		function eliminarDondeSucede(item)
		{
	 		
	 		var data = {
	 				idDetalleCaso : item.idDetalleCaso

	 		};
	 		DetalleCasoFactory
			.eliminar(angular.copy(data))
			.then(function (response)
			{
				if (response.valido) 
				{
//					vm.listarContacto();
//					vm.listarTitular();
					
					toastr.success(response.mensaje);
					ngDialog.close();
				}
				else 
				{
					toastr.error(response.mensaje);
				}
			}).catch(function (error) 
			{
				toastr.error('No se pudo eliminar a la entidad asignada a este caso');
			});
		}


		function guardar()
		{
			if(vm.dataDondeSucede.idDetalleCaso  == 0)
			{
				registrar();
			}
			else
			{
				actualizar();
			}
		}



		function actualizar()
		{
			if (vm.dataDondeSucede.descripcion != '')
            {
				DetalleCasoFactory
				.actualizar(vm.dataDondeSucede)
				.then(function (response) 
				{
					if (response.valido) 
					{
						toastr.success(response.mensaje);
						ngDialog.close();
					}
					else
					{
						toastr.info(response.mensaje)
					}
				})
				.catch(function (error) 
				{
					toastr.error('Ocurrió un error al actualizar la información');
				});
            }
			else
			{
				toastr.warning("Debe agregar una descripción");
			}
		}



		function registrar()
		{	
			if (vm.dataDondeSucede.descripcion != '')
            {

	  			DetalleCasoFactory
				.registrar(vm.dataDondeSucede)
				.then(function (response) 
				{
					if(response.valido)
					{
						vm.dataDondeSucede.idDetalleCaso = response.data;
						ngDialog.close();
						toastr.success(response.mensaje);
					}
					else
					{
						toastr.info(response.mensaje);
					}
				})
				.catch(function (error) 
				{
					toastr.error('Ocurrió un error al registrar información.');
				});
			}
			else
			{
				toastr.warning("Debe agregar una descripción");
			}
		}
		
		function buscarZonaSuceso()
		{
			DetalleCasoFactory
			.buscarPorId(vm.dataDondeSucede)
			.then(function (response) 
			{
				if (response.valido) 
				{
					vm.dataDondeSucede = response.data;
				
				}
				else 
				{
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) 
			{
				toastr.error('Error al consultar');
			})
			.finally(function () 
			{
							
			});
		
		}



        function cancelar() 
        {
			ngDialog.close();
		}

		
		/*fin de metodos*/

		function init() 
		{
			if (angular.isDefined($scope.ngDialogData)) 
			{
				vm.dataDondeSucede = angular.copy($scope.ngDialogData.zonaSuceso);
				if(vm.dataDondeSucede.idDetalleCaso>0){
						buscarZonaSuceso();
				}
				
			}
			
		}

		init();

		/*fin de controller*/
	}
})();