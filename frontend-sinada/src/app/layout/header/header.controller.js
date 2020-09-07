(function() {
    'use strict';
    angular.module('spapp').controller('HeaderController', HeaderController);
    /** @ngInject */
    function HeaderController($state, $rootScope, toastr, $log, INTERNAL_HOME_PAGE, $window, API_CONFIG, $cookies) {
        var vmHeader = this;
        /*declaracion de variables globales*/
        vmHeader.cerrarSesion = cerrarSesion;
        vmHeader.rutaSSO = API_CONFIG.urlSSO;
        /*declaración de métodos enlazados a la vista*/
        vmHeader.obtenerAccesoMenu = obtenerAccesoMenu;
        /*implementación de métodos*/
        function cerrarSesion() {
            $cookies.remove('cookieSesSinaWeb');
            sessionStorage.removeItem('objSesSinadaWebRefRD');
            localStorage.removeItem('oSuSinWebDataSys');
            localStorage.removeItem('dataObjUsuIdEncrypSinadaWeb');
            $window.location.href = vmHeader.rutaSSO + "/Home/Index?wa=wsignout1.0";
            $state.go('ingreso');
        }

        function obtenerAccesoMenu() {
            /*
            $log.log("vmHeader.prmComponente filtro", vmHeader.prmComponente);
            localStorage.setItem('tmpIdPerfilSeleccionado',vmHeader.prmComponente.idPerfil);
            */
        }
        /*implementación de métodos*/
        function init() {
            /*$log.log("iniciando HeaderController");
	    	if (angular.isDefined(localStorage.objSesionUsuario)) {
	    		vmHeader.usuarioActual = angular.fromJson(localStorage.objSesionUsuario);
	    		$log.log("usuarioActual",vmHeader.usuarioActual);
		        $rootScope.usuarioActivo = angular.copy(vmHeader.usuarioActual);

		        if (angular.isDefined(localStorage.tmpIdPerfilSeleccionado)) {
		        	vmHeader.prmComponente.idPerfil = Number(localStorage.tmpIdPerfilSeleccionado);
		        }else{
		        	vmHeader.prmComponente.idPerfil = vmHeader.usuarioActual.listaUsuarioPerfil[0].idPerfil;
		        }	

	    	}else {
	    		$state.go('login'); 
	    		toastr.warning('Debe iniciar sesión en el sistema');
	    	}*/
        }
        init();
        /*fin de controller*/
    }
})();