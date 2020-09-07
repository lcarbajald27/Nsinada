(function() {
    'use strict';
    angular.module('spapp.invitado').controller('InvitadoInicioController', InvitadoInicioController);
    /** @ngInject */
    function InvitadoInicioController($state, ngDialog, $window, API_CONFIG, CookiesFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.rutaBase = API_CONFIG.url;
        vm.rutaSSO = API_CONFIG.urlSSO;
        /*declaración de metodos enlazados a la vista*/
        vm.mostrarModalContenido = mostrarModalContenido;
        vm.irARegistroDenuncia = irARegistroDenuncia;
        vm.iraLogin = iraLogin;
        /*implementación de metodos*/
        function iraLogin() {
            //          $window.location.href = "http://200.37.65.227/OEFA.STS/Home/Index?wa=wsignin1.0&wtrealm=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/index/&wctx=rm=2&id=pluralsoft&ru=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso&wreply=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso";
            //          $window.location.href = "http://200.37.65.227/OEFA.STS/Home/Index?wa=wsignin1.0&wtrealm=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/index/&wctx=rm=2&id=pluralsoft&ru=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso&wreply=http://127.0.0.1:8080/oefa-sinada-web/rest/api/seguridad/confirmsso";
            //console.log("pasa"+vm.rutaSSO + "/Home/Index?wa=wsignin1.0&wtrealm=" + vm.rutaBase + "seguridad/index/&wctx=rm=2&id=pluralsoft&ru=" + vm.rutaBase + "seguridad/confirmsso&wreply=" + vm.rutaBase + "seguridad/confirmsso");
            //alert(vm.rutaSSO + "/Home/Index?wa=wsignin1.0&wtrealm=" + vm.rutaBase + "seguridad/index/&wctx=rm=2&id=pluralsoft&ru=" + vm.rutaBase + "seguridad/confirmsso&wreply=" + vm.rutaBase + "seguridad/confirmsso");
            $window.location.href = vm.rutaSSO + "/Home/Index?wa=wsignin1.0&wtrealm=" + vm.rutaBase + "seguridad/index/&wctx=rm=2&id=pluralsoft&ru=" + vm.rutaBase + "seguridad/confirmsso&wreply=http://localhostng :8089/oefa-sinada-web/rest/api/seguridad/confirmsso";
              
             /*   $state.go('login');*/
        }

        function mostrarModalContenido() {
            ngDialog.open({
                template: 'app/modules/invitado/listado/dialog/validacion-invitado-denuncia-list-dialog.html',
                controller: 'ValidacionInvitadoDenunciasListDialogController',
                data: {},
                controllerAs: 'vm',
                width: '550px'
            }).closePromise.then(function(dataDialog) {
                /*if (dataDialog.value.cliente) {
                    vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
                }*/
            });
        }

        function irARegistroDenuncia() {
            localStorage.removeItem("objDenuncia");
            if (angular.isDefined(localStorage.dataBandeja)) {
                localStorage.removeItem("dataBandeja");
            }
            $state.go('invitado.registro.paso1');
        }

        function init() {
            CookiesFactory.borrarDatosCookieLocalStorage();
        }
        init();
        /*fin de metodos*/
    }
})();
