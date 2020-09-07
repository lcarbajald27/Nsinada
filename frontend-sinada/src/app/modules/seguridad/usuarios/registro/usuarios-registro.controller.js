(function() {
	'use strict';

	angular
		.module('spapp.seguridad')
		.controller('UsuariosRegistroController', UsuariosRegistroController);

	/* @ngInject */
	function UsuariosRegistroController(toastr, ngDialog,UsuarioFactory,PersonaFactory,EntidadFactory,PerfilFactory) {
		
		var vm = this;

		/*declaracion de variables globales*/
        vm.dataUsuario=UsuarioFactory.modelUsuario();
        vm.dataPersona=PersonaFactory.model();
		vm.dataEntidad=EntidadFactory.model();
	    vm.listaTipoPersona = [];
		vm.listaTipoDocumento = [];
		vm.listaPerfil = [];

		/*declaracion de metodos */

		vm.guardar=guardar;
		vm.buscarPersonaXNumeroDocumento=buscarPersonaXNumeroDocumento;
		vm.buscarEntidadXNumeroDocumento=buscarEntidadXNumeroDocumento;
		/*implementacion de metodos*/

		function listarPerfiles(){
			PerfilFactory
			.listar()
			.then(function (response) {
				if(response.valido){

					vm.listaPerfil=response.data;
				
				
			//		console.log("vm.listaPerfiles"+ vm.listaPerfil);

				}
				else{
					toastr.error(response.mensaje);
				}
				
			})
			.catch(function (error) {
				toastr.error('Hubo un error al registrar perfil')
			});

		}


		function guardar(){
			if(vm.dataUsuario.idUsuario == 0){
				registrar();
			}else{
				actualizar();
			}

		}


		function buscarPersonaXNumeroDocumento(){
	//		console.log('sdadsasad');
			PersonaFactory
						.buscarXNumeroDocumento(angular.copy(vm.dataPersona))
						.then(function (response) {
							if (response.valido) {
								
								if(response.data.idPersona>0){
										vm.dataPersona= response.data;
									vm.dataUsuario.idPersona=vm.dataPersona.idPersona;
									
					
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
						
						});

		}

		function buscarEntidadXNumeroDocumento(){

			EntidadFactory
						.buscarXEntidadNumeroDocumento(vm.dataEntidad)
						.then(function (response) {
							if (response.valido) {
								vm.dataEntidad= response.data;
								
								if(response.data.idEntidad>0){
										vm.dataEntidad= response.data;
										vm.dataUsuario.idEntidad=vm.dataEntidad.idEntidad;
						
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
						
						});

		}



		function registrar(){

			/* vm.dataUsuario.idTipoPerfil = vm.dataPerfil.idPerfil;*/
			
			UsuarioFactory
			.registrar(vm.dataUsuario)
			.then(function (response) {
				if(response.valido){
					vm.dataUsuario.idUsuario = response.data;
			
					toastr.success('Usuario registrado con código ' + vm.dataUsuario.idUsuario);

				}
				else{
					toastr.error(response.mensaje);
				}
				
			})
			.catch(function (error) {
				toastr.error('Hubo un error al registrar usuario')
			});

		}


			function actualizar(){
		
			UsuarioFactory
			.actualizar(vm.dataUsuario)
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
				
			});

		}

		

		/*fin de implementacion de metodos*/

		function init() {
			listarPerfiles();

			vm.listaTipoPersona.push({codigoRegistro:1,descripcion:'Natural'});
			vm.listaTipoPersona.push({codigoRegistro:2,descripcion:'Jurídica'});
			vm.listaTipoDocumento.push({codigoRegistro:1,descripcion:'RUC'});
			vm.listaTipoDocumento.push({codigoRegistro:2,descripcion:'DNI'});

			
		}

		init();

		/*fin de controller*/
	}
})();