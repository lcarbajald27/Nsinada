(function() {
    'use strict';
    angular.module('spapp.bandeja').controller('DenuncianteController', DenuncianteController);
    /** @ngInject */
    function DenuncianteController(ngDialog, API_CONFIG, $state, INTERNAL_HOME_PAGE, BandejaFactory, MaestroFactory, $filter, DetalleCasoFactory, CasosFactory, CasoFactory, $rootScope, UbigeoFactory, CookiesFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.rutaBase = API_CONFIG.url;
        vm.usuario = {};
        vm.dataBandeja = BandejaFactory.modelBandeja();
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.dataDenunciante = BandejaFactory.bandejaDenuncia();
        vm.buscar = listarBandejaDenunciante;
        /*declaración de metodos enlazados a la vista*/
        vm.listaDenunciante = [];
        vm.fechaRegistroDenunciaInicio = null;
        vm.fechaRegistroDenunciaFin = null;
        vm.fechaTiempoTranscurrido = null;
        vm.fechaTiempoTranscurridoUltimaAccion = null;
        vm.opcionesPaginacion = {
            itemsPorPag: 10,
            cantidades: [10, 25, 50, 100]
        };
        vm.listaEstadoDenuncia = [];
        vm.openDatepicker = openDatepicker;
        vm.limpiar = limpiar;
        /*PROBLEMATICA*/
        vm.listarProblematica = listarProblematica;
        vm.limpiarGenerico = limpiarGenerico;
        vm.listarCasosGenerales = listarCasosGenerales;
        vm.dataProblematica = CasosFactory.model();
        vm.prmDetalleCaso = DetalleCasoFactory.model();
        vm.padreDondeSucede = DetalleCasoFactory.model();
        vm.prmDataCaso = CasoFactory.model();
        vm.prmDataCasoFiltro = CasoFactory.model();
        vm.prmDataCasoAsignar = CasoFactory.model();
        vm.listaProblematica = [];
        vm.listaDebidoANivel1 = [];
        vm.listaDebidoANivel2 = [];
        vm.listaDebidoANivel3 = [];
        vm.listaZonaSucesonivel1 = [];
        vm.listaZonaSucesonivel2 = [];
        vm.listaZonaSucesonivel3 = [];
        vm.listaCaso = [];
        vm.listaDireccionEvaluacion = [];
        vm.listaOefaCaso = [];
        vm.listaEfas = [];
        vm.listaAnios = [];
        vm.listaCentroPoblado = [];
        /************Variables de Ubigeo*************/
        vm.departamento = UbigeoFactory.departamento();
        vm.provincia = UbigeoFactory.provincia();
        vm.distrito = UbigeoFactory.distrito();
        vm.listaDepartamento = [];
        vm.listaProvincia = [];
        vm.listaDistrito = [];
        /***************Fin de Variables Ubigeo********************/
        /********************************/
        vm.verHistorialDenunciante = verHistorialDenunciante;
        vm.verFichaDenuncia = verFichaDenuncia;
        vm.irARegistroDenuncia = irARegistroDenuncia;
        vm.mostrarModalEncuestaRevision = mostrarModalEncuestaRevision
        /*********Metodos de Ubigeo**********/
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        /*********Fin Metodos de Ubigeo*********
         *
         */
        /*implementación de metodos*/
        /***************Metodos de Ubigeo*******************/
        function listarDepartamento() {
            UbigeoFactory.listarDepartamento(vm.departamento).then(function(response) {
                if (response.valido) {
                    vm.listaDepartamento = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarProvincia() {
            vm.provincia.codigoDepartamento = vm.dataDenunciante.departamento;
            UbigeoFactory.listarProvincia(vm.provincia).then(function(response) {
                if (response.valido) {
                    vm.listaProvincia = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.dataDenunciante.departamento != null && vm.dataDenunciante.provincia != null) {
                    listarDistrito();
                }
            });
        }

        function listarDistrito() {
            vm.distrito.codigoDepartamento = vm.dataDenunciante.departamento;
            vm.distrito.codigoProvincia = vm.dataDenunciante.provincia;
            UbigeoFactory.listarDistrito(vm.distrito).then(function(response) {
                if (response.valido) {
                    vm.listaDistrito = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /************Variables de Ubigeo*************/
        function mostrarModalEncuestaRevision(data) {
            ngDialog.open({
                template: 'app/modules/bandeja/denunciante/encuesta-denunciante/encuesta-denunciante.html',
                controller: 'DialogEncuestaDenuncianteController',
                data: {
                    prmData: data,
                },
                controllerAs: 'vm',
                width: '550px'
            }).closePromise.then(function(dataDialog) {
                listarBandejaDenunciante();
            });
        }

        function irARegistroDenuncia() {
            localStorage.setItem("dataBandeja", angular.toJson(vm.dataBandeja));
            //Bandeja Procedencia 1-Asignacion 2-Especialista 3-EFA 4-Denunciante
            var bandejaProcedencia = 4;
            localStorage.setItem("bandejaProcedencia", angular.copy(bandejaProcedencia));
            //      console.log("SETEO EN EL LOCAL STORAGE");
            $state.go('invitado.registro.paso1');
        }

        function verFichaDenuncia(data) {
            /*  var prmData= {
                        idDenuncia:vm.dataDenunciaRevision.idDenuncia,

                };*/
            var data = {
                flagActivo: 1,
                prmData: data
            };
            localStorage.setItem("objDenunciaFicha", angular.toJson(data));
            ngDialog.open({
                template: 'app/modules/ficha-denuncia/ficha.html',
                controller: 'FichaController',
                /*data : {
                    data :item
                },*/
                controllerAs: 'vm',
                width: '950px'
            }).closePromise.then(function(dataDialog) {
                /*if (dataDialog.value.cliente) {
                    vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
                }*/
            });
        }
        /***************************************************************/
        function verHistorialDenunciante(data) {
            $state.go('spapp.home.bandeja.historial-denunciante');
            $rootScope.dataDenunciaHistorialDenunciante = data;
        }
        /**************************************************************/
        function convertDateToString(prmdate) {
            if (!angular.isUndefined(prmdate) && prmdate != null && prmdate != "") {
                try {
                    var strDate = $filter('date')(prmdate, "dd/MM/yyyy");
                    return strDate;
                } catch (e) {
                    return "";
                }
            }
        }

        function listarBandejaDenunciante() {
            //      console.log('sdsdsdssdvdv');
            vm.dataDenunciante.idBandeja = vm.dataBandeja.idBandeja;
            vm.dataDenunciante.tipoBandeja = 1;
            if (vm.fechaRegistroDenunciaInicio != null || vm.fechaRegistroDenunciaInicio != undefined) {
                vm.dataDenunciante.fechaRegistroDenunciaInicio = convertDateToString(vm.fechaRegistroDenunciaInicio);
                if (vm.fechaRegistroDenunciaFin != null || vm.fechaRegistroDenunciaFin != undefined) {
                    vm.dataDenunciante.fechaRegistroDenunciaFin = convertDateToString(vm.fechaRegistroDenunciaFin);
                } else {
                    vm.dataDenunciante.fechaRegistroDenunciaFin = convertDateToString(new Date());
                }
            } else {
                vm.dataDenunciante.fechaRegistroDenunciaInicio = "01/01/16";
                if (vm.fechaRegistroDenunciaFin != null || vm.fechaRegistroDenunciaFin != undefined) {
                    vm.dataDenunciante.fechaRegistroDenunciaFin = convertDateToString(vm.fechaRegistroDenunciaFin);
                }
            }
            //vm.dataDenunciante.fechaRegistroDenunciaInicio = convertDateToString(vm.fechaRegistroDenunciaInicio);
            //vm.dataDenunciante.fechaRegistroDenunciaFin= convertDateToString(vm.fechaRegistroDenunciaFin);
            vm.dataDenunciante.fechaTiempoTranscurrido = convertDateToString(vm.fechaTiempoTranscurrido);
            vm.dataDenunciante.fechaTiempoTranscurridoUltimaAccion = convertDateToString(vm.fechaTiempoTranscurridoUltimaAccion);
            vm.dataDenunciante.problematica = vm.prmDataCaso.problematica;
            vm.dataDenunciante.debidoA1 = vm.prmDataCaso.debidoA1;
            vm.dataDenunciante.debidoA2 = vm.prmDataCaso.debidoA2;
            vm.dataDenunciante.debidoA3 = vm.prmDataCaso.debidoA3;
            vm.dataDenunciante.zonasuceso1 = vm.prmDataCaso.zonasuceso1;
            vm.dataDenunciante.zonasuceso2 = vm.prmDataCaso.zonasuceso2;
            vm.dataDenunciante.zonasuceso3 = vm.prmDataCaso.zonasuceso3;
            //  console.log(vm.dataDenunciante);
            BandejaFactory.listarBandejaDenunciante(vm.dataDenunciante).then(function(response) {
                if (response != null && response.valido) {
                    //vm.listaDenunciante = angular.copy(response.data);
                    vm.listaDenunciante =response.data;
                    //      console.log(response.data);
                    //console.log("vm.listaDenunciante" + angular.toJson(vm.listaDenunciante));
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos.');
            });
        }

        function cargarCombos() {
            var codigosCombos = ['EstadoDenuncia', 'CentroPoblado'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                //  console.log('response cargarCombos',response);
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'EstadoDenuncia':
                                vm.listaEstadoDenuncia = datosCombo;
                                break;
                            case 'CentroPoblado':
                                vm.listaCentroPoblado = datosCombo;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos.');
            });
        }
        /*fin de metodos*/
        function openDatepicker(opcion) {
            vm.options = {
                showWeeks: false,
                //maxDate: vm.maxDate,
                //minDate: vm.date
            };
            switch (opcion) {
                case "dpFechaInicio":
                    vm.showDpFechaInicio = true;
                    break;
                case "dpFechaFin":
                    vm.showDpFechaFin = true;
                    vm.optionsRangoFinal = {
                        minDate: vm.fechaRegistroDenunciaInicio
                    };
                    break;
                case "dpFechaTiempoTranscurrido":
                    vm.showDpFechaTiempoTranscurrido = true;
                    break;
                case "dpFechaTiempoTranscurridoActualizacion":
                    vm.showDpFechaTiempoTranscurridoActualizacion = true;
                    break;
                default:
                    break;
            }
        }

        function limpiar() {
            vm.dataDenunciante.year = 0;
            vm.dataDenunciante.month = 0;
            vm.fechaRegistroDenunciaInicio = "";
            vm.fechaRegistroDenunciaFin = "";
            vm.fechaTiempoTranscurrido = "";
            vm.fechaTiempoTranscurridoUltimaAccion = "";
            vm.dataDenunciante.codigoDenuncia = "";
            vm.dataDenunciante.estadoDenuncia = 0;
            vm.dataDenunciante.nombreDenunciante = "";
            vm.dataDenunciante.nombreDenunciado = "";
            vm.prmDataCaso.problematica.idDetalleCaso = 0;
            vm.prmDataCaso.debidoA1.idDetalleCaso = 0;
            vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
            vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
            vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
            vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
            vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
            vm.dataDenunciante.departamento = "";
            vm.dataDenunciante.provincia = "";
            vm.dataDenunciante.distrito = "";
            vm.dataDenunciante.direccion = "";
            vm.listaProvincia = [];
            vm.listaDistrito = [];
        }
        /****************************  Problematica  *****************************/
        function listarProblematica() {
            var dataProblematica = {
                tipoCaso: 1,
                codigoPadre: 0,
                tipoNivel: 0
            };
            DetalleCasoFactory.listar(dataProblematica).then(function(response) {
                if (response.valido) {
                    vm.listaProblematica = response.data;
                    //      console.log("vm.listaProblematica"+vm.listaProblematica);
                    /* vm.dataDenuncia.tipoPersona=0; */
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /*******************************************************************/
        /********************************************************************/
        function limpiarGenerico(tipo, nivel) {
            if (tipo == 0 && nivel == 0) {
                vm.prmDetalleCaso = DetalleCasoFactory.model();
                vm.padreDondeSucede = DetalleCasoFactory.model();
                vm.prmDataCaso = CasoFactory.model();
                vm.listaDebidoANivel1 = [];
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
                vm.listaZonaSucesonivel1 = [];
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 2 && nivel == 1) {
                vm.prmDataCaso.debidoA1.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 2 && nivel == 2) {
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 2 && nivel == 3) {
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
            }
            if (tipo == 3 && nivel == 1) {
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 3 && nivel == 2) {
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 3 && nivel == 3) {
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
            }
        }
        /******************************************************************/
        /********************************** Debido a Por Niveles ************************************/
        function listarCasosGenerales(tipoCaso, codigoPadre, nivel) {
            var dataProblematica = {
                tipoCaso: tipoCaso,
                codigoPadre: codigoPadre,
                tipoNivel: nivel
            };
            DetalleCasoFactory.listar(dataProblematica).then(function(response) {
                if (response.valido) {
                    if (tipoCaso == 2 && nivel == 1) {
                        //              console.log("listaDebidoANivel1" + response.data);
                        vm.listaDebidoANivel1 = response.data;
                        limpiarGenerico(tipoCaso, nivel);
                    }
                    if (tipoCaso == 2 && nivel == 2) {
                        limpiarGenerico(tipoCaso, nivel);
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel2 = response.data;
                            limpiarGenerico(tipoCaso, nivel);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 2 && nivel == 3) {
                        limpiarGenerico(tipoCaso, nivel);
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel3 = response.data;
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 3 && nivel == 1) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                        vm.listaZonaSucesonivel1 = response.data;
                    }
                    if (tipoCaso == 3 && nivel == 2) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaZonaSucesonivel2 = response.data;
                    }
                    if (tipoCaso == 3 && nivel == 3) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaZonaSucesonivel3 = response.data;
                    }
                    /*vm.listaDebidoA = response.data;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /********************************************************************/
        /** ******************************PROBLEMATICA************************************** */
        function init() {
            CookiesFactory.obtenerCookie();
            listarDepartamento();
            /******************* Datos Bandeja ******************************/
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                vm.usuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                //      console.log("vm.usuario",vm.usuario);
                vm.dataBandeja = vm.usuario.bandeja;
                //      console.log("vm.dataBandeja",vm.dataBandeja);
            }
            vm.dataDenunciante.departamento = "";
            vm.dataDenunciante.provincia = "";
            vm.dataDenunciante.distrito = "";
            vm.dataDenunciante.year = 0;
            vm.dataDenunciante.month = 0;
            vm.dataDenunciante.fechaRegistroDenunciaFin = "";
            vm.dataDenunciante.fechaRegistroDenunciaInicio = "";
            vm.anio = (new Date).getFullYear();
            vm.dataDenunciante.nombreDenunciado = '';
            for (var i = 2005; i <= vm.anio; i++) {
                vm.listaAnios.push(i)
            }
            //console.log("vm.listaAnios",vm.listaAnios);
            listarBandejaDenunciante();
            
            /**************************************************/
            cargarCombos();
        }
        init();
    }
})();