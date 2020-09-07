(function() {
	'use strict';

	angular
	.module('spapp.denuncias')
	.controller('EvaluacionDerivoDialogController',EvaluacionDerivoDialogController);


	/** @ngInject */
	function EvaluacionDerivoDialogController(toastr, $scope, ngDialog) {
		var vm = this;
		
		/*declaracion de variables globales*/

		vm.informeSeguimiento ={
			motivo : 0,
			tipoAmbito : 0,
			tipoOrgano : 0,
			otroMotivo: '',
			tipoEntidad : 0
		};
		
		vm.listaTiposAmbito = [];
		vm.listaTiposOrgano = [];
		/*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;


		/*implementación de metodos*/
        function cancelar() {
			ngDialog.close();
		}

		
		/*fin de metodos*/

		function init() {
			
			vm.listaTiposAmbito.push({codigoRegistro:1,descripcion:'Ámbito nacional'});
			vm.listaTiposAmbito.push({codigoRegistro:2,descripcion:'Ámbito regional'});
			vm.listaTiposAmbito.push({codigoRegistro:3,descripcion:'Ámbito local'});

			vm.listaTiposOrgano.push({codigoRegistro:3,descripcion:'Órgano de línea'});

		}

		init();

		/*fin de controller*/
	}
})();