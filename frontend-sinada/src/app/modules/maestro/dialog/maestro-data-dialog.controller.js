(function() {
    'use strict';
    angular.module('spapp.maestro').controller('MaestroDataDialogController', MaestroDataDialogController);

    function MaestroDataDialogController($scope, toastr, ngDialog, $log, $rootScope, MaestroFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.lstMaestrosItem = [];
        vm.esNuevoRegistro = true;
        vm.prmMaestro = MaestroFactory.model();
        vm.dataFechaNoLaborable;
        /*variables de paginacion*/
        vm.opcionesPaginacion = {
            itemsPorPag: 5
        };
        /*declaración de metodos enlazados a la vista*/
        vm.guardar = guardar;
        vm.confirmarEliminar = confirmarEliminar;
        vm.obtener = obtener;
        vm.cancelar = cancelar;
        vm.limpiar = limpiar;
        vm.obtenerDatosFecha = obtenerDatosFecha;
        /*implementación de metodos*/
        function obtenerDatosFecha() {
            var fecha = vm.dataFechaNoLaborable;
            var dia = fecha.getDate();
            var mes = fecha.getMonth();
            var anio = fecha.getFullYear();
            var nombreMes = "";
            switch (mes) {
                case 0:
                    nombreMes = "Enero";
                    break;
                case 1:
                    nombreMes = "Febrero";
                    break;
                case 2:
                    nombreMes = "Marzo";
                    break;
                case 3:
                    nombreMes = "Abril";
                    break;
                case 4:
                    nombreMes = "Mayo";
                    break;
                case 5:
                    nombreMes = "Junio";
                    break;
                case 6:
                    nombreMes = "Julio";
                case 7:
                    nombreMes = "Agosto";
                    break;
                case 8:
                    nombreMes = "Septiembre";
                    break;
                case 9:
                    nombreMes = "Octubre";
                    break;
                case 10:
                    nombreMes = "Noviembre";
                    break;
                case 11:
                    nombreMes = "Diciembre";
                    break;
            }
            vm.prmMaestro.valorGeneral01 = dia + "";
            vm.prmMaestro.valorGeneral02 = mes + "";
            vm.prmMaestro.valorGeneral04 = anio + "";
            vm.prmMaestro.descripcion = dia + " de " + nombreMes + " del " + anio;
        }

        function cancelar() {
            ngDialog.close();
        }

        function obtener(item) {
            vm.prmMaestro = angular.copy(item);
        }

        function limpiar() {
            vm.prmMaestro = MaestroFactory.model();
            $rootScope.itemMaestroGeneral = null;
            /*vm.prmMaestro.codigoTabla= angular.copy($rootScope.itemMaestroGeneral.codigoTabla);*/
        }

        function cargarCombos(codigosCombos) {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            default: vm.lstMaestrosItem = datosCombo;
                            break;
                        }
                    }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos.');
            });
        }

        function validarMaestro() {
            if (angular.isDefined($rootScope.listaMaestros)) {
                var lista = angular.copy($rootScope.listaMaestros);
                for (var i = 0; i < lista.length; i++) {
                    if (lista[i].descripcion.trim().toUpperCase() == vm.prmMaestro.descripcion.trim().toUpperCase()) {
                        return i;
                    }
                }
            }
            return -1;
        }

        function guardar() {
            if (validarMaestro() != -1) {
                toastr.info('El ítem ya se encuentra registrado');
                return;
            }
            if (vm.prmMaestro.idMaestro == 0) {
                registrar();
            } else {
                actualizar();
            }
        }

        function confirmarGuardar() {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar guardar',
                    Mensaje: '¿Está seguro que desea guardar?'
                },
                width: '380px'
            }).then(function(okValue) {
                registrar();
            });
        }

        function confirmarEliminar(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro que desea eliminar el registro seleccionado?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminar(item);
            });
        }

        function registrar() {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            vm.prmMaestro.valorGeneral05 = '3';
            if (vm.prmMaestro.codigoMaestro == 'DireccionSupervision') {
                vm.prmMaestro.valorGeneral04 = 'DireccionEvaluacion';
                vm.prmMaestro.codigoNivel = 1;
            }
            MaestroFactory.registrar(angular.copy(vm.prmMaestro)).then(function(response) {
                if (response != null && response.valido) {
                    limpiar();
                    /*vm.lstMaestrosItem = response.data;*/
                    toastr.success('Éxito en la operación');
                    ngDialog.close();
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function actualizar() {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            MaestroFactory.actualizar(angular.copy(vm.prmMaestro)).then(function(response) {
                if (response != null && response.valido) {
                    limpiar();
                    vm.lstMaestrosItem = response.data;
                    toastr.success('Éxito en la operación');
                    ngDialog.close();
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function eliminar(item) {
            //var codigosCombos = ['TipoEvento','TipoSituacionEvento'];
            MaestroFactory.eliminar(angular.copy(item)).then(function(response) {
                if (response != null && response.valido) {
                    vm.lstMaestrosItem = response.data;
                    toastr.success('Éxito en la operación');
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function init() {
            if ($rootScope.itemRegistroHijo != null && $rootScope.activaRegistroHijo == '1') {
                vm.prmMaestro = angular.copy($rootScope.itemRegistroHijo);
            } else {
                //      console.log("$rootScope.itemMaestroGeneral" + $rootScope.itemMaestroGeneral);
                if ($rootScope.itemMaestroGeneral != null) {
                    //      console.log($rootScope.itemMaestroGeneral);
                    var codigo = [$rootScope.itemMaestroGeneral.codigoTabla]
                    vm.prmMaestro = angular.copy($rootScope.itemMaestroGeneral);
                } else {
                    vm.prmMaestro.codigoTabla = angular.copy($rootScope.itemMaestroGeneral.codigoTabla);
                }
            }
        }
        init();
        /*fin de metodos*/
    }
})();