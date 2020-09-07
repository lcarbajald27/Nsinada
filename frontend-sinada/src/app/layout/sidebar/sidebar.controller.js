(function() {
	'use strict';

	angular
	.module('spapp')
	.controller('SidebarController',SidebarController);


	/** @ngInject */
	function SidebarController($log,$state) {

		var vmSidebar = this;


		/*declaracion de variables globales*/
		vmSidebar.moduloActivo = 0;//por defecto inicio - ningun modulo al crearse el controller


		vmSidebar.subModuloActivo = '';
		vmSidebar.isNavCollapsed = true;
		vmSidebar.isCollapsed = false;
		vmSidebar.windowSistem = true;
		/*declaración de métodos enlazados a la vista*/

		vmSidebar.activarModulo = activarModulo;

		/*implementación de métodos*/


		function activarModulo(modulo) {
			vmSidebar.moduloActivo = (modulo == vmSidebar.moduloActivo) ? -1 : modulo;
		}

		function init() {
			vmSidebar.moduloActivo = 0;//por defecto el modulo del menu de inicio cuando carga totalmente

			if (angular.isDefined(localStorage.objSesionUsuario)) {
	    		vmSidebar.usuarioActual = angular.fromJson(localStorage.objSesionUsuario);
	    		$log.log("usuarioActual sideBar",vmSidebar.usuarioActual);
	    	}
		}

		init();
	}
})();