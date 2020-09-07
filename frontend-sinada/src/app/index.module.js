(function() {
  'use strict';

  angular
    .module('spapp', [
      /*modulos de librerias externas*/
      'esri.map',
    	'ngAnimate', 
    	'ngCookies', 
    	'ngTouch', 
    	'ngSanitize', 
    	'ngMessages', 
    	'ngAria', 
    	'ui.router', 
    	'ui.bootstrap', 
    	'toastr',
    	'ngDialog',
        'ngMask',
        'rt.select2',
        'angularUtils.directives.dirPagination',
    	/*modulos de la aplicacion */
        'spapp.ficha',
        'spapp.denuncias',
        'spapp.seguridad',
        'spapp.bandeja',
        'spapp.invitado',
        /*'spapp.usuario'*/
        'spapp.maestro'
        /*'spapp.procesos',*/
    	]);

})();
