(function() {
	'use strict';

	angular
	.module('spapp.maestro')
	.controller('MaestroController',MaestroController);


	/** @ngInject */
	function MaestroController(AccesoFactory,$location,$state,$log,$rootScope) {
		var vm = this;
		/*declaracion de variables globales*/
		vm.itemTabActivo = 1;//Tab por defecto el primero
		
		/*declaración de metodos enlazados a la vista*/

		/*implementación de metodos*/
	    
		function leerOperaciones(ref) {

	    	AccesoFactory.operaciones(ref,$rootScope.aplicacionSinada.opciones.idMantenimientoMaestro).then(function (response)
			{
	    		$rootScope.operaciones=response;//angular.toJson(response);
				//$log.log('operaciones x '+$rootScope.operaciones);				
	    		$rootScope.validarOperacion=validarOperacion;
			}).catch(function (error) 
			{
				toastr.error(error);
			});
	    }
	    
	    function validarOperacion(operacion) {
	    	for (var int = 0; int < $rootScope.operaciones.length; int++) {
	    		var oOperacion=$rootScope.operaciones[int];	    		
				if (oOperacion.elemento==operacion) {
					return true;
				}
			}
	    	return false;
	    }

	    $rootScope.ref_tmp=$location.search().ref;
	    if (angular.isDefined($rootScope.ref_tmp)) {
	    	leerOperaciones($rootScope.ref_tmp);
	    	$rootScope.ref=$rootScope.ref_tmp;
	    }else{
	    	leerOperaciones($rootScope.ref);
	    }
	

		/*fin de metodos*/
	}
})();