(function() {
    'use strict';
    angular.module('spapp').factory('CasoEfaFactory', CasoEfaFactory);
    /* @ngInject */
    function CasoEfaFactory(API_CONFIG, APIFactory, CasoFactory, MaestroFactory, DenunciaFactory) {
        /*inicializacion y declaracion de metodos del factory*/
        var factory = {
            model: model,
            getEntidadCaso: getEntidadCaso,
            listar: listar,
            registrar: registrar,
            actualizar: actualizar,
            eliminar: eliminar,
            listarCasoEntidad: listarCasoEntidad,
            registrarCasoEntidad: registrarCasoEntidad,
            actualizarCasoEntidad: actualizarCasoEntidad,
            eliminarCasoEntidad: eliminarCasoEntidad,
            listarNormaCaso: listarNormaCaso,
            registrarNormaCaso: registrarNormaCaso,
            actualizarNormaCaso: actualizarNormaCaso,
            eliminarNormaCaso: eliminarNormaCaso,
            listarEfas: listarEfas,
            listarEfasXCaso: listarEfasXCaso,
            getEfa: getEfa,
            registrarListaEfa: registrarListaEfa
        };
        return factory;
        /* implementacion de metodos */
        function model() {
            return {
                idCasoEfa: 0,
                efa: getEfa(),
                tipoAsignacion: MaestroFactory.model(),
                caso: CasoFactory.model()
            };
        }

        function getEfa() {
            return {
                idEfa: 0,
                idUbigeo: 0,
                ruc: '',
                nombre: '',
                tipoNivel: 0,
                tipoGobierno: 0,
                paginaWeb: '',
                correo: '',
                telefono: '',
                departamento: '0',
                provincia: '0',
                distrito: '0',
                direccion: '',
                referencia: '',
                flagActivo: '1',
                idCaso: 0,
                denuncia: DenunciaFactory.model(),
                //				        nivel		:'',
                //				        titular 	:'',
                //				        documento 	:'',
                //				        cargo 		:'',
                //				        tipoCargo 	:0
            }
        }

        function getEntidadCaso() {
            return {
                idCasoEntidad: 0,
                tipoEntidad: 0,
                nombreTipoEntidad: '',
                idEntidad: 0,
                nombreAsignacion: '',
                nombreAmbito: '',
                responsable: '',
                tipoNivel: 0,
                tipoAsignacion: 0,
                idProblematica: 0,
                idDebioA: 0,
                idZonaSuceso: 0,
                lstNormasCaso: [],
                swEditable: '0'
            };
        }
        /*********************************** EFA CASO ***************************************/
        function listar(prmCasoEfa) {
            return APIFactory.listar(API_CONFIG.url + 'casoEfa/listar', prmCasoEfa);
        }

        function registrar(prmCasoEfa) {
            return APIFactory.registrar(API_CONFIG.url + 'casoEfa/registrar', prmCasoEfa);
        }

        function actualizar(prmCasoEfa) {
            return APIFactory.actualizar(API_CONFIG.url + 'casoEfa/actualizar', prmCasoEfa);
        }

        function eliminar(prmCasoEfa) {
            return APIFactory.eliminar(API_CONFIG.url + 'casoEfa/eliminar', prmCasoEfa);
        }

        function listarEfasXCaso(prmEfa) {
            return APIFactory.listar(API_CONFIG.url + 'casoEfa/listar-efas-caso', prmEfa);
        }

        function registrarListaEfa(prmEfa) {
            return APIFactory.registrar(API_CONFIG.url + 'casoEfa/registrar-lista-efa', prmEfa);
        }
        /**************************************************************************************/
        /* ************************************CASO**************************************** */
        function listarEfas(prmEfa) {
            return APIFactory.listar(API_CONFIG.url + 'efa/listar', prmEfa);
        }
        /* ******************************************************************************** */
        /* ************************************CASO**************************************** */
        /* ******************************************************************************** */
        /* ********************************CASO-ENTIDAD************************************ */
        function listarCasoEntidad(prmCasos) {
            return APIFactory.listar(API_CONFIG.url + 'casoEntidad/listar', prmCasos);
        }

        function registrarCasoEntidad(prmCasos) {
            return APIFactory.registrar(API_CONFIG.url + 'casoEntidad/registrar', prmCasos);
        }

        function actualizarCasoEntidad(prmCasos) {
            return APIFactory.actualizar(API_CONFIG.url + 'casoEntidad/actualizar', prmCasos);
        }

        function eliminarCasoEntidad(prmCasos) {
            return APIFactory.eliminar(API_CONFIG.url + 'casoEntidad/eliminar', prmCasos);
        }
        /* ******************************************************************************** */
        /* ********************************NORMA-CASO************************************ */
        function listarNormaCaso(prmCasos) {
            return APIFactory.listar(API_CONFIG.url + 'normaCaso/listar', prmCasos);
        }

        function registrarNormaCaso(prmCasos) {
            return APIFactory.registrar(API_CONFIG.url + 'normaCaso/registrar', prmCasos);
        }

        function actualizarNormaCaso(prmCasos) {
            return APIFactory.actualizar(API_CONFIG.url + 'normaCaso/actualizar', prmCasos);
        }

        function eliminarNormaCaso(prmCasos) {
            return APIFactory.eliminar(API_CONFIG.url + 'normaCaso/eliminar', prmCasos);
        }
        /* ******************************************************************************** */
        /*fin de factory*/
    }
})();