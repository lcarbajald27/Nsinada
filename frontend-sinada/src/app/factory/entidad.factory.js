(function() {
    'use strict';
    angular.module('spapp').factory('EntidadFactory', EntidadFactory);
    /* @ngInject */
    function EntidadFactory(API_CONFIG, APIFactory) {
        /*inicializacion y declaracion de metodos del factory*/
        var factory = {
            model: model,
            buscarXId: buscarXId,
            buscarXEntidadNumeroDocumento: buscarXEntidadNumeroDocumento,
            registrarEntidadOefa: registrarEntidadOefa,
            actualizar: actualizar
        };
        return factory;
        /* implementacion de metodos */
        function model() {
            return {
                idEntidad: 0,
                razonSocial: '',
                ruc: '',
                /*cargo 					:0,*/
                /*representanteLegal 	:{},*/
                direccion: '',
                referencia: '',
                nomCargo: '',
                nomRepresentante: ''
            };
        }

        function buscarXId(prmEntidad) {
            return APIFactory.listar(API_CONFIG.url + 'entidad/buscar', prmEntidad);
        }

        function buscarXEntidadNumeroDocumento(prmEntidad) {
            return APIFactory.listar(API_CONFIG.url + 'entidad/buscar-documento', prmEntidad);
        }

        function registrarEntidadOefa(prmCliente) {
            return APIFactory.registrar(API_CONFIG.url + 'entidad/insertar-entidad-oefa', prmCliente);
        }

        function actualizar(prmPersona) {
            return APIFactory.actualizar(API_CONFIG.url + 'entidad/actualizar', prmPersona);
        }
        /*	function listar(prmCliente) {
        		return APIFactory.listar(API_CONFIG.url+'persona', prmCliente);
        	}
        	
        	function registrar(prmCliente) {
        		return APIFactory.registrar(API_CONFIG.url+'persona/registrar', prmCliente);
        	}

        	function obtener(idCliente) {
        		return APIFactory.obtener(API_CONFIG.url+'persona/', idCliente);
        	}

        	function actualizar(prmCliente) {
        		return APIFactory.actualizar(API_CONFIG.url+'persona/actualizar', prmCliente);
        	}

        	function eliminar(prmCliente) {
        		return APIFactory.eliminar(API_CONFIG.url+'persona/eliminar', prmCliente);
        	}

        	function buscarPorRUC(prmCliente) {
        		return APIFactory.consultaPOST(API_CONFIG.url+'persona/buscar-x-ruc',prmCliente);
        		
        	}*/
        /*fin de factory*/
    }
})();