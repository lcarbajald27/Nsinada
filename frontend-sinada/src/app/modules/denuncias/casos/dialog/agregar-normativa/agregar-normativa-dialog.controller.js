(function() {
	'use strict';

	angular
	.module('spapp.denuncias')
	.controller('AgregarNormativaDialogController',AgregarNormativaDialogController);


	/** @ngInject */
	function AgregarNormativaDialogController(toastr, $scope, ngDialog,NormasFactory,ContenidoNormaFactory,NormaCasoFactory) {
		var vm = this;
		
		/*declaracion de variables globales*/
		/*Configuracion de paginacion de tablas*/
		 $scope.config = {
				    itemsPerPage: 5,
				    fillLastPage: true,
				    current:1
				  };
		 /*fin de configuracion de tablas*/
		 
		
		vm.prmFiltroContenidoNorma = ContenidoNormaFactory.model();
		vm.dataNormaCaso=NormaCasoFactory.model();
		vm.flagBusquedaContenido=0;
		vm.dataNormas=NormasFactory.modelNormas();
		vm.listaContenidoNorma=[];
		vm.listaNormativas=[];
		
		/*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;

        vm.listarNormas=listarNormas;
        vm.buscarContenido=buscarContenido;
       /* vm.registrarNormaCaso=registrarNormaCaso;*/
        vm.registrarNormaContenidoCaso=registrarNormaContenidoCaso;
        vm.listarContenidoNorma=listarContenidoNorma;
		/*implementación de metodos*/

	/*function registrarNormaCaso(item)
	{
		vm.dataNormaCaso.norma.idNorma	=	item.idNorma;
		
		NormaCasoFactory
		.registrar(vm.dataNormaCaso)
		.then(function (response) 
		{
			if(response.valido)
			{
				vm.dataNormaCaso.idNormaCaso = response.data;
				toastr.success("Se registro la norma al caso");
				listarNormas();
			}
			else
			{
				toastr.error(response.mensaje);
			}
		})
		.catch(function (error) 
		{
			toastr.error('Ocurrió un error al registrar problematica');
		});
	}*/


			function registrarNormaContenidoCaso(item){
			
  			vm.dataNormaCaso.contenidoNorma.idContenidoNorma	=	item.idContenidoNorma;

    	NormaCasoFactory
			.registrar(vm.dataNormaCaso)
			.then(function (response) {
				if(response.valido){
					//vm.dataPedido.idPedido = response.data;
					vm.dataNormaCaso.idNormaCaso = response.data;
						toastr.success("Se registró la norma al caso");
						ngDialog.close();
				}
				else{
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al registrar la norma');
			});
		}




		function listarNormas() 
		{
			/*vm.dataNormas.idCasoEfa = vm.dataNormaCaso.idCasoEfa;
			console.log(vm.dataNormas);*/

			NormasFactory
			.listar(angular.copy(vm.dataNormas))
			.then(function (response)
			{
				if (response.valido) 
				{
					vm.listaNormativas = response.data;
				}
				else 
				{
					toastr.error(response.mensaje);
				}
			}).catch(function (error) 
			{
				toastr.error('No se pudo listar los títulos');
			});
		}
		

		function buscarContenido(itemNorma){
			
		
				vm.flagBusquedaContenido=1;
				var contenidoNorma ={
					idCasoOefa:vm.dataNormaCaso.idCasoOefa,
					idCasoEfa:vm.dataNormaCaso.idCasoEfa,
					idNorma :itemNorma.idNorma,
					tipoNormaCaso:vm.dataNormaCaso.tipoNormaCaso.codigoRegistro,
					

			};
			vm.prmFiltroContenidoNorma.idNorma=itemNorma.idNorma;

			ContenidoNormaFactory
			.listar(angular.copy(contenidoNorma))
			.then(function (response)
			{
				if (response.valido) {
						vm.listaContenidoNorma = response.data;
					
				}else {
						toastr.error(response.mensaje);
					}
			}).catch(function (error) 
			{
				toastr.error('No se pudo listar los títulos');
			});

	}


		function listarContenidoNorma(){
			/*vm.prmFiltroContenidoNorma.idNorma=vm.dataNormaCaso.norma.idNorma;*/
			vm.prmFiltroContenidoNorma.idCasoOefa=angular.copy(vm.dataNormaCaso.idCasoOefa);
			vm.prmFiltroContenidoNorma.idCasoEfa=angular.copy(vm.dataNormaCaso.idCasoEfa);
			vm.prmFiltroContenidoNorma.tipoNormaCaso = angular.copy(vm.dataNormaCaso.tipoNormaCaso.codigoRegistro);
				vm.flagBusquedaContenido=1;
			

			ContenidoNormaFactory
			.listar(angular.copy(vm.prmFiltroContenidoNorma))
			.then(function (response)
			{
				if (response.valido) {
						vm.listaContenidoNorma = response.data;
					
				}else {
						toastr.error(response.mensaje);
					}
			}).catch(function (error) 
			{
				toastr.error('No se pudo listar los títulos');
			});

	}


		


        function cancelar() {
			ngDialog.close();
		}

		
		/*fin de metodos*/

		function init() 
		{

			if (angular.isDefined($scope.ngDialogData)) 
			{
				vm.dataNormaCaso= angular.copy($scope.ngDialogData.normaCaso);
			}

			listarNormas();
			// body...
		}

		init();

		/*fin de controller*/
	}
})();