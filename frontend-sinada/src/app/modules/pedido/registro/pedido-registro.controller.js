(function() {
	'use strict';

	angular
		.module('spapp.pedido')
		.controller('PedidoRegistroController', PedidoRegistroController);

	/* @ngInject */
	function PedidoRegistroController(ngDialog, toastr, PedidoFactory,
		                              ConvertFactory, $state, $scope,$rootScope, MaestroFactory, ClienteFactory, ProductoFactory) {
		var vm = this;

		/*declaracion de variables globales*/
		vm.listaProducto= [];
		vm.dataPedido = PedidoFactory.modelPedido();
		vm.objaAditoria=[];
		vm.dtFechaAtencionOptions = {
			showWeeks : false,
			minDate : new Date()
		};
		/*declaración de metodos enlazados a la vista*/

		vm.guardar = guardar;
		vm.limpiar = limpiar;
		vm.cancelar = cancelar;
		vm.buscarClientePorDocumento = buscarClientePorDocumento;
		vm.mostrarBusquedaClientes = mostrarBusquedaClientes;
		vm.mostrarBusquedaProductos = mostrarBusquedaProductos;
    vm.confirmarEliminacionItem = confirmarEliminacionItem;
    vm.calcularImporteItem = calcularImporteItem;
		
		/*implementación de metodos*/

		function buscarClientePorDocumento() {
			var prmCliente = {
        			ruc : angular.copy(vm.dataPedido.cliente.ruc)
        		};
  		if (!ConvertFactory.isNotNoE(prmCliente.ruc)) {
  			toastr.warning('Indique número de ruc del cliente');
  			limpiarCliente();
  			return;
  		}
  		if (prmCliente.ruc.length!=11) {
  			toastr.warning('Indique un número de ruc de 11 dígitos');
  			limpiarCliente(prmCliente.ruc);
  			return;
  		}
			ClienteFactory
			.buscarPorRUC(prmCliente)
			.then(function (response) {
				if (response.valido) {
					if (response.data!=null) {
						vm.dataPedido.cliente = angular.copy(response.data);
					}
					else{
					  toastr.warning('No se encontró cliente con RUC '+prmCliente.ruc);
					  limpiarCliente(prmCliente.ruc);
					}
				}
				else {
					toastr.error('Error al consultar cliente');
					limpiarCliente(prmCliente.ruc);
				}
			})
			.catch(function (error) {
				toastr.error('Error al consultar cliente');
				limpiarCliente(prmCliente.ruc);
			});
			return;
    }

    function confirmarEliminacionItem(item) {
      ngDialog
				.openConfirm({
					template : 'app/base/template/dialog-confirm/dialog-confirm.html',
					controller : 'DialogConfirmController',
					controllerAs : 'vm',
					data : {
						Titulo : 'Confirmar acción',
						Mensaje : '¿Desea eliminar el item'+(item.producto ? ': "'+item.producto.nombre+'"' : '')+'?'
					},
					width : '380px'
				})
				.then(
					function (okValue) {
						for(var x in vm.dataPedido.detalle)
						{
							if (vm.dataPedido.detalle[x].item == item.item)
							{
								if (vm.dataPedido.detalle[x].idPedidoItem == 0) {
									vm.dataPedido.detalle.splice(x,1);
								}
								else {
									vm.dataPedido.detalle[x].flagActivo = '0';
								}
								calcularImporteTotal();
								return;
							}
						}
					},
					function (cancelValue) {
						//toastr.warning('Cancelar eliminacion de ítem');
					}
				);
      }

   	function guardar(){

   		if (!vm.dataPedido.cliente || vm.dataPedido.cliente.idCliente<1) {
   			toastr.warning('Debe especificar un cliente');
   			return;
   		}

    	if (!vm.dataPedido.detalle || vm.dataPedido.detalle.length==0) {
    		toastr.warning('Debe agregar productos al detalle del pedido');
    		return;
    	}
    	for(var x in vm.dataPedido.detalle){
    		if (isNaN(vm.dataPedido.detalle[x].cantidad) && vm.dataPedido.detalle[x].cantidad==0 || vm.dataPedido.detalle[x].cantidad>99) {
    			toastr.warning('Debe indicar una cantidad mayor a 0 y menor a 99');
    			return;
    		}
    	}

			if(vm.dataPedido.idPedido < 1){
				registrar();
			}else{
				actualizar();
			}
		}

		function limpiar() {
			vm.dataPedido = PedidoFactory.modelPedido();
		}

		function limpiarCliente(ruc) {
			vm.dataPedido.cliente = ClienteFactory.modelCliente();
			if (ruc) {
				vm.dataPedido.cliente.ruc = angular.copy(ruc);
			}
		}

		function cancelar() {
			$state.go('spapp.home.pedidos.listado');
		}

    function registrar(){
    	vm.dataPedido.idUsuario = vm.objaAditoria.idUsuario;
	//	console.log(vm.dataPedido);
	//	console.log(vm.dataPedido.idUsuario);
    //	console.log('registrar Pedido', angular.copy(vm.dataPedido));
    	PedidoFactory
			.registrar(angular.copy(vm.dataPedido))
			.then(function (response) {
				if(response.valido){
					//vm.dataPedido.idPedido = response.data;
					vm.dataPedido = angular.copy(response.data);
					localStorage.removeItem('objIdPedido');
					localStorage.setItem('objIdPedido',angular.copy(vm.dataPedido.idPedido));
					toastr.success('Pedido registrado con código ' + vm.dataPedido.idPedido);
				}
				else{
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al registrar pedido');
			});
		}

		function actualizar(){
			
			vm.dataPedido.idUsuario = vm.objaAditoria.idUsuario;
			PedidoFactory
			.actualizar(angular.copy(vm.dataPedido))
			.then(function (response) {
				if (response.valido) {
					vm.dataPedido = angular.copy(response.data);
					toastr.success(response.mensaje);
				}
				else{
					toastr.error(response.mensaje)
				}
			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al actualizar el pedido');
			});
		}

	    function mostrarBusquedaProductos(productosEscogidos) {

            ngDialog.open({
							template : 'app/modules/producto/dialog/producto-list-dialog.html',
							controller : 'ProductoListDialogController',
							data : {
								idProductosEscogidos : productosEscogidos
							},
							controllerAs : 'vm',
							width : '700px'
						})
						.closePromise
						.then(function (dataDialog) {
							//console.log('productoSeleccionado',dataDialog.value);
							if (dataDialog.value.producto) {
								agregarPedidoItem(dataDialog.value.producto);
							}

						});
      }

        function mostrarBusquedaClientes(clientesEscogidos) {
            ngDialog.open({
							template : 'app/modules/cliente/dialog/cliente-list-dialog.html',
							controller : 'ClienteListDialogController',
							data : {

							},
							controllerAs : 'vm',
							width : '750px'
						})
						.closePromise
						.then(function (dataDialog) {
							if (dataDialog.value.cliente) {
								vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
							}
						});
        }

        function agregarPedidoItem(productoSeleccionado) {
        	var pedidoItem = PedidoFactory.modelPedidoItem();
        	pedidoItem.item = vm.dataPedido.detalle.length == 0 ? 1 : vm.dataPedido.detalle[vm.dataPedido.detalle.length-1].item+1;
        	pedidoItem.producto = angular.copy(productoSeleccionado);
        	pedidoItem.cantidad = 1;
        	pedidoItem.precio = angular.copy(productoSeleccionado.precio);
        	pedidoItem.importe = pedidoItem.cantidad * pedidoItem.precio;
       // 	console.log('pedidoItem',pedidoItem);
        	vm.dataPedido.detalle.push(angular.copy(pedidoItem));
        	calcularImporteTotal();
        }

        function calcularImporteItem(pedidoItem){
        	pedidoItem.importe = pedidoItem.cantidad * pedidoItem.precio;
        	calcularImporteTotal();

        }

        function calcularImporteTotal() {
        	var importeTotal = 0;
        	for(var x in vm.dataPedido.detalle)
        	{
        		if (vm.dataPedido.detalle[x].flagActivo==1)
        		{
        			importeTotal += vm.dataPedido.detalle[x].importe;
        		}
        	}
        	vm.dataPedido.importe = angular.copy(importeTotal);
        }

		function init() {
			
			vm.objaAditoria = angular.fromJson(localStorage.objSesionUsuario);
			limpiar();
			$rootScope.itemTabActivo = 2;
			if (angular.isDefined(localStorage.objIdPedido)) {
				var objIdPedido = Number(localStorage.objIdPedido);
				PedidoFactory
				.obtener(objIdPedido)
				.then(function (response) {
					if (response.valido) {
						vm.dataPedido = response.data;
					}
					else {
						toastr.error(response.mensaje);
					}
				})
				.catch(function (error) {
					toastr.error('Error al obtener datos del pedido');
				});
			}
		}

		init();

		/*fin de metodos*/
	}
})();