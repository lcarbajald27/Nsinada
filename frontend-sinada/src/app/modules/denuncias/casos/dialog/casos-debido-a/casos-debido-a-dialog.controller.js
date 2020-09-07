(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('CasoDebidoADialogController', CasoDebidoADialogController);
    /** @ngInject */
    function CasoDebidoADialogController(toastr, $scope, ngDialog, DetalleCasoFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.dataDebidoA = DetalleCasoFactory.model();
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.guardar = guardar;
        vm.confirmarEliminarDebidoA = confirmarEliminarDebidoA;
        /*implementación de metodos*/
        function confirmarEliminarDebidoA() {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar el Debido A :' + vm.dataDebidoA.descripcion + '?,  ya que podria afectar a los casos registrados. '
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarDebidoA(vm.dataDebidoA);
            });
        }

        function eliminarDebidoA(item) {
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
            if (vm.dataDebidoA.idDetalleCaso == 0) {
                registrar();
            } else {
                actualizar();
            }
        }

        function actualizar() {
            if (vm.dataDebidoA.descripcion != '') {
                DetalleCasoFactory.actualizar(vm.dataDebidoA).then(function(response) {
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
            if (vm.dataDebidoA.descripcion != '') {
                DetalleCasoFactory.registrar(vm.dataDebidoA).then(function(response) {
                    if (response.valido) {
                        vm.dataDebidoA.idDetalleCaso = response.data;
                        ngDialog.close();
                        toastr.success(response.mensaje);
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

        function buscarDebidoA() {
            DetalleCasoFactory.buscarPorId(vm.dataDebidoA).then(function(response) {
                if (response.valido) {
                    vm.dataDebidoA = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function cancelar() {
            ngDialog.close();
        }
        /*fin de metodos*/
        function init() {
            if (angular.isDefined($scope.ngDialogData)) {
                vm.dataDebidoA = angular.copy($scope.ngDialogData.debidoA);
                if (vm.dataDebidoA.idDetalleCaso > 0) {
                    buscarDebidoA();
                }
            }
        }
        init();
        /*fin de controller*/
    }
})();