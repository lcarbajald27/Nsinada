(function() {
	'use strict';

	angular
		.module('spapp.seguridad')
		.controller('UsuariosActualizacionDatosController', UsuariosActualizacionDatosController);

	/* @ngInject */
	function UsuariosActualizacionDatosController($state ,toastr,$scope, ngDialog,UsuarioFactory,PersonaFactory,EntidadFactory,UbigeoFactory) {
		
		var vm = this;

		/*declaracion de variables globales*/
		vm.dataPersona=PersonaFactory.model();
		vm.dataUsuario=UsuarioFactory.modelUsuario();
		
		vm.departamento=UbigeoFactory.departamento();
		 vm.provincia= UbigeoFactory.provincia();
		  vm.distrito= UbigeoFactory.distrito();
		  
		vm.listaDepartamento=[];
		vm.listaProvincia=[];
		vm.listaDistrito=[];
		
		vm.listarProvincia=listarProvincia;
		vm.listarDistrito=listarDistrito;
		
       /* vm.dataUsuario = {
			tipoPersona : 0,
			tipoDocumento :0
		};*/

	    vm.listaTipoPersona = [];
		vm.listaTipoDocumento = [];
		
		vm.flagGuardar=false;

		/*declaracion de metodos */
		
		//vm.cancelar=cancelar;
		//vm.guardar=guardar;
		vm.actualizar = actualizar;
		vm.guardar = guardar;
		
		vm.buscarPersonaXNumeroDocumento = buscarPersonaXNumeroDocumento;
		/*implementacion de metodos*/
		/*function guardar(){
			if(vm.dataUsuario.idUsuario == 0){
				registrar();
			}else{
				actualizar();
			}

		}*/
		function guardar(){
//			console.log("vm.dataUsuario PERSONA INGRESO A ACTUALIZAR",vm.dataUsuario);
			PersonaFactory
			.insertarPersona(vm.dataUsuario.persona)
			.then(function (response) {
				if (response.valido) {
					toastr.success(response.mensaje);
				}
				else{
					toastr.error(response.mensaje)
				}
			})
			.catch(function (error) {
				//toastr.error('Hubo un error al actualizar el usuario')
				
			}).finally(function () {
				ngDialog.close();
			});

		}
		
		function actualizar(){
		//	console.log("vm.dataUsuario PERSONA INGRESO A ACTUALIZAR",vm.dataUsuario);
			PersonaFactory
			.actualizar(vm.dataUsuario.persona)
			.then(function (response) {
				if (response.valido) {
					toastr.success(response.mensaje);
				}
				else{
					toastr.error(response.mensaje)
				}
			})
			.catch(function (error) {
				toastr.error('Hubo un error al actualizar el usuario')
				
			}).finally(function () {
				ngDialog.close();
			});

		}
		
		function listarDepartamento(){
			
			UbigeoFactory
						.listarDepartamento(vm.departamento)
						.then(function (response) {
							if (response.valido) {
								vm.listaDepartamento= response.data;
							
								
								/*vm.dataDenuncia.tipoPersona=0;*/
							}
							else {
								toastr.error(response.mensaje);
							}
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {
					
						});

		}

			function listarProvincia(){
						vm.provincia.codigoDepartamento=vm.dataUsuario.persona.departamento;
			UbigeoFactory
						.listarProvincia(vm.provincia)
						.then(function (response) {
							if (response.valido) {
								vm.listaProvincia= response.data;
							
								
								/*vm.dataDenuncia.tipoPersona=0;*/
							}
							else {
								toastr.error(response.mensaje);
							}
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {

							if(vm.dataUsuario.persona.departamento!=null && vm.dataUsuario.persona.provincia!=null){

								listarDistrito();
							}

							
					
						});

		}

		function listarDistrito(){
						vm.distrito.codigoDepartamento=vm.dataUsuario.persona.departamento;
						vm.distrito.codigoProvincia=vm.dataUsuario.persona.provincia;
			UbigeoFactory
						.listarDistrito(vm.distrito)
						.then(function (response) {
							if (response.valido) {
								vm.listaDistrito= response.data;
						
								
								/*vm.dataDenuncia.tipoPersona=0;*/
							}
							else {
								toastr.error(response.mensaje);
							}
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {
					
						});

		}
		
		function buscarPersonaXNumeroDocumento(){
		//	console.log("vm.dataPersona DATOS QUE ENVIO",vm.dataPersona);
			vm.dataPersona.documento = angular.copy(vm.dataUsuario.persona.documento);
			PersonaFactory
						.buscarXNumeroDocumento(angular.copy(vm.dataPersona))
						.then(function (response) {
							if (response.valido) {
								
								if(response.data.idPersona>0){
									
										vm.dataPersona= angular.copy(response.data);
										
										//console.log("vm.dataPersona TRAIDA DE LA BUSQUEDA",vm.dataPersona);
										/*if(vm.dataPersona.primerNombre!=null && vm.dataPersona.apellidoPaterno!=null && vm.dataPersona.apellidoMaterno!=null  && vm.dataPersona.departamento!=null  && vm.dataPersona.provincia!=null  && vm.dataPersona.distrito!=null  && vm.dataPersona.direccion!=null){

											agregarDenunciante();
										}*/
											
								}
							
							}
							else {
								toastr.error(response.mensaje);
							}
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {
							vm.dataUsuario.persona.apellidoMaterno = angular.copy(vm.dataPersona.apellidoMaterno);
							vm.dataUsuario.persona.apellidoPaterno = angular.copy(vm.dataPersona.apellidoPaterno);
							vm.dataUsuario.persona.nombres = angular.copy(vm.dataPersona.nombres);
							vm.dataUsuario.persona.primerNombre = angular.copy(vm.dataPersona.primerNombre);
							vm.dataUsuario.persona.segundoNombre = angular.copy(vm.dataPersona.segundoNombre);
							vm.dataUsuario.persona.apellidoMaterno = angular.copy(vm.dataPersona.apellidoMaterno);
							
							
							/*if(vm.dataPersona.departamento!=null ){

								listarProvincia();
							}*/
							/*vm.dataPersona=PersonaFactory.model();*/
								/*listarContactoPersona();
							vm.dataDenunciante={};*/
						});

		}

		/*fin de implementacion de metodos*/

		function init() {
			vm.listaTipoPersona.push({codigoRegistro:1,descripcion:'Natural'});
			vm.listaTipoPersona.push({codigoRegistro:2,descripcion:'Jurídica'});
			vm.listaTipoDocumento.push({codigoRegistro:1,descripcion:'DNI'});
			vm.listaTipoDocumento.push({codigoRegistro:2,descripcion:'RUC'});	
			
			if (angular.isDefined($scope.ngDialogData)) {
				vm.dataUsuario = angular.copy($scope.ngDialogData.dataUsuario);
				vm.flagGuardar = angular.copy($scope.ngDialogData.flagGuardar);
				vm.dataUsuario.tipoPersona = angular.copy(vm.dataUsuario.bandeja.tipoResponsable);
			//	console.log("vm.dataUsuario",vm.dataUsuario);
			///	console.log("vm.flagGuardar",vm.flagGuardar);
				
				listarDepartamento();
			}
		}

		init();

		/*fin de controller*/
	}
})();