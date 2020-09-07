(function() {
	'use strict';

	angular
		.module('spapp.maestro')
		.controller('MaestroDataDialogControllerXD', MaestroDataDialogControllerXD);

	function MaestroDataDialogControllerXD() {
		var vm = this;

        /*declaracion de variables globales*/

        vm.esNuevoRegistro = true;

        /*declaración de metodos enlazados a la vista*/
        
        vm.cancelar = cancelar;

        /*implementación de metodos*/

    	function cancelar() 
		{
			ngDialog.close();
		}

        function init() {
           
        }

        init();
        
        /*fin de metodos*/
	}
})();