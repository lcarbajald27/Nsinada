(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('NormasFactory', NormasFactory);

	/* @ngInject */
	function NormasFactory(API_CONFIG, APIFactory) {
		
		/*inicializacion y declaracion de metodos del factory*/
		var factory = {
				registrar:registrar,
				modelNormas:modelNormas,
				entidad:entidad,
				listar:listar,
				actualizar:actualizar,
				eliminar:eliminar,
				buscarPorNumeroNorma : buscarPorNumeroNorma,
				buscarEntidadXNumeroDocumento : buscarEntidadXNumeroDocumento,
				buscarEntidadXId :buscarEntidadXId,
				listarNormasConcordancia:listarNormasConcordancia
			/*modelProducto : modelProducto*/
			
		};

		return factory;

		/* implementacion de metodos */
		function entidad(){

			return {

		 	 	idEntidad		:0,
		 	 	nombrePersona	:'',
		 	 	numeroDocumento	:'',
		 	 	flagActivo		:''

			};
		}
		function modelNormas() {
			return {	

		 	 	idNorma				:0,
    	 	 	numeroNorma			:'',
    	 	 	descripcion			:'',
    	 	 	tipoDispositivo		:0,
    	 	 	fechaVigencia		:'',
    	 	 	fechaModificacion	:'',
    	 	 	fechaDerogacion		:'',
    	 	 	sector 				:0,
    	 	 	subSector 			:0,
    	 	 	actividad			:0,
    	 	 	entidadEmisora		:0,
    	 	 	estado				:0,
    	 	 	flagActivo			:'',
    	 	 	ambitoDispositivo	:0

			};
		}

		/*function listar() {
			return APIFactory.listar(API_CONFIG.url+'efa' );
		}*/
		
		function registrar(prmNormas) {
			return APIFactory.registrar(API_CONFIG.url+'normas/registrar', prmNormas);
		}

		function listar(prmNormas) {
			return APIFactory.listar(API_CONFIG.url+'normas/listar', prmNormas);
		}

		function listarNormasConcordancia(prmNormas) {
			return APIFactory.listar(API_CONFIG.url+'normas/listarnormasconcordancia', prmNormas);
		}
		function actualizar(prmNormas) {
			return APIFactory.actualizar(API_CONFIG.url+'normas/actualizar', prmNormas);
		}

		function eliminar(prmNormas) {
			return APIFactory.eliminar(API_CONFIG.url+'normas/eliminar', prmNormas);
		}

	    function buscarPorNumeroNorma(numeroNorma) {
			return APIFactory.consultaPOST(API_CONFIG.url+'normas/',numeroNorma);
			
		}

		function buscarEntidadXNumeroDocumento(prmEntidad) {
			return APIFactory.listar(API_CONFIG.url+'entidad/buscar-entidad', prmEntidad);
		}


		function buscarEntidadXId(prmEntidad) {
			return APIFactory.listar(API_CONFIG.url+'entidad/buscar-entidad-id', prmEntidad);
		}
		/*fin de factory*/
	}
})();