(function() {
    'use strict';
    angular.module('spapp.invitado').controller('HojaTramiteDialogController', HojaTramiteDialogController);
    /** @ngInject */
    function HojaTramiteDialogController(toastr, $scope, DenunciaFactory, ngDialog, $document, $log) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.STDHojaTramite = DenunciaFactory.STDHojaTramiteModel();
        /*declaración de metodos enlazados a la vista*/
        vm.aceptar = aceptar;
        vm.validaVincular = 0;
        vm.buscarHojaTramite = buscarHojaTramite;
        /*implementación de metodos*/
        function buscarHojaTramite() {
            DenunciaFactory.STDObtenerHojaTramite(vm.STDHojaTramite.anio, vm.STDHojaTramite.intext, vm.STDHojaTramite.nrohoja).then(function(response) {
                if (response.pcursor != null && response.pcursor.length != 0) {
                    vm.STDHojaTramite = angular.copy(response);
                    vm.validaVincular = 1;
                } else {
                    toastr.info('No se encontro hoja de trámite.');
                    vm.STDHojaTramite = angular.copy(response);
                    vm.validaVincular = 0;
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al buscar hoja de trámite.');
            });
            //console.log(vm.STDHojaTramite);
        }

        function aceptar() {
            //ngDialog.close();
            if (vm.STDHojaTramite.denuncia.descripcion != null && vm.STDHojaTramite.denuncia.descripcion.trim() != '') {
                $scope.closeThisDialog({
                    successData: vm.STDHojaTramite
                });
            } else {
                ngDialog.close();
            }
        }
        /*fin de metodos*/
        function init() {
            /*
            if(angular.isDefined(localStorage.objDenuncia)){
            vm.denuncia =angular.fromJson(localStorage.objDenuncia);
            $log.info("vm.denuncia"+vm.denuncia);
            }
            */
            // body...
        }
        init();
        /*fin de controller*/
    }
})();