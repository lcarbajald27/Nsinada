(function() {
    'use strict';
    angular.module('spapp.seguridad').config(routeConfig);
    /* @ngInject */
    function routeConfig($stateProvider) {
        /*inicio de route*/
        $stateProvider.state('spapp.home.seguridad', {
                url: '/seguridad',
                templateUrl: 'app/modules/seguridad/seguridad.html',
                controller: 'SeguridadController',
                controllerAs: 'vm'
            }).state('spapp.home.seguridad.usuarios', {
                url: '/usuarios',
                templateUrl: 'app/modules/seguridad/usuarios/registro/usuarios-registro.html',
                controller: 'UsuariosRegistroController',
                controllerAs: 'vm'
            }).state('spapp.home.seguridad.contacto', {
                url: '/contacto',
                templateUrl: 'app/modules/seguridad/usuarios/contacto/contacto-registro.html',
                controller: 'ContactoRegistroController',
                controllerAs: 'vm'
            })
            /*	.state('spapp.home.seguridad.denunciante',{
            		url : '/listado-denunciante',
            		templateUrl : 'app/modules/seguridad/usuarios/listado/denunciante/usuariosdenunciante-listado.html',
            		controller : 'UsuariosDenuncianteListadoController',
            		controllerAs : 'vm'
            	})
            	
            	.state('spapp.home.seguridad.oefa',{
            		url : '/listado-oefa',
            		templateUrl : 'app/modules/seguridad/usuarios/listado/oefa/usuariosoefa-listado.html',
            		controller : 'UsuariosOefaListadoController',
            		controllerAs : 'vm'
            	})*/
            .state('spapp.home.seguridad.clave', {
                url: '/clave',
                templateUrl: 'app/modules/seguridad/usuarios/cambioclave/cambio-clave.html',
                controller: 'CambioClaveController',
                controllerAs: 'vm'
            }).state('spapp.home.seguridad.encuesta', {
                url: '/encuesta',
                templateUrl: 'app/modules/seguridad/encuesta/encuesta-registro.html',
                controller: 'EncuestaController',
                controllerAs: 'vm'
            }).state('spapp.home.seguridad.perfiles', {
                url: '/perfiles',
                templateUrl: 'app/modules/seguridad/perfiles/perfil-registro.html',
                controller: 'PerfilRegistroController',
                controllerAs: 'vm'
            }).state('spapp.home.seguridad.listado', {
                url: '/listado',
                templateUrl: 'app/modules/seguridad/usuarios/listado/usuario-listado.html',
                controller: 'UsuarioListadoController',
                controllerAs: 'vm'
            });
        /*.state('spapp.home.seguridad.sinada',{
        	url : '/listado-sinada',
        	templateUrl : 'app/modules/seguridad/usuarios/listado/sinada/usuariossinada-listado.html',
        	controller : 'UsuariosSinadaListadoController',
        	controllerAs : 'vm'
        });*/
        /*fin de route*/
    }
})();