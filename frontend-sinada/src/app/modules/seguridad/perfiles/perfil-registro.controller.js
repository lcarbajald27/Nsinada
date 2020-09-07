(function() {
    'use strict';
    angular.module('spapp.seguridad').controller('PerfilRegistroController', PerfilRegistroController);
    /* @ngInject */
    function PerfilRegistroController($scope, toastr, ngDialog, UsuarioFactory, PerfilFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.dataPerfil = PerfilFactory.model();
        //declarar variables para guardar los combos desde la bd
        /*vm.listaTiposDocumentoPersona = [];
        vm.listaTiposPerfil = [];
        vm.listaTiposGeneros = [];
        vm.listaTiposUsuario = [];*/
        vm.listaPerfiles = [];
        /*declaración de métodos enlazados a la vista*/
        vm.limpiar = limpiar;
        vm.editarPerfil = editarPerfil;
        vm.dataUsuario = [];
        vm.cancelar = cancelar;
        vm.guardar = guardar;
        vm.eliminar = eliminar;
        vm.confirmarEliminacion = confirmarEliminacion;
        /*	vm.cambiarTipoPersona = cambiarTipoPersona;
        	vm.cambiarTipoPerfil = cambiarTipoPerfil;*/
        /*implementación de métodos*/
        /*
        	function cargarCombos() {
        		var codigosCombos = ['TipoDocumentoPersona','TipoUsuario','TipoGenero','TipoPerfil'];
        		MaestroFactory
        		.buscarPorCodigoTabla(angular.copy(codigosCombos)) 
        		.then(function (response) {
        			if (response!=null&&response.valido)
        			{
        				for(var x in response.data)
        				{
        					var tipoCombo = response.data[x].Key;
        					var datosCombo = response.data[x].Value;
        					switch(tipoCombo)
        					{
        						case 'TipoDocumentoPersona' : vm.listaTiposDocumentoPersona = datosCombo; break;
        						case 'TipoUsuario' : vm.listaTiposUsuario = datosCombo; break;
        						case 'TipoGenero' : vm.listaTiposGeneros = datosCombo; break;
        						case 'TipoPerfil' : vm.listaTiposPerfil = datosCombo; break;
        						default : break;
        					}
        				}
        			}
        		})
        		.catch(function (error) {
        			toastr.error('No se pudo obtener los datos para los combos');
        		});
        	}*/
        function cancelar() {
            //localStorage.removeItem('objLocalUsuario');
            $state.go('vlibrary.home.usuario.listado'); //, { dataAlumno : null }
        }

        function guardar() {
            if (vm.dataPerfil.idPerfil == 0) {
                registrar();
            } else {
                actualizar();
            }
        }
        /*
        		function cambiarTipoPersona() {
        			switch (vm.dataUsuario.persona.idTipoPersona) {
        				case '' : vm.dataUsuario.idTipoPerfil = ''; break;
        				case '1' : vm.dataUsuario.idTipoPerfil = '1'; break;
        				case '2' : vm.dataUsuario.idTipoPerfil = '2'; break;
        				case '3' : vm.dataUsuario.idTipoPerfil = '2'; break;
        				case '4' : vm.dataUsuario.idTipoPerfil = '3'; break;
        				default: vm.dataUsuario.idTipoPerfil = ''; break;
        			}
        		}

        		function cambiarTipoPerfil() {
        			switch (vm.dataUsuario.idTipoPerfil) {
        				case '' : vm.dataUsuario.persona.idTipoPersona = ''; break;
        				case '1' : vm.dataUsuario.persona.idTipoPersona = '1'; break;
        				case '2' : vm.dataUsuario.persona.idTipoPersona = '2'; break;
        				case '3' : vm.dataUsuario.persona.idTipoPersona = '4'; break;
        				default: vm.dataUsuario.persona.idTipoPersona = ''; break;
        			}
        		}
        */
        function limpiar() {
            vm.dataPerfil = PerfilFactory.model();
        }

        function registrar() {
            //	console.log("vm.dataPerfil"+ vm.dataPerfil);
            PerfilFactory.registrar(angular.copy(vm.dataPerfil)).then(function(response) {
                if (response.valido) {
                    vm.dataPerfil.idPerfil = response.data;
                    toastr.success('Perfil registrado con código ' + vm.dataPerfil.idPerfil);
                    listar();
                    limpiar();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Hubo un error al registrar Perfil')
            });
        }

        function actualizar() {
            PerfilFactory.actualizar(angular.copy(vm.dataPerfil)).then(function(response) {
                if (response.valido) {
                    toastr.success(response.mensaje);
                    listar();
                    limpiar();
                } else {
                    toastr.error(response.mensaje)
                }
            }).catch(function(error) {
                toastr.error('Hubo un error al actualizar el Perfil')
            });
        }

        function listar() {
            PerfilFactory.listar().then(function(response) {
                if (response.valido) {
                    vm.listaPerfiles = response.data;
                    //	console.log("vm.listaPerfiles"+ vm.listaPerfiles);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Hubo un error al registrar Perfil')
            });
        }

        function editarPerfil(idPerfil) {
            for (var i = 0; i < vm.listaPerfiles.length; i++) {
                if (idPerfil == vm.listaPerfiles[i].idPerfil) {
                    vm.dataPerfil.idPerfil = vm.listaPerfiles[i].idPerfil;
                    vm.dataPerfil.nombrePerfil = vm.listaPerfiles[i].nombrePerfil;
                }
            }
        }

        function confirmarEliminacion(idPerfil) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar Perfil Numero : ' + idPerfil + '?'
                },
                width: 350,
                className: 'ngdialog-theme-app'
            }).then(function(okValue) {
                eliminar(idPerfil);
            }, function(cancelValue) {});
        }

        function eliminar(idPerfil) {
            vm.dataPerfil.idPerfil = idPerfil;
            PerfilFactory.eliminar(angular.copy(vm.dataPerfil)).then(function(response) {
                if (response.valido) {
                    toastr.success(response.mensaje);
                    listar();
                } else {
                    toastr.error(response.mensaje)
                }
            }).catch(function(error) {
                toastr.error('Hubo un error al actualizar el Perfil')
            });
        }

        function init() {
            /*	cargarCombos();*/
            listar();
            if (angular.isDefined(localStorage.objLocalUsuario)) {
                vm.dataUsuario = angular.fromJson(localStorage.objLocalUsuario);
            }
        }
        init();
        /*fin de los metodos*/
    }
})();