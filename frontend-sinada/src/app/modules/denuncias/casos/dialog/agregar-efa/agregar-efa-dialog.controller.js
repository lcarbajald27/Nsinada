(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('AgregarEfaDialogController', AgregarEfaDialogController);
    /** @ngInject */
    function AgregarEfaDialogController(toastr, $scope, ngDialog, CasoFactory, CasoEfaFactory, MaestroFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        vm.flagActivoAceptarTodos = 0;
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.dataCasoEfa = CasoEfaFactory.model();
        vm.entidad = CasoEfaFactory.getEfa();
        vm.entidadBusqueda = CasoEfaFactory.getEfa();
        vm.listaEfas = [];
        vm.listaTipoAsignacion = [];
        vm.listaTipoNivel = [];
        vm.listaTipoGobierno = [];
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.registrar = registrar;
        vm.listarEfa = listarEfa;
        vm.registrarEfasCasoXLista = registrarEfasCasoXLista;
        vm.confirmarAgregarEfa = confirmarAgregarEfa;
        /*implementación de metodos*/
        function confirmarAgregarEfa(tipoAsig) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar agregar EFAS',
                    Mensaje: '¿Está seguro de asignar todas las EFAS listadas al caso?'
                },
                width: '380px'
            }).then(function(okValue) {
                registrarEfasCasoXLista(tipoAsig);
            });
        }

        function registrar(efa) {
            //	console.log("efa" + efa);
            vm.dataCasoEfa.efa.idEfa = efa.idEfa;
            vm.dataCasoEfa.tipoAsignacion.codigoRegistro = efa.idUbigeo;
            if (vm.dataCasoEfa.tipoAsignacion != 0) {
                CasoEfaFactory.registrar(vm.dataCasoEfa).then(function(response) {
                    if (response.valido) {
                        //vm.dataPedido.idPedido = response.data;
                        /*vm.dataCasoEfa.idCasoEfa = response.data;*/
                        toastr.success(response.mensaje);
                        /*ngDialog.close();*/
                        listarEfa();
                    } else {
                        toastr.error(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error(response.mensaje);
                });
            } else {
                toastr.warning("Debe seleccionar un tipo de asignación para agregar este ítem");
            }
        }

        function registrarEfasCasoXLista(tipoAsignacion) {
            vm.dataCasoEfa.lstEfa = vm.listaEfas;
            vm.dataCasoEfa.tipoAsignacion.codigoRegistro = tipoAsignacion;
            CasoEfaFactory.registrarListaEfa(angular.copy(vm.dataCasoEfa)).then(function(response) {
                if (response.valido) {
                    //vm.dataPedido.idPedido = response.data;
                    toastr.success(response.mensaje);
                    /***************************************************************/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error(response.mensaje);
            }).finally(function() {
                listarEfa();
            });
        }

        function listarEfa() {
            //  			('uppercase')();
            vm.entidadBusqueda.idCaso = vm.dataCasoEfa.caso.idCaso;
            CasoFactory.listarEfas(vm.entidadBusqueda).then(function(response) {
                vm.listaEfas = [];
                if (response.valido) {
                    for (var x in response.data) {
                        vm.entidad = response.data[x].Value;
                        vm.listaEfas.push(angular.copy(vm.entidad));
                    }
                    vm.entidad = CasoEfaFactory.getEfa();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {});
        }

        function cargarCombos() {
            var codigosCombos = ['TipoNivelSinada', 'TipoGobiernoSinada', 'TipoAsignacion'];
            MaestroFactory.buscarMaestros(codigosCombos).then(function(response) {
                //		console.log('response cargarCombos ---- ',response);
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoNivelSinada':
                                vm.listaTipoNivel = datosCombo;
                                break;
                            case 'TipoGobiernoSinada':
                                vm.listaTipoGobierno = datosCombo;
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

        function cancelar() {
            ngDialog.close();
        }
        /*fin de metodos*/
        function init() {
            /*vm.entidadBusqueda.tipoNivel ='0';*/
            if (angular.isDefined($scope.ngDialogData)) {
                vm.dataCasoEfa = angular.copy($scope.ngDialogData.dataCaso);
            }
            listarEfa();
            cargarCombos();
            //	console.log("vm.entidadBusqueda.tipoNivel" + vm.entidadBusqueda.tipoNivel);
            // body...
        }
        init();
        /*fin de controller*/
    }
})();