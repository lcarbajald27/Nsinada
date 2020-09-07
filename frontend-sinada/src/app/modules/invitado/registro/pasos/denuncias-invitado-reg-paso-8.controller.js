(function() {
	'use strict';

	angular
		.module('spapp.invitado')
		.controller('DenunciasInvitadoRegPaso8Controller', DenunciasInvitadoRegPaso8Controller);

	/* @ngInject */
	function DenunciasInvitadoRegPaso8Controller($rootScope,$state,toastr, ngDialog,DenunciaFactory,API_CONFIG) {
		
		var vm = this;

		/*declaracion de variables globales*/
		  vm.idPerfilDenunciante = API_CONFIG.idPerfilDenunciante;
		 vm.fechaRegistroDenuncia = new Date();
		 vm.denuncia=[];
		 vm.listaEntidadCaso=[];
		 vm.visualizarPDF = visualizarPDF;
		vm.dataDenuncia = {
			codigoDenuncia : 'DEN-XXXX-XXXX',
			codigoAcceso : 'XAS15S',
			tipoOrganismoDescripcion : 'Ley XXX, Articulo XXX, Texto',
			tipoAutoridadDescripcion : 'Ley XXX, Articulo XXX, Texto',
			denuncianteNombre : 'Fulanito Cosme',
			medioRecepcion : 'EN OFICINA ETC',
			fechaRegistro : '27/08/2017'
		};
		
		$rootScope.pasoActivo = 8;
		
		/*declaracion de metodos */
        vm.finalizar = finalizar;

		/*implementacion de metodos*/
        
        
        function validarPerfil(idPerfil) {
            var a = 0;
            for (var x in $rootScope.lstPerfilesUsuario) {
                if ($rootScope.lstPerfilesUsuario[x].pk_eIdPerfil == idPerfil) {
                    a = a + 1;
                }
            }
            return a;
        }
		function finalizar() {
			
			
			   localStorage.removeItem("objPreguntaResLocal");
			localStorage.removeItem("objDenuncia");
			if(validarPerfil(vm.idPerfilDenunciante)==1){
				$state.go('invitado.encuesta');
				$rootScope.dataDenunciaFinal = vm.denuncia;
			}else{
				
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
					$state.go('invitado.encuesta');
					$rootScope.dataDenunciaFinal = vm.denuncia;
				}
				
				
				
			}
		
			
			
			
		}

		function visualizarPDF() 
		{
			$rootScope.datosFichaDenuncia = vm.denuncia;
			ngDialog
				.open({
					template : 'app/modules/invitado/registro/dialog/denuncia-archivo/denuncia-archivo-dialog.html',
					controller : 'DenunciaArchivoDialogController',
					controllerAs : 'vm',
					width : '700px'
				});
		}
		
		/*fin de implementacion de metodos*/

		function init() {
			if(angular.isDefined(localStorage.urlPdfDenuncia)){
				$rootScope.urlPDFFichaDenuncia = angular.fromJson(localStorage.urlPdfDenuncia);
			}
			
				if($rootScope.ValidaPaso8 == '1'){
							$rootScope.ValidaPaso1 = '2';
							$rootScope.ValidaPaso2 = '2';
							$rootScope.ValidaPaso3 = '2';
							$rootScope.ValidaPaso4 = '2';
							$rootScope.ValidaPaso5 = '2';
							$rootScope.ValidaPaso6 = '2';
							$rootScope.ValidaPaso7 = '2';
							$rootScope.ValidaPaso8 = '2';
							

							
			/*if(angular.isDefined(localStorage.objListaEntidadCaso)){
				vm.listaEntidadCaso =angular.fromJson(localStorage.objListaEntidadCaso);
					
					if(vm.listaEntidadCaso!=null){
							localStorage.removeItem("objListaEntidadCaso");
					}
				
			}*/


			if(angular.isDefined(localStorage.objFinalizarDenuncia)){
				vm.denuncia =angular.fromJson(localStorage.objFinalizarDenuncia);
					
					if(vm.denuncia.idDenuncia>0){
							localStorage.removeItem("objFinalizarDenuncia");
					}
				
			}

				}else{

					   localStorage.removeItem("objPreguntaResLocal");
							$state.go('invitado.registro.paso1');
				}
	

		}

		init();

		/*fin de controller*/
	}
})();