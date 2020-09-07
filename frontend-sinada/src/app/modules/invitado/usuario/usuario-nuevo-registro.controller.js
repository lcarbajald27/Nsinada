(function() {
    'use strict';
    angular.module('spapp.seguridad').controller('UsuariosNuevoRegistroController', UsuariosNuevoRegistroController);
    /* @ngInject */
    function UsuariosNuevoRegistroController($scope, $rootScope, $state, toastr, ngDialog, UsuarioFactory, PersonaFactory, EntidadFactory, UbigeoFactory, MaestroFactory, ContactoPersonaFactory, API_CONFIG, $window, AccesoFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        vm.rutaBase = API_CONFIG.url;
        vm.rutaSSO = API_CONFIG.urlSSO;
        /*fin de configuracion de tablas*/
        vm.flagActivoEdicion = 0;
        vm.flagRegistrarDenunciante = 0;
        vm.flagEditarDenunciante = 0;
        vm.flagAgregarManualmenteRepresentanteLegal = 0;
        vm.dataUbigeo = UbigeoFactory.distrito();
        vm.dataUsuario = UsuarioFactory.modelUsuario();
        vm.dataPersona = PersonaFactory.model();
        vm.dataEntidad = EntidadFactory.model();
        vm.dataContactoPersona = ContactoPersonaFactory.model();
        vm.listaTipoPersona = [];
        vm.listaTipoDocumento = [];
        vm.listaPerfil = [];
        vm.listaTipoDatoContacto = [];
        vm.listaContactos = [];
        vm.listaProvinciaRepresente = [];
        vm.listaDistritoRepresente = [];
        /*declaracion de metodos */
        vm.cancelar = cancelar;
        vm.guardar = guardar;
        vm.buscarPersonaXNumeroDocumento = buscarPersonaXNumeroDocumento;
        vm.buscarEntidadXNumeroDocumento = buscarEntidadXNumeroDocumento;
        vm.nuevaBusqueda = nuevaBusqueda;
        vm.actualizarPersona = actualizarPersona;
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        vm.agregarContacto = agregarContacto;
        vm.confirmarEliminarContacto = confirmarEliminarContacto;
        vm.validaCorreo = validaCorreo;
        vm.limpiar = limpiar;
        vm.registrarDatosPersonaOefa = registrarDatosPersonaOefa;
        vm.iraLogin = iraLogin;
        vm.validaContacto = validaContacto;
        vm.regresarInicio = regresarInicio;
        vm.validarCorreoElectronicoExistenteSSO = validarCorreoElectronicoExistenteSSO;
        vm.registrarDatosContactosUsuario = registrarDatosContactosUsuario;
        vm.buscarRepresentanteLegal = buscarRepresentanteLegal;
        vm.limpiarRepresentanteLegal = limpiarRepresentanteLegal;
        vm.listarProvinciaRepresentanteLegal = listarProvinciaRepresentanteLegal;
        vm.listarDistritoRepresentanteLegal = listarDistritoRepresentanteLegal;
        vm.registrarPersonaRepresentante = registrarPersonaRepresentante;
        vm.cancelarRegistroRepresentanteLegal = cancelarRegistroRepresentanteLegal;
        vm.actualizarEntidadYPersonaGenerico = actualizarEntidadYPersonaGenerico;
        /*implementacion de metodos*/
        function limpiarRepresentanteLegal() {
            vm.dataEntidad.representanteLegal = {};
            vm.listaProvinciaRepresente = [];
            vm.listaDistritoRepresente = [];
        }

        function cancelarRegistroRepresentanteLegal() {
            vm.flagAgregarManualmenteRepresentanteLegal = 0;
            vm.dataEntidad.representanteLegal = {};
            vm.listaProvinciaRepresente = [];
            vm.listaDistritoRepresente = [];
        }

        function registrarPersonaRepresentante() {
            PersonaFactory.registrarPersonaOefa(angular.copy(vm.dataEntidad.representanteLegal)).then(function(response) {
                if (response.valido) {
                    toastr.success('Se registró correctamente al representante legal.');
                    vm.flagAgregarManualmenteRepresentanteLegal = 0;
                    buscarRepresentanteLegal();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function buscarRepresentanteLegal() {
            PersonaFactory.buscarXNumeroDocumento(angular.copy(vm.dataEntidad.representanteLegal)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.dataEntidad.representanteLegal = angular.copy(response.data);
                    }
                } else {
                    toastr.warning('No se encontró al representante legal');
                    vm.flagAgregarManualmenteRepresentanteLegal = 1;
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarProvinciaRepresentanteLegal() {
            var dataProvincia = {
                codigoDepartamento: vm.dataEntidad.representanteLegal.departamento,
            };
            UbigeoFactory.listarProvincia(dataProvincia).then(function(response) {
                if (response.valido) {
                    vm.listaProvinciaRepresente = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarDistritoRepresentanteLegal() {
            var dataDistrito = {
                codigoDepartamento: vm.dataEntidad.representanteLegal.departamento,
                codigoProvincia: vm.dataEntidad.representanteLegal.provincia,
            };
            UbigeoFactory.listarDistrito(dataDistrito).then(function(response) {
                if (response.valido) {
                    vm.listaDistritoRepresente = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function validarUsuarioExitentePorAplicacion(codigoUsuario) {
            var data = {
                prm2: codigoUsuario,
            };
            AccesoFactory.buscarUsuarioPorIdAplicacion(angular.copy(data)).then(function(response) {
                if (response.valido) {
                    toastr.warning(response.mensaje);
                    limpiar();
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function registrarDatosContactosUsuario() {
            if (vm.dataContactoPersona.tipoContacto == 2) {
                validarCorreoElectronicoExistenteSSO();
            } else {
                agregarContacto();
            }
        }

        function validaContacto() {
            ContactoPersonaFactory.validarContactoPersona(vm.dataContactoPersona).then(function(response) {
                if (response.valido) {
                    toastr.info(response.mensaje);
                } else {
                    agregarContacto();
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            });
        }

        function validarCorreoElectronicoExistenteSSO() {
            var data = {
                prm2: vm.dataContactoPersona.valor,
            };
            AccesoFactory.validarCorreoElectronicoSSO(angular.copy(data)).then(function(response) {
                if (response.valido) {
                    toastr.warning(response.mensaje);
                } else {
                    agregarContacto();
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function regresarInicio() {
            $state.go('ingreso');
        }

        function iraLogin() {
            $window.location.href = vm.rutaSSO + "/Home/Index?wa=wsignin1.0&wtrealm=" + vm.rutaBase + "seguridad/index/&wctx=rm=2&id=pluralsoft&ru=" + vm.rutaBase + "seguridad/confirmsso&wreply=" + vm.rutaBase + "seguridad/confirmsso";
            //          $window.location.href = "http://200.37.65.227/OEFA.STS/Home/Index?wa=wsignin1.0&wtrealm=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/index/&wctx=rm=2&id=pluralsoft&ru=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso&wreply=http://10.1.1.56:8380/oefa-sinada-web/rest/api/seguridad/confirmsso";
            /*  console.log("pasa");
                $state.go('login');*/
        }

        function limpiar() {
            vm.dataUsuario = UsuarioFactory.modelUsuario();
            vm.dataPersona = PersonaFactory.model();
            vm.dataEntidad = EntidadFactory.model();
            vm.dataContactoPersona = ContactoPersonaFactory.model();
            vm.listaContactos = [];
            vm.dataUsuario.tipoPersona = '1';
            vm.flagRegistrarDenunciante = 0;
            vm.flagEditarDenunciante = 0;
            vm.flagActivoEdicion = 0;
            vm.dataUbigeo = UbigeoFactory.distrito();
            vm.listaProvincia = [];
            vm.listaDistrito = [];
        }

        function validarEmail(valor) {
            var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
            var res = 0;
            if (emailRegex.test(valor)) {
                res = 1;
            }
            return res;
        }

        function validaCorreo() {
            if (vm.dataContactoPersona.tipoContacto == 2) {
                var miCadena = vm.dataContactoPersona.valor;
                var posicion = miCadena.indexOf("@");
                var cuenta = 0;
                while (posicion != -1) {
                    cuenta++;
                    posicion = miCadena.indexOf("@", posicion + 1);
                }
                return cuenta;
            }
        }

        function validaRegistrarUsuario() {
            var mensaje = "Por favor ingrese un correo para seguir con el registro.";
            var z = 0;
            for (var x in vm.listaContactos) {
                if (vm.listaContactos[x].tipoContacto == 2) {
                    z = 1;
                }
            }
            if (z == 0) {
                toastr.info(mensaje);
                return z;
            } else {
                return z;
            }
        }

        function confirmarEliminarContacto(index) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar el registro?'
                },
                width: '380px'
            }).then(function(okValue) {
                vm.listaContactos.splice(index, 1);
            });
        }

        function agregarContacto() {
            if (vm.dataContactoPersona.tipoContacto == 2 && validarEmail(vm.dataContactoPersona.valor) == 0 || vm.dataContactoPersona.tipoContacto == 1 && vm.dataContactoPersona.valor.length <= 7) {
                toastr.info("Debe ingresar un contacto válido");
            } else {
                vm.listaContactos.push(angular.copy(vm.dataContactoPersona));
                vm.dataContactoPersona = ContactoPersonaFactory.model();
            }
        }

        function nuevaBusqueda() {
            vm.dataPersona = PersonaFactory.model();
            vm.dataEntidad = EntidadFactory.model();
            vm.listaContactos = [];
            vm.dataUsuario = UsuarioFactory.modelUsuario();
            vm.dataUsuario.tipoPersona = '1';
            vm.dataUbigeo = UbigeoFactory.distrito();
            vm.dataContactoPersona.tipoContacto = 0;
            vm.flagActivoEdicion = 0;
            vm.listaProvincia = [];
            vm.listaDistrito = [];
        }

        function validaDatosPersona() {
            if (vm.dataPersona.departamento == null || vm.dataPersona.departamento == '' || vm.dataPersona.departamento.length == 0) {
                vm.flagActivoEdicion = 1;
            }
            if (vm.dataPersona.provincia == null || vm.dataPersona.provincia == '' || vm.dataPersona.provincia.length == 0) {
                vm.flagActivoEdicion = 1;
            }
            if (vm.dataPersona.provincia == null || vm.dataPersona.provincia == '' || vm.dataPersona.provincia.length == 0) {
                vm.flagActivoEdicion = 1;
            }
            if (vm.dataPersona.direccion == null || vm.dataPersona.direccion == '' || vm.dataPersona.direccion.length == 0) {
                vm.flagActivoEdicion = 1;
            }
        }

        function cargarCombos() {
            var codigosCombos = ['TipoDocumentoPersona', 'TipoPersonaDenuncia', 'TipoContactoPersona'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoDocumentoPersona':
                                vm.listaTipoDocumento = datosCombo;
                                break;
                            case 'TipoPersonaDenuncia':
                                vm.listaTipoPersona = datosCombo;
                                break;
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
            }).finally(function() {});
        }

        function listarDepartamento() {
            var dataDepartamento = {};
            UbigeoFactory.listarDepartamento(dataDepartamento).then(function(response) {
                if (response.valido) {
                    vm.listaDepartamento = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarProvincia() {
            var dataProvincia = {
                codigoDepartamento: vm.dataUbigeo.codigoDepartamento,
            };
            UbigeoFactory.listarProvincia(dataProvincia).then(function(response) {
                if (response.valido) {
                    vm.listaProvincia = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.dataPersona.departamento != null && vm.dataPersona.provincia != null) {
                    listarDistrito();
                }
            });
        }

        function listarDistrito() {
            var dataDistrito = {
                codigoDepartamento: vm.dataUbigeo.codigoDepartamento,
                codigoProvincia: vm.dataUbigeo.codigoProvincia,
            };
            UbigeoFactory.listarDistrito(dataDistrito).then(function(response) {
                if (response.valido) {
                    vm.listaDistrito = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function guardar() {
            if (validaRegistrarUsuario() != 0) {
                if (vm.dataUsuario.idUsuario == 0) {
                    registrar();
                } else {
                    actualizar();
                }
            }
        }

        function buscarPersonaXNumeroDocumento() {
            //  console.log('sdadsasad');
            PersonaFactory.buscarXNumeroDocumento(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.flagRegistrarDenunciante = 0;
                        vm.dataPersona = response.data;
                        validaDatosPersona();
                        vm.dataUsuario.persona = vm.dataPersona;
                        if (vm.dataPersona.departamento != null && vm.dataPersona.departamento.length == 2) {
                            vm.dataUbigeo.codigoDepartamento = vm.dataPersona.departamento;
                        }
                        if (vm.dataPersona.provincia != null && vm.dataPersona.provincia.length == 2) {
                            vm.dataUbigeo.codigoProvincia = vm.dataPersona.provincia;
                        }
                        if (vm.dataPersona.distrito != null && vm.dataPersona.distrito.length == 2) {
                            vm.dataUbigeo.codigoDistrito = vm.dataPersona.distrito;
                        }
                        validarUsuarioExitentePorAplicacion(vm.dataPersona.documento);
                    }
                } else {
                    //toastr.error(response.mensaje);
                    dialogMensaje();
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.dataPersona.departamento != null && vm.dataPersona.departamento != '' && vm.dataPersona.provincia != null && vm.dataPersona.provincia != '' && vm.dataPersona.distrito != null && vm.dataPersona.distrito != '') {
                    listarProvincia();
                }
            });
        }

        function validaDatosEntidad() {
            if (vm.dataEntidad.departamento == null || vm.dataEntidad.departamento == '' || vm.dataEntidad.departamento.length == 0) {
                vm.flagActivoEdicion = 1;
            }
            if (vm.dataEntidad.provincia == null || vm.dataEntidad.provincia == '' || vm.dataEntidad.provincia.length == 0) {
                vm.flagActivoEdicion = 1;
            }
            if (vm.dataEntidad.distrito == null || vm.dataEntidad.distrito == '' || vm.dataEntidad.distrito.length == 0) {
                vm.flagActivoEdicion = 1;
            }
            if (vm.dataEntidad.direccion == null || vm.dataEntidad.direccion == '' || vm.dataEntidad.direccion.length == 0) {
                vm.flagActivoEdicion = 1;
            }
        }

        function buscarEntidadXNumeroDocumento() {
            EntidadFactory.buscarXEntidadNumeroDocumento(vm.dataEntidad).then(function(response) {
                if (response.valido) {
                    /*vm.dataEntidad= response.data;*/
                    if (response.data.idEntidad > 0) {
                        vm.flagRegistrarDenunciante = 0;
                        vm.dataEntidad = response.data;
                        vm.dataUsuario.entidad = vm.dataEntidad;
                        validaDatosEntidad();
                        if (vm.dataEntidad.departamento != null && vm.dataEntidad.departamento.length == 2) {
                            vm.dataUbigeo.codigoDepartamento = vm.dataEntidad.departamento;
                        }
                        if (vm.dataEntidad.provincia != null && vm.dataEntidad.provincia.length == 2) {
                            vm.dataUbigeo.codigoProvincia = vm.dataEntidad.provincia;
                        }
                        if (vm.dataEntidad.distrito != null && vm.dataEntidad.distrito.length == 2) {
                            vm.dataUbigeo.codigoDistrito = vm.dataEntidad.distrito;
                        }
                        validarUsuarioExitentePorAplicacion(vm.dataEntidad.ruc);
                    }
                } else {
                    //toastr.error(response.mensaje);
                    dialogMensaje();
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.dataEntidad.departamento != null && vm.dataEntidad.departamento != '' && vm.dataEntidad.provincia != null && vm.dataEntidad.provincia != '' && vm.dataEntidad.distrito != null && vm.dataEntidad.distrito != '') {
                    listarProvincia();
                }
            });
        }

        function registrar() {
            /* vm.dataUsuario.idTipoPerfil = vm.dataPerfil.idPerfil;*/
            vm.dataUsuario.lstContactoPersona = vm.listaContactos;
            UsuarioFactory.registrar(vm.dataUsuario).then(function(response) {
                if (response.valido) {
                    VisualizarModalExito();
                    /*   toastr.success('Usuario registrado correctamente ');*/
                    $state.go('ingreso');
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Hubo un error al registrar usuario.')
            });
        }

        function actualizar() {
            UsuarioFactory.actualizar(vm.dataUsuario).then(function(response) {
                if (response.valido) {
                    toastr.success(response.mensaje);
                } else {
                    toastr.error(response.mensaje)
                }
            }).catch(function(error) {
                toastr.error('Hubo un error al actualizar el usuario')
            });
        }

        function VisualizarModalExito() {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-info/dialog-info.html',
                controller: 'DialogInfoController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Usuario registrado',
                    Mensaje: 'Se ha registrado con éxito al Servicio de Información Nacional de Denuncias Ambientales (SINADA), por favor revise sus credenciales de acceso en su correo de contacto.'
                },
                width: '380px'
            }).finally(function() {});
        }

        function actualizarEntidadYPersonaGenerico() {
            if (vm.dataUsuario.tipoPersona == 1) {
                actualizarPersona();
            } else {
                actualizarEntidad();
            }
        }

        function actualizarPersona() {
            vm.dataPersona.departamento = vm.dataUbigeo.codigoDepartamento;
            vm.dataPersona.provincia = vm.dataUbigeo.codigoProvincia;
            vm.dataPersona.distrito = vm.dataUbigeo.codigoDistrito;
            PersonaFactory.actualizar(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.dataPersona = response.data;
                        vm.flagActivoEdicion = 0;
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                buscarPersonaXNumeroDocumento();
            });
        }

        function actualizarEntidad() {
            vm.dataEntidad.departamento = vm.dataUbigeo.codigoDepartamento;
            vm.dataEntidad.provincia = vm.dataUbigeo.codigoProvincia;
            vm.dataEntidad.distrito = vm.dataUbigeo.codigoDistrito;
            EntidadFactory.actualizar(angular.copy(vm.dataEntidad)).then(function(response) {
                if (response.valido) {
                    if (response.data.idEntidad > 0) {
                        vm.dataEntidad = response.data;
                        vm.flagActivoEdicion = 0;
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                buscarEntidadXNumeroDocumento();
            });
        }

        function cancelar() {
            $state.go('ingreso');
        }

        function dialogMensaje() {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-denunciado/dialog-mensaje-denunciado.html',
                controller: 'DialogMensajeDenunciadoController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Denunciante no encontrado',
                    Mensaje: 'El servicio no se encuentra disponible, desea registrar manualmente.'
                },
                width: '380px'
            }).finally(function() {
                if ($rootScope.tipoOperacionDenunciado == 2) {
                    $rootScope.tipoOperacionDenunciado = 0;
                    vm.flagRegistrarDenunciante = 1;
                    vm.flagEditarDenunciante = 1;
                } else {
                    vm.flagRegistrarDenunciante = 0;
                    vm.flagEditarDenunciante = 0;
                }
            });
        }

        function registrarDatosPersonaOefa() {
            if (vm.dataUsuario.tipoPersona == 1) {
                registrarPersonaOefa();
            } else {
                registrarEntidadOefa();
            }
        }

        function registrarEntidadOefa() {
            vm.dataEntidad.departamento = vm.dataUbigeo.codigoDepartamento;
            vm.dataEntidad.provincia = vm.dataUbigeo.codigoProvincia;
            vm.dataEntidad.distrito = vm.dataUbigeo.codigoDistrito;
            EntidadFactory.registrarEntidadOefa(vm.dataEntidad).then(function(response) {
                if (response.valido) {
                    toastr.success('Se registró correctamente la entidad');
                    vm.flagRegistrarDenunciante = 0;
                    buscarEntidadXNumeroDocumento();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function registrarPersonaOefa() {
            vm.dataPersona.departamento = vm.dataUbigeo.codigoDepartamento;
            vm.dataPersona.provincia = vm.dataUbigeo.codigoProvincia;
            vm.dataPersona.distrito = vm.dataUbigeo.codigoDistrito;
            PersonaFactory.registrarPersonaOefa(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    toastr.success('Se registró correctamente la persona');
                    vm.flagRegistrarDenunciante = 0;
                    buscarPersonaXNumeroDocumento();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /*fin de implementacion de metodos*/
        function init() {
            //VisualizarModalExito();
            $rootScope.swBuscar = false;
            $rootScope.tituloBtnRegistrar = "Si";
            vm.dataUsuario.tipoPersona = "1";
            cargarCombos();
            listarDepartamento();
        }
        init();
        /*fin de controller*/
    }
})();