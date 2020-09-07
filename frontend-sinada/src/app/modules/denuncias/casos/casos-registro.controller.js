(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('CasosRegistroController', CasosRegistroController);
    /* @ngInject */
    function CasosRegistroController(toastr, ngDialog, CasosFactory, CasoEfaFactory, NormaCasoFactory, NormasFactory, MaestroFactory, DetalleCasoFactory, CasoFactory, PersonaOefaFactory, $rootScope, $location, AccesoFactory, $scope, CookiesFactory) {
        var vm = this;
        /* declaracion de variables globales */
        $scope.config = {
            itemsPerPage: 10,
            fillLastPage: true,
            current: 1
        };
        vm.flagActivaPersonaOefa = 0;
        /*        Variable Detalle Caso       */
        vm.dataCaso = {
            tipoEntidad: 2,
        };
        vm.dataPersonaOefa = PersonaOefaFactory.model();
        vm.prmDetalleCaso = DetalleCasoFactory.model();
        vm.padreDondeSucede = DetalleCasoFactory.model();
        /*              Cabecera Caso           */
        vm.prmDataCaso = CasoFactory.model();
        vm.prmDataCasoFiltro = CasoFactory.model();
        vm.prmDataCasoAsignar = CasoFactory.model();
        /*           Asignacion Caso           */
        vm.modelMaestro = MaestroFactory.model();
        vm.dataCasoOefa = CasoFactory.getOefa();
        vm.dataCasoEfa = CasoEfaFactory.model();
        /*  Lista Casos  por niveles  */
        vm.listaProblematica = [{
            idDetalleCaso: 0,
            descripcion: 'Seleccione'
        }];
        vm.listaDebidoANivel1 = [{
            idDetalleCaso: 0,
            descripcion: 'Seleccione'
        }];
        vm.listaDebidoANivel2 = [{
            idDetalleCaso: 0,
            descripcion: 'Seleccione'
        }];
        vm.listaDebidoANivel3 = [{
            idDetalleCaso: 0,
            descripcion: 'Seleccione'
        }];
        vm.listaZonaSucesonivel1 = [{
            idDetalleCaso: 0,
            descripcion: 'Seleccione'
        }];
        vm.listaZonaSucesonivel2 = [{
            idDetalleCaso: 0,
            descripcion: 'Seleccione'
        }];
        vm.listaZonaSucesonivel3 = [{
            idDetalleCaso: 0,
            descripcion: 'Seleccione'
        }];
        vm.listaCaso = [];
        vm.listaDireccionEvaluacion = [];
        vm.listaOefaCaso = [];
        vm.listaEfas = [];
        /************************************************/
        vm.dataNormaCaso = NormaCasoFactory.model();
        vm.entidad = CasosFactory.getEntidad();
        vm.oefa = CasosFactory.getOefa();
        vm.casoTabActivo = 1; //Primer Tab por defecto
        //      vm.listarEfa = listarEfa;
        vm.dataEntidadCaso = CasoEfaFactory.getEntidadCaso();
        vm.dataProblematica = CasosFactory.model();
        vm.dataDebidoA = CasosFactory.model();
        vm.dataZonaSuceso = CasosFactory.model();
        /*vm.dataCaso = {
            problematica    : 0,
            debidoA         : 0,
            sucede          : 0,
            tipoEntidad     : 0,
            direcciones     : 0,
            subDireccion    : 0,
            tipoAmbito      : 1,
            tipoGobierno    : 0,
            tipoAmbitoDistrital : 1,
            tipoAsignacion  : 0
        };*/
        vm.listaNormaCaso = [];
        vm.listaEntidadCaso = [];
        vm.nombreEntidad = "";
        vm.listaDebidoA = [];
        vm.listaDondeSucede = [];
        vm.listaDireccion = [];
        //      vm.listaSubDireccion =[];
        vm.listaEFAsNacional = [];
        vm.listaTipoAsignacion = [];
        vm.listaNormativas = [];
        vm.listaPersonasOefa = [];
        /*JLGM*/
        /******/
        vm.verNormativas = false;
        /* declaracion de metodos */
        vm.confirmarEliminarCaso = confirmarEliminarCaso;
        vm.listarUsuariosOefa = listarUsuariosOefa;
        vm.confirmarEliminarPersonaOefa = confirmarEliminarPersonaOefa;
        vm.cargarCombosOefa = cargarCombosOefa;
        vm.listarCasosGenerales = listarCasosGenerales;
        vm.registrarCaso = registrarCaso;
        vm.listarProblematica = listarProblematica;
        vm.obtenerDatoCaso = obtenerDatoCaso;
        vm.listarOefasCaso = listarOefasCaso;
        vm.listaMaestrosHijo = listaMaestrosHijo;
        vm.registrarOEfaCaso = registrarOEfaCaso;
        vm.editarCasoOefa = editarCasoOefa;
        vm.confirmarEliminarCasoOefa = confirmarEliminarCasoOefa;
        vm.listarEfaXCasos = listarEfaXCasos;
        vm.editarCasoEfa = editarCasoEfa;
        vm.confirmarEliminarCasoEfa = confirmarEliminarCasoEfa;
        vm.confirmarEliminarNormaCaso = confirmarEliminarNormaCaso;
        vm.agregarNormativa = agregarNormativa;
        vm.mostrarCasosDebidoA = mostrarCasosDebidoA;
        vm.mostrarCasosDondeSucede = mostrarCasosDondeSucede;
        vm.mostrarCasosProblematicaDialog = mostrarCasosProblematicaDialog;
        vm.mostrarAgregarNormativaDialog = mostrarAgregarNormativaDialog;
        vm.mostrarAgregarEfaDialog = mostrarAgregarEfaDialog;
        vm.listarNormaCaso = listarNormaCaso;
        vm.listarCasoRegistrados = listarCasoRegistrados;
        vm.limpiarGenerico = limpiarGenerico;
        vm.listarNormaCasoOefa = listarNormaCasoOefa;
        vm.cancelarAgregarNorma = cancelarAgregarNorma;
        vm.mostrarListaUsuariosSinada = mostrarListaUsuariosSinada;
        vm.cancelar = cancelar;
        /*vm.listarZonaSuceso = listarZonaSuceso;*/
        /*vm.listarEntidadCasos=listarEntidadCasos;*/
        /* implementacion de metodos */
        /*************************************************************************************/
        function cancelar() {
            vm.prmDataCasoAsignar = CasoFactory.model();
            vm.listaDireccionEvaluacion = [];
            vm.listaEfas = [];
            vm.casoTabActivo = 1;
            vm.verNormativas = false;
        }

        function mostrarListaUsuariosSinada() {
            /*  vm.dataCasoEfa.caso =   vm.prmDataCasoAsignar;*/
            ngDialog.open({
                template: 'app/modules/denuncias/casos/dialog/lista-usuarios/lista-usuario-dialog.html',
                controller: 'ListaUsuarioDialogController',
                data: {
                    dataCaso: vm.dataCasoOefa,
                },
                controllerAs: 'vm',
                width: '700px',
                preCloseCallback: function() {
                    /*listarEntidadCasos();*/
                }
            }).closePromise.then(function(dataDialog) {
                listarUsuariosOefa();
                //console.log('productoSeleccionado',dataDialog.value);
                /* listarEfaXCasos();*/
            });
        }
        /***************************************************************************/
        function confirmarEliminarCaso(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar el caso Nº ' + item.numeroCaso + ' y todos sus datos asociados?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarCaso(item);
            });
        }

        function eliminarCaso(item) {
            var data = {
                idCaso: item.idCaso
            };
            CasoFactory.eliminar(angular.copy(data)).then(function(response) {
                if (response.valido) {
                    //                  vm.listarContacto();
                    //                  vm.listarTitular();
                    listarCasoRegistrados();
                    toastr.success(response.mensaje);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo eliminar a la entidad asignada a este caso');
            });
        }
        /************************* Lista Usuarios de Oefa ***********************/
        function listarUsuariosOefa() {
            vm.flagActivaPersonaOefa = 1;
            vm.dataPersonaOefa.direccion.codigoRegistro = vm.dataCasoOefa.direccionSupervision.codigoRegistro;
            vm.dataPersonaOefa.subDireccion.codigoRegistro = vm.dataCasoOefa.direccionEvaluacion.codigoRegistro;
            PersonaOefaFactory.listar(vm.dataPersonaOefa).then(function(response) {
                if (response.valido) {
                    vm.listaPersonasOefa = response.data;
                    //      console.log("vm.listaPersonasOefa"+vm.listaPersonasOefa);
                    /* vm.dataDenuncia.tipoPersona=0; */
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function confirmarEliminarPersonaOefa(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar a ' + item.persona.nombreCompleto + '?. Recuerde que al eliminar el usuario del órgano de línea podría afectar a los demás casos que tienen asignado la misma Oefa.'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarPersonaOefa(item);
            });
        }

        function eliminarPersonaOefa(item) {
            var data = {
                idPersonaOefa: item.idPersonaOefa
            };
            PersonaOefaFactory.eliminar(angular.copy(data)).then(function(response) {
                if (response.valido) {
                    //                  vm.listarContacto();
                    //                  vm.listarTitular();
                    listarUsuariosOefa();
                    toastr.success(response.mensaje);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo eliminar el usuario del OEFA.');
            });
        }
        /**********************************************************************/
        /** ******************************EFA************************************** */
        function obtenerDatoCaso(item) {
            vm.dataCasoOefa = angular.copy(CasoFactory.getOefa());
            vm.prmDataCasoAsignar = angular.copy(item);
            vm.casoTabActivo = 2;
            listarOefasCaso();
            listarEfaXCasos();
        }
        /****************************  Problematica  *****************************/
        function obtenerListaSeleccione(lstDataMaestra) {
            var listaMaestra = [{
                idDetalleCaso: 0,
                descripcion: 'Seleccione'
            }];
            for (var x in lstDataMaestra) {
                listaMaestra.push(lstDataMaestra[x]);
            }
            return listaMaestra;
        }

        function listarProblematica() {
            var dataProblematica = {
                tipoCaso: 1,
                codigoPadre: 0,
                tipoNivel: 0
            };
            DetalleCasoFactory.listar(dataProblematica).then(function(response) {
                if (response.valido) {
                    var dataObj = obtenerListaSeleccione(response.data);
                    vm.listaProblematica = angular.copy(dataObj);
                    /*vm.listaProblematica = response.data;
                    console.log("vm.listaProblematica"+vm.listaProblematica);*/
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
                vm.listaProblematica = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaDebidoANivel1 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaDebidoANivel2 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaDebidoANivel3 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaZonaSucesonivel1 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaZonaSucesonivel2 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaZonaSucesonivel3 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                /*vm.listaDebidoANivel1=[];
                vm.listaDebidoANivel2=[];
                vm.listaDebidoANivel3=[];
                vm.listaZonaSucesonivel1=[];
                vm.listaZonaSucesonivel2=[];
                vm.listaZonaSucesonivel3=[];*/
                listarProblematica();
            }
            if (tipo == 2 && nivel == 1) {
                vm.prmDataCaso.debidoA1.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel2 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaDebidoANivel3 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
            }
            if (tipo == 2 && nivel == 2) {
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel3 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
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
                vm.listaZonaSucesonivel2 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
                vm.listaZonaSucesonivel3 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
            }
            if (tipo == 3 && nivel == 2) {
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel3 = [{
                    idDetalleCaso: 0,
                    descripcion: 'Seleccione'
                }];
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
                    var dataObj = obtenerListaSeleccione(response.data);
                    if (tipoCaso == 2 && nivel == 1) {
                        vm.listaDebidoANivel1 = angular.copy(dataObj);
                        limpiarGenerico(tipoCaso, nivel);
                    }
                    if (tipoCaso == 2 && nivel == 2) {
                        limpiarGenerico(tipoCaso, nivel);
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel2 = angular.copy(dataObj);
                            limpiarGenerico(tipoCaso, nivel);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 2 && nivel == 3) {
                        limpiarGenerico(tipoCaso, nivel);
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel3 = angular.copy(dataObj);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 3 && nivel == 1) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                        vm.listaZonaSucesonivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 2) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaZonaSucesonivel2 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 3) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaZonaSucesonivel3 = angular.copy(dataObj);
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
        function mostrarCasosProblematicaDialog() {
            ngDialog.open({
                template: 'app/modules/denuncias/casos/dialog/casos-problematica/casos-problematica-dialog.html',
                controller: 'CasoProblematicaDialogController',
                data: {
                    idProblematica: vm.prmDataCaso.problematica.idDetalleCaso,
                },
                controllerAs: 'vm',
                width: '700px',
                preCloseCallback: function() {
                    listarProblematica();
                    vm.prmDataCaso.problematica.idDetalleCaso = 0;
                    listarCasoRegistrados();
                }
            });
        }
        /** ******************************************************************************** */
        /** ******************************DEBIDO A:************************************** */
        function mostrarCasosDebidoA(nivel, codigoPadre, codigoDebidoA) {
            var data = vm.prmDetalleCaso;
            data.idDetalleCaso = codigoDebidoA;
            data.tipoCaso = 2;
            data.codigoPadre = codigoPadre;
            data.tipoNivel = nivel;
            ngDialog.open({
                template: 'app/modules/denuncias/casos/dialog/casos-debido-a/casos-debido-a-dialog.html',
                controller: 'CasoDebidoADialogController',
                data: {
                    debidoA: data,
                },
                controllerAs: 'vm',
                width: '700px',
                preCloseCallback: function() {
                    listarCasosGenerales(2, codigoPadre, nivel);
                    listarCasoRegistrados();
                }
            });
        }
        /** ******************************************************************************** */
        /** ******************************DONDE SUCEDE?************************************** */
        function mostrarCasosDondeSucede(nivel, codigoPadre, codigoDebidoA) {
            var data = vm.prmDetalleCaso;
            data.idDetalleCaso = codigoDebidoA;
            data.tipoCaso = 3;
            data.codigoPadre = codigoPadre;
            data.tipoNivel = nivel;
            ngDialog.open({
                template: 'app/modules/denuncias/casos/dialog/casos-donde-sucede/casos-donde-sucede-dialog.html',
                controller: 'CasoDondeSucedeDialogController',
                data: {
                    zonaSuceso: data,
                },
                controllerAs: 'vm',
                width: '700px',
                preCloseCallback: function() {
                    listarCasosGenerales(3, codigoPadre, nivel);
                    listarCasoRegistrados();
                }
            });
        }
        /****************************************************************************************/
        /***********************************************************************************************/
        function registrarCaso() {
            CasoFactory.registrar(angular.copy(vm.prmDataCaso)).then(function(response) {
                if (response.valido) {
                    vm.prmDataCaso.idCaso = response.data;
                    toastr.success("Se registró el caso número : " + vm.prmDataCaso.idCaso);
                    vm.prmDataCaso = CasoFactory.model();
                    limpiarGenerico(0, 0);
                    listarCasoRegistrados();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar al OEFA');
            });
        }
        /***************************************************************************************************/
        /*****************************************************************************************************/
        function listarCasoRegistrados() {
            vm.prmDataCasoFiltro = vm.prmDataCaso;
            CasoFactory.listar(angular.copy(vm.prmDataCasoFiltro)).then(function(response) {
                vm.listaCaso = [];
                if (response.valido) {
                   // vm.listaCaso = angular.copy(response.data);
                    vm.listaCaso = response.data;
					//      console.log("vm.vm.listaCaso" +vm.listaCaso);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {});
        }
        /*********************************************************************************************************/
        /************************************************/
        function listarOefasCaso() {
            var dataEntidadCaso = {
                caso: vm.prmDataCasoAsignar,
            }
            //  console.log(dataEntidadCaso);
            CasoFactory.listarCasoOefa(dataEntidadCaso).then(function(response) {
                vm.listaOefaCaso = [];
                if (response.valido) {
                    vm.listaOefaCaso = angular.copy(response.data);
                    console.log("vm.listaOefaCaso" + vm.listaOefaCaso);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {});
        }
        /*************************************************/
        function registrarOEfaCaso() {
            vm.dataCasoOefa.caso.idCaso = vm.prmDataCasoAsignar.idCaso;
            vm.dataCasoOefa.estado.codigoRegistro = 1;
            if (vm.dataCasoOefa.direccionSupervision.codigoRegistro != 0 && vm.dataCasoOefa.direccionEvaluacion.codigoRegistro != 0) {
                CasoFactory.registrarCasoOefa(vm.dataCasoOefa).then(function(response) {
                    if (response.valido) {
                        vm.dataCasoOefa.idCasoOefa = response.data;
                        listarOefasCaso();
                        toastr.success("Se registró OEFA al caso");
                    } else {
                        toastr.info(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error('Ocurrió un error al registrar el OEFA.');
                });
            } else {
                toastr.warning('Debe seleccionar una dirección para agregarla al caso');
            }
        }
        /***************************************************************/
        /*******************************************************************/
        function listaMaestrosHijo() {
            vm.modelMaestro.codigoMaestro = 'DireccionEvaluacion';
            vm.modelMaestro.codigoRegistro = vm.dataCasoOefa.direccionSupervision.codigoRegistro;
            //     console.log("vm.modelMaestro "+vm.modelMaestro);
            MaestroFactory.listarHijos(vm.modelMaestro).then(function(response) {
                //  console.log('response cargarCombos',response);
                if (response != null && response.valido) {
                    vm.listaDireccionEvaluacion = angular.copy(response.data);
                    //                  for(var x in response.data)
                    //                  {
                    //                      var tipoCombo = response.data[x].Key;
                    //                      var datosCombo = response.data[x].Value;
                    //                      switch(tipoCombo)
                    //                      {
                    //                          case 'DireccionSupervision' : vm.listaDireccion = datosCombo; break;
                    //                          default : break;
                    //                      }
                    //                  }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }
        /********************************************************************/
        /************************************************************************/
        function editarCasoOefa(item) {
            vm.dataCasoOefa = item;
            /*vm.oefa.idCasoOefa            = item.idCasoEntidad;
            vm.dataCasoOefa.tipoAsignacion      = item.tipoAsignacion;*/
            if (vm.dataCasoOefa.tipoAsignacion.codigoRegistro != 0) {
                CasosFactory.actualizarCasoOefa(vm.dataCasoOefa).then(function(response) {
                    if (response.valido) {
                        listarOefasCaso();
                        toastr.success(response.mensaje);
                        vm.dataCasoOefa = CasoFactory.getOefa();
                    } else {
                        toastr.error(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error(response.mensaje);
                });
            } else {
                toastr.warning("Seleccione un ítem");
            }
        }
        /****************************************************************************/
        /*******************************************************************************/
        function confirmarEliminarCasoOefa(item) {
            //console.log(item.direccionSupervision.descripcion);
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar a ' + item.direccionSupervision.descripcion + ' y todos sus datos asociados?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarCasoOefa(item);
            });
        }
        /*********************************************************************************/
        /************************************************************************************/
        function eliminarCasoOefa(item) {
            var dataCasoOefa = {
                idCasoOefa: item.idCasoOefa
            };
            CasoFactory.eliminarCasoOefa(angular.copy(dataCasoOefa)).then(function(response) {
                if (response.valido) {
                    //                  vm.listarContacto();
                    //                  vm.listarTitular();
                    listarOefasCaso();
                    toastr.success(response.mensaje);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo eliminar a la entidad asignada a este caso');
            });
        }
        /*************************************************************************************/
        function mostrarAgregarEfaDialog() {
            vm.dataCasoEfa.caso = vm.prmDataCasoAsignar;
            ngDialog.open({
                template: 'app/modules/denuncias/casos/dialog/agregar-efa/agregar-efa-dialog.html',
                controller: 'AgregarEfaDialogController',
                data: {
                    dataCaso: vm.dataCasoEfa,
                },
                controllerAs: 'vm',
                width: '700px',
                preCloseCallback: function() {
                    /*listarEntidadCasos();*/
                }
            }).closePromise.then(function(dataDialog) {
                //console.log('productoSeleccionado',dataDialog.value);
                listarEfaXCasos();
            });
        }
        /***************************************************************************/
        /************************** Lista Efa X caso *******************************/
        function listarEfaXCasos() {
            vm.listaEfas =[];
            vm.dataCasoEfa.caso.idCaso = vm.prmDataCasoAsignar.idCaso;
            CasoEfaFactory.listarEfasXCaso(vm.dataCasoEfa).then(function(response) {
                vm.listaEfas = [];
                if (response.valido) {
                    vm.listaEfas = angular.copy(response.data);
                    //      console.log("vm.listaEfas" +vm.listaEfas);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {});
        }
        /**************************************************************************/
        function editarCasoEfa(item) {
            vm.dataCasoEfa = item;
            //  console.log(vm.dataCasoEfa);
            if (vm.dataCasoEfa.tipoAsignacion.codigoRegistro != 0) {
                CasoEfaFactory.actualizar(vm.dataCasoEfa).then(function(response) {
                    if (response.valido) {
                        /*listarEntidadCasos();*/
                        listarEfaXCasos();
                        toastr.success(response.mensaje);
                    } else {
                        toastr.error(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error(response.mensaje);
                });
            } else {
                toastr.warning("Seleccione un ítem");
            }
        }
        /********************************************************************************/
        /*******************************************************************************/
        function confirmarEliminarCasoEfa(item) {
            console.log(item);
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar a ' + item.efa.nombre + ' y todos sus datos asociados?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarCasoEfa(item);
            });
        }
        /*********************************************************************************/
        /************************************************************************************/
        function eliminarCasoEfa(item) {
            var dataCasoOefa = item;
            CasoEfaFactory.eliminar(angular.copy(dataCasoOefa)).then(function(response) {
                if (response.valido) {
                    //                  vm.listarContacto();
                    //                  vm.listarTitular();
                    listarEfaXCasos();
                    toastr.success(response.mensaje);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo eliminar a la entidad asignada a este caso');
            });
        }
        /*************************************************************************************/
        /** ******************************************************************************** */
        /*
               
               function listarZonaSuceso()
               {
                   
                    vm.dataCaso.sucede              = 0;
                    vm.dataZonaSuceso.tipoCaso      = 3;
                    vm.dataZonaSuceso.codigoPadre   = vm.dataCaso.debidoA;
                    console.log("listarZonaSuceso",vm.dataZonaSuceso);
                    console.log("dataCaso",vm.dataCaso);
                    CasosFactory
                    .listar(vm.dataZonaSuceso)
                    .then(function (response) 
                    {
                        if (response.valido) 
                        {
                            vm.listaDondeSucede= response.data;
                            
                    
                        }
                        else 
                        {
                            toastr.error(response.mensaje);
                        }
                    })
                    .catch(function (error) 
                    {
                        toastr.error('Error al consultar');
                    })
                    .finally(function () 
                    {
                
                    });

        }*/
        /** ******************************************************************************** */
        function cargarCombosOefa() {
            var codigosCombos = ['DireccionSupervision', 'TipoAsignacion'];
            MaestroFactory.buscarMaestros(codigosCombos).then(function(response) {
                //      console.log('response cargarCombos',response);
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'DireccionSupervision':
                                vm.listaDireccion = datosCombo;
                                break;
                            case 'TipoAsignacion':
                                vm.listaTipoAsignacion = datosCombo;
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
        /** ******************************************************************************** */
        /** ****************************NORMATIVA******************************************* */
        function confirmarEliminarNormaCaso(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar a ' + item.contenidoNorma.nombreTipoDispositivo + ' y todos sus datos asociados?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarNormaCaso(item);
            });
        }

        function eliminarNormaCaso(item) {
            vm.dataNormaCaso.idNormaCaso = item.idNormaCaso;
            NormaCasoFactory.eliminar(vm.dataNormaCaso).then(function(response) {
                if (response.valido) {
                    listarNormaCaso();
                    toastr.success(response.mensaje);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo eliminar a la norma asignada a esta entidad');
            });
        }

        function mostrarAgregarNormativaDialog() {
            ngDialog.open({
                template: 'app/modules/denuncias/casos/dialog/agregar-normativa/agregar-normativa-dialog.html',
                controller: 'AgregarNormativaDialogController',
                data: {
                    normaCaso: vm.dataNormaCaso,
                },
                controllerAs: 'vm',
                width: '700px',
                preCloseCallback: function() {
                    if (vm.dataNormaCaso.tipoNormaCaso.codigoRegistro == 2) {
                        listarNormaCaso();
                    }
                    if (vm.dataNormaCaso.tipoNormaCaso.codigoRegistro == 1) {
                        mostrarNormaCasoOefa();
                    }
                }
            }).closePromise.then(function(dataDialog) {
                //console.log('productoSeleccionado',dataDialog.value);
                //              listarNormaCaso();
            });
        }

        function listarNormaCaso() {
            NormaCasoFactory.listar(angular.copy(vm.dataNormaCaso)).then(function(response) {
                if (response.valido) {
                    vm.listaNormaCaso = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener la información');
            });
        }

        function cancelarAgregarNorma() {
            vm.casoTabActivo = 2;
            vm.verNormativas = false;
            vm.listaNormaCaso = [];
            vm.dataNormaCaso = NormaCasoFactory.model();
        }

        function listarNormaCasoOefa(data) {
            vm.casoTabActivo = 3;
            vm.verNormativas = true;
            vm.nombreEntidad = data.direccionSupervision.descripcion + ' ' + data.direccionEvaluacion.descripcion;
            vm.dataNormaCaso = angular.copy(NormaCasoFactory.model());
            vm.dataNormaCaso.idCasoOefa = angular.copy(data.idCasoOefa);
            vm.dataNormaCaso.tipoNormaCaso.codigoRegistro = angular.copy(1);
            mostrarNormaCasoOefa();
        }

        function mostrarNormaCasoOefa() {
            NormaCasoFactory.listar(angular.copy(vm.dataNormaCaso)).then(function(response) {
                if (response.valido) {
                    vm.listaNormaCaso = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener la información');
            });
        }

        function agregarNormativa(item) {
            vm.casoTabActivo = 3;
            vm.verNormativas = true;
            vm.nombreEntidad = item.efa.nombre;
            vm.dataNormaCaso.idCasoEfa = item.idCasoEfa;
            vm.dataNormaCaso.tipoNormaCaso.codigoRegistro = 2;
            vm.dataNormaCaso.idCasoEfa = vm.dataNormaCaso.idCasoEfa;
            /*console.log("vm.dataNormaCaso.idCasoEfa"+ vm.dataNormaCaso.idCasoEfa);*/
            listarNormaCaso();
        }
        /** ******************************************************************************** */
        /* fin de implementacion de metodos */
        function init() {
            CookiesFactory.obtenerCookie();
            cargarCombosOefa();
            listarProblematica();
            listarCasoRegistrados();
        }

        function leerOperaciones(ref) {
            AccesoFactory.operaciones(ref, $rootScope.aplicacionSinada.opciones.idCasosDenuncias).then(function(response) {
                $rootScope.operaciones = response; //angular.toJson(response);
                //$log.log('operaciones x '+$rootScope.operaciones);                
                $rootScope.validarOperacion = validarOperacion;
            }).catch(function(error) {
                toastr.error('Hubo en error al obtener las operaciones');
                //toastr.error(error);
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
        /*  $rootScope.ref_tmp=$location.search().ref;*/
        if (angular.isDefined($rootScope.ref_tmp)) {
            leerOperaciones($rootScope.ref_tmp);
            $rootScope.ref = $rootScope.ref_tmp;
        } else {
            leerOperaciones($rootScope.ref);
        }
        init();
        /* fin de controller */
    }
})();