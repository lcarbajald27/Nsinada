(function() {
    'use strict';
    angular.module('spapp.maestro').controller('MaestroListadoController', MaestroListadoController);
    /* @ngInject */
    function MaestroListadoController($scope, ngDialog, toastr, $rootScope, MaestroFactory, CookiesFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.maestroCabecera = MaestroFactory.model();
        vm.dataRegistrar = '';
        vm.activaRegistroHijo = '0';
        vm.lstMaestros = [];
        vm.lstMaestrosItem = [];
        vm.dataMaestros = MaestroFactory.model();
        vm.listaRegistroHijo = [];
        vm.dataRegistroHijo = MaestroFactory.model();
        /*variables de paginacion*/
        vm.opcionesPaginacion = {
            itemsPorPag: 10,
            cantidades: [10, 25, 50, 100]
        };
        //funciones//
        vm.agregarItem = agregarItem;
        vm.listarItemMaestros = listarItemMaestros;
        /*declaración de metodos enlazados a la vista*/
        vm.confirmarEliminacion = confirmarEliminacion;
        vm.agregarRegistroHijo = agregarRegistroHijo;
        vm.agregarItemHijo = agregarItemHijo;
        vm.cancelarRegistroHijo = cancelarRegistroHijo;
        vm.confirmarEliminacionRegistroHijo = confirmarEliminacionRegistroHijo;
        vm.buscarMaestroPorId = buscarMaestroPorId;
        /*implementación de metodos*/
        function agregarRegistroHijo(itemMaestroGeneral) {
            vm.dataRegistroHijo.codigoRegistroPadre = itemMaestroGeneral.codigoRegistro;
            vm.dataRegistroHijo.codigoMaestro = itemMaestroGeneral.valorGeneral04;
            vm.dataRegistroHijo.codigoNivel = 2;
            vm.activaRegistroHijo = '1';
            vm.dataRegistrar = itemMaestroGeneral.descripcion;
            listarRegistroHijo();
        }

        function cancelarRegistroHijo() {
            vm.activaRegistroHijo = '0';
        }

        function agregarItemHijo(itemRegistroHijo) {
            if (itemRegistroHijo != null) {
                /*$rootScope.itemMaestroGeneral = angular.copy(itemMaestroGeneral);*/
            } else {
                $rootScope.itemRegistroHijo = vm.dataRegistroHijo;
                $rootScope.activaRegistroHijo = vm.activaRegistroHijo;
            }
            ngDialog.open({
                template: 'app/modules/maestro/dialog/maestro-data-dialog.html',
                controller: 'MaestroDataDialogController',
                data: {},
                controllerAs: 'vm',
                width: '50%'
            }).closePromise.then(function(dataDialog) {
                listarRegistroHijo();
                $rootScope.itemRegistroHijo = null;
                $rootScope.activaRegistroHijo = '0';
                /*if (dataDialog.value.cliente) {
                    vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
                }*/
            });
        }

        function agregarItem(itemMaestroGeneral) {
            $rootScope.listaMaestros = angular.copy(vm.lstMaestrosItem);
            //  console.log("itemMaestroGeneral"+ itemMaestroGeneral);
            if (itemMaestroGeneral != null) {
                $rootScope.itemMaestroGeneral = angular.copy(itemMaestroGeneral);
            } else {
                $rootScope.itemMaestroGeneral = vm.dataMaestros;
            }
            ngDialog.open({
                template: 'app/modules/maestro/dialog/maestro-data-dialog.html',
                controller: 'MaestroDataDialogController',
                data: {
                    itemMaestroGeneral: itemMaestroGeneral
                },
                controllerAs: 'vm',
                width: '50%'
            }).closePromise.then(function(dataDialog) {
                listarItemMaestros();
                if (vm.activaRegistroHijo == '1') {
                    listarRegistroHijo();
                }
                /*if (dataDialog.value.cliente) {
                    vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
                }*/
            });
        }

        function confirmarEliminacionRegistroHijo(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar el registro: ' + item.descripcion + '?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarRegistroHijo(item.idMaestro);
            });
        }

        function confirmarEliminacion(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar el registro: ' + item.descripcion + '?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminar(item.idMaestro);
            });
        }

        function eliminarRegistroHijo(idMaestro) {
            var temp = {
                idMaestro: idMaestro
            };
            MaestroFactory.eliminar(angular.copy(temp)).then(function(response) {
                if (response.valido) {
                    //  console.log("sdadasasd---WW");
                    toastr.success(response.mensaje);
                    listarRegistroHijo();
                } else {
                    toastr.error(response.mensaje)
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al eliminar el producto');
            });
        }

        function eliminar(idMaestro) {
            var temp = {
                idMaestro: idMaestro
            };
            MaestroFactory.eliminar(angular.copy(temp)).then(function(response) {
                if (response.valido) {
                    toastr.success(response.mensaje);
                    listarItemMaestros();
                } else {
                    toastr.error(response.mensaje)
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al eliminar el producto');
            });
        }

        function buscarMaestroPorId() {
            debugger;
            for (var x = 0; x < vm.lstMaestros.length; x++) {
                var strMaestro = vm.lstMaestros[x].codigoMaestro;
                if (strMaestro == vm.dataMaestros.codigoMaestro) {
                    vm.maestroCabecera = vm.lstMaestros[x];
                }
            }
        }

        function listarMaestros() {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            MaestroFactory.listar().then(function(response) {
                if (response != null && response.valido) {
                    vm.lstMaestros = angular.copy(response.data);
                    //console.log("vm.lstMaestros" + angular.toJson(vm.lstMaestros));
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function listarRegistroHijo() {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            MaestroFactory.buscarRegistroHijo(vm.dataRegistroHijo).then(function(response) {
                if (response != null && response.valido) {
                    vm.listaRegistroHijo = angular.copy(response.data);
                    //  console.log("vm.listaRegistroHijo" + angular.toJson(vm.listaRegistroHijo));
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function listarItemMaestros() {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            //  console.log("vm.dataMaestros.codigoMaestro" + vm.dataMaestros.codigoMaestro);
            MaestroFactory.buscarXCodigoMaestro(angular.copy(vm.dataMaestros)).then(function(response) {
                if (response != null && response.valido) {
                    vm.lstMaestrosItem = response.data;
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function cargarCombos(codigosCombos) {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case '':
                                vm.lstMaestros = datosCombo;
                                break;
                            case 'TipoSituacionEvento':
                                vm.listaSituacionEvento = datosCombo;
                                break;
                            default:
                                lstMaestrosItem = datosCombo;
                                break;
                        }
                    }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function init() {
            /*var codigos=[''];
            cargarCombos(codigos);*/
            CookiesFactory.obtenerCookie();
            listarMaestros();
        }
        init();
        /*fin de metodos*/
    }
})();