(function() {
    'use strict';
    angular.module('spapp').factory('AccesoFactory', AccesoFactory);
    /* @ngInject */
    function AccesoFactory(API_CONFIG, APIFactory, $log) {
        /*inicializacion y declaracion de metodos del factory*/
        var factory = {
            opciones: opciones,
            operaciones: operaciones,
            validaPerfiles: validaPerfiles,
            obtenerSesion: obtenerSesion,
            buscarUsuarioPorIdAplicacion: buscarUsuarioPorIdAplicacion,
            validarCorreoElectronicoSSO: validarCorreoElectronicoSSO,
            cambiarClave: cambiarClave
        };
        return factory;

        function opciones(ref) {
            return APIFactory.listar(API_CONFIG.url + 'seguridad/opciones', {
                'prm1': ref
            });
        }

        function operaciones(ref, id) {
            //$log.log('ref+id '+ref+id);
            return APIFactory.listar(API_CONFIG.url + 'seguridad/operaciones', {
                'prm1': ref,
                'prm2': id
            });
        }

        function validaPerfiles(prmData) {
            //$log.log('ref+id '+ref+id);
            return APIFactory.consultaPOST(API_CONFIG.url + 'seguridad/obtener-perfiles', prmData);
        }

        function obtenerSesion(prmData) {
            //$log.log('ref+id '+ref+id);
            return APIFactory.consultaPOST(API_CONFIG.url + 'seguridad/obtener-session', prmData);
        }

        function buscarUsuarioPorIdAplicacion(prmData) {
            //$log.log('ref+id '+ref+id);
            return APIFactory.consultaPOST(API_CONFIG.url + 'seguridad/buscarUsuarioPorIdAplicacion', prmData);
        }

        function validarCorreoElectronicoSSO(prmData) {
            //$log.log('ref+id '+ref+id);
            return APIFactory.consultaPOST(API_CONFIG.url + 'seguridad/validarCorreoElectronicoSSO', prmData);
        }

        function cambiarClave(prmData) {
            //$log.log('ref+id '+ref+id);
            return APIFactory.actualizar(API_CONFIG.url + 'seguridad/cambiarClave', prmData);
        }
        /*fin de factory*/
    }
})();