(function() {
    'use strict';
    angular.module('spapp.invitado').controller('InvitadoController', InvitadoController);
    /** @ngInject */
    function InvitadoController($state, CookiesFactory) {
        var vm = this;
        vm.myInterval = 5000;
        vm.noWrapSlides = false;
        vm.noWrapSlides = false;
        vm.activeSliderImagenes2 = 0;
        vm.slider = [
            // {
            //   image: 'assets/images/fondo/ballena.jpg',
            //   title:'Nuevo Cambio',
            //   subtitulo: 'Sibtitulo Slider 1',
            //   id: 0
            // },
            {
                image: 'http://greenrescue.ancorathemes.com/wp-content/uploads/2016/08/slider1-1.jpg',
                title: 'SIN FRONTERAS',
                subtitulo: '',
                id: 0
            }, {
                image: 'http://greenrescue.ancorathemes.com/wp-content/uploads/2016/08/slider1-3.jpg',
                title: 'CUIDEMOS NUESTRO AMBIENTE',
                subtitulo: '',
                id: 1
            }
        ]
        /*declaracion de variables globales*/
        vm.itemTabActivo = 1; //Primer Tab por defecto
        CookiesFactory.borrarDatosCookieLocalStorage();
        /*declaración de metodos enlazados a la vista*/
        /*implementación de metodos*/
        /*fin de metodos*/
    }
})();