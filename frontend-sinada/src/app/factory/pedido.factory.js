(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('PedidoFactory', PedidoFactory);

	/* @ngInject */
	function PedidoFactory(API_CONFIG, APIFactory, ClienteFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			modelPedido : modelPedido,
			modelPedidoItem : modelPedidoItem,
			listar : listar,
		    registrar : registrar,
			obtener : obtener,
			actualizar : actualizar,
			eliminar : eliminar
		};

		return factory;

		/* implementacion de metodos */

		function modelPedido() {
			return {				
				idPedido : 0,
				fechaAtencion : null,
				fechaInicio : '',
				fechaTermino: '',
				fechaRegistro : new Date(),
				importe : 0,
				idCliente : 0,
				cliente : ClienteFactory.modelCliente(),
				detalle : [],
				flagActivo : '1',
				idUsuario:0
			};
		}

		function modelPedidoItem() {
			return {
				idPedidoItem : 0,
				item : 0,
				pedido : null,
				producto : null,
				cantidad : 0,
				precio : 0,
				importe : 0,
				flagActivo : '1'
			};
		}

		function listar(prmPedido) {
			return APIFactory.listar(API_CONFIG.url+'pedido', prmPedido);
		}
		
		function registrar(prmPedido) {
			return APIFactory.registrar(API_CONFIG.url+'pedido/registrar', prmPedido);
		}

		function obtener(idPedido) {
			return APIFactory.obtener(API_CONFIG.url+'pedido/', idPedido);
		}

		function actualizar(prmPedido) {
			return APIFactory.actualizar(API_CONFIG.url+'pedido/actualizar', prmPedido);
		}

		function eliminar(prmPedido) {
			return APIFactory.eliminar(API_CONFIG.url+'pedido/eliminar', prmPedido);
		}

		/*fin de factory*/
	}
})();