(function() {
    'use strict';
    angular.module('spapp').factory('DenunciaFactory', DenunciaFactory);
    /* @ngInject */
    function DenunciaFactory(API_CONFIG, APIFactory, CasoFactory, MaestroFactory,$log, esriLoader) {
        /*inicializacion y declaracion de metodos del factory*/
        var factory = {
            model: model,
            denunciante: denunciante,
            listar: listar,
            enviarUbicacionDenuncia: enviarUbicacionDenuncia,
            registrar: registrar,
            eliminar: eliminar,
            validarDocumentoMedio: validarDocumentoMedio,
            guardarHojaTramite: guardarHojaTramite,
            buscarDenunciaInvitado: buscarDenunciaInvitado,
            descripcionCasoModel: descripcionCasoModel,
            registrarProblematicaNoEncontrada: registrarProblematicaNoEncontrada,
            listarDenunciantes: listarDenunciantes,
            enviarTempPDF: enviarTempPDF,
            modelArchivo: modelArchivo,
            actualizarEstado: actualizarEstado,
            obtenerDenuncia: obtenerDenuncia,
            obtenerDatosFichaDenuncia: obtenerDatosFichaDenuncia,
            obtenerPDFFichaDenunciaHtml: obtenerPDFFichaDenunciaHtml,
            STDHojaTramiteModel: STDHojaTramiteModel,
            STDObtenerHojaTramite: STDObtenerHojaTramite
            /*obtener       : obtener,
            actualizar      : actualizar,
            eliminar        : eliminar,
            buscarMaestros  : buscarMaestros,
            buscar          : buscar*/
        };
        return factory;
        /* implementacion de metodos */
        function denunciante() {
            return {
                idDenunciante: 0,
                idDenuncia: 0,
                tipoPersona: 0,
                personaDenunciante: {},
                entidadDenunciante: {},
                estado: 0,
                flagActivo: ''
            };
        }

        function modelArchivo() {
            return {
                idArchivoDenuncia: 0,
                idDenuncia: 0,
                nombreArchivo: '',
                rutaArchivoDenuncia: '',
                mimeTypeArchivo: '',
                //estado :MaestroFactory.model(),
                flagActivo: '',
                tipoArchivo: MaestroFactory.model(),
                //tipoTabla :0, // TipoTablaArchivoAtencion
                descripcionArchivo: '',
                posicionArchivo: ''
            };
        }

        function model() {
            return {
                idDenuncia: 0,
                codigoDenuncia: '',
                tipoDenuncia: 0,
                medioRecepcion: 0,
                hojaTramite: '',
                lstDenunciante: [],
                departamento: '',
                provincia: '',
                distrito: '',
                codigoCentroPoblado: '',
                direccion: '',
                referencia: '',
                tipo_responsable: 0,
                responsableProblema: 0,
                tipoMedio: 0,
                codigoAcceso: '',
                flagActivo: '',
                caso: CasoFactory.model(),
                centroPoblado: MaestroFactory.model(),
                lstArchivoMedio: []
            };
        }

        function descripcionCasoModel() {
            return {
                idDescripcionCaso: 0,
                descripcionCaso: '',
                documento: '',
                direccion: '',
                nombreCompleto: '',
                telefono: '',
                correo: '',
                estado: 0,
                flagActivo: '',
                mensajeHtml: ''
            };
        }

        function listar(prmDenuncia) {
            return APIFactory.listar(API_CONFIG.url + 'denuncia/listar', prmDenuncia);
        }

        function enviarUbicacionDenuncia(prmDenuncia){

            return esriLoader.require([
                "esri/layers/FeatureLayer",
                'esri/Graphic',
                'esri/config',
            ], function(FeatureLayer, Graphic, esriConfig) {
                esriConfig.request.proxyUrl = API_CONFIG.urlArcGisProxy;
                var layer = new FeatureLayer({
                    url: API_CONFIG.urlFeatureService,
                });

                var utmPoint = UTMConverter.toUtm(prmDenuncia.latitude, prmDenuncia.longitude);

                var attributes = {
                    COD_DENUNCIA_SINADA: prmDenuncia.codigoDenuncia,
                    COD_ALA: prmDenuncia.adminLocales,
                    COD_ODE: prmDenuncia.oficinasDesconcentradas,
                    COD_EFA: '',
                    COD_ACN: prmDenuncia.areaConservacion,

                    COORD_X_UTM: utmPoint.easting,
                    COORD_Y_UTM: utmPoint.northing,
                    ZONA: utmPoint.zoneNum,

                    LATITUD: prmDenuncia.latitude,
                    LONGITUD: prmDenuncia.longitude,
                    COD_DEPARTAMENTO: prmDenuncia.departamento,
                    COD_PROVINCIA: prmDenuncia.provincia,
                    COD_DISTRITO: prmDenuncia.distrito,
                    ZONAAMORTIGUAMIENTO: prmDenuncia.zonaAmortiguamiento,
                    COD_CCPP: prmDenuncia.centroPoblado,
                };

                var feature =  new Graphic({
                    geometry: {
                        type: "point",  // autocasts as new Point()
                        longitude: prmDenuncia.longitude,
                        latitude: prmDenuncia.latitude,
                    },
                    attributes: attributes
                });

                return layer.applyEdits({
                    addFeatures: [feature],
                });
            });
        }

        function registrar(files, prmDenuncia,$log) {
            if (files != null && files.length != 0) {
                var formData = new FormData();
                for (var x in files) {
                    formData.append("file", files[x]);
                }
                formData.append("strContenido", angular.toJson(prmDenuncia));
                console.log("formData", formData);
                return APIFactory.guardarArchivo(API_CONFIG.url + 'denuncia/registrar', formData);
            } else {
                console.log("prmDenuncia", prmDenuncia);
                return APIFactory.registrar(API_CONFIG.url + 'denuncia/registrar-denuncia', prmDenuncia);
            }
        }

        function registrarProblematicaNoEncontrada(files, prmData) {
            var formData = new FormData();
            if (files instanceof FileList) {
                for (var x in files) {
                    formData.append("file", files[x]);
                }
            } else {
                formData.append("file", files);
            }
            formData.append("strContenido", angular.toJson(prmData));
            return APIFactory.guardarArchivo(API_CONFIG.url + 'denuncia/problematica-no-encontrada', formData);
        }

        function guardarHojaTramite(file, prmDenuncia) {
            var formData = new FormData();
            formData.append("file", file);
            formData.append("strContenido", angular.toJson(prmDenuncia));
            return APIFactory.guardarArchivo(API_CONFIG.url + 'denuncia/registrar-hoja-tramite', formData);
        }

        function buscarDenunciaInvitado(prmDenuncia) {
            return APIFactory.consultaPOST(API_CONFIG.url + 'denuncia/buscarDenunciaInvitado', prmDenuncia);
        }

        function validarDocumentoMedio(prmDenuncia) {
            var formData = new FormData();
            formData.append("file", file);
            return APIFactory.guardarArchivo(API_CONFIG.url + 'denuncia/validar-documento-medio-probatorio', formData);
        }
        /*
        function obtener(idMaestro) {
            return APIFactory.obtener(API_CONFIG.url+'maestro/obtener/', idAutor);
        }

        function actualizar(prmMaestro) {
            return APIFactory.actualizar(API_CONFIG.url+'maestro/update', prmMaestro);
        }*/
        function actualizarEstado(prmDenuncia) {
            return APIFactory.actualizar(API_CONFIG.url + 'denuncia/actualizar-estado', prmDenuncia);
        }

        function eliminar(prmDenuncia) {
            return APIFactory.eliminar(API_CONFIG.url + 'denuncia/eliminar', prmDenuncia);
        }

        function listarDenunciantes(prmData) {
            return APIFactory.eliminar(API_CONFIG.url + 'denuncia/buscarDenunciantes', prmData);
        }

        function enviarTempPDF(prmData) {
            return APIFactory.eliminar(API_CONFIG.url + 'denuncia/preVisualPDF', prmData);
        }

        function obtenerDenuncia(prmDenuncia) {
            return APIFactory.consultaPOST(API_CONFIG.url + 'denuncia/obtenerDenuncia', prmDenuncia);
        }

        function obtenerDatosFichaDenuncia(prmDenuncia) {
            return APIFactory.consultaPOST(API_CONFIG.url + 'denuncia/obtener-datos-ficha-denuncia', prmDenuncia);
        }

        function obtenerPDFFichaDenunciaHtml(prmDenuncia) {
            return APIFactory.consultaPOST(API_CONFIG.url + 'denuncia/generar-pdf-ficha-denuncia-html', prmDenuncia);
        }

        function STDHojaTramiteModel() {
            return {
                nrohoja: null,
                anio: null,
                intext: null,
                pcursor: [],
                denuncia: {
                    codigosinada: null,
                    descripcion: null,
                    remitente: null,
                    flgexito: null,
                    hojanueva: null,
                    expediente: null,
                }
            };
        }

        function STDObtenerHojaTramite(anio, tipo, numero) {
            return APIFactory.obtener(API_CONFIG.url + 'denuncia/consulta-std-hoja-tramite/' + anio + '/' + tipo + '/' + numero);
        }
        /*
        function buscarMaestros(prmListaMaestros) {
            return APIFactory.listar(API_CONFIG.url+'maestro/buscarMaestros', prmListaMaestros);
        }
        
        function buscar(prmListaMaestros) {
            return APIFactory.registrar(API_CONFIG.url+'maestro/buscar', prmListaMaestros);
        }*/
    }
})();
