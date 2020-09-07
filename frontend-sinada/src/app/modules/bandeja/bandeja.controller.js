(function() {
    'use strict';
    angular.module('spapp.bandeja').controller('BandejaController', BandejaController);
    /** @ngInject */
    function BandejaController($state, INTERNAL_HOME_PAGE, CookiesFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.itemTabActivo = 1; //Primer Tab por defecto
        /*declaración de metodos enlazados a la vista*/
        /*implementación de metodos*/
        CookiesFactory.obtenerCookie();
        /*fin de metodos*/
    }
})();