(function() {
    'use strict';
    angular.module('spapp.bandeja').controller('AsignadasController', AsignadasController);
    /** @ngInject */
    function AsignadasController(API_CONFIG, $state, $scope, toastr, $filter, ngDialog, BandejaFactory, $rootScope, AtencionDenuncia, MaestroFactory, CasosFactory, DetalleCasoFactory, CasoFactory, $location, $log, AccesoFactory, UbigeoFactory, CookiesFactory) {
        //
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 10,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.rutaBase = API_CONFIG.url;
        vm.usuario = {};
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.dataBandejaEntidad = BandejaFactory.bandejaDenuncia();
        vm.objRegistroRevicion = AtencionDenuncia.model();
        vm.dataBandeja = BandejaFactory.modelBandeja();
        vm.listaBandeja = [];
        vm.listaEstadoDenuncia = [];
        vm.asignadasTabActivo = 1;
        /*declaración de metodos enlazados a la vista*/
        vm.revisarDenuncia = revisarDenuncia;
        vm.informarDenuncia = informarDenuncia;
        vm.historialDenuncia = historialDenuncia;
        vm.buscar = buscar;
        vm.limpiar = limpiar;
        vm.fechaRegistroDenunciaInicio = null;
        vm.fechaRegistroDenunciaFin = null;
        vm.fechaTiempoTranscurrido = null;
        vm.fechaTiempoTranscurridoUltimaAccion = null;
        vm.openDatepicker = openDatepicker;
        vm.listaAnios = [];
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
        /************Variables de Ubigeo*************/
        vm.departamento = UbigeoFactory.departamento();
        vm.provincia = UbigeoFactory.provincia();
        vm.distrito = UbigeoFactory.distrito();
        vm.listaDepartamento = [];
        vm.listaProvincia = [];
        vm.listaDistrito = [];
        /***************Fin de Variables Ubigeo********************/
        /****************************/
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        vm.irARegistroDenuncia = irARegistroDenuncia;
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
            vm.provincia.codigoDepartamento = vm.dataBandejaEntidad.departamento;
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
                if (vm.dataBandejaEntidad.departamento != null && vm.dataBandejaEntidad.provincia != null) {
                    listarDistrito();
                }
            });
        }

        function listarDistrito() {
            vm.distrito.codigoDepartamento = vm.dataBandejaEntidad.departamento;
            vm.distrito.codigoProvincia = vm.dataBandejaEntidad.provincia;
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
        /***************Fin de Metodos de Ubigeo*******************/
        function irARegistroDenuncia() {
            localStorage.setItem("dataBandeja", angular.toJson(vm.dataBandeja));
            //  console.log("SETEO EN EL LOCAL STORAGE");
            $state.go('invitado.registro.paso1');
        }

        function cambiarEstadoDetalleDenuncia(data) {
            var detalleBandeja = BandejaFactory.detalleBandeja();
            detalleBandeja.idBandejaDetalle = data.idBandejaDetalle;
            detalleBandeja.estado = 2;
            BandejaFactory.actualizaEstadoDetalleBandeja(angular.copy(detalleBandeja)).then(function(response) {
                /*if(response.valido)
                {
                    
                    
                                
                }
                else
                {
                    toastr.error(response.mensaje);
                }*/
            }).catch(function(error) {
                /*toastr.error('Ocurrió un error al registrar la OEFA.');*/
            }).finally(function() {});
        }

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

        function convertStringToDate(strDate) {
            if (!angular.isUndefined(strDate) && strDate != null && strDate != "") {
                try {
                    var dateArray = strDate.split("/");
                    var date = new Date(dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0])
                    return date;
                } catch (e) {
                    $log.log("convertStringToDate: ", e);
                    return null;
                }
            }
        }

        function listarBandejaDenunciasEntidad() {
            if (vm.fechaRegistroDenunciaInicio != null || vm.fechaRegistroDenunciaInicio != undefined) {
                vm.dataBandejaEntidad.fechaRegistroDenunciaInicio = convertDateToString(vm.fechaRegistroDenunciaInicio);
                if (vm.fechaRegistroDenunciaFin != null || vm.fechaRegistroDenunciaFin != undefined) {
                    vm.dataBandejaEntidad.fechaRegistroDenunciaFin = convertDateToString(vm.fechaRegistroDenunciaFin);
                } else {
                    vm.dataBandejaEntidad.fechaRegistroDenunciaFin = convertDateToString(new Date());
                }
            } else {
                vm.dataBandejaEntidad.fechaRegistroDenunciaInicio = "01/01/16";
                if (vm.fechaRegistroDenunciaFin != null || vm.fechaRegistroDenunciaFin != undefined) {
                    vm.dataBandejaEntidad.fechaRegistroDenunciaFin = convertDateToString(vm.fechaRegistroDenunciaFin);
                }
            }
            //vm.dataDenunciante.fechaRegistroDenunciaInicio = convertDateToString(vm.fechaRegistroDenunciaInicio);
            //vm.dataDenunciante.fechaRegistroDenunciaFin= convertDateToString(vm.fechaRegistroDenunciaFin);
            vm.dataBandejaEntidad.fechaTiempoTranscurrido = convertDateToString(vm.fechaTiempoTranscurrido);
            vm.dataBandejaEntidad.fechaTiempoTranscurridoUltimaAccion = convertDateToString(vm.fechaTiempoTranscurridoUltimaAccion);
            vm.dataBandejaEntidad.problematica = vm.prmDataCaso.problematica;
            vm.dataBandejaEntidad.debidoA1 = vm.prmDataCaso.debidoA1;
            vm.dataBandejaEntidad.debidoA2 = vm.prmDataCaso.debidoA2;
            vm.dataBandejaEntidad.debidoA3 = vm.prmDataCaso.debidoA3;
            vm.dataBandejaEntidad.zonasuceso1 = vm.prmDataCaso.zonasuceso1;
            vm.dataBandejaEntidad.zonasuceso2 = vm.prmDataCaso.zonasuceso2;
            vm.dataBandejaEntidad.zonasuceso3 = vm.prmDataCaso.zonasuceso3;
            //  console.log("vm.dataBandejaEntidad",vm.dataBandejaEntidad);
            BandejaFactory.listarBandejaEntidad(vm.dataBandejaEntidad).then(function(response) {
                if (response.valido) {
                    vm.listaBandeja = angular.copy(response.data);
                    //  console.log("vm.listaBandeja" + angular.toJson(vm.listaBandeja));
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function registrarRevisionDenuncia(data) {
            vm.objRegistroRevicion.detalleBandeja.idBandejaDetalle = data.idBandejaDetalle;
            /*  vm.objRegistroRevicion.tipoEntidadCompetente.codigoRegistro = vm.usuario.tipoEntidadCompetente;
                if(vm.objRegistroRevicion.tipoEntidadCompetente.codigoRegistro == 1){
                }

                if(vm.objRegistroRevicion.tipoEntidadCompetente.codigoRegistro == 2){

                        vm.objRegistroRevicion.efa.idEfa = vm.usuario.idEfa;
                    
                }*/
            vm.objRegistroRevicion.tipoAtencion.codigoRegistro = 1;
            vm.objRegistroRevicion.denuncia.idDenuncia = data.idDenuncia;
            vm.objRegistroRevicion.estado.codigoRegistro = 1;
            AtencionDenuncia.registrar(angular.copy(vm.objRegistroRevicion)).then(function(response) {
                if (response.valido) {
                    vm.objRegistroRevicion.idAtencionDenuncia = response.data;
                    cambiarEstadoDetalleDenuncia(data);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                /*toastr.error('Ocurrió un error al registrar la OEFA.');*/
            }).finally(function() {});
        }

        function historialDenuncia(data) {
            $state.go('spapp.home.bandeja.historial');
            $rootScope.dataDenunciaHistorial = data;
        }

        function revisarDenuncia(data) {
            registrarRevisionDenuncia(data);
            $rootScope.dataRevisionDenuncia = data;
            $state.go('spapp.home.bandeja.asignadas-detalle');
        }

        function informarDenuncia(data) {
            $rootScope.dataInformeDenuncia = data;
            $state.go('spapp.home.bandeja.asignadas-informar');
        }

        function cargarCombos() {
            var codigosCombos = ['EstadoDenuncia'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                //console.log('response cargarCombos',response);
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'EstadoDenuncia':
                                vm.listaEstadoDenuncia = datosCombo;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function buscar() {
            listarBandejaDenunciasEntidad();
            /*BandejaFactory.enviarDatos();*/
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
                    //  console.log("vm.listaProblematica"+vm.listaProblematica);
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
                        //      console.log("listaDebidoANivel1" + response.data);
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
        function limpiar() {
            vm.dataBandejaEntidad.codigoDenuncia = "";
            vm.dataBandejaEntidad.estadoBandejaDetalle = 0;
            vm.dataBandejaEntidad.nombreDenunciante = "";
            vm.dataBandejaEntidad.nombreDenunciado = "";
            vm.especialistaAsignado = "";
            vm.dataBandejaEntidad.year = 0;
            vm.dataBandejaEntidad.month = 0;
            vm.fechaRegistroDenunciaInicio = "";
            vm.fechaRegistroDenunciaFin = "";
            vm.fechaTiempoTranscurrido = "";
            vm.fechaTiempoTranscurridoUltimaAccion = "";
            vm.prmDataCaso.problematica.idDetalleCaso = 0;
            vm.prmDataCaso.debidoA1.idDetalleCaso = 0;
            vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
            vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
            vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
            vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
            vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
            vm.dataBandejaEntidad.departamento = 0;
            vm.dataBandejaEntidad.provincia = 0;
            vm.dataBandejaEntidad.distrito = 0;
            //vm.dataBandejaEntidad.direccion = "";
        }

        function init() {
            CookiesFactory.obtenerCookie();
            listarDepartamento();
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                /*************  Obtenemos los datos de usuario  **************/
                vm.usuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                /*************** Pasamos los datos de Bandeja  *************/
                vm.dataBandeja = vm.usuario.bandejaEntidad;
                vm.dataBandejaEntidad.idBandeja = vm.dataBandeja.idBandeja;
                vm.dataBandejaEntidad.tipoBandeja = 3;
                vm.dataBandejaEntidad.year = 0;
                vm.dataBandejaEntidad.month = 0;
                vm.dataBandejaEntidad.fechaRegistroDenunciaFin = "";
                vm.dataBandejaEntidad.fechaRegistroDenunciaInicio = "";
                vm.anio = (new Date).getFullYear();
                vm.dataBandejaEntidad.nombreDenunciado = '';
                for (var i = 2005; i <= vm.anio; i++) {
                    vm.listaAnios.push(i)
                }
                vm.dataBandejaEntidad.departamento = "";
                vm.dataBandejaEntidad.provincia = "";
                vm.dataBandejaEntidad.distrito = "";
                listarBandejaDenunciasEntidad();
                listarProblematica();
                cargarCombos();
            }
        }

        function leerOperaciones(ref) {
            AccesoFactory.operaciones(ref, $rootScope.aplicacionSinada.opciones.idEntidadBandejaDenuncias).then(function(response) {
                $rootScope.operaciones = response;
                $log.log('operaciones ' + response);
                $rootScope.validarOperacion = validarOperacion;
            }).catch(function(error) {
                toastr.error(error);
            });
        }

        function validarOperacion(operacion) {
            for (var int = 0; int < $rootScope.operaciones.length; int++) {
                var oOperacion = $rootScope.operaciones[int];
                $log.log('oOperacion ' + oOperacion.elemento);
                if (oOperacion.elemento == operacion) {
                    return true;
                }
            }
            return false;
        }
        $rootScope.ref_tmp = $location.search().ref;
        if (angular.isDefined($rootScope.ref_tmp)) {
            leerOperaciones($rootScope.ref_tmp);
            $rootScope.ref = $rootScope.ref_tmp;
        } else {
            leerOperaciones($rootScope.ref);
        }
        init();
        /*fin de metodos*/
    }
})();