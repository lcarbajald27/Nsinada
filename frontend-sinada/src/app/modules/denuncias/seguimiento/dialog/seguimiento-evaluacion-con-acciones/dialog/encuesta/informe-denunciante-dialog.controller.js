(function() {
	'use strict';

	angular
	.module('spapp.invitado')
	.controller('EncuestaDenuncianteController',EncuestaDenuncianteController);


	/** @ngInject */
	function EncuestaDenuncianteController($state, toastr, ngDialog, $scope) {
		var vm = this;
		
		/*declaracion de variables globales*/

		vm.pasoActivo = 1;
		vm.encuesta ={
             pregunta1 : 0,
             pregunta2 : 0,
             pregunta3 : 0
		}
		vm.encuestaTerminada = false;
		vm.listapregunta1 = [];
		vm.listapregunta2 = [];
		vm.listapregunta3 = [];
		/*declaración de metodos enlazados a la vista*/

		vm.cambiarPaso = cambiarPaso;
        vm.terminarEncuesta = terminarEncuesta;
        vm.salir= salir;


		/*implementación de metodos*/

		function cambiarPaso(numero) {
			vm.pasoActivo = numero;
			$state.go('invitado.registro.paso'+numero);
		}

		function terminarEncuesta() {
			$scope.closeThisDialog({enviado:true});
		}

		function salir() {
			$state.go('invitado.inicio');
		}
		
		/*fin de metodos*/

		function init() {
			vm.listapregunta1.push({codigoRegistro:1,descripcion:'Mala'});
			vm.listapregunta1.push({codigoRegistro:2,descripcion:'Regular'});
			vm.listapregunta1.push({codigoRegistro:3,descripcion:'Buena'});
			vm.listapregunta1.push({codigoRegistro:4,descripcion:'Excelente'});

			vm.listapregunta2.push({codigoRegistro:1,descripcion:'Prolongado'});
			vm.listapregunta2.push({codigoRegistro:2,descripcion:'Adecuado'});
			vm.listapregunta2.push({codigoRegistro:3,descripcion:'Rápido'});
			vm.listapregunta2.push({codigoRegistro:4,descripcion:'Inmediato'});

			vm.listapregunta3.push({codigoRegistro:1,descripcion:'Insatisfactoria, necesita más acciones'});
			vm.listapregunta3.push({codigoRegistro:2,descripcion:'Adecuada, está en camino de solución'});
			vm.listapregunta3.push({codigoRegistro:3,descripcion:'Satisfactoria'});
		}

		init();

		/*fin de controller*/
	}
})();