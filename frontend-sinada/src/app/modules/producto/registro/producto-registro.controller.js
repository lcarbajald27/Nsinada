(function() {
	'use strict';

	angular
		.module('spapp.producto')
		.controller('ProductoRegistroController', ProductoRegistroController);

	/* @ngInject */
	function ProductoRegistroController($state, $scope,
										ngDialog, toastr,
										ProductoFactory, ConvertFactory,
										MaestroFactory) {
		var vm = this;

		/*declaracion de variables globales*/
		vm.dataProducto = ProductoFactory.modelProducto();
		vm.objaAditoria=[];


		/*declaración de metodos enlazados a la vista*/

		vm.limpiar = limpiar;
		vm.cancelar = cancelar;
		vm.guardar = guardar;
		
	

		/*implementación de metodos*/
		/*
        function mostrarListaEvento() {
            ngDialog.open({
				template : 'app/modules/evento/dialog/evento-list-dialog.html',
				controller : 'EventoListDialogController',
				data : {

				},
				controllerAs : 'vm',
				width : '40%'
			});
        }

        $scope.$on('eventoSeleccionado', function (event, prmEventoSeleccionado) {
        	vm.dataProducto.evento.idEvento = prmEventoSeleccionado.idEvento;
        	vm.dataProducto.evento.nombre = prmEventoSeleccionado.nombre;
        });*/

        function limpiar() {
			vm.dataProducto = ProductoFactory.modelProducto();
		}

		function cancelar() {
			$state.go('spapp.home.productos.listado');
		}

		function guardar() {
			if (vm.dataProducto.idProducto<1) {
				registrar();
			} else {
				actualizar();
			}
		}

		function registrar() {
			
			vm.dataProducto.idUsuario = vm.objaAditoria.idUsuario;
	//		console.log(vm.dataProducto);
			ProductoFactory
			.registrar(angular.copy(vm.dataProducto))
			.then(function (response) {
				if (response.valido) {
					vm.dataProducto.idProducto = response.data;
					toastr.success(response.mensaje);
				} else {
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al registrar los datos de el producto');
			});
		}

		function actualizar() {
			vm.dataProducto.idUsuario = vm.objaAditoria.idUsuario;
		//	console.log(vm.dataProducto);
			
			ProductoFactory
			.actualizar(angular.copy(vm.dataProducto))
			.then(function (response) {
				if (response.valido) {
					toastr.success(response.mensaje);
				} else {
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al actualizar los datos de el producto');
			});
		}

        function init()
        {
        	vm.objaAditoria = angular.fromJson(localStorage.objSesionUsuario);
        	
//        	var objUsuario = angular.fromJson(localStorage.objSesionUsuario);
//        	vm.objUsu.getItem("sideBar")
//        	console.log(vm.objUsu.idUsuario);
//        	vm.dataProducto.idUsuario=vm.objUsu.idUsuario;
//        	console.log(vm.dataProducto.idUsuario);
        	
			limpiar();
		
			if (angular.isDefined(localStorage.objIdProducto))
			{
				var tmpIdProducto = Number(localStorage.objIdProducto);
				ProductoFactory
				.obtener(tmpIdProducto)
				.then(function (response) {
					if (response.valido) {
						vm.dataProducto = response.data;
					}
					else {
						toastr.error(response.mensaje);
					}
				})
				.catch(function (error) {
					toastr.error('Error al obtener datos del producto');
				});
			}
		}

		init();


		/*fin de metodos*/
	}
})();