(function() {
	'use strict';

	angular
		.module('spapp.usuario')
		.controller('UsuarioListadoController', UsuarioListadoController);

	/* @ngInject */
	function UsuarioListadoController($state, $controller, UsuarioFactory, toastr, ngDialog, MaestroFactory) {
		var vm = this;

		/*declaracion de variables globales*/
		vm.listaUsuario = [];
		vm.prmUsuario = UsuarioFactory.modelUsuario();
		//declarar variables para guardar los combos desde la bd
		vm.listaTipoPerfil = [];

		/*variables de paginacion*/

        vm.opcionesPaginacion = {
        	itemsPorPag : 10,
        	cantidades : [10,25,50,100]
        };

		/*declaración de metodos enlazados a la vista*/
        vm.buscar = buscar;
        vm.nuevo = nuevo;
        vm.limpiar = limpiar;
        vm.editar = editar;
        //vm.eliminar = eliminar;
        vm.confirmarEliminacion = confirmarEliminacion;

		/*implementación de metodos*/
		/*function cargarCombos() {
			var codigosCombos = ['TipoPerfil'];
			MaestroFactory
			.buscarMaestros(angular.copy(codigosCombos))
			.then(function (response) {
				if (response!=null&&response.valido)
				{
					for(var x in response.data)
					{
						var tipoCombo = response.data[x].Key;
						var datosCombo = response.data[x].Value;
						switch(tipoCombo)
						{
							case 'TipoPerfil' : vm.listaTipoPerfil = datosCombo; break;
							default : break;
						}
					}
				}
			})
			.catch(function (error) {
				toastr.error('No se pudo obtener los datos para los combos');
			});
		}*/
        function nuevo() {
            localStorage.removeItem('tmpIdUsuario');
			$state.go('spapp.home.usuarios.registro');
		}

		function confirmarEliminacion(idUsuario) {
			ngDialog
				.openConfirm({
					template : 'app/base/template/dialog-confirm/dialog-confirm.html',
					controller : 'DialogConfirmController',
					controllerAs : 'vm',
					data : {
						Titulo : 'Confirmar eliminación',
						Mensaje : '¿Está seguro de eliminar al usuario seleccionado?'
					},
					width : '500px'
				})
				.then(
					function (okValue) {
						eliminar(idUsuario);
					}
				);
		}

		function editar(idUsuario) {
			localStorage.setItem('tmpIdUsuario',idUsuario);
            $state.go('spapp.home.usuarios.registro');
		}


        function buscar() {
        	// debugger
			UsuarioFactory
			.listar(angular.copy(vm.prmUsuario))
			.then(function (response) {
				if (response.valido) {
					vm.listaUsuario = response.data;
				}
				else {
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) {
				toastr.error('Error al consultar');
			});
		}

		function limpiar() {
			vm.prmUsuario = UsuarioFactory.modelUsuario();
		}

		function eliminar(idUsuario) {
			var tmpUsuarioEliminar = {
				idUsuario : idUsuario
			};
			UsuarioFactory
			.eliminar(angular.copy(tmpUsuarioEliminar))
			.then(function (response) {
				if (response.valido) {
					toastr.success(response.mensaje);
					buscar();
				}
				else{
					toastr.error(response.mensaje)
				}
			})
			.catch(function (error) {
				toastr.error('Hubo un error al eliminar al usuario');
			});
		}

		function listarPerfil() {
			/*PermisoFactory
			.listarPerfil(angular.copy(vm.prmPerfil))
			.then(function (response) {
				if (response.valido) {
					vm.listaTipoPerfil = response.data;
				}
				else {
					toastr.error(response.mensaje);
				}
			})
			.catch(function (error) {
				toastr.error('Error al consultar');
			});*/
		}

		function init() {
			//cargarCombos();
			limpiar();
			listarPerfil();
			/*vm.listaRegistros.push({idUsuario : 1, nombreCompleto : 'DANIEL ENRIQUE ÁVILA MÉNDEZ', nombreUsuario : 'davila', nombrePerfil : 'ADMINISTRADOR'});
			vm.listaRegistros.push({idUsuario : 1, nombreCompleto : 'SEBASTIÁN JOAQUÍN ÁVILA MÉNDEZ', nombreUsuario : 'savila', nombrePerfil : 'ALUMNO'});
			vm.listaRegistros.push({idUsuario : 1, nombreCompleto : 'ESTHER VILLALOBOS CARRERA', nombreUsuario : 'evillalobos', nombrePerfil : 'DOCENTE'});*/
		}

		init();
		/*fin de metodos*/
	}
})();