(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('CasosFactory', CasosFactory);

	/* @ngInject */
	function CasosFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
			model 		: model,
			getOefa		: getOefa,
			
			listar 		: listar,
		    registrar 	: registrar,
			actualizar 	: actualizar,
			eliminar 	: eliminar,
			
			listarCasoEntidad 		: listarCasoEntidad,
		    registrarCasoEntidad 	: registrarCasoEntidad,
			actualizarCasoEntidad 	: actualizarCasoEntidad,
			eliminarCasoEntidad 	: eliminarCasoEntidad,
			
			listarCasoOefa 		: listarCasoOefa,
		    registrarCasoOefa 	: registrarCasoOefa,
			actualizarCasoOefa 	: actualizarCasoOefa,
			eliminarCasoOefa 	: eliminarCasoOefa,
			
			listarNormaCaso 	: listarNormaCaso,
		    registrarNormaCaso 	: registrarNormaCaso,
			actualizarNormaCaso : actualizarNormaCaso,
			eliminarNormaCaso 	: eliminarNormaCaso,
			
			listarEfas : listarEfas,
			getEntidad : getEntidad,
			listarEntidadCaso:listarEntidadCaso,
			buscarPorId : buscarPorId
		};

		return factory;

		/* implementacion de metodos */

		function model() {
			return {		

		 	 	idCaso		:0,
	     	 	tipoCaso	:0,
	     	 	descripcion	:'',
	     	 	codigoPadre	:0,
	     	 	tipoNivel	:0,
	     	 	estado		:0,
	     	 	flagActivo	:''	
			};
		}
		function getEntidad(){
			return {
						idEfa       :0,
				        idUbigeo    :0, 
				        ruc         :'', 
				        nombre      :'',
				        tipoNivel   :0,
				        tipoGobierno:0,
				        paginaWeb   :'',
				        correo      :'',
				        telefono    :'',
				        departamento:'0',
				        provincia   :'0',
				        distrito    :'0',
				        direccion   :'',
				        referencia  :'',
				        flagActivo  :'1'
//				        nivel		:'',
//				        titular 	:'',
//				        documento 	:'',
//				        cargo 		:'',
//				        tipoCargo 	:0
			}
		}
		
		function getOefa(){
			return{
				idCasoOefa 			 : 0,
				direccionSupervision : 0,
				direccionEvaluacion  : 0,
				estado 				 : 0,
				idProblematica		 : 0,
				idDebidoA			 : 0,
				idZonaSuceso		 : 0,
				tipoAsignacion		 : 0,

				flagActivo 			 : '1'
			};
		}
		
//		function getOefa(){
//			return{
//				idCasoOefa 			 : 0,
//				direccionSupervision : 0,
//				direccionEvaluacion  : 0,
//				estado 				 : 0,
//				idProblematica		 : 0,
//				idDebidoA			 : 0,
//				idZonaSuceso		 : 0,
//				tipoAsignacion		 : 0,
//
//				flagActivo 			 : '1'
//			};
//		}
/* ************************************CASO**************************************** */
		function listarEfas(prmEfa) 
		{
			return APIFactory.listar(API_CONFIG.url+'efa/listar',prmEfa);
		}
/* ******************************************************************************** */

/* ************************************CASO**************************************** */
		function listarEntidadCaso(prmEfa) 
		{
			return APIFactory.listar(API_CONFIG.url+'casos/listar-entidad-caso',prmEfa);
		}
/* ******************************************************************************** */


/* ************************************CASO**************************************** */
		function listar(prmCasos) {
			return APIFactory.listar(API_CONFIG.url+'casos/listar', prmCasos);
		}
		
		function registrar(prmCasos) {
			return APIFactory.registrar(API_CONFIG.url+'casos/registrar', prmCasos);
		}

		function actualizar(prmCasos) {
			return APIFactory.actualizar(API_CONFIG.url+'casos/actualizar', prmCasos);
		}

		function eliminar(prmCasos) {
			return APIFactory.eliminar(API_CONFIG.url+'casos/eliminar', prmCasos);
		}
		
		function buscarPorId(prmCasos) {
			return APIFactory.listar(API_CONFIG.url+'casos/buscarPorId', prmCasos);
		}
/* ******************************************************************************** */
		
/* ********************************CASO-ENTIDAD************************************ */
		function listarCasoEntidad(prmCasos) {
			return APIFactory.listar(API_CONFIG.url+'casoEntidad/listar', prmCasos);
		}
		
		function registrarCasoEntidad(prmCasos) {
			return APIFactory.registrar(API_CONFIG.url+'casoEntidad/registrar', prmCasos);
		}

		function actualizarCasoEntidad(prmCasos) {
			return APIFactory.actualizar(API_CONFIG.url+'casoEntidad/actualizar', prmCasos);
		}

		function eliminarCasoEntidad(prmCasos) {
			return APIFactory.eliminar(API_CONFIG.url+'casoEfa/eliminar', prmCasos);
		}
/* ******************************************************************************** */
		
/* ********************************CASO-EFA************************************ */
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

/* ********************************NORMA-CASO************************************ */
		function listarNormaCaso(prmCasos) {
			return APIFactory.listar(API_CONFIG.url+'normaCaso/listar', prmCasos);
		}
		
		function registrarNormaCaso(prmCasos) {
			return APIFactory.registrar(API_CONFIG.url+'normaCaso/registrar', prmCasos);
		}

		function actualizarNormaCaso(prmCasos) {
			return APIFactory.actualizar(API_CONFIG.url+'normaCaso/actualizar', prmCasos);
		}

		function eliminarNormaCaso(prmCasos) {
			return APIFactory.eliminar(API_CONFIG.url+'normaCaso/eliminar', prmCasos);
		}
/* ******************************************************************************** */
	
		/*fin de factory*/
	}
})();