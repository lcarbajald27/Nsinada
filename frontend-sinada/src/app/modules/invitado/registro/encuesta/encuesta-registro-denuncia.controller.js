(function() {
	'use strict';

	angular
	.module('spapp.invitado')
	.controller('EncuestaRegistroDenunciaController',EncuestaRegistroDenunciaController);


	/** @ngInject */
	function EncuestaRegistroDenunciaController($state, INTERNAL_HOME_PAGE,EncuestaFactory,$rootScope,toastr,MaestroFactory ) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.dataDenuncia = {};
		vm.pasoActivo = 1;
		vm.encuesta = EncuestaFactory.model();
		vm.encuestaTerminada = false;
		vm.listapregunta1 = [];
		vm.listapregunta2 = [];
		vm.listapregunta3 = [];
		/*declaración de metodos enlazados a la vista*/

		vm.cambiarPaso = cambiarPaso;
        vm.terminarEncuesta = terminarEncuesta;
        vm.salir= salir;


		/*implementación de metodos*/

			function cargarCombos() 
		{
			var codigosCombos = ['EncuestaDenunciaPregunta1','EncuestaDenunciaPregunta2','EncuestaDenunciaPregunta3'];
			MaestroFactory
			.buscarMaestros(angular.copy(codigosCombos))
			.then(function (response) 
			{
			
				if (response!=null&&response.valido)
				{
					for(var x in response.data)
					{
						var tipoCombo = response.data[x].Key;
						var datosCombo = response.data[x].Value;

						switch(tipoCombo)
						{
							case 'EncuestaDenunciaPregunta1' : vm.listapregunta1 		= datosCombo; break;
							case 'EncuestaDenunciaPregunta2' : vm.listapregunta2 			= datosCombo; break;
							case 'EncuestaDenunciaPregunta3' : vm.listapregunta3  	= datosCombo; break;
					
							
							default : break;
						}
					}
				}
			}).catch(function (error) 
			{
				toastr.error('No se pudo obtener los datos para los combos');
			});
		}


		function cambiarPaso(numero) {
			vm.pasoActivo = numero;
			$state.go('invitado.registro.paso'+numero);
		}

			function terminarEncuesta(){
		
						vm.encuesta.idDenuncia = vm.dataDenuncia.idDenuncia;
						vm.encuesta.tipoEncuesta.codigoRegistro = 1;
			EncuestaFactory
						.registrar(angular.copy(vm.encuesta))
						.then(function (response) {
							if (response.valido) {
								
								vm.encuesta.idEncuesta = response.data;
								vm.encuestaTerminada = true;
								toastr.success('Encuesta registrada correctamente');
							
							}
							else {
								toastr.error(response.mensaje);
							}
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {
						
						vm.dataEntidad=EntidadFactory.model();
						});

		}



		/*function terminarEncuesta() {



			vm.encuestaTerminada = true;
		}*/

		function salir() {
			if(angular.isDefined(localStorage.dataBandeja)){
			localStorage.removeItem("dataBandeja");
			if(angular.isDefined(localStorage.bandejaProcedencia)){
				var bandejaProcedencia = angular.fromJson(localStorage.bandejaProcedencia);
				localStorage.removeItem("bandejaProcedencia");
				if(bandejaProcedencia == 1){
					$state.go('spapp.home.bandeja.asignacion');
				}
				if(bandejaProcedencia == 2){
					$state.go('spapp.home.denuncias.seguimiento');
				}
				if(bandejaProcedencia == 3){
					$state.go('spapp.home.bandeja.asignadas');
				}
				if(bandejaProcedencia == 4){
					$state.go('spapp.home.bandeja.denunciante');
				}
			}
			
		}else{
			$state.go('ingreso');
		}

		}
		
		/*fin de metodos*/

		function init() {

			if($rootScope.dataDenunciaFinal!=null){
					vm.dataDenuncia =$rootScope.dataDenunciaFinal;
			} 
			cargarCombos();
		}

		init();

		/*fin de controller*/
	}
})();