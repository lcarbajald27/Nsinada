(function() {
    'use strict';
    angular.module('spapp').config(routerConfig);
    /** @ngInject */
    function routerConfig($stateProvider, $urlRouterProvider, EXTERNAL_HOME_PAGE, LOGIN_PAGE) {
        $stateProvider.state('ingreso', {
            url: '/ingreso',
            views: {
                'root': {
                    templateUrl: 'app/modules/autenticacion/ingreso/ingresoinicio.html',
                    controller: 'IngresoInicioController',
                    controllerAs: 'vm'
                }
            }
        })
        $stateProvider.state('login', {
                url: '/login',
                views: {
                    'root': {
                        templateUrl: 'app/modules/autenticacion/login.html',
                        controller: 'LoginController',
                        controllerAs: 'vm'
                    }
                }
            })
            /*.state('ingreso',{
        url : '/login/ingreso',
        templateUrl : 'app/modules/autenticacion/ingreso/ingresoinicio.html',
        controller : 'IngresoInicioController',
        controllerAs : 'vm'
      })*/
            .state('aviso', {
                url: '/aviso',
                views: {
                    'root': {
                        templateUrl: 'app/layout/error/aviso.html',
                        controller: 'ErrorPageController',
                        controllerAs: 'vm'
                    }
                }
            }).state('404', {
                url: '/404',
                views: {
                    'root': {
                        templateUrl: 'app/layout/error/404.html',
                        controller: 'ErrorPageController',
                        controllerAs: 'vm'
                    }
                }
            }).state('500', {
                url: '/500',
                views: {
                    'root': {
                        templateUrl: 'app/layout/error/500.html',
                        controller: 'ErrorPageController',
                        controllerAs: 'vm'
                    }
                }
            }).state('acceso-denegado', {
                url: '/acceso-denegado',
                views: {
                    'root': {
                        templateUrl: 'app/layout/error/acceso-denegado.html',
                        controller: 'ErrorPageController',
                        controllerAs: 'vm'
                    }
                }
            }).state('spapp', {
                abstract: true,
                views: {
                    'root': {
                        templateUrl: 'app/layout/root/root.html',
                        controller: 'RootController',
                        controllerAs: 'vmRoot'
                    },
                    'header@spapp': {
                        templateUrl: 'app/layout/header/header.html',
                        controller: 'HeaderController',
                        controllerAs: 'vmHeader'
                    },
                    /*'navbar@spapp': {
                      templateUrl : 'app/layout/navbar/navbar.html',
                      controller  : 'NavbarController',
                      controllerAs: 'vmNavbar'
                    },*/
                    'footer@spapp': {
                        templateUrl: 'app/layout/footer/footer.html',
                        controller: 'FooterController',
                        controllerAs: 'vmFooter'
                    }
                }
            })
            /*****************************/
            .state('invitado', {
                url: '/invitado',
                views: {
                    'root': {
                        templateUrl: 'app/modules/invitado/invitado.html',
                        controller: 'InvitadoController',
                        controllerAs: 'vm'
                    },
                }
            })
            /******************************/
            .state('spapp.home', {
                url: '/home',
                views: {
                    /*'main@spapp': {
                      templateUrl : 'app/layout/main/main.html',
                      controller  : 'MainController',
                      controllerAs: 'vm'
                    },*/
                    'sidebar@spapp': {
                        templateUrl: 'app/layout/sidebar/sidebar.html',
                        controller: 'SidebarController',
                        controllerAs: 'vmSidebar'
                    }
                }
            }).state('spapp.home.inicio', {
                url: '/inicio',
                templateUrl: 'app/modules/inicio/inicio.html',
                controller: 'InicioController',
                controllerAs: 'vm'
            });
        $urlRouterProvider.when('', LOGIN_PAGE.url);
        $urlRouterProvider.when('/', LOGIN_PAGE.url);
        $urlRouterProvider.otherwise('/404');
    }
})();