(function() {
	'use strict';

	angular
		.module('spapp.usuario')
		.controller('UsuarioRegistroController', UsuarioRegistroController);

	/* @ngInject */
	function UsuarioRegistroController($state, $scope,
										ngDialog, toastr, ConvertFactory,
										UsuarioFactory, MaestroFactory) {
		var vm = this;


		/*declaracion de variables globales*/
		vm.dataUsuario = UsuarioFactory.modelUsuario();
		//vm.prmPerfil=PermisoFactory.modelPerfil();
		//vm.perfilUsuario = UsuarioPerfilFactory.modelUsuarioPerfil();
		vm.isActivo=false;
		vm.isActivoUsuario=false;

		vm.listadoPerfil=[];
	    vm.agregarUsuarioPerfil = agregarUsuarioPerfil;
	    vm.eliminarUsuarioPerfil = eliminarUsuarioPerfil;

		//opciones de calendarios
		vm.dtFechaNacimientoOptions = {
			showWeeks : false,
			datepickerMode : 'year',
			minDate : new Date('January 1, 1900'),
			maxDate : new Date()
		};

		//declarar variables para guardar los combos desde la bd
		vm.listaTipoDocumento = [];
		vm.listaTipoGenero = [];
		vm.listaTipoNacionalidad = [];
		vm.listaTipoPerfil = [];
		//vm.listaTipoPersona = [];

		/*declaración de metodos enlazados a la vista*/
		vm.guardar = guardar;
    vm.limpiar = limpiar;
		vm.cancelar = cancelar;
		vm.buscarPersonaPorDocumento = buscarPersonaPorDocumento;
    vm.cambiarDocumentoPersona = cambiarDocumentoPersona;
    vm.obtenerMaxlengthNumDoc = obtenerMaxlengthNumDoc;

		/*implementación de metodos*/
		function cargarCombos() {
			var codigosCombos = ['TipoDocumento','TipoGenero','TipoNacionalidad','TipoPerfil'];
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
							case 'TipoDocumento' : vm.listaTipoDocumento = datosCombo; break;
							case 'TipoGenero' : vm.listaTipoGenero = datosCombo; break;
							case 'TipoNacionalidad' : vm.listaTipoNacionalidad = datosCombo; break;
							//case 'TipoPerfil' : vm.listaTipoPerfil = datosCombo; break;
							/*case 'TipoPersona' : vm.listaTipoPersona = datosCombo; break;*/
							default : break;
						}
					}
				}
			})
			.catch(function (error) {
				toastr.error('No se pudo obtener los datos para los combos');
			});
		}

		function buscarPersonaPorDocumento(keyCode) {
        	if (keyCode=='13') {
        		var prmPersona = {
        			idTipoPersona : angular.copy(vm.dataUsuario.persona.idTipoPersona),
        			idTipoDocumento : angular.copy(vm.dataUsuario.persona.idTipoDocumento),
        			numeroDocumento : angular.copy(vm.dataUsuario.persona.numeroDocumento)
        		};
        		if (prmPersona.idTipoDocumento==0||
        			!ConvertFactory.isNotNoE(prmPersona.numeroDocumento)) {
        			toastr.warning('Indique tipo y número de documento de la persona.');
        			return;
        		}
        		if (prmPersona.idTipoDocumento==1&&
        			prmPersona.numeroDocumento.length!=8) {
        			limpiarPersona();
        			toastr.warning('Indique un número de documento de 8 dígitos');
        			return;
        		}
        		UsuarioFactory
				.buscarPorTipoYNumeroDocumento(prmPersona)
				.then(function (response) {
					if (response.valido) {
						if (response.data!=null) {
							vm.dataUsuario = angular.copy(response.data);
							vm.personaCambiada = angular.copy(vm.dataUsuario.persona);

							vm.isActivo=true;
							if(vm.dataUsuario.idUsuario!=null && vm.dataUsuario.idUsuario>0){
								vm.isActivoUsuario=true;
								vm.perfilUsuario.idUsuario = angular.copy(vm.dataUsuario.idUsuario);
								listarPerfiles();
							}else{
								vm.isActivoUsuario=false;
								vm.dataUsuario.nombreUsuario = angular.copy(vm.dataUsuario.persona.numeroDocumento);
							}
						}
						else{
							limpiarPersona();
							toastr.warning('No se han encontrado datos de la persona con documento '+prmPersona.numeroDocumento);

							vm.isActivo=false;
						}
					}
					else {
						toastr.error('Error al consultar persona');
						vm.isActivo=false;
					}
				})
				.catch(function (error) {
					toastr.error('Error al consultar persona');
				});
			}
			return;
        }

        function confirmarCambioPersona(personaInexistente) {
        	ngDialog
				.openConfirm({
					template : 'app/base/template/dialog-confirm/dialog-confirm.html',
					controller : 'DialogConfirmController',
					controllerAs : 'vm',
					data : {
						Titulo : 'Confirmar acción',
						Mensaje : 'No se hallaron datos de alguna persona con documento '+personaInexistente.numeroDocumento+
						'. ¿Desea registrarlo?'
					},
					width : '380px'
				})
				.then(
					function (okValue) {
						limpiarPersona();
						vm.dataUsuario.persona.idTipoDocumento = angular.copy(personaInexistente.idTipoDocumento);
						vm.dataUsuario.persona.numeroDocumento = angular.copy(personaInexistente.numeroDocumento);
						vm.dataUsuario.persona.idPersona = -1;
					},
					function (cancelValue) {
						//toastr.warning('Cancelar registro de persona');
						limpiarPersona();
					}
				);
        }

        function cambiarDocumentoPersona() {
        	if (vm.dataUsuario.persona.numeroDocumento!=vm.personaCambiada.numeroDocumento) {
        		buscarPersonaPorDocumento('13');
        	}
        }

        function obtenerMaxlengthNumDoc(idTipoDocumento) {
        	/*return PersonaFactory.obtenerMaxlengthNumDoc(idTipoDocumento);*/
        }


        function limpiar() {
			vm.dataUsuario = UsuarioFactory.modelUsuario();
			vm.isActivo=false;
			vm.isActivoUsuario=false;
			vm.dataUsuario.persona.idTipoDocumento = 1;
			vm.dataUsuario.persona.idTipoPersona = '0';
			vm.personaCambiada = angular.copy(vm.dataUsuario.persona);
			vm.dataUsuario.idTipoPerfil="";
			vm.listadoPerfil=[];
			//vm.perfilUsuario = UsuarioPerfilFactory.modelUsuarioPerfil();
		}

		function limpiarPersona() {
			vm.dataUsuario = UsuarioFactory.modelUsuario();
			vm.dataUsuario.persona.idTipoDocumento = 1;
			vm.dataUsuario.persona.idTipoPersona = '0';
			vm.personaCambiada = angular.copy(vm.dataUsuario.persona);

		}

		function cancelar() {
			$state.go('spapp.home.usuarios.listado');
		}

		function guardar(){

			if(vm.dataUsuario.persona==null){
				toastr.warning("Datos de persona no encontrados");
			}
			if(vm.dataUsuario.persona.idPersona==null || vm.dataUsuario.persona.idPersona==0){
				toastr.warning("Datos de persona no encontrados");
			}

			if(vm.dataUsuario.persona.idTipoDocumento=='0'){
        		vm.formUsuario.idTipoDocumento.$invalid = true;
        		vm.formUsuario.idTipoDocumento.$touched = true;
               	toastr.warning('Seleccione tipo de documento');
        		return;
        	}
        	if(vm.dataUsuario.persona.idTipoGenero=='0'){
        		vm.formUsuario.idTipoGenero.$invalid = true;
        		vm.formUsuario.idTipoGenero.$touched = true;
               	toastr.warning('Seleccione tipo de género');
        		return;
        	}

			if(vm.dataUsuario.idUsuario > 0){
				actualizar();
			}else{
				registrar();
			}

		}
		vm.guardarLista = false;
		function registrar(){
			if(vm.dataUsuario.idUsuario>0 && vm.dataUsuario.idUsuario !=null){
				vm.guardarLista = false;
			}else{
				vm.guardarLista = true;
			}

			if(vm.listadoPerfil==null || vm.listadoPerfil.length==0){
				toastr.warning("Debe asignar uno o mas perfiles");
				return;
			}

			UsuarioFactory
			.registrar(angular.copy(vm.dataUsuario))
			.then(function (response) {
				if(response.valido){
					vm.dataUsuario.idUsuario = response.data;
					//vm.dataUsuario.persona.idPersona = response.data[1];
					vm.perfilUsuario.idUsuario = angular.copy(vm.dataUsuario.idUsuario);
					if(vm.guardarLista){
						for (var i = 0; i < vm.listadoPerfil.length; i++) {
							vm.listadoPerfil[i].idUsuario = angular.copy(vm.dataUsuario.idUsuario);
						}
						guardarListaPerfiles();
					}else{
						toastr.success('Usuario registrado con código ' + vm.dataUsuario.idUsuario);
					}

				}
				else{
					toastr.error(response.mensaje);
				}

			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al registrar usuario.')
			});

		}

		function actualizar(){
			if(vm.listadoPerfil==null || vm.listadoPerfil.length==0){
				toastr.warning("Debe asignar uno o mas perfiles");
				return;
			}
			UsuarioFactory
			.actualizar(angular.copy(vm.dataUsuario))
			.then(function (response) {
				if (response.valido) {
					toastr.success(response.mensaje);
				}
				else{
					toastr.error(response.mensaje)
				}
			})
			.catch(function (error) {
				toastr.error('Ocurrió un error al actualizar el usuario')

			});

		}

		/*
    function listarPerfil() {
			PermisoFactory
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
			});
		}*/

        function existe(){
        	var existe = false;

    		/** VERIFICAR SI LA SESION YA SE ENCUENTRA EN LA LISTA **/
    		for (var i = 0; i < vm.listadoPerfil.length; i++) {

    			var perfil = vm.listadoPerfil[i];

    			if (perfil.idPerfil == vm.perfilUsuario.idPerfil) {
    				existe = true;
    				break;
    			}
    		}
    		return existe;
        }

        function agregarPerfil() {

			if (!existe()) {

				for (var i = 0; i < vm.listaTipoPerfil.length; i++) {

					var perfil = vm.listaTipoPerfil[i];

					if (perfil.idPerfil == vm.perfilUsuario.idPerfil) {
						vm.perfilUsuario.nombrePrefil = angular.copy(perfil.nombrePrefil);
						break;
					}
				}
				vm.listadoPerfil.push(angular.copy(vm.perfilUsuario));

			} else {

				toastr.info("Ya existe un registro con ese perfil");
			}


		//limpiarPerfil();
	}

        

        function eliminarPerfil(perfil) {

        	for (var i = 0; i < vm.listadoPerfil.length; i++) {

    			var item = vm.listadoPerfil[i];

    			if (item.idPerfil == perfil.idPerfil) {

    				vm.listadoPerfil.splice(i, 1);
    				break;
    			}
    		}
    	}




        function init() {
			cargarCombos();
			listarPerfil();
			limpiar();
			if (angular.isDefined(localStorage.tmpIdUsuario)) {
				var tmpIdUsuario = Number(localStorage.tmpIdUsuario);
				vm.perfilUsuario.idUsuario = Number(localStorage.tmpIdUsuario);

				UsuarioFactory
				.obtener(tmpIdUsuario)
				.then(function (response) {
					if (response.valido) {
						vm.dataUsuario = response.data;
					}
					else {
						toastr.error(response.mensaje);
					}
				})
				.catch(function (error) {
					toastr.error('Error al obtener datos del usuario');
				});
				listarPerfiles();
			}
		}



		init();



		/*fin de metodos*/
	}
})();