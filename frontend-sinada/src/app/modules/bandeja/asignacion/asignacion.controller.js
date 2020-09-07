(function() {
    'use strict';
    angular.module('spapp.bandeja').controller('AsignacionController', AsignacionController);
    /** @ngInject */
    function AsignacionController(API_CONFIG, $scope, $state, INTERNAL_HOME_PAGE, BandejaFactory, $window, PersonaFactory, toastr, InformeAccionFactory, CasoFactory, ngDialog, MaestroFactory, $filter, CasosFactory, DetalleCasoFactory, $rootScope, UbigeoFactory, CookiesFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.configData = {
            itemsPerPage: 10,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.rutaBase = API_CONFIG.url;
        vm.usuario = {};
        vm.dataBandeja = {};
        vm.dataBandejaDetalle = BandejaFactory.bandejaDenuncia();
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.enviardata = enviardata;
        vm.datosPersona = PersonaFactory.model();
        vm.listaEstadoDenuncia = [];
        vm.listaDenuncias = [];
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
        /**************** Metodos enlazados a la vista *******************/
        vm.listarEntidadesCasoDenuncia = listarEntidadesCasoDenuncia;
        vm.mostrarModalAsignarDenuncia = mostrarModalAsignarDenuncia;
        vm.verHistorialCoordinador = verHistorialCoordinador;
        vm.verFichaDenuncia = verFichaDenuncia;
        vm.irARegistroDenuncia = irARegistroDenuncia;
        /*********Metodos de Ubigeo**********/
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        vm.listarBandejaOrganoCompententeCompletoExcelData = listarBandejaOrganoCompententeCompletoExcelData;
        /*********Fin Metodos de Ubigeo**********/
        /***********************************************************/
        /************** Implementacion de Metodos *************/
        /***************Metodos de Ubigeo*******************/
        function listarBandejaOrganoCompententeCompletoExcelData() {
            BandejaFactory.listarBandejaOrganoCompententeCompletoExcelData().then(function(response) {
                if (response.valido) {
                    $window.location.href = vm.rutaBase + 'bandeja/exportar-excel-coordinador';
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

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
        /************Variables de Ubigeo*************/
        /***************Metodos de Ubigeo*******************/
        /*
        function listarDepartamento(){

            UbigeoFactory
                        .listarDepartamento(vm.departamento)
                        .then(function (response) {
                            if (response.valido) {
                                vm.listaDepartamento= response.data;



                            }
                            else {
                                toastr.error(response.mensaje);
                            }
                        })
                        .catch(function (error) {
                            toastr.error('Error al consultar');
                        })
                        .finally(function () {

                        });

        }

            function listarProvincia(){
                        vm.provincia.codigoDepartamento=vm.dataBandejaDetalle.departamento;
            UbigeoFactory
                        .listarProvincia(vm.provincia)
                        .then(function (response) {
                            if (response.valido) {
                                vm.listaProvincia= response.data;


                            }
                            else {
                                toastr.error(response.mensaje);
                            }
                        })
                        .catch(function (error) {
                            toastr.error('Error al consultar');
                        })
                        .finally(function () {

                            if(vm.dataBandejaDetalle.departamento!=null && vm.dataBandejaDetalle.provincia!=null){

                                listarDistrito();
                            }



                        });

        }

        function listarDistrito(){
                        vm.distrito.codigoDepartamento=vm.dataBandejaDetalle.departamento;
                        vm.distrito.codigoProvincia=vm.dataBandejaDetalle.provincia;
            UbigeoFactory
                        .listarDistrito(vm.distrito)
                        .then(function (response) {
                            if (response.valido) {
                                vm.listaDistrito= response.data;



                            }
                            else {
                                toastr.error(response.mensaje);
                            }
                        })
                        .catch(function (error) {
                            toastr.error('Error al consultar');
                        })
                        .finally(function () {

                        });

        }*/
        /***************Fin de Metodos de Ubigeo*******************/
        function irARegistroDenuncia() {
            localStorage.setItem("dataBandeja", angular.toJson(vm.dataBandeja));
            //Bandeja Procedencia 1-Asignacion 2-Especialista 3-EFA 4-Denunciante
            var bandejaProcedencia = 1;
            localStorage.setItem("bandejaProcedencia", angular.copy(bandejaProcedencia));
            $state.go('invitado.registro.paso1');
        }

        function verFichaDenuncia(data) {
            /*  var prmData= {
                        idDenuncia:vm.dataDenunciaRevision.idDenuncia,

                };*/
            // debugger;
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
        /*
        function verFichaDenuncia(data){
            debugger;
                $state.go('spapp.home.ficha');
                var data = {
                        flagActivo : 1,
                        prmData : data

                };
                localStorage.setItem("objDenunciaFicha", angular.toJson(data));


        }*/
        function verHistorialCoordinador(data) {
            $rootScope.dataHistorialDenuncia = data;
            $state.go('spapp.home.bandeja.historial-coordinador');
        }
        $window.onbeforeunload = function(e) {
            BandejaFactory.closeConnect();
            //              var confirmation = {};
            //              var event = $rootScope.$broadcast('onBeforeUnload', confirmation);
            //              if (event.defaultPrevented) {
            //                  return confirmation.message;
            //              }
        };

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

        function listarBandejaAsignacionDenuncia() {
            if (vm.fechaRegistroDenunciaInicio != null || vm.fechaRegistroDenunciaInicio != undefined) {
                vm.dataBandejaDetalle.fechaRegistroDenunciaInicio = convertDateToString(vm.fechaRegistroDenunciaInicio);
                if (vm.fechaRegistroDenunciaFin != null || vm.fechaRegistroDenunciaFin != undefined) {
                    vm.dataBandejaDetalle.fechaRegistroDenunciaFin = convertDateToString(vm.fechaRegistroDenunciaFin);
                } else {
                    vm.dataBandejaDetalle.fechaRegistroDenunciaFin = convertDateToString(new Date());
                }
            } else {
                vm.dataBandejaDetalle.fechaRegistroDenunciaInicio = "01/01/16";
                if (vm.fechaRegistroDenunciaFin != null || vm.fechaRegistroDenunciaFin != undefined) {
                    vm.dataBandejaDetalle.fechaRegistroDenunciaFin = convertDateToString(vm.fechaRegistroDenunciaFin);
                }
            }
            //vm.dataDenunciante.fechaRegistroDenunciaInicio = convertDateToString(vm.fechaRegistroDenunciaInicio);
            //vm.dataDenunciante.fechaRegistroDenunciaFin= convertDateToString(vm.fechaRegistroDenunciaFin);
            vm.dataBandejaDetalle.fechaTiempoTranscurrido = convertDateToString(vm.fechaTiempoTranscurrido);
            vm.dataBandejaDetalle.fechaTiempoTranscurridoUltimaAccion = convertDateToString(vm.fechaTiempoTranscurridoUltimaAccion);
            vm.dataBandejaDetalle.problematica = vm.prmDataCaso.problematica;
            vm.dataBandejaDetalle.debidoA1 = vm.prmDataCaso.debidoA1;
            vm.dataBandejaDetalle.debidoA2 = vm.prmDataCaso.debidoA2;
            vm.dataBandejaDetalle.debidoA3 = vm.prmDataCaso.debidoA3;
            vm.dataBandejaDetalle.zonasuceso1 = vm.prmDataCaso.zonasuceso1;
            vm.dataBandejaDetalle.zonasuceso2 = vm.prmDataCaso.zonasuceso2;
            vm.dataBandejaDetalle.zonasuceso3 = vm.prmDataCaso.zonasuceso3;
            BandejaFactory.listarBandejaAsignacionDenuncia(vm.dataBandejaDetalle).then(function(response) {
                if (response.valido) {
                    vm.listaDenuncias = angular.copy(response.data);
                    //  console.log("vm.listaDenuncias" + angular.toJson(vm.listaDenuncias));
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarEntidadesCasoDenuncia(data, idBandejaDetalle) {
            // debugger;
            var prmData = {
                idDenuncia: data.idDenuncia,
            };
            InformeAccionFactory.listarSegundoNivelBandejaEspecialista(prmData).then(function(response) {
                // debugger;
                if (response.valido) {
                    for (var x in vm.listaDenuncias) {
                        if (vm.listaDenuncias[x].idBandejaDetalle == idBandejaDetalle) {
                            vm.listaDenuncias[x].lstEntidadDenuncia = angular.copy(response.data);
                        }
                    }
                    //console.log("lstEntidadDenuncia" + response.data);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /* function listarEntidadesCasoDenuncia(data,index){

                    console.log("index" + index);
                    var prmData = {
                                    idCaso: data.idCaso,

                    };

            CasoFactory
                        .listarEntidadCaso(prmData)
                        .then(function (response) {
                            if (response.valido) {
                                    vm.listaDenuncias[index].lstEntidadCaso = angular.copy(response.data);
                                console.log("listarEntidadCaso" + response.data);
                                /*vm.listaDenuncias= angular.copy(response.data);
                                console.log("vm.listaDenuncias" + angular.toJson(vm.listaDenuncias));
                                */
        /*vm.dataDenuncia.tipoPersona=0;*/
        /*      }
                            else {
                                toastr.error(response.mensaje);
                            }
                        })
                        .catch(function (error) {
                            toastr.error('Error al consultar');
                        })
                        .finally(function () {

                        });

        }*/
        function mostrarModalAsignarDenuncia(item) {
            /*  var prmData= {
                        idDenuncia:vm.dataDenunciaRevision.idDenuncia,

                };*/
            ngDialog.open({
                template: 'app/modules/bandeja/asignacion/asignar-especialista/asignar-especialista-dialog.html',
                controller: 'AsingnarEspecialistaDialogController',
                data: {
                    data: item
                },
                controllerAs: 'vm',
                width: '650px'
            }).closePromise.then(function(dataDialog) {
                listarBandejaAsignacionDenuncia();
                /*if (dataDialog.value.cliente) {
                    vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
                }*/
            });
        }
        //        $scope.$on('onBeforeUnload', function (e, confirmation) {
        //            console.log("pasa Detenido");
        //              confirmation.message = "All data willl be lost.";
        //              e.preventDefault();
        //          });
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

        function enviardata() {
            listarBandejaAsignacionDenuncia();
            /*BandejaFactory.enviarDatos();*/
        }
        /*declaración de metodos enlazados a la vista*/
        /*implementación de metodos*/
        function cargarCombos() {
            var codigosCombos = ['EstadoDenuncia'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
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
                        //console.log("listaDebidoANivel1" + response.data);
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
            vm.dataBandejaDetalle.codigoDenuncia = "";
            vm.dataBandejaDetalle.estadoDenuncia = 0;
            vm.dataBandejaDetalle.year = 0;
            vm.dataBandejaDetalle.month = 0;
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
            vm.dataBandejaDetalle.departamento = "";
            vm.dataBandejaDetalle.provincia = "";
            vm.dataBandejaDetalle.distrito = "";
            vm.dataBandejaDetalle.direccion = "";
            vm.listaProvincia = [];
            vm.listaDistrito = [];
        }

        function init() {
            CookiesFactory.obtenerCookie();
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                vm.usuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                vm.dataBandeja = vm.usuario.bandeja;
                //  console.log("vm.dataBandeja",vm.dataBandeja);
                vm.dataBandejaDetalle.idBandeja = vm.dataBandeja.idBandeja;
                vm.dataBandejaDetalle.tipoBandeja = 2;
                vm.dataBandejaDetalle.year = 0;
                vm.dataBandejaDetalle.month = 0;
                vm.dataBandejaDetalle.fechaRegistroDenunciaFin = "";
                vm.dataBandejaDetalle.fechaRegistroDenunciaInicio = "";
                vm.anio = (new Date).getFullYear();
                vm.dataBandejaDetalle.nombreDenunciado = '';
                for (var i = 2005; i <= vm.anio; i++) {
                    vm.listaAnios.push(i)
                }
                vm.dataBandejaDetalle.departamento = "";
                vm.dataBandejaDetalle.provincia = "";
                vm.dataBandejaDetalle.distrito = "";
                listarBandejaAsignacionDenuncia();
                listarProblematica();
                cargarCombos();
                listarDepartamento()
            }
        }
        init();
        /*fin de metodos*/
    }
})();