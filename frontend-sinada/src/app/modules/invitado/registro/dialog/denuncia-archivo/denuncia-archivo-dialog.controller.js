(function() {
	'use strict';

	angular
	.module('spapp.invitado')
	.controller('DenunciaArchivoDialogController',DenunciaArchivoDialogController);


	/** @ngInject */
	function DenunciaArchivoDialogController(toastr, $scope,$sce, ngDialog,$rootScope) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.prmDataFichaDenuncia=[];
		$scope.renderHtml = function (htmlCode) {
	        return $sce.trustAsHtml(htmlCode);
	    };
	
		
		/*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;


		/*implementación de metodos*/
        function cancelar() {
			ngDialog.close();
		}

		/*fin de metodos*/

		function init() {
			if(angular.isDefined($rootScope.datosFichaDenuncia)){
				vm.prmDataFichaDenuncia = $rootScope.datosFichaDenuncia;
			}
		}

		init();

		/*fin de controller*/
	}
})();