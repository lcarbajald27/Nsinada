(function() {
	'use strict';

	angular
		.module('spapp.producto')
		.controller('ProductoListadoController', ProductoListadoController);

	/* @ngInject */
	function ProductoListadoController($state, $controller, ProductoFactory, toastr, ngDialog, ExportFactory, $timeout) {
		var vm = this;

		/*declaracion de variables globales*/
		vm.listaProducto= [];
		vm.prmProducto= ProductoFactory.modelProducto();

		/*variables de paginacion*/

        vm.opcionesPaginacion = {
        	itemsPorPag : 10,
        	cantidades : [10,25,50,100]
        };

		/*declaración de metodos enlazados a la vista*/
        vm.nuevo = nuevo;
        vm.limpiar = limpiar;
		vm.buscar = buscar;
		vm.confirmarEliminacion = confirmarEliminacion;
		vm.editarProducto = editarProducto;
		vm.exportarListado = exportarListado;

		/*implementación de metodos*/
		function nuevo() {
			localStorage.removeItem('objIdProducto');
			$state.go('spapp.home.productos.registro');
		}

		function confirmarEliminacion(itemProducto) {
			ngDialog
				.openConfirm({
					template : 'app/base/template/dialog-confirm/dialog-confirm.html',
					controller : 'DialogConfirmController',
					controllerAs : 'vm',
					data : {
						Titulo : 'Confirmar eliminación',
						Mensaje : '¿Está seguro de eliminar el producto : '+itemProducto.nombre+'?'
					},
					width : '380px'
				})
				.then(
					function (okValue) {
						eliminar(itemProducto.idProducto);
					}
				);
		}

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

		function eliminar(idProducto) {
			var tmpProductoEliminar = {
				idProducto: idProducto
			};
			ProductoFactory
			.eliminar(angular.copy(tmpProductoEliminar))
			.then(function (response) {
				if (response.valido) {
					toastr.success(response.mensaje);
					buscar();
				}
				else{
					toastr.error(response.mensaje)
				}
			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al eliminar el producto');
			});
		}

		function editarProducto(itemProducto) {

			localStorage.setItem('objIdProducto', angular.toJson(angular.copy(itemProducto.idProducto)));
			$state.go('spapp.home.productos.registro');
        }

        function exportarListado(idTabla) {
  
        	var exportHref = ExportFactory.tableToExcel(idTabla,'lista_productos');
            $timeout(function(){
            	location.href = exportHref;
            },100); // trigger download
        }

		function init() {
			limpiar();
		}

		init();


		/*fin de metodos*/
	}
})();