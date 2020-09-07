(function() {
	'use strict';

	angular
		.module('spapp.seguridad')
		.controller('UsuarioListadoController', UsuarioListadoController);

	/* @ngInject */
	function UsuarioListadoController(toastr, ngDialog,$controller,$state) {
		
		var vm = this;

		/*declaracion de variables globales*/
        vm.dataUsuario = {
			tipoPersona : 0,
			tipoDocumento :0
		};

	    vm.listaTipoPersona = [];
		vm.listaTipoDocumento = [];

			vm.listaUsuarios=[
				
		
				
				{
					idUsuario :1,
					nombre: 'lucero',
					perfil:'Sinada',
					situacion :'Activo'
				
				},
				
				{
					idUsuario :1,
					nombre: 'Alcantara',
					perfil:'EFA',
					situacion :'Activo'
				
				},
				
				{
					idUsuario :1,
					nombre: 'Bryan',
					perfil:'Web',
					situacion :'Activo'
				
				},
				
				
				
			];
		/*declaracion de metodos */
			vm.irRegistroUsuario=irRegistroUsuario;

		/*implementacion de metodos*/
		

		/*fin de implementacion de metodos*/
			
			function irRegistroUsuario(){
				$state.go('spapp.home.seguridad.usuarios');
			}

		function init() {
			vm.listaTipoPersona.push({codigoRegistro:1,descripcion:'Natural'});
			vm.listaTipoPersona.push({codigoRegistro:2,descripcion:'Jurídica'});
			vm.listaTipoDocumento.push({codigoRegistro:1,descripcion:'RUC'});
			vm.listaTipoDocumento.push({codigoRegistro:2,descripcion:'DNI'});
			
		}

		init();

		/*fin de controller*/
	}
})();