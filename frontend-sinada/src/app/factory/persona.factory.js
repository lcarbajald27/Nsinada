(function() {
    'use strict';
    angular.module('spapp').factory('PersonaFactory', PersonaFactory);
    /* @ngInject */
    function PersonaFactory(API_CONFIG, APIFactory) {
        /*inicializacion y declaracion de metodos del factory*/
        var factory = {
            model: model,
            buscarXId: buscarXId,
            buscarXNumeroDocumento: buscarXNumeroDocumento,
            actualizar: actualizar,
            insertarPersona: insertarPersona,
            registrarPersonaOefa: registrarPersonaOefa,
            listarPersonaSSO: listarPersonaSSO,
            personaSSOmodel: personaSSOmodel
        };
        return factory;
        /* implementacion de metodos */
        function personaSSOmodel() {
            return {
                idPersona: 0,
                tipoPersona: 0,
                nombreComercial: '',
                nombreCompleto: '',
                documento: ''
            };
        }

        function model() {
            return {
                idPersona: 0,
                documento: '',
                primerNombre: '',
                segundoNombre: '',
                nombres: '',
                apellidoPaterno: '',
                apellidoMaterno: '',
                ubigeo: '',
                departamento: '',
                provincia: '',
                distrito: '',
                direccion: '',
                referencia: ''
            };
        }

        function buscarXId(prmPersona) {
            return APIFactory.listar(API_CONFIG.url + 'persona/buscar', prmPersona);
        }

        function buscarXNumeroDocumento(prmPersona) {
            return APIFactory.listar(API_CONFIG.url + 'persona/buscar-documento', prmPersona);
        }

        function actualizar(prmPersona) {
            return APIFactory.actualizar(API_CONFIG.url + 'persona/actualizar', prmPersona);
        }

        function insertarPersona(prmPersona) {
            return APIFactory.listar(API_CONFIG.url + 'persona/insertar-persona', prmPersona);
        }

        function registrarPersonaOefa(prmPersona) {
            return APIFactory.registrar(API_CONFIG.url + 'persona/insertar-persona-oefa', prmPersona);
        }

        function listarPersonaSSO(prmPersona) {
            return APIFactory.consultaPOST(API_CONFIG.url + 'persona/listar-persona-sso', prmPersona);
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