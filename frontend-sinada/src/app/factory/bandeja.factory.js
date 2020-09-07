(function() {
    'use strict';
    angular.module('spapp').factory('BandejaFactory', BandejaFactory);
    /* @ngInject */
    function BandejaFactory(API_CONFIG, APIFactory, $window) {
        /*inicializacion y declaracion de metodos del factory*/
        var factory = {
            model: model,
            bandejaDenuncia: bandejaDenuncia,
            bandeja: bandeja,
            detalleBandeja: detalleBandeja,
            listarBandejaEntidad: listarBandejaEntidad,
            listarBandejaDenunciante: listarBandejaDenunciante,
            validarBandeja: validarBandeja,
            enviarDatos: enviarDatos,
            closeConnect: closeConnect,
            validaBandeja: validaBandeja,
            modelBandeja: modelBandeja,
            listarBandejaAsignacionDenuncia: listarBandejaAsignacionDenuncia,
            listarBandejaEspecialistaSinada: listarBandejaEspecialistaSinada,
            actualizaEstadoDetalleBandeja: actualizaEstadoDetalleBandeja,
            asignarBandejaBandeja: asignarBandejaBandeja,
            eliminarEspecialistaAsignado: eliminarEspecialistaAsignado,
            buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion: buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion,
            listarBandejaOrganoCompententeCompletoExcelData: listarBandejaOrganoCompententeCompletoExcelData
            /*registrar : registrar,
            obtener : obtener,
            actualizar : actualizar,
            eliminar : eliminar*/
            /*  loginUsuario : loginUsuario,
                cambiarClave : cambiarClave*/
        };
        /* $window.onbeforeunload = function (e) {
               var confirmation = {};
               var event = $rootScope.$broadcast('onBeforeUnload', confirmation);
               if (event.defaultPrevented) {
                   return confirmation.message;
               }
           };
           
           
           $window.onunload = function () {
               $rootScope.$broadcast('onUnload');
           };*/
        /* var ws = new WebSocket("ws://localhost:8042/oefa-sinada-web/wschat");
            ws.onopen = function(){
            };*/
        function closeConnect() {
            /* ws.close();*/
        }

        function enviarDatos() {
            /*  ws.send("Pruebas");*/
        }
        /*  ws.onmessage = function(message){
                console.log("adsdaddasdsa------" + message.data); /*+= message.data + "\n";*/
        /*  };*/
        return factory;
        /* implementacion de metodos */
        function detalleBandeja() {
            return {
                idBandejaDetalle: 0,
                idBandeja: 0,
                bandeja: modelBandeja(),
                tipoBandeja: 0,
                idDenuncia: 0,
                tipoAsignacion: 0,
                estado: 0,
                flagActivo: ''
            };
        }

        function modelBandeja() {
            return {
                idBandeja: 0,
                tipoResponsable: 0,
                idResponsable: 0,
                estado: 0,
                flagActivo: ''
            };
        }

        function model() {
            return {
                idBandeja: 0,
                tipoBandeja: 0,
                idEntidad: 0,
                idDenuncia: 0,
                estado: 0,
                flagActivo: '',
                tipoAsignacion: 0,
                codigoDenuncia: '',
                tipoDenuncia: 0,
                medioRecepcion: 0,
                nombreMedioRecepcion: '',
                hojaTramite: '',
                problematica: 0,
                debido: 0,
                descripcionCaso: '',
                archivoCaso: '',
                documentoContenidoCaso: '',
                zonaSuceso: 0,
                fechaRegistro: '',
                estadoDenuncia: 0,
                nombreEstadoDenuncia: '',
                tiempoTranscurrido: ''
            };
        }

        function bandejaDenuncia() {
            return {
                idBandejaDetalle: 0,
                idBandeja: 0,
                tipoResponsableBandeja: 0,
                idResponsableBandeja: 0,
                estadoBandeja: 0,
                tipoBandeja: 0,
                idDenuncia: 0,
                estadoBandejaDetalle: 0,
                tipoAsignacion: 0,
                nombreTipoAsignacion: '',
                codigoDenuncia: '',
                tipoDenuncia: 0,
                nombreTipoDenuncia: '',
                medioRecepcion: 0,
                nombreMedioRecepcion: '',
                idCaso: 0,
                departamento: '',
                provincia: '',
                distrito: '',
                direccion: '',
                referencia: '',
                tipoResponsable: 0,
                nombreTipoResponsable: '',
                responsableProblema: 0,
                codigoAcceso: '',
                fechaRegistroDenuncia: '',
                estadoDenuncia: 0,
                nombreEstadoDenuncia: '',
                tiempoTranscurrido: '',
                tipoEntidadAtencion: 0
            };
        }

        function bandeja() {
            return {
                idBandeja: 0,
                tipoResponsable: 0,
                idResponsable: 0,
                estado: 0,
                flagActivo: ''
            };
        }

        function listarBandejaEntidad(prmBandeja) {
            return APIFactory.listar(API_CONFIG.url + 'bandeja/bandeja-entidad', prmBandeja);
        }

        function listarBandejaDenunciante(prmCriterio) {
            return APIFactory.listar(API_CONFIG.url + 'bandeja/bandeja-denunciante', prmCriterio);
        }

        function validaBandeja(prmData) {
            return APIFactory.consultaPOST(API_CONFIG.url + 'bandeja/valida-bandeja', prmData);
        }

        function listarBandejaAsignacionDenuncia(prmCriterio) {
            return APIFactory.listar(API_CONFIG.url + 'bandeja/bandeja-asignacion-denuncia', prmCriterio);
        }

        function listarBandejaEspecialistaSinada(prmCriterio) {
            return APIFactory.listar(API_CONFIG.url + 'bandeja/bandeja-especialista-sinada', prmCriterio);
        }

        function validarBandeja(prmCriterio) {
            //debugger;
            return APIFactory.consultaPOST(API_CONFIG.url + 'bandeja/validar-bandeja', prmCriterio);
        }

        function asignarBandejaBandeja(prmCriterio) {
            //debugger;
            return APIFactory.consultaPOST(API_CONFIG.url + 'bandeja/asignar-denuncia-bandeja', prmCriterio);
        }

        function buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(prmCriterio) {
            //debugger;
            return APIFactory.listar(API_CONFIG.url + 'bandeja/buscar-denuncia-oefa-por-direccion-sub-direccion', prmCriterio);
        }
        /*function registrar(prmPerfil) {
            return APIFactory.registrar(API_CONFIG.url+'bandeja/registrar', prmPerfil);
        }*/
        /*
                function obtener(idUsuario) {
                    return APIFactory.obtener(API_CONFIG.url+'perfil/obtener/', idUsuario);
                }
        */
        /*  function actualizar(prmPerfil) {
                return APIFactory.actualizar(API_CONFIG.url+'bandeja/actualizar', prmPerfil);
            }

            function eliminar(prmPerfil) {
                return APIFactory.eliminar(API_CONFIG.url+'bandeja/eliminar', prmPerfil);
            }*/
        /*  function loginUsuario(prmPerfil){
            return APIFactory.consultaPOST(API_CONFIG.url+'perfil/login', prmUsuario);
        }

        function cambiarClave(prmPerfil){
            return APIFactory.actualizar(API_CONFIG.url+'perfil/cambiarClave',prmUsuario);
        }

    

*/
        /******************************* Bandeja Detalle ***********************************/
        function actualizaEstadoDetalleBandeja(prmCriterio) {
            //debugger;
            return APIFactory.actualizar(API_CONFIG.url + 'bandeja/actualiza-estado-detalle-bandeja', prmCriterio);
        }

        function eliminarEspecialistaAsignado(prmCriterio) {
            //debugger;
            return APIFactory.eliminar(API_CONFIG.url + 'bandeja/eliminar-especialista-asignado', prmCriterio);
        }
        /**************************** Excel *******************************/
        function listarBandejaOrganoCompententeCompletoExcelData() {
            return APIFactory.listar(API_CONFIG.url + 'bandeja/listar-bandeja-organo-completo-excel-data');
        }
    }
})();