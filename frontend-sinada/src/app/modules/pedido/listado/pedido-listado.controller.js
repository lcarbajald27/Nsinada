(function() {
	'use strict';

	angular
		.module('spapp.pedido')
		.controller('PedidoListadoController', PedidoListadoController);

	/* @ngInject */
	function PedidoListadoController($state, $timeout, $controller,$filter , PedidoFactory, ExportFactory, toastr, ngDialog) {
		var vm = this;

		/*declaracion de variables globales*/
		vm.listaPedido= [];
		vm.prmPedido= PedidoFactory.modelPedido();

		/*variables de paginacion*/

    vm.opcionesPaginacion = {
    	itemsPorPag : 10,
    	cantidades : [10,25,50,100]
    };

		/*declaración de metodos enlazados a la vista*/
    vm.fechaIniPedido = '';
    vm.fechaTerminoPedido = '';
    vm.nuevo = nuevo;
    vm.limpiar = limpiar;
		vm.buscar = buscar;
		vm.confirmarEliminacion = confirmarEliminacion;
		vm.editar = editar;
		vm.exportarListado = exportarListado;
		/*implementación de metodos*/
		function nuevo() {
			$state.go('spapp.home.pedidos.registro');
		}

		function confirmarEliminacion(itemPedido) {
			ngDialog
				.openConfirm({
					template : 'app/base/template/dialog-confirm/dialog-confirm.html',
					controller : 'DialogConfirmController',
					controllerAs : 'vm',
					data : {
						Titulo : 'Confirmar eliminación',
						Mensaje : '¿Está seguro de eliminar el pedido'
					},
					width : '380px'
				})
				.then(
					function (okValue) {
						eliminar(itemPedido.idPedido);
					}
				);
		}
		
		  function convertDateToString(prmdate) {

		      if (  !angular.isUndefined(prmdate)
		        &&  prmdate != null
		        &&  prmdate != "") {
		        try {
		          var strDate = $filter('date')(prmdate, "dd/MM/yyyy");
		          return strDate;
		        } catch (e) {
		          return "";
		        }
		      }
		    }
		  
		  

		  function validaFechas(){


		  var date = new Date();


//		  if(vm.fechaIniPedido == null || vm.fechaIniPedido == ''){
//			  vm.fechaInicioObra = date;
//		  }else{
//		
//
//		    toastr.warning('La Fecha Ingresada es Menos a la Fecha del Sistema <br> Por Favor Ingrese Una Fecha Valida');
//		  vm.fechaInicioObra = date;
//
//
//		  }
		}

    function buscar() {
    	
    //	console.log("vm.fechaIniPedido : "+ convertDateToString(vm.fechaIniPedido));
    //	console.log("vm.fechaTerminoPedido : "+ convertDateToString(vm.fechaTerminoPedido));
    	vm.prmPedido.fechaInicio  =convertDateToString(vm.fechaIniPedido);
    	vm.prmPedido.fechaTermino =convertDateToString(vm.fechaTerminoPedido);
    	
    	if(vm.prmPedido.fechaInicio==''|| vm.prmPedido.fechaInicio ==null ){
    		vm.prmPedido.fechaInicio ='01/01/1900';
    		
    	}
    	
    	if(vm.prmPedido.fechaTermino==''|| vm.prmPedido.fechaTermino ==null ){
    		vm.prmPedido.fechaTermino ='01/01/3000';
    		
    	}
        	
			PedidoFactory
			.listar(angular.copy(vm.prmPedido))
			.then(function (response) {
				if (response.valido) {
					vm.listaPedido= response.data;
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
			vm.prmPedido= PedidoFactory.modelPedido();
			vm.prmPedido.flagActivo= 1;
		}

		function eliminar(idPedido) {
			var tmpPedidoEliminar = {
				idPedido: idPedido
			};
			PedidoFactory
			.eliminar(angular.copy(tmpPedidoEliminar))
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
				toastr.error('Ocurrió un error al eliminar el pedido');
			});
		}

		function editar(itemPedido) {
//			debugger;
			localStorage.setItem('objIdPedido', angular.toJson(angular.copy(itemPedido.idPedido)));
			$state.go('spapp.home.pedidos.registro');

    }

    function exportarListado(idTabla) {
    	var exportHref = ExportFactory.tableToExcel(idTabla,'lista_pedidos');
        $timeout(function(){
        	location.href = exportHref;
        },100); // trigger download
    }

		function init() {
			limpiar();
			//cargarCombos();		}
		}

		init();


		/*fin de metodos*/
	}
})();