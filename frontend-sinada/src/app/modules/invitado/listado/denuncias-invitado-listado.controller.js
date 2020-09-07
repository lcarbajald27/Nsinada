(function() {
	'use strict';

	angular
	.module('spapp.invitado')
	.controller('DenunciasInvitadoListadoController',DenunciasInvitadoListadoController);


	/** @ngInject */
	function DenunciasInvitadoListadoController($state, $scope, ngDialog,DenunciaFactory,$rootScope,toastr) {
		var vm = this;

		/*declaracion de variables globales*/
		/*Configuracion de paginacion de tablas*/
		 $scope.config = {
				    itemsPerPage: 5,
				    fillLastPage: true,
				    current:1
				  };
		 /*fin de configuracion de tablas*/

		vm.dateActual= new Date();
		  vm.opcionesPaginacion = {
		        	itemsPorPag : 10,
		        	cantidades : [10,25,50,100]
		        };

		vm.dataDenunciaInvitado=[];

		/*declaración de metodos enlazados a la vista*/
		vm.mostrarModalContenido=mostrarModalContenido;
		vm.irARegistroDenuncia = irARegistroDenuncia;
		vm.irAHistorialDenuncia=irAHistorialDenuncia;
		vm.cancelar=cancelar;
		vm.verFichaDenuncia=verFichaDenuncia;
		vm.mostrarModalEncuestaRevision=mostrarModalEncuestaRevision;


		/*implementación de metodos*/


		function obtenerDenuncia(){

			var dataDenuncia= DenunciaFactory.model();

			dataDenuncia.idDenuncia = angular.copy(vm.dataDenunciaInvitado[0].idDenuncia);

			DenunciaFactory
				.obtenerDenuncia(angular.copy(dataDenuncia))
				.then(function (response) {
					if(response.valido){
						//vm.dataPedido.idPedido = response.data;
						vm.dataDenunciaInvitado = [];
						vm.dataDenunciaInvitado.push(response.data);
						sessionStorage.setItem('dataDenunciaInvitado', angular.toJson(vm.dataDenunciaInvitado));




						/*siguiente();*/
					}
					else{
						toastr.error(response.mensaje);
					}
				})
				.catch(function (error) {
					toastr.error('Ocurrió un error al realizar la consulta');
				});
			}


			function verFichaDenuncia(data) {

    		/*	var prmData= {
    					idDenuncia:vm.dataDenunciaRevision.idDenuncia,

    			};*/
				// debugger;
    				var data = {
						flagActivo : 1,
						prmData : data

				};
				localStorage.setItem("objDenunciaFicha", angular.toJson(data));

            ngDialog.open({
							template : 'app/modules/ficha-denuncia/ficha.html',
							controller : 'FichaController',
							/*data : {
								data :item
							},*/
							controllerAs : 'vm',
							width : '650px'
						})
						.closePromise
						.then(function (dataDialog) {
							/*if (dataDialog.value.cliente) {
								vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
							}*/
						});
        }

			function mostrarModalEncuestaRevision(data) {
	            ngDialog.open({
								template : 'app/modules/invitado/listado/encuesta-revision/encuesta-revision.html',
								controller : 'DialogEncuestaRevisionController',
								data : {
									prmData : data,
								},
								controllerAs : 'vm',
								width : '550px'
							})
							.closePromise
							.then(function (dataDialog) {
								obtenerDenuncia();

							});
	        }


		function cancelar(){
			$rootScope.dataDenunciaHistorialInvitado = null;
			sessionStorage.removeItem('dataDenunciaInvitado');
			$state.go('ingreso');
		}

		function irARegistroDenuncia() {
			$state.go('invitado.registro.paso1');
		}


		function irAHistorialDenuncia(item) {
			$rootScope.dataDenunciaHistorialInvitado = item;
			$state.go('invitado.historial');
		}




    	function mostrarModalContenido() {
            ngDialog.open({
							template : 'app/modules/denuncias/denuncias/listado/dialog/validacion-denuncia-list-dialog.html',
							controller : 'ValidacionDenunciasListDialogController',
							data : {

							},
							controllerAs : 'vm',
							width : '550px'
						})
						.closePromise
						.then(function (dataDialog) {
							/*if (dataDialog.value.cliente) {
								vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
							}*/
						});
        }


		/*fin de metodos*/

		function init() {
//			console.log("dssa"+angular.isDefined(sessionStorage.dataDenunciaInvitado));
			if(angular.isDefined(sessionStorage.dataDenunciaInvitado)){
					vm.dataDenunciaInvitado = angular.fromJson(sessionStorage.dataDenunciaInvitado);
		//			console.log("sessionStorage.getItem('dataDenunciaInvitado')"+sessionStorage.dataDenunciaInvitado);
					if(vm.dataDenunciaInvitado !=null){
						obtenerDenuncia();

					}


			}else{
					$state.go('ingreso');
			}
			// body...
		}

		init();

		/*fin de controller*/
	}
})();