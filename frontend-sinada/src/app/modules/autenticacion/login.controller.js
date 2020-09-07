(function() {
    'use strict';
    angular.module('spapp').controller('LoginController', LoginController);
    /** @ngInject */
    function LoginController($window, $cookies, $state, INTERNAL_HOME_PAGE, UsuarioFactory, toastr, $rootScope, AccesoFactory, $location, API_CONFIG, CookiesFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.usuarioLogin = UsuarioFactory.modelUsuario();
        vm.rutaSSO = API_CONFIG.urlSSO;
        /*declaración de métodos enlazados a la vista*/
        /*implementación de métodos*/
        function recuperarSession(sessionTemp) {
            var data = {
                prm1: sessionTemp,
            };
            AccesoFactory.obtenerSesion(data).then(function(response) {
                if (response.valido) {
                    var cookieSesSinaWeb = response.data;
                    CookiesFactory.crearCookie(cookieSesSinaWeb.ref);
                    $state.go('spapp.home.inicio');
                } else {
                    $window.location.href = vm.rutaSSO + "/Home/Index?wa=wsignout1.0";
                    $state.go('ingreso');
                }
            }).catch(function(error) {
                //toastr.error('xx');
            });
        }

        function logearUsuarioSSO(ref) {
            if (angular.isDefined(ref)) {
                recuperarSession(ref);
            } else {
                sessionStorage.removeItem('objSesSinadaWebRefRD');
                localStorage.removeItem('oSuSinWebDataSys');
                localStorage.removeItem('dataObjUsuIdEncrypSinadaWeb');
                $state.go('ingreso');
                $cookies.remove('cookieSesSinaWeb');
            }
        }

        function init() {
            var ref = $location.search().ref;
            logearUsuarioSSO(ref);
        }
        init();
    }
})();