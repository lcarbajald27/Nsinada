(function() {
    'use strict';
    angular.module('spapp.invitado').controller('ValidacionInvitadoDenunciasListDialogController', ValidacionInvitadoDenunciasListDialogController);
    /* @ngInject */
    function ValidacionInvitadoDenunciasListDialogController($state, $controller, $scope, toastr, ngDialog, DenunciaFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*vm.listaProducto = [];
        vm.prmProducto = ProductoFactory.modelProducto();*/
        /*variables de paginacion*/
        vm.dataDenunciaInvitado = DenunciaFactory.model();
        vm.opcionesPaginacion = {
            itemsPorPag: 10,
            cantidades: [10, 25, 50, 100]
        };
        /*declaración de metodos enlazados a la vista*/
        /*  vm.limpiar = limpiar;
		vm.buscar = buscar;
		vm.seleccionar = seleccionar;
*/
        vm.irConsultaDenunciaInvitado = irConsultaDenunciaInvitado;
        vm.cancelar = cancelar;
        /*implementación de metodos*/
        /*

		function limpiar() {
			vm.prmProducto= ProductoFactory.modelProducto();
			vm.prmProducto.nombre = '';
		}*/
        function cancelar() {
            ngDialog.close();
        }

        function irConsultaDenunciaInvitado() {
            DenunciaFactory.buscarDenunciaInvitado(angular.copy(vm.dataDenunciaInvitado)).then(function(response) {
                if (response.valido) {
                    vm.dataDenunciaInvitado = response.data;
                    sessionStorage.setItem('dataDenunciaInvitado', angular.toJson(vm.dataDenunciaInvitado));
                    $state.go('invitado.listado');
                } else {
                    toastr.warning(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                ngDialog.close();
            });
        }

        function init() {
            /*	limpiar();*/
        }
        init();
        /*fin de metodos*/
    }
})();