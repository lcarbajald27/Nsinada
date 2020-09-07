(function() {
    'use strict';
    angular.module('spapp').factory('CookiesFactory', CookiesFactory);
    /* @ngInject */
    function CookiesFactory($q, $http, $rootScope, $cookies, $state, API_CONFIG, $window) {
        var factory = {
            generarTiempoSession: generarTiempoSession,
            crearCookie: crearCookie,
            obtenerCookie: obtenerCookie,
            aumentarTiempoCookie: aumentarTiempoCookie,
            obtenerObjCookie: obtenerObjCookie,
            validaCookie: validaCookie,
            borrarDatosCookieLocalStorage: borrarDatosCookieLocalStorage
        };
        return factory;
        /*****************************************************************************************/
        function generarTiempoSession() {
            var today = new Date();
            var expiresValue = new Date(today);
            expiresValue.setMinutes(today.getMinutes() + 20);
            return expiresValue;
        }

        function crearCookie(prmRef) {
            var cookieSesSinaWeb = {
                ref: prmRef,
                tiempoSession: generarTiempoSession()
            };
            var data = angular.toJson(cookieSesSinaWeb);
            $cookies.put('cookieSesSinaWeb', data, {
                'expires': generarTiempoSession()
            });
        }

        function aumentarTiempoCookie() {
            if (angular.isDefined($cookies.get('cookieSesSinaWeb'))) {
                var obj = $cookies.get('cookieSesSinaWeb');
                var data = angular.fromJson(obj);
                crearCookie(data.ref);
            }
        }

        function obtenerCookie() {
            var dataRef = null;
            if (angular.isDefined($cookies.get('cookieSesSinaWeb'))) {
                var obj = $cookies.get('cookieSesSinaWeb');
                var data = angular.fromJson(obj);
                crearCookie(data.ref);
                dataRef = data.ref;
            } else {
                $state.go('ingreso');
            }
            return dataRef;
        }

        function obtenerObjCookie() {
            debugger;
            var dataRef = null;
            if (angular.isDefined($cookies.get('cookieSesSinaWeb'))) {
                var obj = $cookies.get('cookieSesSinaWeb');
                var data = angular.fromJson(obj);
                dataRef = data;
            }
            return dataRef;
        }

        function validaCookie() {
            console.log('paso --- > Cokkie');
            if (!angular.isDefined($cookies.get('cookieSesSinaWeb'))) {
                sessionStorage.removeItem('objSesSinadaWebRefRD');
                localStorage.removeItem('oSuSinWebDataSys');
                localStorage.removeItem('dataObjUsuIdEncrypSinadaWeb');
                $window.location.href = API_CONFIG.urlSSO + "/Home/Index?wa=wsignout1.0";
                $state.go('ingreso');
            }
        }

        function borrarDatosCookieLocalStorage() {
            if (!angular.isDefined($cookies.get('cookieSesSinaWeb'))) {
                sessionStorage.removeItem('objSesSinadaWebRefRD');
                localStorage.removeItem('oSuSinWebDataSys');
                localStorage.removeItem('dataObjUsuIdEncrypSinadaWeb');
            }
        }
    }
})();