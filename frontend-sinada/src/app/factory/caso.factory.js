(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('CasoFactory', CasoFactory);

	/* @ngInject */
	function CasoFactory(API_CONFIG, APIFactory,DetalleCasoFactory,MaestroFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			listar 		: listar,
		    registrar 	: registrar,
			actualizar 	: actualizar,
			buscarPorId : buscarPorId,
			eliminar 	: eliminar,
			getOefa     : getOefa,
			listarCasoOefa : listarCasoOefa,
			registrarCasoOefa : registrarCasoOefa,
			actualizarCasoOefa: actualizarCasoOefa,
			eliminarCasoOefa   : eliminarCasoOefa,
			listarEfas         : listarEfas,
			validaCasoRegistrado : validaCasoRegistrado,
			modelVistaEntidadCaso : modelVistaEntidadCaso,
			listarEntidadCaso : listarEntidadCaso


		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {		

		 	 	idCaso			:0,
		 	 	numeroCaso		:'',
		 	 	problematica 	:DetalleCasoFactory.model(),
	     	 	debidoA1		:DetalleCasoFactory.model(),
	     	 	debidoA2		:DetalleCasoFactory.model(),
	     	 	debidoA3		:DetalleCasoFactory.model(),

	     	 	zonasuceso1		:DetalleCasoFactory.model(),
	     	 	zonasuceso2		:DetalleCasoFactory.model(),
	     	 	zonasuceso3		:DetalleCasoFactory.model(),

	     	 	estado			:0,
	     	 	flagActivo		:''
			};
		}


		function getOefa(){
			return{

					idCasoOefa				:0,
				    direccionSupervision	:MaestroFactory.model(),
					direccionEvaluacion 	:MaestroFactory.model(),
				    estado 					:MaestroFactory.model(),
					caso 					:model(),
					tipoAsignacion 			:MaestroFactory.model(),
			    	flagActivo 				:'0'

				};
		}

		function modelVistaEntidadCaso(){

				return{

						idCasoEntidad 			:0,
						tipoEntidad 			:0,
						nombreTipoEntidad 		:'',
						idEntidad 				:0,
						nombreEntidad 			:'',
						tipoAsignacion 			:0,
						nombretipoAsignacion 	:'',
						idCaso 					:0

				};

		}


/* ************************************CASO**************************************** */
		function listar(prmData) {
			return APIFactory.listar(API_CONFIG.url+'caso/listar', prmData);
		}
		
		function registrar(prmData) {
			return APIFactory.registrar(API_CONFIG.url+'caso/registrar', prmData);
		}

		function actualizar(prmData) {
			return APIFactory.actualizar(API_CONFIG.url+'caso/actualizar', prmData);
		}

		function eliminar(prmData) {
			return APIFactory.eliminar(API_CONFIG.url+'caso/eliminar', prmData);
		}
		
		function buscarPorId(prmData) {
			return APIFactory.listar(API_CONFIG.url+'caso/buscarPorId', prmData);
		}

		function validaCasoRegistrado(prmData) {
			return APIFactory.consultaPOST(API_CONFIG.url+'caso/valida-caso-registrado', prmData);
		}
/* ******************************************************************************** */


/* ********************************CASO-OEFA************************************ */
		function listarCasoOefa(prmCasos) {
			return APIFactory.listar(API_CONFIG.url+'casoOefa/listar', prmCasos);
		}
		
		function registrarCasoOefa(prmCasos) {
			return APIFactory.registrar(API_CONFIG.url+'casoOefa/registrar', prmCasos);
		}

		function actualizarCasoOefa(prmCasos) {
			return APIFactory.actualizar(API_CONFIG.url+'casoOefa/actualizar', prmCasos);
		}

		function eliminarCasoOefa(prmCasos) {
			return APIFactory.eliminar(API_CONFIG.url+'casoOefa/eliminar', prmCasos);
		}
/* ******************************************************************************** */
		
/* ************************************  Lista Efa **************************************** */
		function listarEfas(prmEfa) 
		{
			return APIFactory.listar(API_CONFIG.url+'efa/listar',prmEfa);
		}
/* ******************************************************************************** */

/* ************************************  ENTIDAD CASO **************************************** */
		function listarEntidadCaso(prmData) 
		{
			return APIFactory.listar(API_CONFIG.url+'caso/listar-entidad-caso',prmData);
		}
/* ******************************************************************************** */



	
		/*fin de factory*/
	}
})();