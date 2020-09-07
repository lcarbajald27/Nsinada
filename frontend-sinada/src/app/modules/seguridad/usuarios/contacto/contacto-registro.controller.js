(function() {
    'use strict';
    angular.module('spapp.seguridad').controller('ContactoRegistroController', ContactoRegistroController);
    /* @ngInject */
    function ContactoRegistroController($scope, MaestroFactory, ContactoPersonaFactory, toastr, ngDialog, UsuarioFactory, PersonaFactory, EntidadFactory, PerfilFactory, $state) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.dataUsuario = {};
        vm.dataContactoPersona = ContactoPersonaFactory.model();
        vm.listaTipoDatoContacto = [];
        vm.listaContactos = [];
        /*declaracion de metodos */
        vm.guardarContacto = guardarContacto;
        vm.confirmarEliminarContacto = confirmarEliminarContacto;
        vm.regresar = regresar;
        /*implementacion de metodos*/
        function regresar() {
            $state.go('spapp.home.bandeja.denunciante');
        }

        function guardarContacto() {
            if (vm.dataContactoPersona.idContactoPersona == 0) {
                registrarContacto();
            } else {
                actualizarContacto();
            }
        }

        function confirmarEliminarContacto(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar el contacto ' + item.valor + '?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarContacto(item);
            });
        }

        function eliminarContacto(prmData) {
            ContactoPersonaFactory.eliminar(prmData).then(function(response) {
                if (response.valido) {
                    //vm.dataPedido.idPedido = response.data;
                    toastr.success("Se eliminó el Contacto");
                    listarContactoPersona();
                    /*siguiente();*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar Contacto.');
            });
        }

        function validarEmail(valor) {
            //debugger;
            var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
            var res = 0;
            if (emailRegex.test(valor)) {
                res = 1;
            }
            return res;
        }

        function actualizarContacto() {
            if (vm.dataContactoPersona.tipoContacto == 2 && validarEmail(vm.dataContactoPersona.valor) == 0) {
                toastr.info('Debe ingresar un correo válido');
                return;
            }
            ContactoPersonaFactory.actualizar(vm.dataContactoPersona).then(function(response) {
                if (response.valido) {
                    // vm.dataPedido.idPedido = response.data;
                    toastr.success("Se actualizó correctamente.");
                    listarContactoPersona();
                    /* siguiente(); */
                } else {
                    toastr.warning(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar contacto.');
            });
        }

        function registrarContacto() {
            if (vm.dataContactoPersona.tipoContacto == 2 && validarEmail(vm.dataContactoPersona.valor) == 0) {
                toastr.info('Debe ingresar un correo válido');
                return;
            }
            vm.dataContactoPersona.tipoPersona = vm.dataUsuario.tipoPersona;
            /*  if(vm.dataContactoPersona.tipoPersona ==1){*/
            vm.dataContactoPersona.idPersona = vm.dataUsuario.idPersona;
            /*  }*/
            /*if(vm.dataContactoPersona.tipoPersona ==2){
                        vm.dataContactoPersona.idPersona  = vm.dataEntidad.idEntidad;

            }*/
            ContactoPersonaFactory.registrar(vm.dataContactoPersona).then(function(response) {
                if (response.valido) {
                    //vm.dataPedido.idPedido = response.data;
                    vm.dataContactoPersona.idContactoPersona = response.data;
                    toastr.success("Se registró correctamente el Contacto");
                    vm.dataContactoPersona = ContactoPersonaFactory.model();
                    listarContactoPersona();
                    /*siguiente();*/
                } else {
                    toastr.warning(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar Problematica.');
            });
        }

        function listarContactoPersona() {
            vm.dataContactoPersona.tipoPersona = vm.dataUsuario.tipoPersona;
            /*if(vm.dataContactoPersona.tipoPersona ==1){*/
            vm.dataContactoPersona.idPersona = vm.dataUsuario.idPersona;
            /*}


            if(vm.dataContactoPersona.tipoPersona ==2){
                        vm.dataContactoPersona.idPersona  = vm.dataEntidad.idEntidad;

            }*/
            ContactoPersonaFactory.listar(vm.dataContactoPersona).then(function(response) {
                if (response.valido) {
                    vm.listaContactos = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function cargarCombos() {
            var codigosCombos = ['TipoContactoPersona'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoContactoPersona':
                                vm.listaTipoDatoContacto = datosCombo;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos.');
            });
        }
        /*fin de implementacion de metodos*/
        function init() {
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                /*vm.dataUsuario.tipoPersona = 1;
                vm.dataUsuario.idPersona = 17;*/
                debugger;
                vm.dataUsuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                listarContactoPersona();
            }
            cargarCombos();
        }
        init();
        /*fin de controller*/
    }
})();