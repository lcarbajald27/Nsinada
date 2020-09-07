(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('CasoProblematicaDialogController', CasoProblematicaDialogController);
    /** @ngInject */
    function CasoProblematicaDialogController(toastr, $scope, ngDialog, DetalleCasoFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.dataProblematica = DetalleCasoFactory.model();
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.guardar = guardar;
        vm.confirmarEliminarProblematica = confirmarEliminarProblematica;
        /*implementación de metodos*/
        function confirmarEliminarProblematica() {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar la problematica : ' + vm.dataProblematica.descripcion + '?, ya que podria afectar a los casos registrados.'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarProblematica(vm.dataProblematica);
            });
        }

        function eliminarProblematica(item) {
            var data = {
                idDetalleCaso: item.idDetalleCaso
            };
            DetalleCasoFactory.eliminar(angular.copy(data)).then(function(response) {
                if (response.valido) {
                    //					vm.listarContacto();
                    //					vm.listarTitular();
                    toastr.success(response.mensaje);
                    ngDialog.close();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('No se pudo eliminar a la entidad asignada a este caso');
            });
        }

        function guardar() {
            if (vm.dataProblematica.idDetalleCaso == 0) {
                registrar();
            } else {
                actualizar();
            }
        }

        function actualizar() {
            if (vm.dataProblematica.descripcion != '') {
                DetalleCasoFactory.actualizar(vm.dataProblematica).then(function(response) {
                    if (response.valido) {
                        toastr.success(response.mensaje);
                        ngDialog.close();
                    } else {
                        toastr.info(response.mensaje)
                    }
                }).catch(function(error) {
                    toastr.error('Ocurrió un error al actualizar la problemática');
                });
            } else {
                toastr.warning("Debe agregar una descripción");
            }
        }

        function registrar() {
            if (vm.dataProblematica.descripcion != '') {
                DetalleCasoFactory.registrar(vm.dataProblematica).then(function(response) {
                    if (response.valido) {
                        vm.dataProblematica.idDetalleCaso = response.data;
                        toastr.success(response.mensaje);
                        ngDialog.close();
                    } else {
                        toastr.info(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error('Ocurrió un error al registrar problemática');
                });
            } else {
                toastr.warning("Debe agregar una descripción");
            }
        }

        function cancelar() {
            ngDialog.close();
        }

        function buscarProblematica() {
            DetalleCasoFactory.buscarPorId(vm.dataProblematica).then(function(response) {
                if (response.valido) {
                    vm.dataProblematica = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /*fin de metodos*/
        function init() {
            if (angular.isDefined($scope.ngDialogData)) {
                vm.dataProblematica.idDetalleCaso = angular.copy($scope.ngDialogData.idProblematica);
                vm.dataProblematica.tipoCaso = 1;
                if (vm.dataProblematica.idDetalleCaso > 0) {
                    buscarProblematica();
                }
            }
        }
        init();
        /*fin de controller*/
    }
})();