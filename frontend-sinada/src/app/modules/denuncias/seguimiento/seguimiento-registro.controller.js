(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('SeguimientoRegistroController', SeguimientoRegistroController);
    /* @ngInject */
    function SeguimientoRegistroController($window, API_CONFIG, $rootScope, $filter, $state, toastr, ngDialog, $scope, BandejaFactory, InformeAccionFactory, AtencionDenuncia, CasosFactory, DetalleCasoFactory, CasoFactory, MaestroFactory, DenunciaFactory, $location, AccesoFactory, UbigeoFactory, UsuarioFactory, CookiesFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        vm.dataObjSegundoNivel = {
            dataObj: {},
            idObjeto: 0
        };
        vm.validaPerfilCoordinador = 0;
        vm.dataIdEspecialistaSinada = API_CONFIG.idPerfilEspecialista;
        vm.listaTipoEntidadAtencionDenuncia = [];
        $scope.config = {
            itemsPerPage: 10,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.fechaActual = new Date();
        vm.rutaBase = API_CONFIG.url;
        vm.usuario = {};
        vm.dataBandeja = {};
        vm.dataBandejaEntidad = BandejaFactory.bandejaDenuncia();
        vm.dataSeguimiento = {
            problematica: 0,
            debidoA: 0,
            sucede: 0,
            tipoEntidad: 1,
            direcciones: 0,
            subDireccion: 0,
            tipoAmbito: 1,
            tipoAsignacion: 0
        };
        vm.listaProblematica = [];
        vm.listaDebidoA = [];
        vm.listaDondeSucede = [];
        vm.listaDirecciones = [];
        vm.listaSubDireccion = [];
        vm.listaEFAsNacional = [];
        vm.listaTipoAsignacion = [];
        vm.listaNormativas = [];
        vm.verNormativas = false;
        vm.seguimientoTabActivo = 1;
        vm.listaDenuncias = [];
        vm.denunciaPorRechazar = null;
        /*declaracion de metodos */
        vm.mostrarSeguimientoInformeDialog = mostrarSeguimientoInformeDialog;
        vm.mostrarEvaluacionRechazo = mostrarEvaluacionRechazo;
        vm.mostrarEvaluacionConAcciones = mostrarEvaluacionConAcciones;
        vm.listarBandejaEspecialistaSinada = listarBandejaEspecialistaSinada;
        vm.listarSegundoNivelXNumeroDeFila = listarSegundoNivelXNumeroDeFila;
        vm.mostrarReitero = mostrarReitero;
        vm.mostrarDerivo = mostrarDerivo;
        vm.mostrarArchivo = mostrarArchivo;
        vm.verHistorialEntidad = verHistorialEntidad;
        vm.verHistorialAccionDenuncia = verHistorialAccionDenuncia;
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
        /********************************/
        vm.listaUsuariosEspecialistas = [];
        /******************************/
        /***************************************/
        vm.confirmarDarPorAtendidaDenuncia = confirmarDarPorAtendidaDenuncia;
        vm.verFichaDenuncia = verFichaDenuncia;
        vm.irARegistroDenuncia = irARegistroDenuncia;
        vm.validarFechaPlazo = validarFechaPlazo;
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        vm.listarBandejaOrganoCompententeCompletoExcelData = listarBandejaOrganoCompententeCompletoExcelData;
        /************Variables de Ubigeo*************/
        vm.departamento = UbigeoFactory.departamento();
        vm.provincia = UbigeoFactory.provincia();
        vm.distrito = UbigeoFactory.distrito();
        vm.listaDepartamento = [];
        vm.listaProvincia = [];
        vm.listaDistrito = [];
        /***************Fin de Variables Ubigeo********************/
        /*implementacion de metodos*/
        function listarBandejaOrganoCompententeCompletoExcelData() {
            BandejaFactory.listarBandejaOrganoCompententeCompletoExcelData().then(function(response) {
                if (response.valido) {
                    $window.location.href = vm.rutaBase + 'bandeja/exportar-excel-especialista';
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarUsuariosEspecialistas() {
            var prmUsuarioFiltro = UsuarioFactory.modelUsuario();
            prmUsuarioFiltro.flagFiltroUsuario = 0;
            prmUsuarioFiltro.idPerfil = vm.dataIdEspecialistaSinada;
            UsuarioFactory.listar(angular.copy(prmUsuarioFiltro)).then(function(response) {
                if (response.valido) {
                    vm.listaUsuariosEspecialistas = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al obtener la información');
            }).finally(function() {});
        }
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
        function validarFechaPlazo(fechaPlazo) {
            var flagActivo = 0;
            vm.datePlazo = convertStringToDate(fechaPlazo);
            if (vm.fechaActual >= vm.datePlazo) {
                flagActivo = 1;
                //  console.log("flagActivo"+ flagActivo);
            }
            return flagActivo;
            // body...
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

        function irARegistroDenuncia() {
            localStorage.setItem("dataBandeja", angular.toJson(vm.dataBandeja));
            //Bandeja Procedencia 1-Asignacion 2-Especialista 3-EFA 4-Denunciante
            var bandejaProcedencia = 2;
            localStorage.setItem("bandejaProcedencia", angular.copy(bandejaProcedencia));
            //  console.log("SETEO EN EL LOCAL STORAGE");
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
                width: '650px'
            }).closePromise.then(function(dataDialog) {
                /*if (dataDialog.value.cliente) {
                    vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
                }*/
            });
        }

        function confirmarDarPorAtendidaDenuncia(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar atención',
                    Mensaje: '¿Desea dar por atendida la denuncia ' + item.codigoDenuncia + '?'
                },
                width: '380px'
            }).then(function(okValue) {
                cambiarEstadoDenuncia(item);
            });
        }

        function cambiarEstadoDenuncia(data) {
            var denuncia = {
                idDenuncia: data.idDenuncia,
                estado: 8
            };
            DenunciaFactory.actualizarEstado(angular.copy(denuncia)).then(function(response) {
                /*$state.go('spapp.home.bandeja.asignadas');*/
                toastr.success("La denuncia " + data.codigoDenuncia + " fue atendida");
            }).catch(function(error) {
                /*toastr.error('Ocurrió un error al registrar la OEFA.');*/
            }).finally(function() {
                listarBandejaEspecialistaSinada();
            });
        }

        function verHistorialAccionDenuncia(data) {
            //      console.log("pasasas");
            $state.go('spapp.home.denuncias.historial-general-denuncia');
            $rootScope.dataDenunciaHistorial = data;
        }

        function verHistorialEntidad(data) {
            //      console.log("pasasas");
            $state.go('spapp.home.denuncias.historial-seguimiento');
            $rootScope.dataHistorialEntidadSeguimiento = data;
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

        function listarBandejaEspecialistaSinada() {
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
            //  console.log("vm.dataBandejaEntidadx",vm.dataBandejaEntidad);
            BandejaFactory.listarBandejaEspecialistaSinada(vm.dataBandejaEntidad).then(function(response) {
                if (response.valido) {
                    vm.listaDenuncias = angular.copy(response.data);
                    vm.validaPerfilCoordinador = angular.copy(response.objData);
                    //          console.log("vm.listaDenunciasy" + angular.toJson(vm.listaDenuncias));
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarSegundoNivelXNumeroDeFila(data, idBandejaDetalle) {
            vm.dataObjSegundoNivel.dataObj = data;
            vm.dataObjSegundoNivel.idObjeto = idBandejaDetalle;
            var prmData = {
                idDenuncia: data.idDenuncia,
            };
            InformeAccionFactory.listarSegundoNivelBandejaEspecialista(prmData).then(function(response) {
                if (response.valido) {
                    for (var x in vm.listaDenuncias) {
                        if (vm.listaDenuncias[x].idBandejaDetalle == idBandejaDetalle) {
                            // debugger;
                            vm.listaDenuncias[x].lstEntidadDenuncia = angular.copy(response.data);
                            //              console.log("lstEntidadDenuncia" + response.data);
                        }
                    }
                    /*vm.listaDenuncias= angular.copy(response.data);
                    console.log("vm.listaDenuncias" + angular.toJson(vm.listaDenuncias));
                    */
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function mostrarReitero() {
            ngDialog.open({
                template: 'app/modules/denuncias/seguimiento/dialog/evaluacion-reitero/evaluacion-reitero-dialog.html',
                controller: 'EvaluacionReiteroDialogController',
                data: {},
                controllerAs: 'vm',
                width: '700px'
            });
        }

        function mostrarDerivo() {
            ngDialog.open({
                template: 'app/modules/denuncias/seguimiento/dialog/evaluacion-derivo/evaluacion-derivo-dialog.html',
                controller: 'EvaluacionDerivoDialogController',
                data: {},
                controllerAs: 'vm',
                width: '700px'
            });
        }

        function mostrarArchivo() {
            ngDialog.open({
                template: 'app/modules/denuncias/seguimiento/dialog/evaluacion-archivo/evaluacion-archivo-dialog.html',
                controller: 'EvaluacionArchivoDialogController',
                data: {},
                controllerAs: 'vm',
                width: '700px'
            });
        }

        function mostrarSeguimientoInformeDialog(prmData) {
            /*var data = AtencionDenuncia.model();
                data.tipoEntidadCompetente.codigoRegistro = prmData.tipoEntidadComponente;
                if(data.tipoEntidadCompetente.codigoRegistro  == 1){
                        data.direccionSupervicion.codigoRegistro = prmData.direccionSupervicion;
                        data.direccionEvaluacion.codigoRegistro = prmData.direccionEvaluacion;
                }

                if(data.tipoEntidadCompetente.codigoRegistro  == 2){
                            data.efa.idEfa = prmData.idEfa;
                }*/
            ngDialog.open({
                template: 'app/modules/denuncias/seguimiento/dialog/seguimiento-informe/seguimiento-informe-dialog.html',
                controller: 'SeguimientoInformeDialogController',
                data: {
                    prmEntidadDenuncia: prmData
                },
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                if (vm.dataObjSegundoNivel != null && vm.dataObjSegundoNivel.dataObj != null && vm.dataObjSegundoNivel.idObjeto != 0) {
                    listarSegundoNivelXNumeroDeFila(vm.dataObjSegundoNivel.dataObj, vm.dataObjSegundoNivel.idObjeto);
                }
                listarBandejaEspecialistaSinada();
            });
        }

        function mostrarEvaluacionRechazo(data) {
            //vm.denunciaPorRechazar = angular.copy(itemEntidad);
            ngDialog.open({
                template: 'app/modules/denuncias/seguimiento/dialog/seguimiento-evaluacion-rechazo/seguimiento-evaluacion-rechazo-dialog.html',
                controller: 'EvaluacionRechazoDialogController',
                data: {
                    prmBandeja: data,
                },
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                /* AQUI ES */
                //console.log('productoSeleccionado',dataDialog.value);
                if (vm.dataObjSegundoNivel != null && vm.dataObjSegundoNivel.dataObj != null && vm.dataObjSegundoNivel.idObjeto != 0) {
                    listarSegundoNivelXNumeroDeFila(vm.dataObjSegundoNivel.dataObj, vm.dataObjSegundoNivel.idObjeto);
                }
                listarBandejaEspecialistaSinada();
            });
        }

        function mostrarEvaluacionConAcciones(itemEntidad) {
            //vm.denunciaPorRechazar = angular.copy(itemEntidad);
            ngDialog.open({
                template: 'app/modules/denuncias/seguimiento/dialog/seguimiento-evaluacion-con-acciones/seguimiento-evaluacion-con-acciones-dialog.html',
                controller: 'EvaluacionConAccionesDialogController',
                data: {},
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                //console.log('productoSeleccionado',dataDialog.value);
                if (dataDialog.value.enviado) {
                    mostrarEncuestaDenunciante(itemEntidad);
                }
            });
        }

        function mostrarEncuestaDenunciante(itemEntidad) {
            //vm.denunciaPorRechazar = angular.copy(itemEntidad);
            ngDialog.open({
                template: 'app/modules/denuncias/seguimiento/dialog/seguimiento-evaluacion-con-acciones/dialog/encuesta/informe-denunciante-dialog.html',
                controller: 'EncuestaDenuncianteController',
                data: {},
                controllerAs: 'vm',
                width: '900px'
            }).closePromise.then(function(dataDialog) {
                //console.log('productoSeleccionado',dataDialog.value);
                if (dataDialog.value.enviado) {
                    toastr.success('Encuesta de satisfacción');
                }
            });
        }
        /*fin de implementacion de metodos*/
        function cargarCombos() {
            var codigosCombos = ['EstadoDenuncia', 'TipoEntidadAtencionDenuncia'];
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
                            case 'TipoEntidadAtencionDenuncia':
                                vm.listaTipoEntidadAtencionDenuncia = datosCombo;
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
            //toastr.error('buscar...');
            //console.log('buscar...');
            listarBandejaEspecialistaSinada();
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
            vm.dataBandejaEntidad.departamento = "";
            vm.dataBandejaEntidad.provincia = "";
            vm.dataBandejaEntidad.distrito = "";
            vm.dataBandejaEntidad.nombreEntidad = "";
            vm.listaProvincia = [];
            vm.listaDistrito = [];
            vm.dataBandejaEntidad.tipoEntidadAtencion = 0;
            vm.listaDebidoANivel1 = [];
            vm.listaDebidoANivel2 = [];
            vm.listaDebidoANivel3 = [];
            vm.listaZonaSucesonivel1 = [];
            vm.listaZonaSucesonivel2 = [];
            vm.listaZonaSucesonivel3 = [];
        }

        function init() {
            CookiesFactory.obtenerCookie();
            //  console.log("Bandeja Especialista Sinada")
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                vm.usuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                vm.dataBandeja = vm.usuario.bandeja;
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
                vm.dataBandejaEntidad.nombreEntidad = "";
                vm.dataBandejaEntidad.idBandeja = vm.dataBandeja.idBandeja;
                listarBandejaEspecialistaSinada();
                listarProblematica();
                cargarCombos();
                listarDepartamento();
                listarUsuariosEspecialistas();
                vm.dataBandejaEntidad.idResponsableBandeja = '0';
            }
        }

        function leerOperaciones(ref) {
            AccesoFactory.operaciones(ref, $rootScope.aplicacionSinada.opciones.idEspecialistaBandejaDenuncias).then(function(response) {
                $rootScope.operaciones = response;
                $rootScope.validarOperacion = validarOperacion;
            }).catch(function(error) {
                toastr.error(error);
            });
        }

        function validarOperacion(operacion) {
            for (var int = 0; int < $rootScope.operaciones.length; int++) {
                var oOperacion = $rootScope.operaciones[int];
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
        /*fin de controller*/
    }
})();