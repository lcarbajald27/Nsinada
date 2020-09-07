(function() {
	'use strict';

	angular
		.module('spapp')
		.controller('NavbarController', NavbarController);

	/* @ngInject */
	function NavbarController(toastr, $rootScope) {
		var vmNavbar = this;
		
		/*declaracion de variables globales*/
		vmNavbar.usuarioActual = {
			nombrePerfil : 'Administrador',
			nombreCompletoPersona : 'Daniel Ávila Méndez'
		};

		/*declaración de métodos enlazados a la vista*/


		/*implementación de métodos*/

		
		/*fin de controller*/

		function init() {
	    	/*console.log("iniciando NavbarController");
	    	if (angular.isDefined(localStorage.objSesionUsuario)) {
	    		vmNavbar.usuarioActual = angular.fromJson(localStorage.objSesionUsuario);

		        $rootScope.usuarioActivo = angular.copy(vmNavbar.usuarioActual);

	    	}else {
	    		$state.go('login'); 
	    		toastr.warning('Debe iniciar sesión en el sistema');
	    	}*/
	    }

	    init();

	}
})();