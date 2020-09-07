(function() {
    'use strict';
    angular.module('spapp').controller('IngresoInicioController', IngresoInicioController);
    /** @ngInject */
    function IngresoInicioController($window, $state, INTERNAL_HOME_PAGE, toastr, $rootScope, API_CONFIG, CookiesFactory) {
        var vm = this;
        /**Slider numero 2**/
        /*declaracion de variables globales*/
        vm.rutaBase = API_CONFIG.url;
        vm.rutaSSO = API_CONFIG.urlSSO;
        /*declaración de métodos enlazados a la vista*/
        vm.iraLogin = iraLogin;
        vm.iraconsultas = iraconsultas;
        vm.registrarse = registrarse;
        /*implementación de métodos*/
        function iraLogin() {
            $window.location.href = vm.rutaSSO + "/Home/Index?wa=wsignin1.0&wtrealm=" + vm.rutaBase + "seguridad/index/&wctx=rm=2&id=pluralsoft&ru=" + vm.rutaBase + "seguridad/confirmsso&wreply=" + vm.rutaBase + "seguridad/confirmsso";
            //          $window.location.href = "http://200.37.65.227/OEFA.STS/Home/Index?wa=wsignin1.0&wtrealm=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/index/&wctx=rm=2&id=pluralsoft&ru=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso&wreply=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso";
            /*  console.log("pasa");
                $state.go('login');*/
        }

        function iraconsultas() {
            $state.go('invitado.inicio');
        }

        function registrarse() {
            $state.go('invitado.nuevo-usuario');
        }

        function init() {
            CookiesFactory.borrarDatosCookieLocalStorage();
        }
        init();
    }
})();