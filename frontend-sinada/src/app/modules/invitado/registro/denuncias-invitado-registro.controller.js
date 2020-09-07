(function() {
	'use strict';

	angular
	.module('spapp.invitado')
	.controller('DenunciasInvitadoRegistroController',DenunciasInvitadoRegistroController);


	/** @ngInject */
	function DenunciasInvitadoRegistroController($rootScope,$state, INTERNAL_HOME_PAGE,DenunciaFactory,$window ) {
		var vm = this;

		/*declaracion de variables globales*/

		$rootScope.pasoActivo  = 1;
		vm.pasoActivo = 1;
		vm.validaUsuario = 0;


	/*	$rootScope.denuncia={
		  	idDenuncia 			:'',
	      	tipoDenuncia		:0 ,
	      	medioRecepcion		:0,
	      	hojaTramite			:'',
	      	problematica		:0,
	      	debido				:0,
	      	idDescripcionCaso	:0,
	      	zonaSuceso			:0,
	      	departamento		:'',
	      	provincia			:'',
	      	distrito			:'',
	      	direccion			:'',
	      	referencia			:'',
	      	responsableProblema	:0,
	      	tipoMedio			:0,
	      	archivoMedio		:'',
	      	codigoAcceso		:'',
	      	flagActivo			:''
		};*/


		/*declaración de metodos enlazados a la vista*/
		vm.iraLogin=iraLogin;
		vm.cambiarPaso = cambiarPaso;
    vm.registrarse=registrarse;
		/*implementación de metodos*/


		function iraLogin() {
			$window.location.href = "http://200.37.65.227/OEFA.STS/Home/Index?wa=wsignin1.0&wtrealm=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/index/&wctx=rm=2&id=pluralsoft&ru=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso&wreply=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso";
//			$window.location.href = "http://200.37.65.227/OEFA.STS/Home/Index?wa=wsignin1.0&wtrealm=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/index/&wctx=rm=2&id=pluralsoft&ru=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso&wreply=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso";

		/*	console.log("pasa");
			$state.go('login');*/
		}

		function registrarse(){
      $state.go('invitado.nuevo-usuario');
    }

		function cambiarPaso(numero) {

			console.log("$rootScope.ValidaPaso1: "+$rootScope.ValidaPaso1);
				vm.pasoActivo = numero;
				$rootScope.pasoActivo  = numero;
			if($rootScope.ValidaPaso1=='1' && numero==1){
				/*$state.go('invitado.registro.paso'+numero);*/

			}
			if($rootScope.ValidaPaso2=='1' && numero==2){
				/*$state.go('invitado.registro.paso'+numero);*/

			}

			if($rootScope.ValidaPaso3=='1' && numero==3){
				/*	$state.go('invitado.registro.paso'+numero);*/

			}

			if($rootScope.ValidaPaso4=='1' && numero==4){
				/*	$state.go('invitado.registro.paso'+numero);*/

			}

			if($rootScope.ValidaPaso5=='1' && numero==5){
				/*	$state.go('invitado.registro.paso'+numero);*/

			}
			if($rootScope.ValidaPaso6=='1' && numero==6){
				/*$state.go('invitado.registro.paso'+numero);*/

			}
			if($rootScope.ValidaPaso7=='1' && numero==7){
				/*$state.go('invitado.registro.paso'+numero);*/

			}
			if($rootScope.ValidaPaso8=='1' && numero==8){
				/*$state.go('invitado.registro.paso'+numero);*/

			}

		}

		/*fin de metodos*/

		function init() {

			if(angular.isDefined(localStorage.objRef)){
				vm.validaUsuario = 1;
	    	}else{
	    		vm.validaUsuario = 0;
	    	}
			$rootScope.ValidaPaso1='1';
			// body...
		}

		init();

		/*fin de controller*/
	}
})();
