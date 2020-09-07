(function() {
    'use strict';
    angular.module('spapp.seguridad').controller('CambioClaveController', CambioClaveController);
    /* @ngInject */
    function CambioClaveController(toastr, ngDialog, $controller, $state, AccesoFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.dataClave = {
            claveAntigua: '',
            claveNueva: '',
            claveConfirmacion: ''
        }
        vm.limpiar = limpiar;
        vm.cambiarClaveUsuario = cambiarClaveUsuario;
        /*declaracion de metodos */
        function cambiarClaveUsuario() {
            debugger;
            var data = {
                claveAntigua: vm.dataClave.claveAntigua,
                claveNueva: vm.dataClave.claveNueva
            };
            AccesoFactory.cambiarClave(angular.copy(data)).then(function(response) {
                if (response.valido) {
                    toastr.success(response.mensaje);
                    limpiar();
                } else {
                    toastr.warning(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Hubo un error al cambiar la clave');
            });
        }
        /*implementacion de metodos*/
        function limpiar() {
            vm.dataClave = {
                claveAntigua: '',
                claveNueva: '',
                claveConfirmacion: ''
            }
        }
        /*fin de implementacion de metodos*/
        function init() {}
        init();
        /*fin de controller*/
    }
})();