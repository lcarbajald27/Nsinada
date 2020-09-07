(function() {
    'use strict';
    angular.module('spapp').factory('APIFactory', APIFactory);
    /* @ngInject */
    function APIFactory($q, $http, $rootScope, CookiesFactory) {
        var factory = {
            listar: listar,
            registrar: registrar,
            obtener: obtener,
            actualizar: actualizar,
            eliminar: eliminar,
            consultaPOST: consultaPOST,
            guardarArchivo: guardarArchivo,
            download: download
        };
        return factory;

        function listar(url, objeto) {
            CookiesFactory.aumentarTiempoCookie();
            $rootScope.cargandoInformacion++;
            //$rootScope.$broadcast('aumentarContadorCargando', null);
            var deferred = $q.defer();
            var parametros = undefined;
            if (angular.isDefined(objeto)) {
                parametros = {
                    prmCriterio: angular.toJson(objeto)
                };
            }
            $http({
                url: url,
                method: 'GET',
                params: parametros
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            //$rootScope.$broadcast('disminuirContadorCargando', null);
            return deferred.promise;
        }

        function registrar(url, objeto) {
            CookiesFactory.aumentarTiempoCookie();
            if (angular.isDefined(localStorage.dataObjUsuIdEncrypSinadaWeb)) {
                objeto.prm1 = angular.copy(localStorage.dataObjUsuIdEncrypSinadaWeb);
            }
            $rootScope.cargandoInformacion++;
            var deferred = $q.defer();
            $http({
                url: url,
                method: 'POST',
                data: objeto
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            //$rootScope.cargandoInformacion--;
            return deferred.promise;
        }

        function obtener(url, id) {
            CookiesFactory.aumentarTiempoCookie();
            $rootScope.cargandoInformacion++;
            var deferred = $q.defer();
            $http({
                url: url + (id ? id : ''),
                method: 'GET'
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            //$rootScope.cargandoInformacion--;
            return deferred.promise;
        }

        function actualizar(url, objeto) {
            CookiesFactory.aumentarTiempoCookie();
            if (angular.isDefined(localStorage.dataObjUsuIdEncrypSinadaWeb)) {
                objeto.prm1 = angular.copy(localStorage.dataObjUsuIdEncrypSinadaWeb);
            }
            $rootScope.cargandoInformacion++;
            var deferred = $q.defer();
            $http({
                url: url,
                method: 'POST',
                data: objeto
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            //$rootScope.cargandoInformacion--;
            return deferred.promise;
        }

        function eliminar(url, objeto) {
            CookiesFactory.aumentarTiempoCookie();
            if (angular.isDefined(localStorage.dataObjUsuIdEncrypSinadaWeb)) {
                objeto.prm1 = angular.copy(localStorage.dataObjUsuIdEncrypSinadaWeb);
            }
            $rootScope.cargandoInformacion++;
            var deferred = $q.defer();
            $http({
                url: url,
                method: 'POST',
                data: objeto
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            return deferred.promise;
        }

        function consultaPOST(url, objeto) {
            CookiesFactory.aumentarTiempoCookie();
            $rootScope.cargandoInformacion++;
            var deferred = $q.defer();
            $http({
                url: url,
                method: 'POST',
                data: objeto
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            return deferred.promise;
        }

        function guardarArchivo(url, data) {
            CookiesFactory.aumentarTiempoCookie();
            // debugger;
            if (angular.isDefined(localStorage.dataObjUsuIdEncrypSinadaWeb)) {
                var dataForm = data.getAll("strContenido");
                var dataObj = angular.copy(angular.fromJson(dataForm[0]));
                dataObj.prm1 = angular.copy(localStorage.dataObjUsuIdEncrypSinadaWeb);
                data.delete("strContenido");
                data.append("strContenido", angular.toJson(dataObj));
            }
            $rootScope.cargandoInformacion++;
            var deferred = $q.defer();
            $http.post(url, data, {
                headers: {
                    "Content-type": undefined
                },
                transformRequest: angular.identity
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            return deferred.promise;
        }

        function download(url, objeto) {
            CookiesFactory.aumentarTiempoCookie();
            $rootScope.cargandoInformacion++;
            var deferred = $q.defer();
            $http.post(url, objeto, {
                responseType: 'arraybuffer'
            }).success(function(response) {
                deferred.resolve(response);
                $rootScope.cargandoInformacion--;
            }).error(function(error) {
                deferred.reject(error);
                $rootScope.cargandoInformacion--;
            });
            return deferred.promise;
        }
    }
})();