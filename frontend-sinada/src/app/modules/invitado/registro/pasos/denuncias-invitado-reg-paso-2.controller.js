(function() {
    'use strict';
    angular.module('spapp.invitado').controller('DenunciasInvitadoRegPaso2Controller', DenunciasInvitadoRegPaso2Controller);
    /* @ngInject */
    function DenunciasInvitadoRegPaso2Controller($scope, $rootScope, $state, toastr, ngDialog, DenunciaFactory, PersonaFactory, EntidadFactory, MaestroFactory, UbigeoFactory, ContactoPersonaFactory, API_CONFIG) {
        var vm = this;
        /* declaracion de variables globales */
        /* Configuracion de paginacion de tablas */
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /* fin de configuracion de tablas */
        vm.idPerfilDenunciante = API_CONFIG.idPerfilDenunciante;
        vm.flagAgregarManualmenteRepresentanteLegal = 0;
        vm.flagEdicionDenunciante = 0;
        vm.flagUbigeoDenunciante = 0;
        vm.flagRegistrarDenunciante = 0;
        vm.itemTab = 0;
        vm.denuncia = DenunciaFactory.model();
        vm.dataPersona = PersonaFactory.model();
        vm.dataEntidad = EntidadFactory.model();
        vm.dataDenunciante = {};
        vm.listaDenunciante = [];
        vm.dataDenuncia = {
            tipoPersona: 0,
            tipoDocumento: 0
        };
        vm.dataUbigeo = UbigeoFactory.distrito();
        vm.dataContactoPersona = ContactoPersonaFactory.model();
        vm.departamento = UbigeoFactory.departamento();
        vm.provincia = UbigeoFactory.provincia();
        vm.distrito = UbigeoFactory.distrito();
        $rootScope.pasoActivo = 2;
        vm.pasoActivo = 2;
        vm.listaTipoPersona = [];
        vm.listaTipoDocumento = [];
        vm.listaDepartamento = [];
        vm.listaProvincia = [];
        vm.listaDistrito = [];
        vm.listaTipoDatoContacto = [];
        vm.listaContactos = [];
        vm.listaTotalContactos = [];
        vm.listaCargo = [];
        vm.dataBandeja = null;
        vm.listaProvinciaRepresente = [];
        vm.listaDistritoRepresente = [];
        /* declaracion de metodos */
        vm.siguiente = siguiente;
        vm.regresar = regresar;
        vm.buscarPersonaXNumeroDocumento = buscarPersonaXNumeroDocumento;
        vm.buscarEntidadXNumeroDocumento = buscarEntidadXNumeroDocumento;
        vm.agregarDenunciante = agregarDenunciante;
        vm.eliminarPorPosicion = eliminarPorPosicion;
        vm.nuevaBusqueda = nuevaBusqueda;
        vm.guardarContacto = guardarContacto;
        vm.editarDenunciante = editarDenunciante;
        vm.editarContacto = editarContacto;
        vm.confirmarEliminarContacto = confirmarEliminarContacto;
        vm.actualizarPersona = actualizarPersona;
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        vm.registrarDatosPersonaOefa = registrarDatosPersonaOefa;
        vm.confirmarEliminarDenunciante = confirmarEliminarDenunciante;
        vm.buscarRepresentanteLegal = buscarRepresentanteLegal;
        vm.limpiarRepresentanteLegal = limpiarRepresentanteLegal;
        vm.listarProvinciaRepresentanteLegal = listarProvinciaRepresentanteLegal;
        vm.listarDistritoRepresentanteLegal = listarDistritoRepresentanteLegal;
        vm.cancelarRegistroRepresentanteLegal = cancelarRegistroRepresentanteLegal;
        vm.registrarPersonaRepresentante = registrarPersonaRepresentante;
        vm.actualizarEntidad = actualizarEntidad;
        /* implementacion de metodos */
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

        function cancelarRegistroRepresentanteLegal() {
            vm.flagAgregarManualmenteRepresentanteLegal = 0;
            vm.dataEntidad.representanteLegal = {};
            vm.listaProvinciaRepresente = [];
            vm.listaDistritoRepresente = [];
        }

        function listarProvinciaRepresentanteLegal() {
            var dataProvincia = {
                codigoDepartamento: vm.dataEntidad.representanteLegal.departamento,
            };
            UbigeoFactory.listarProvincia(angular.copy(dataProvincia)).then(function(response) {
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
            UbigeoFactory.listarDistrito(angular.copy(dataDistrito)).then(function(response) {
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

        function limpiarRepresentanteLegal() {
            vm.dataEntidad.representanteLegal = {};
            vm.listaProvinciaRepresente = [];
            vm.listaDistritoRepresente = [];
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

        function validarPerfil(idPerfil) {
            var c = 0;
            var a = 0;
            var b = 0;
            for (var x in $rootScope.lstPerfilesUsuario) {
                if ($rootScope.lstPerfilesUsuario[x].pk_eIdPerfil == idPerfil) {
                    a = 1;
                } else {
                    b = b + 1;
                }
            }
            if (a == 1 && b == 0) {
                c = 1;
            } else {
                c = 0;
            }
            return c;
        }

        function editarDenunciante(prmDenunciante) {
            vm.dataDenuncia.tipoPersona = angular.copy(prmDenunciante.tipoPersona);
            vm.dataDenuncia.tipoDocumento = angular.copy(vm.dataDenuncia.tipoPersona);
            if (prmDenunciante.tipoPersona == 1) {
                vm.dataPersona = angular.copy(prmDenunciante.personaDenunciante);
                if (vm.dataPersona.idPersona > 0) {
                    buscarPersonaXNumeroDocumento();
                    /* listarProvincia();
                     listarContactoPersona();*/
                }
            }
            if (prmDenunciante.tipoPersona == 2) {
                vm.dataEntidad = angular.copy(prmDenunciante.entidadDenunciante);
                if (vm.dataEntidad.idEntidad > 0) {
                    buscarEntidadXNumeroDocumento();
                }
            }
            vm.itemTab = 1;
        }

        function cargarCombos() {
            var codigosCombos = ['TipoDocumentoPersona', 'TipoPersonaDenuncia', 'TipoContactoPersona', 'CargoSinada'];
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
                            case 'CargoSinada':
                                vm.listaCargo = datosCombo;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos.');
            }).finally(function() {
                // console.log("listaTipoDocumento",vm.listaTipoDocumento);
            });
        }

        function editarContacto(prmData) {
            vm.dataContactoPersona = prmData;
        }

        function validarEmail(valor) {
            // debugger;
            var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
            var res = 0;
            if (emailRegex.test(valor)) {
                res = 1;
            }
            return res;
        }

        function buscarNombre(lista, dato) {
            if (lista != null && lista != undefined) {
                for (var i = 0; i < lista.length; i++) {
                    if (lista[i].valor.trim() == dato.trim()) {
                        return i;
                    }
                }
            }
            return -1;
        }

        function guardarContacto() {
            
            if (vm.dataContactoPersona.tipoContacto == 1 && (vm.dataContactoPersona.valor.length < 7 || vm.dataContactoPersona.valor.length > 15)) {
                toastr.info('Debe ingresar un teléfono valido, de entre 7 a 15 dígitos.');
                return;
            }
            if (vm.dataContactoPersona.tipoContacto == 2 && validarEmail(vm.dataContactoPersona.valor) == 0) {
                toastr.info('Debe ingresar un correo válido');
                return;
            }

            if (buscarNombre(angular.copy(vm.listaContactos), vm.dataContactoPersona.valor) != -1) {
                toastr.info('El contacto ya se encuentra registrado.');
                return;
            }
            if (vm.dataContactoPersona.idContactoPersona == 0) {
                registrarContacto();
            } else {
                actualizarContacto();
            }
        }

        function actualizarContacto() {
            ContactoPersonaFactory.actualizar(angular.copy(vm.dataContactoPersona)).then(function(response) {
                if (response.valido) {
                    // vm.dataPedido.idPedido = response.data;
                    toastr.success("Se actualizó correctamente.");
                    listarContactoPersona(1);
                    /* siguiente(); */
                } else {
                    toastr.warning(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar contacto.');
            });
        }

        function confirmarEliminarContacto(item) {
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
                eliminarContacto(item);
            });
        }

        function confirmarEliminarDenunciante(item) {
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
                eliminarPorPosicion(item);
            });
        }

        function eliminarContacto(prmData) {
            console.log("prmData", prmData);
            console.log(" vm.listaTotalContactos", vm.listaTotalContactos);
            //tipoPersona
            //tipoContacto
            var listaTotalContactosTempotal = [];
            for (var i = 0; i < vm.listaTotalContactos.length; i++) {
                if (prmData.idPersona == vm.listaTotalContactos[i][0].idPersona) {
                    //Se encuentra en esta lista
                    var tmpLista = [];
                    for (var j = 0; j < vm.listaTotalContactos[i].length; j++) {
                        //Remover 
                        if (prmData.idContactoPersona == vm.listaTotalContactos[i][j].idContactoPersona) {
                            //Remover 
                            console.log("remover", vm.listaTotalContactos[i][j]);
                        } else {
                            tmpLista.push(angular.copy(vm.listaTotalContactos[i][j]));
                        }
                        console.log("vm.listaTotalContactos[i]", vm.listaTotalContactos[i]);
                        console.log("vm.listaTotalContactos[i][j]", vm.listaTotalContactos[i][j]);
                    }
                    if (tmpLista.length > 0) {
                        listaTotalContactosTempotal.push(angular.copy(tmpLista));
                    }
                } else {
                    listaTotalContactosTempotal.push(angular.copy(vm.listaTotalContactos[i]));
                }
            }
            vm.listaTotalContactos = angular.copy(listaTotalContactosTempotal);
            ContactoPersonaFactory.eliminar(angular.copy(prmData)).then(function(response) {
                if (response.valido) {
                    // vm.dataPedido.idPedido = response.data;
                    console.log("vm.listaTotalContactos", vm.listaTotalContactos)
                    toastr.success("Se eliminó correctamente.");
                    listarContactoPersona(0);
                    /* siguiente(); */
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al eliminar contacto.');
            });
        }

        function registrarContacto() {
            vm.dataContactoPersona.tipoPersona = vm.dataDenuncia.tipoPersona;
            if (vm.dataContactoPersona.tipoPersona == 1) {
                vm.dataContactoPersona.idPersona = vm.dataPersona.idPersona;
            }
            if (vm.dataContactoPersona.tipoPersona == 2) {
                vm.dataContactoPersona.idPersona = vm.dataEntidad.idEntidad;
            }
            ContactoPersonaFactory.registrar(angular.copy(vm.dataContactoPersona)).then(function(response) {
                if (response.valido) {
                    // vm.dataPedido.idPedido = response.data;
                    vm.dataContactoPersona.idContactoPersona = response.data;
                    toastr.success("Se registró correctamente.");
                    vm.dataContactoPersona = ContactoPersonaFactory.model();
                    listarContactoPersona(1);
                    /* siguiente(); */
                } else {
                    toastr.warning(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar problemática.');
            });
        }

        function listarContactoPersona(item) {
            vm.dataContactoPersona.tipoPersona = vm.dataDenuncia.tipoPersona;
            if (vm.dataContactoPersona.tipoPersona == 1) {
                vm.dataContactoPersona.idPersona = vm.dataPersona.idPersona;
            }
            if (vm.dataContactoPersona.tipoPersona == 2) {
                vm.dataContactoPersona.idPersona = vm.dataEntidad.idEntidad;
            }
            if (vm.dataContactoPersona.tipoPersona == 3) {
                vm.dataContactoPersona.idPersona = vm.dataEntidad.idEntidad;
            }
            ContactoPersonaFactory.listar(angular.copy(vm.dataContactoPersona)).then(function(response) {
                if (response.valido) {
                    vm.listaContactos = response.data;
                    if (item == 1) {
                        console.log("agregarListaContactoTotal");
                        agregarListaContactoTotal(vm.listaContactos);
                    }
                    /* vm.dataDenuncia.tipoPersona=0; */
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {});
        }

        function agregarListaContactoTotal(listaContactos) {
            debugger;
            console.log("listaContactos");
            console.log(listaContactos);
            console.log("vm.listaTotalContactos");
            console.log(vm.listaTotalContactos);
            var tmpListaTotalContactos = [];
            var exite = false;
            var contador = 0;
            //Identificar si exiten registros en vm.vm.listaTotalContactos
            if (listaContactos.length > 0) {
                if ((vm.listaTotalContactos != []) && (vm.listaTotalContactos.length > 0)) {
                    for (var i = 0; i < vm.listaTotalContactos.length; i++) {
                        console.log("vm.listaTotalContactos[i][0].idPersona", vm.listaTotalContactos[i][0].idPersona);
                        console.log("listaContactos.idPersona", listaContactos[0].idPersona);
                        if (angular.equals(vm.listaTotalContactos[i][0].idPersona, listaContactos[0].idPersona)) {
                            tmpListaTotalContactos.push(angular.copy(listaContactos));
                            console.log(vm.listaTotalContactos[i][0].idPersona);
                            console.log(listaContactos.idPersona);
                            console.log("es igual");
                            exite = true;
                            contador = contador + 1;
                        } else {
                            console.log("nuevo");
                            tmpListaTotalContactos.push(angular.copy(vm.listaTotalContactos[i]));
                        }
                    }
                    if (!exite) {
                        tmpListaTotalContactos.push(angular.copy(listaContactos));
                    }
                    vm.listaTotalContactos = angular.copy(tmpListaTotalContactos);
                } else {
                    console.log("primero");
                    tmpListaTotalContactos.push(angular.copy(listaContactos));
                    vm.listaTotalContactos = angular.copy(tmpListaTotalContactos);
                }
                console.log("vm.listaTotalContactos");
                console.log(vm.listaTotalContactos);
            }
        }
        /*
        function eliminarListaContactoTotal(listaContactos) {
            var tmpListaTotalContactos = [];
            //Identificar si exiten registros en vm.vm.listaTotalContactos
            if ((vm.listaTotalContactos != []) && (vm.listaTotalContactos.length > 0)) {
                for (var i = 0; i < vm.listaTotalContactos.length; i++) {
                    if (!(angular.equals(vm.listaTotalContactos[i].idPersona, listaContactos.idPersona))) {
                        tmpListaTotalContactos.push(angular.copy(vm.listaTotalContactos[i]));
                    }
                }
            }
            vm.listaTotalContactos = angular.copy(tmpListaTotalContactos);
            console.log(vm.listaTotalContactos);
        }*/
        function listarDepartamento() {
            var dataDepartamento = {};
            UbigeoFactory.listarDepartamento(angular.copy(dataDepartamento)).then(function(response) {
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
            UbigeoFactory.listarProvincia(angular.copy(dataProvincia)).then(function(response) {
                if (response.valido) {
                    vm.listaProvincia = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.dataPersona.departamento != null && vm.dataPersona.provincia != null && vm.dataPersona.departamento != '' && vm.dataPersona.provincia != '' && vm.dataPersona.departamento.length != 0 && vm.dataPersona.provincia.length != 0) {
                    listarDistrito();
                }
                debugger;
                if (vm.dataEntidad.departamento != null && vm.dataEntidad.departamento != '' && vm.dataEntidad.provincia != null && vm.dataEntidad.provincia != '' && vm.dataEntidad.distrito != null && vm.dataEntidad.distrito != '') {
                    listarDistrito();
                }
            });
        }

        function listarDistrito() {
            debugger;
            var dataDistrito = {
                codigoDepartamento: vm.dataUbigeo.codigoDepartamento,
                codigoProvincia: vm.dataUbigeo.codigoProvincia,
            };
            UbigeoFactory.listarDistrito(angular.copy(dataDistrito)).then(function(response) {
                if (response.valido) {
                    vm.listaDistrito = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.dataDenuncia.tipoPersona == 1 && vm.flagEdicionDenunciante == 0 && vm.flagRegistrarDenunciante == 0) {
                    agregarDenunciante();
                }
            });
        }
        /*     function listarDepartamento() {
                 UbigeoFactory.listarDepartamento(vm.departamento).then(function(response) {
                     if (response.valido) {
                         vm.listaDepartamento = response.data;
                    
                     } else {
                         toastr.error(response.mensaje);
                     }
                 }).catch(function(error) {
                     toastr.error('Error al consultar.');
                 }).finally(function() {});
             }

             function listarProvincia() {
                 vm.provincia.codigoDepartamento = vm.dataPersona.departamento;
                 console.log(vm.dataPersona.departamento);
                 if (vm.dataPersona.departamento != '') {
                     UbigeoFactory.listarProvincia(vm.provincia).then(function(response) {
                         if (response.valido) {
                             vm.listaProvincia = response.data;
                       
                         } else {
                             toastr.error(response.mensaje);
                         }
                     }).catch(function(error) {
                         toastr.error('Error al consultar.');
                     }).finally(function() {
                         if (vm.dataPersona.departamento != null && vm.dataPersona.provincia != null) {
                             listarDistrito();
                         }
                     });
                 } else {
                     vm.listaProvincia = [];
                     vm.listaDistrito = [];
                 }
             }

            
             }*/
        function nuevaBusqueda() {
            vm.dataPersona = PersonaFactory.model();
            vm.dataEntidad = EntidadFactory.model();
            vm.listaContactos = [];
            vm.listaProvincia = [];
            vm.listaDistrito = [];
            vm.dataUbigeo = UbigeoFactory.distrito();
            vm.flagRegistrarDenunciante = 0;
            vm.flagEdicionDenunciante = 0;
            vm.flagUbigeoDenunciante = 0;
        }

        function siguiente() {
            debugger;
            console.log(vm.listaDenunciante);
            if (vm.listaTotalContactos.length != [] && vm.listaTotalContactos.length > 0) {
                if (vm.listaTotalContactos.length != vm.listaDenunciante.length) {
                    for (var i = 0; i < vm.listaDenunciante.length; i++) {
                        var tieneCorreo = false;
                        for (var j = 0; j < vm.listaTotalContactos.length; j++) {
                            if (vm.listaDenunciante[i].idPersona == vm.listaTotalContactos[j][0].idPersona) {
                                tieneCorreo = true;
                            }
                        }
                        if (!tieneCorreo) {
                            var nombre = "";
                            if (vm.listaDenunciante[i].tipoPersona == "1") {
                                nombre = angular.copy(vm.listaDenunciante[i].personaDenunciante.nombres + " " + vm.listaDenunciante[i].personaDenunciante.apellidoPaterno + " " + vm.listaDenunciante[i].personaDenunciante.apellidoMaterno);
                            } else {
                                nombre = angular.copy(vm.listaDenunciante[i].entidadDenunciante.razonSocial);
                            }
                            toastr.warning("Falta ingresar correo a " + nombre);
                        }
                    }
                    return;
                }
                for (var i = 0; i < vm.listaTotalContactos.length; i++) {
                    var tieneCorreo = false;
                    for (var j = 0; j < vm.listaTotalContactos[i].length; j++) {
                        if (vm.listaTotalContactos[i][j].tipoContacto == 2) {
                            tieneCorreo = true;
                        }
                    }
                    if (!tieneCorreo) {
                        var nombre = "";
                        for (var k = 0; k < vm.listaDenunciante.length; k++) {
                            if (vm.listaDenunciante[k].idPersona == vm.listaTotalContactos[i][0].idPersona) {
                                if (vm.listaDenunciante[k].tipoPersona == "1") {
                                    nombre = angular.copy(vm.listaDenunciante[k].personaDenunciante.nombres + " " + vm.listaDenunciante[k].personaDenunciante.apellidoPaterno + " " + vm.listaDenunciante[k].personaDenunciante.apellidoMaterno);
                                } else {
                                    nombre = angular.copy(vm.listaDenunciante[k].entidadDenunciante.razonSocial);
                                }
                                break;
                            }
                        }
                        toastr.warning("Falta ingresar correo a " + nombre);
                        return;
                    }
                }
                //if ((vm.listaTotalContactos != []) && (vm.listaTotalContactos.length > 0)) {
                /*       if (vm.listaTotalContactos.length != [] && vm.listaTotalContactos.length > 0) {
                           if (vm.listaTotalContactos.length != vm.listaDenunciante.length) {
                               for (var i = 0; i < vm.listaDenunciante.length; i++) {
                                   var tieneCorreo = false;
                                   for (var j = 0; j < vm.listaTotalContactos.length; j++) {
                                       if (vm.listaDenunciante[i].idPersona == vm.listaTotalContactos[j][0].idPersona) {
                                           tieneCorreo = true;
                                       }
                                   }
                                   if (!tieneCorreo) {
                                       var nombre = "";
                                       if (vm.listaDenunciante[i].tipoPersona == "1") {
                                           nombre = angular.copy(vm.listaDenunciante[i].personaDenunciante.nombres + " " + vm.listaDenunciante[i].personaDenunciante.apellidoPaterno + " " + vm.listaDenunciante[i].personaDenunciante.apellidoMaterno);
                                       } else {
                                           nombre = angular.copy(vm.listaDenunciante[i].entidadDenunciante.razonSocial);
                                       }
                                       toastr.warning("Falta ingresar correo a " + nombre);
                                   }
                               }
                               return;
                           }
                           for (var i = 0; i < vm.listaTotalContactos.length; i++) {
                               var tieneCorreo = false;
                               for (var j = 0; j < vm.listaTotalContactos[i].length; j++) {
                                   if (vm.listaTotalContactos[i][j].tipoContacto == 2) {
                                       tieneCorreo = true;
                                   }
                               }
                               if (!tieneCorreo) {
                                   var nombre = "";
                                   for (var k = 0; k < vm.listaDenunciante.length; k++) {
                                       if (vm.listaDenunciante[k].idPersona == vm.listaTotalContactos[i][0].idPersona) {
                                           if (vm.listaDenunciante[k].tipoPersona == "1") {
                                               nombre = angular.copy(vm.listaDenunciante[k].personaDenunciante.nombres + " " + vm.listaDenunciante[k].personaDenunciante.apellidoPaterno + " " + vm.listaDenunciante[k].personaDenunciante.apellidoMaterno);
                                           } else {
                                               nombre = angular.copy(vm.listaDenunciante[k].entidadDenunciante.razonSocial);
                                           }
                                           break;
                                       }
                                   }
                                   toastr.warning("Falta ingresar correo a " + nombre);
                                   return;
                               }
                           }*/
                //}
                if (angular.isDefined(localStorage.objDenuncia)) {
                    localStorage.removeItem("objDenuncia");
                    localStorage.removeItem("objDenunciaCorreo");
                }
                vm.denuncia.lstDenunciante = vm.listaDenunciante;
                if (vm.denuncia.lstDenunciante != null) {
                    localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
                    localStorage.setItem("objDenunciaCorreo", angular.toJson(vm.listaTotalContactos));
                }
                $rootScope.ValidaPaso4 = '1';
                $state.go('invitado.registro.paso4');
            } else {
                toastr.warning("Falta ingresar datos de contacto");
            }
        }

        function regresar() {
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            localStorage.removeItem("objDenunciaCorreo");
            localStorage.setItem("objDenunciaCorreo", angular.toJson(vm.listaTotalContactos));
            $state.go('invitado.registro.paso1');
        }

        function actualizarPersona() {
            vm.dataPersona.departamento = vm.dataUbigeo.codigoDepartamento;
            vm.dataPersona.provincia = vm.dataUbigeo.codigoProvincia;
            vm.dataPersona.distrito = vm.dataUbigeo.codigoDistrito;
            PersonaFactory.actualizar(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.dataPersona = response.data;
                        toastr.success(response.mensaje);
                        vm.flagRegistrarDenunciante = 0;
                        vm.flagEdicionDenunciante = 0;
                        vm.flagUbigeoDenunciante = 0;
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
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
                        toastr.success(response.mensaje);
                        vm.flagRegistrarDenunciante = 0;
                        vm.flagEdicionDenunciante = 0;
                        vm.flagUbigeoDenunciante = 0;
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

        function validaDatosPersona() {
            if (vm.dataPersona.departamento == null || vm.dataPersona.departamento == '' || vm.dataPersona.departamento.length == 0) {
                vm.flagEdicionDenunciante = 1;
            }
            if (vm.dataPersona.provincia == null || vm.dataPersona.provincia == '' || vm.dataPersona.provincia.length == 0) {
                vm.flagEdicionDenunciante = 1;
            }
            if (vm.dataPersona.provincia == null || vm.dataPersona.provincia == '' || vm.dataPersona.provincia.length == 0) {
                vm.flagEdicionDenunciante = 1;
            }
            if (vm.dataPersona.direccion == null || vm.dataPersona.direccion == '' || vm.dataPersona.direccion.length == 0) {
                vm.flagEdicionDenunciante = 1;
            }
        }

        function buscarPersonaXNumeroDocumento() {
            vm.listaContactos = [];
            // console.log("vm.dataPersona",vm.dataPersona);
            PersonaFactory.buscarXNumeroDocumento(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    console.log("response");
                    console.log(response);
                    if (response.data.idPersona > 0) {
                        vm.flagRegistrarDenunciante = 0;
                        vm.dataPersona = response.data;
                        /************************************************************************************/
                        if (vm.dataPersona.departamento != null && vm.dataPersona.departamento.length == 2) {
                            vm.dataUbigeo.codigoDepartamento = vm.dataPersona.departamento;
                        }
                        if (vm.dataPersona.provincia != null && vm.dataPersona.provincia.length == 2) {
                            vm.dataUbigeo.codigoProvincia = vm.dataPersona.provincia;
                        }
                        if (vm.dataPersona.distrito != null && vm.dataPersona.distrito.length == 2) {
                            vm.dataUbigeo.codigoDistrito = vm.dataPersona.distrito;
                        }
                        /************************************************************************************/
                        validaDatosPersona();
                        if (vm.dataPersona.departamento == null || vm.dataPersona.departamento == '' || vm.dataPersona.provincia == null || vm.dataPersona.provincia == '' || vm.dataPersona.distrito == null || vm.dataPersona.distrito == '' || vm.dataPersona.direccion == null || vm.dataPersona.direccion.length == 0) {
                            vm.flagEdicionDenunciante = 1;
                        } else {
                            vm.flagEdicionDenunciante = 0;
                            //  agregarDenunciante();
                        }
                        //Validar Ubigeo
                        if (vm.dataPersona.distrito == null) {
                            vm.flagUbigeoDenunciante = 1;
                        } else {
                            vm.flagUbigeoDenunciante = 0;
                        }
                        //console.log(vm.flagUbigeoDenunciante);
                        if (vm.dataPersona.departamento != null) {
                            listarProvincia();
                        }
                        /* vm.dataPersona=PersonaFactory.model(); */
                        listarContactoPersona(1);
                        vm.dataDenunciante = {};
                    } else {
                        toastr.info("En estos momentos no se puede completar la busqueda, por favor intente mas tarde.");
                    }
                } else {
                    // toastr.error(response.mensaje);
                    // vm.dataPersona=PersonaFactory.model();
                    dialogMensaje();
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {
                vm.dataEntidad = EntidadFactory.model();
            });
        }

        function validaDatosEntidad() {
            if (vm.dataEntidad.departamento == null || vm.dataEntidad.departamento == '' || vm.dataEntidad.departamento.length == 0) {
                vm.flagEdicionDenunciante = 1;
                vm.dataEntidad.departamento = '';
            }
            if (vm.dataEntidad.provincia == null || vm.dataEntidad.provincia == '' || vm.dataEntidad.provincia.length == 0) {
                vm.flagEdicionDenunciante = 1;
                vm.dataEntidad.provincia = '';
            }
            if (vm.dataEntidad.distrito == null || vm.dataEntidad.distrito == '' || vm.dataEntidad.distrito.length == 0) {
                vm.flagEdicionDenunciante = 1;
                vm.dataEntidad.distrito = '';
            }
            if (vm.dataEntidad.direccion == null || vm.dataEntidad.direccion == '' || vm.dataEntidad.direccion.length == 0) {
                vm.flagEdicionDenunciante = 1;
            }
        }

        function buscarEntidadXNumeroDocumento() {
            EntidadFactory.buscarXEntidadNumeroDocumento(angular.copy(vm.dataEntidad)).then(function(response) {
                if (response.valido) {
                    vm.dataEntidad = response.data;
                    if (response.data.idEntidad > 0) {
                        debugger;
                        vm.dataEntidad = response.data;
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
                        listarContactoPersona(1);
                        agregarDenuncianteEntidad();
                    }
                } else {
                    // toastr.error(response.mensaje);
                    dialogMensaje();
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {
                /* vm.dataEntidad=EntidadFactory.model(); */
                if (vm.dataEntidad.departamento != null && vm.dataEntidad.departamento != '' && vm.dataEntidad.provincia != null && vm.dataEntidad.provincia != '' && vm.dataEntidad.distrito != null && vm.dataEntidad.distrito != '') {
                    listarProvincia();
                }
                vm.dataDenunciante = {};
            });
        }

        function eliminarPorPosicion(item) {
            debugger;
            //  
            for (var x = 0; x < vm.listaDenunciante.length; x++) {
                if (vm.listaDenunciante[x].idPersona == item.idPersona) {
                    vm.listaDenunciante.splice(x, 1);
                }
            }
            if (vm.listaTotalContactos != null && vm.listaTotalContactos.length != 0) {
                for (var i = 0; i < vm.listaTotalContactos.length; i++) {
                    if (vm.listaTotalContactos[i][0].idPersona == item.idPersona) {
                        vm.listaTotalContactos.splice(i, 1);
                    }
                }
            }
            nuevaBusqueda();
        }

        function agregarDenunciante() {
            if (vm.listaDenunciante != null) {
                var x = 0;
                for (var i = 0; i < vm.listaDenunciante.length; i++) {
                    if (vm.listaDenunciante[i].personaDenunciante != null) {
                        if (vm.listaDenunciante[i].personaDenunciante.idPersona == vm.dataPersona.idPersona) {
                            x = x + 1;
                        }
                    }
                }
                if (x == 0) {
                    vm.dataDenunciante.idPersona = angular.copy(vm.dataPersona.idPersona);
                    vm.dataDenunciante.idDenunciante = angular.copy(vm.dataPersona.idPersona);
                    vm.dataDenunciante.tipoPersona = angular.copy(vm.dataDenuncia.tipoPersona);
                    vm.dataDenunciante.personaDenunciante = angular.copy(vm.dataPersona);
                    //departamento
                    console.log("vm.listaDepartamento", vm.listaDepartamento);
                    console.log("vm.listaProvincia", vm.listaProvincia);
                    console.log("vm.listaDistrito", vm.listaDistrito);
                    for (var i = 0; i < vm.listaDepartamento.length; i++) {
                        if (vm.listaDepartamento[i].codigoDepartamento == vm.dataDenunciante.personaDenunciante.departamento) {
                            vm.dataDenunciante.personaDenunciante.nombreDepartamento = angular.copy(vm.listaDepartamento[i].descripcionDepartamento);
                            for (var j = 0; j < vm.listaProvincia.length; j++) {
                                if (vm.listaProvincia[j].codigoProvincia == vm.dataDenunciante.personaDenunciante.provincia) {
                                    vm.dataDenunciante.personaDenunciante.nombreProvincia = angular.copy(vm.listaProvincia[j].descripcion);
                                    for (var k = 0; k < vm.listaDistrito.length; k++) {
                                        if (vm.listaDistrito[k].codigoDistrito == vm.dataDenunciante.personaDenunciante.distrito) {
                                            vm.dataDenunciante.personaDenunciante.nombreDistrito = angular.copy(vm.listaDistrito[k].descripcionDistrito);
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    console.log("vm.dataDenunciante", vm.dataDenunciante);
                    vm.listaDenunciante.push(angular.copy(vm.dataDenunciante));
                }
            }
        }

        function agregarDenuncianteEntidad() {
            if (vm.listaDenunciante != null) {
                var x = 0;
                /* if(vm.listaDenunciante.entidadDenunciante!=null){ */
                for (var i = 0; i < vm.listaDenunciante.length; i++) {
                    if (vm.listaDenunciante[i].entidadDenunciante != null) {
                        if (vm.listaDenunciante[i].entidadDenunciante.idEntidad == vm.dataEntidad.idEntidad) {
                            x = x + 1;
                        }
                    }
                }
                /* } */
                if (x == 0) {
                    vm.dataDenunciante.idPersona = angular.copy(vm.dataEntidad.idEntidad);
                    vm.dataDenunciante.idDenunciante = angular.copy(vm.dataEntidad.idEntidad);
                    vm.dataDenunciante.tipoPersona = angular.copy(vm.dataDenuncia.tipoPersona);
                    vm.dataDenunciante.entidadDenunciante = angular.copy(vm.dataEntidad);
                    console.log(vm.dataDenunciante);
                    vm.listaDenunciante.push(angular.copy(vm.dataDenunciante));
                }
            }
        }

        function obtenerDatosUsuario() {
            if (vm.dataBandeja.tipoResponsable == 1) {
                vm.dataPersonaLocal = PersonaFactory.model();
                vm.dataPersonaLocal.idPersona = angular.copy(vm.dataBandeja.idResponsable);
                // console.log("vm.dataPersona",vm.dataPersonaLocal);
                PersonaFactory.buscarXId(angular.copy(vm.dataPersonaLocal)).then(function(response) {
                    if (response.valido) {
                        if (response.data.idPersona > 0) {
                            vm.dataPersonaLocal = angular.copy(response.data);
                            // console.log("vm.dataPersonaLocal",vm.dataPersonaLocal);
                            vm.dataPersona.documento = angular.copy(vm.dataPersonaLocal.documento);
                            // console.log("vm.dataPersona",vm.dataPersona);
                            vm.dataDenuncia.tipoPersona = "1";
                            vm.dataDenuncia.tipoDocumento = "1";
                            buscarPersonaXNumeroDocumento();
                            // console.log("vm.dataDenuncia",vm.dataDenuncia);
                        }
                    } else {
                        toastr.error(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error('Error al consultar');
                }).finally(function() {
                    // buscarPersonaXNumeroDocumento();
                });
            } else {
                vm.dataEntidadLocal = EntidadFactory.model();
                vm.dataEntidadLocal.idEntidad = angular.copy(vm.dataBandeja.idResponsable);
                // console.log("vm.dataEntidadLocal",vm.dataEntidadLocal);
                EntidadFactory.buscarXId(vm.dataEntidadLocal).then(function(response) {
                    if (response.valido) {
                        if (response.data.idEntidad > 0) {
                            vm.dataEntidadLocal = response.data;
                            vm.dataEntidad.ruc = vm.dataEntidadLocal.ruc;
                            vm.dataDenuncia.tipoPersona = "2";
                            vm.dataDenuncia.tipoDocumento = "2";
                            buscarEntidadXNumeroDocumento();
                        }
                    } else {
                        toastr.error(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error('Error al consultar');
                }).finally(function() {
                    /* vm.dataEntidad=EntidadFactory.model(); */
                });
            }
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
                    vm.flagUbigeoDenunciante = 1;
                    //  vm.flagEdicionDenunciante = 1;
                } else {
                    vm.flagRegistrarDenunciante = 0;
                    vm.flagEdicionDenunciante = 0;
                    vm.flagUbigeoDenunciante = 0;
                }
            });
        }

        function registrarDatosPersonaOefa() {
            if (vm.dataDenuncia.tipoPersona == 1) {
                registrarPersonaOefa();
            } else {
                registrarEntidadOefa();
            }
        }

        function registrarEntidadOefa() {
            vm.dataEntidad.departamento = vm.dataUbigeo.codigoDepartamento;
            vm.dataEntidad.provincia = vm.dataUbigeo.codigoProvincia;
            vm.dataEntidad.distrito = vm.dataUbigeo.codigoDistrito;
            EntidadFactory.registrarEntidadOefa(angular.copy(vm.dataEntidad)).then(function(response) {
                if (response.valido) {
                    toastr.success('Se registró correctamente.');
                    vm.flagRegistrarDenunciante = 0;
                    vm.flagEdicionDenunciante = 0;
                    vm.flagUbigeoDenunciante = 0;
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
                    toastr.success('Se registró correctamente.');
                    vm.flagRegistrarDenunciante = 0;
                    vm.flagEdicionDenunciante = 0;
                    vm.flagUbigeoDenunciante = 0;
                    buscarPersonaXNumeroDocumento();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /* fin de implementacion de metodos */
        function init() {
            $rootScope.swBuscar = false;
            $rootScope.tituloBtnRegistrar = "Si";
            // console.log(vm.listaTipoDocumento);
            if ($rootScope.ValidaPaso2 == '1') {
                if (angular.isDefined(localStorage.objDenuncia)) {
                    vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                    if (vm.denuncia.lstDenunciante != null) {
                        vm.listaDenunciante = vm.denuncia.lstDenunciante;
                    }
                    listarDepartamento();
                }
            } else {
                localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                localStorage.removeItem("objPreguntaResLocal");
                $state.go('invitado.registro.paso1');
            }
            cargarCombos();
            if (validarPerfil(vm.idPerfilDenunciante) == 1) {
                if (angular.isDefined(localStorage.dataBandeja)) {
                    vm.dataBandeja = angular.fromJson(localStorage.dataBandeja);
                    obtenerDatosUsuario();
                }
            } else {}
            if (angular.isDefined(localStorage.objDenunciaCorreo)) {
                vm.listaTotalContactos = angular.copy(angular.fromJson(localStorage.objDenunciaCorreo));
            }
            //localStorage.setItem("objDenunciaCorreo", angular.toJson(vm.listaTotalContactos));            
        }
        init();
        /* fin de controller */
    }
})();