(function() {
	'use strict';

	angular
	.module('spapp.denuncias')
	.controller('ListaUsuarioDialogController',ListaUsuarioDialogController);


	/** @ngInject */
	function ListaUsuarioDialogController(toastr, $scope, ngDialog, UsuarioFactory,PersonaOefaFactory,API_CONFIG) {
		var vm = this;
		
		/*declaracion de variables globales*/
		/*Configuracion de paginacion de tablas*/
		vm.dataIdEntidad = API_CONFIG.idPerfilEntidad;
		 $scope.config = {
				    itemsPerPage: 5,
				    fillLastPage: true,
				    current:1
				  };
		 /*fin de configuracion de tablas*/
	
		vm.flagAsignarActivo = 1;
		vm.prmUsuarioFiltro = UsuarioFactory.modelUsuario();
		vm.dataCasoOefa = {};
		
		/*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.asignarUsuarioSinada=asignarUsuarioSinada;
      

		/*implementación de metodos*/

	function asignarUsuarioSinada(data)
       {
       			var dataPersonaOEfa = PersonaOefaFactory.model();
       			dataPersonaOEfa.persona.idPersona = data.idUsuario;
				dataPersonaOEfa.estado.codigoRegistro = 1;
				dataPersonaOEfa.direccion = vm.dataCasoOefa.direccionSupervision;
       			dataPersonaOEfa.subDireccion = vm.dataCasoOefa.direccionEvaluacion;
 				PersonaOefaFactory
 				.registrar(angular.copy(dataPersonaOEfa))
 				.then(function (response) 
 				{
 					if(response.valido)
 					{
 							toastr.success('Se asignó el usuario al OEFA');
 					 		ngDialog.close();
 					}
 					else
 					{
 						toastr.error(response.mensaje);
 					}
 				})
 				.catch(function (error) 
 				{
 					toastr.error('Ocurrió un error al registrar el OEFA.');
 				})
 				.finally(function () {
					
						});
          
		}


	
		
		    function listarUsuariosSinada()
       {
   
       		vm.prmUsuarioFiltro.personaOefa.direccion = angular.copy(vm.dataCasoOefa.direccionSupervision);
       		vm.prmUsuarioFiltro.personaOefa.subDireccion = vm.dataCasoOefa.direccionEvaluacion;
       		vm.prmUsuarioFiltro.flagFiltroUsuario=0;
       		vm.prmUsuarioFiltro.idPerfil= vm.dataIdEntidad;
 				PersonaOefaFactory
 				.listarUsuarioSinada(angular.copy(vm.prmUsuarioFiltro))
 				.then(function (response) 
 				{
 					if(response.valido)
 					{
 						vm.listaUsuariosSinada = response.data;
 					
 						
 					
 					
 					}
 					else
 					{
 						toastr.error(response.mensaje);
 					}
 				})
 				.catch(function (error) 
 				{
 					toastr.error('Ocurrió un error al obtener la información');
 				})
 				.finally(function () {
					
						});
          
		}



        function cancelar() {
			ngDialog.close();
		}
		
		/*fin de metodos*/

		function init() 
		{
		
				/*vm.entidadBusqueda.tipoNivel ='0';*/
			if (angular.isDefined($scope.ngDialogData)) 
			{	
			
				vm.dataCasoOefa = angular.copy($scope.ngDialogData.dataCaso);

					listarUsuariosSinada();
			}
		
		}

		init();

		/*fin de controller*/
	}
})();