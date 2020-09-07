(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('EstadoController', EstadoController);
    /** @ngInject */
    function EstadoController($state, toastr, ngDialog, INTERNAL_HOME_PAGE) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.itemTabActivo = 1; //Primer Tab por defecto
        /*declaración de metodos enlazados a la vista*/
        vm.confirmar = confirmar;
        vm.close = close;
        /*implementación de metodos*/
        function confirmar(codigo) { // se recibe el parametro enviado desde la vista en caso de ser necesario.
            ngDialog.openConfirm({
                template: '<div class="">' + '<div class="modal-header">' + '<h4 class="modal-title">Confirmar la acción</h4>' + '</div>' + '<div class="modal-body">' + '<p id="msj_confirmacion">Desea dar por atendida la denuncia ' + codigo + '</p>' // mensaje de confirmacion
                    + '</div>' + '<div class="modal-footer">' + '<button type="button" class="btn btn-default btn-sm" ng-click="vm.close()">SI</button>' + '<button type="button" id="save_modal_confirmation" ng-click="confirm()" class="btn btn-primary btn-sm">NO</button>' + '</div>' + '</div>',
                width: 350,
                plain: true
            }).then(function(value) {
                toastr.success('Éxito  en la operación');
                /** save the contact form **/
                //eliminarExamen(parametro); // llamada a la funcion que se ejecutara al confirmar accion. se envia parametro de ser necesario.
            }, function(value) {
                /** Cancel or do nothing **/
                console.log("cancel");
            });
        }

        function close() {
            ngDialog.close();
        }
        /*fin de metodos*/
    }
})();