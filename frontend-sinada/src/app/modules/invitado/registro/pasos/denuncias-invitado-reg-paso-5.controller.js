(function() {
    'use strict';
    angular.module('spapp.invitado').controller('DenunciasInvitadoRegPaso5Controller', DenunciasInvitadoRegPaso5Controller);
    /* @ngInject */
    function DenunciasInvitadoRegPaso5Controller($rootScope, $state, toastr, ngDialog, MaestroFactory, EntidadFactory, PersonaFactory, DenunciaFactory, UbigeoFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.textoPreguntaVerificarDenunciado = {
            pregunta1: 'B) ¿Conoce el documento del denunciado?',
            pregunta2: 'C) ¿Conoce el nombre o razón social del denunciado?'
        };
        vm.objPreguntaRes = {
            validaPregunta: 0,
            respuestaPregunta1: 0,
            respuestaPregunta2: 0
        };
        vm.flagAgregarManualmenteRepresentanteLegal = 0;
        vm.flagRegistrarDenunciado = 0;
        vm.responsableProblema = 0;
        vm.flagAgregarRepresentanteLegal = 0;
        vm.denuncia = DenunciaFactory.model();
        vm.dataPersona = PersonaFactory.model();
        vm.dataEntidad = EntidadFactory.model();
        vm.listaDepartamento = [];
        vm.listaProvincia = [];
        vm.listaDistrito = [];
        vm.listaDepartamento2 = [];
        vm.listaProvincia2 = [];
        vm.listaDistrito2 = [];
        vm.dataDenuncia = {
            tipoPersona: 0,
            tipoDocumento: 0,
            afirmacion: 0
        };
        vm.flagUbigeoDenunciante = 0;
        $rootScope.pasoActivo = 5;
        vm.listaTipoPersona = [];
        vm.listaTipoDocumento = [];
        vm.listaCargo = [];
        vm.departamento = UbigeoFactory.departamento();
        vm.provincia = UbigeoFactory.provincia();
        vm.distrito = UbigeoFactory.distrito();
        vm.listaProvinciaRepresente = [];
        vm.listaDistritoRepresente = [];
        /*declaracion de metodos */
        vm.siguiente = siguiente;
        vm.regresar = regresar;
        vm.buscarPersona = buscarPersona;
        vm.buscarEntidad = buscarEntidad;
        vm.limpiar = limpiar;
        vm.registrarDatosPersonaOefa = registrarDatosPersonaOefa;
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        vm.buscarRepresentanteLegal = buscarRepresentanteLegal;
        vm.listarProvinciaRepresentanteLegal = listarProvinciaRepresentanteLegal;
        vm.listarDistritoRepresentanteLegal = listarDistritoRepresentanteLegal;
        vm.limpiarRepresentanteLegal = limpiarRepresentanteLegal;
        vm.cancelarRegistroRepresentanteLegal = cancelarRegistroRepresentanteLegal;
        vm.registrarPersonaRepresentante = registrarPersonaRepresentante;
        vm.actualizarEntidadYPersonaGenerico = actualizarEntidadYPersonaGenerico;
        vm.procesoPregunta = procesoPregunta;
        vm.dialogBusquedaPersona = dialogBusquedaPersona;
        vm.limpiarTextoVerificarDenunciado = limpiarTextoVerificarDenunciado;
        vm.cambiarTipoDocumento = cambiarTipoDocumento;
        /*implementacion de metodos*/
        /********************************************************************/
        function cambiarTipoDocumento(tipoDocumento) {
            var tipDoc = parseInt(tipoDocumento);
            return tipDoc;
        }

        function limpiarTextoVerificarDenunciado() {
            vm.denuncia.nombreDenunciado = '';
            vm.denuncia.direccionDenunciado = '';
        }

        function procesoPregunta(tipo) {
            vm.flagRegistrarDenunciado = 0;
            vm.flagUbigeoDenunciante = 0;
            if (tipo == 0) {
                vm.objPreguntaRes.validaPregunta = 0;
                vm.objPreguntaRes.respuestaPregunta1 = 0;
                vm.objPreguntaRes.respuestaPregunta2 = 0;
                vm.responsableProblema = 2;
                vm.denuncia.responsableProblema = 0;
                vm.denuncia.tipo_responsable = 0;
            }
            if (tipo == 1) {
                vm.objPreguntaRes.validaPregunta = 1;
                vm.objPreguntaRes.respuestaPregunta1 = 2;
                limpiarTextoVerificarDenunciado();
                vm.responsableProblema = 1;
            }
            if (tipo == 2) {
                vm.dataDenuncia.tipoPersona = 0;
                vm.denuncia.responsableProblema = 0;
                vm.denuncia.tipo_responsable = 0;
                vm.objPreguntaRes.validaPregunta = 2;
                vm.objPreguntaRes.respuestaPregunta2 = 2;
            }
        }

        function procesoPreguntaData(tipo) {
            vm.flagRegistrarDenunciado = 0;
            vm.flagUbigeoDenunciante = 0;
            if (tipo == 0) {
                vm.objPreguntaRes.validaPregunta = 0;
                vm.objPreguntaRes.respuestaPregunta1 = 0;
                vm.objPreguntaRes.respuestaPregunta2 = 0;
                vm.responsableProblema = 0;
                vm.denuncia.responsableProblema = 0;
                vm.denuncia.tipo_responsable = 0;
            }
            if (tipo == 1) {
                vm.objPreguntaRes.validaPregunta = 1;
                vm.objPreguntaRes.respuestaPregunta1 = 1;
                limpiarTextoVerificarDenunciado();
                vm.responsableProblema = 1;
            }
            if (tipo == 2) {
                vm.dataDenuncia.tipoPersona = 0;
                vm.denuncia.responsableProblema = 0;
                vm.denuncia.tipo_responsable = 0;
                vm.objPreguntaRes.validaPregunta = 2;
                vm.objPreguntaRes.respuestaPregunta2 = 0;
            }
        }

        function procesoTraerDatosDenunciado(tipoPersona) {
            limpiar();
            limpiarTextoVerificarDenunciado();
            vm.responsableProblema = 1;
            vm.objPreguntaRes.respuestaPregunta1 = 1;
            vm.objPreguntaRes.validaPregunta = 1;
            vm.dataDenuncia.tipoPersona = cambiarTipoDocumento(tipoPersona);
        }

        function actualizarEntidadYPersonaGenerico() {
            if (vm.dataDenuncia.tipoPersona == 1) {
                actualizarPersona();
            } else {
                actualizarEntidad();
            }
        }

        function actualizarPersona() {
            PersonaFactory.actualizar(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.dataPersona = response.data;
                        vm.flagUbigeoDenunciante = 0;
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                buscarPersona();
            });
        }

        function actualizarEntidad() {
            EntidadFactory.actualizar(angular.copy(vm.dataEntidad)).then(function(response) {
                if (response.valido) {
                    if (response.data.idEntidad > 0) {
                        vm.dataEntidad = response.data;
                        vm.flagUbigeoDenunciante = 0;
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                buscarEntidad();
            });
        }

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

        function registrarDatosPersonaOefa() {
            //debugger;
            if (vm.dataDenuncia.tipoPersona == 1) {
                registrarPersonaOefa();
            } else {
                registrarEntidadOefa();
            }
        }

        function listarDepartamento() {
            UbigeoFactory.listarDepartamento(vm.departamento).then(function(response) {
                if (response.valido) {
                    vm.listaDepartamento = angular.copy(response.data);
                    vm.listaDepartamento2 = angular.copy(response.data);
                    console.log(vm.listaDepartamento);
                    console.log(vm.listaDepartamento2);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarProvincia() {
            if (vm.dataDenuncia.tipoPersona == 1) {
                vm.provincia.codigoDepartamento = angular.copy(vm.dataPersona.departamento);
            } else {
                vm.provincia.codigoDepartamento = angular.copy(vm.dataEntidad.departamento);
            }
            UbigeoFactory.listarProvincia(vm.provincia).then(function(response) {
                if (response.valido) {
                    if (vm.dataDenuncia.tipoPersona == 1) {
                        vm.listaProvincia = angular.copy(response.data);
                    } else {
                        vm.listaProvincia2 = angular.copy(response.data);
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.dataDenuncia.tipoPersona == 1) {
                    if (vm.dataPersona.provincia != null && vm.dataPersona.provincia.length == 2) {
                        listarDistrito()
                    } else {
                        vm.listaDistrito = [];
                    }
                } else {
                    if (vm.dataEntidad.provincia != null && vm.dataEntidad.provincia.length == 2) {
                        listarDistrito()
                    } else {
                        vm.listaDistrito2 = [];
                    }
                }
            });
        }

        function listarDistrito() {
            if (vm.dataDenuncia.tipoPersona == 1) {
                vm.distrito.codigoDepartamento = angular.copy(vm.dataPersona.departamento);
                vm.distrito.codigoProvincia = angular.copy(vm.dataPersona.provincia);
            } else {
                vm.distrito.codigoDepartamento = angular.copy(vm.dataEntidad.departamento);
                vm.distrito.codigoProvincia = angular.copy(vm.dataEntidad.provincia);
            }
            UbigeoFactory.listarDistrito(vm.distrito).then(function(response) {
                if (response.valido) {
                    if (vm.dataDenuncia.tipoPersona == 1) {
                        vm.listaDistrito = angular.copy(response.data);
                    } else {
                        vm.listaDistrito2 = angular.copy(response.data);
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /**********************************************************/
        function cargarCombos() {
            var codigosCombos = ['TipoDocumentoPersona', 'TipoPersonaDenuncia', 'CargoSinada'];
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
            });
        }

        function dialogMensaje() {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-denunciado/dialog-mensaje-denunciado.html',
                controller: 'DialogMensajeDenunciadoController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Denunciado no encontrado',
                    Mensaje: 'Ha ocurrido un error inesperado, por lo que no es posible encontrar al Denunciado.' + '\n¿Desea usted registrar manualmente el denunciado o realizar la búsqueda por nombre?'
                },
                width: '380px'
            }).finally(function() {
                if ($rootScope.tipoOperacionDenunciado == 1) {
                    $rootScope.tipoOperacionDenunciado = 0;
                    dialogBusquedaPersona();
                }
                if ($rootScope.tipoOperacionDenunciado == 2) {
                    $rootScope.tipoOperacionDenunciado = 0;
                    vm.flagRegistrarDenunciado = 1;
                    vm.flagUbigeoDenunciante = 1;
                }
            });
        }

        function dialogBusquedaPersona() {
            ngDialog.openConfirm({
                template: 'app/modules/invitado/registro/dialog/busqueda-persona-generico/busqueda-persona-generico.html',
                controller: 'BusquedaPersonaGenericoController',
                controllerAs: 'vm',
                /*data : {
                    Titulo : 'Denunciado no encontrado',
                    Mensaje : 'Usted desea registrar manualmente el denunciado o buscar por nombre.'
                },*/
                width: '750px'
            }).finally(function() {
                if (angular.isDefined($rootScope.dataPersonaSSOObtenida)) {
                    if ($rootScope.dataPersonaSSOObtenida.tipoPersona == 21) {
                        //juridica
                        vm.dataDenuncia.tipoPersona = 2;
                        vm.dataDenuncia.tipoDocumento = 2;
                        procesoTraerDatosDenunciado(vm.dataDenuncia.tipoPersona);
                        vm.dataEntidad.ruc = $rootScope.dataPersonaSSOObtenida.documento;
                        buscarEntidad();
                    }
                    if ($rootScope.dataPersonaSSOObtenida.tipoPersona == 20) {
                        //persona
                        vm.dataDenuncia.tipoPersona = 1;
                        vm.dataDenuncia.tipoDocumento = 1;
                        procesoTraerDatosDenunciado(vm.dataDenuncia.tipoPersona);
                        vm.dataPersona.documento = $rootScope.dataPersonaSSOObtenida.documento;
                        buscarPersona();
                    }
                }
                if (!angular.isDefined($rootScope.dataPersonaSSOObtenida)) {
                    vm.objPreguntaRes.respuestaPregunta2 = 0;
                }
            });
        }

        function validaDatosPersona() {
            if (vm.dataPersona.departamento == null || vm.dataPersona.departamento == '' || vm.dataPersona.departamento.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
            if (vm.dataPersona.provincia == null || vm.dataPersona.provincia == '' || vm.dataPersona.provincia.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
            if (vm.dataPersona.provincia == null || vm.dataPersona.provincia == '' || vm.dataPersona.provincia.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
            if (vm.dataPersona.direccion == null || vm.dataPersona.direccion == '' || vm.dataPersona.direccion.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
        }

        function buscarPersona() {
            PersonaFactory.buscarXNumeroDocumento(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.dataPersona = response.data;
                        console.log(vm.dataPersona);
                        vm.denuncia.tipo_responsable = vm.dataDenuncia.tipoPersona;
                        vm.denuncia.responsableProblema = vm.dataPersona.idPersona;
                        if (vm.dataPersona != null && vm.dataPersona.departamento != null && vm.dataPersona.departamento.length == 2) {
                            listarProvincia();
                        } else {
                            vm.listaProvincia = [];
                        }
                        //Validar Ubigeo
                        if (vm.dataPersona.distrito == null) {
                            vm.flagUbigeoDenunciante = 1;
                        } else {
                            vm.flagUbigeoDenunciante = 0;
                        }
                        validaDatosPersona();
                        console.log("vm.flagUbigeoDenunciante");
                        console.log(vm.flagUbigeoDenunciante);
                        if (angular.isDefined($rootScope.dataPersonaSSOObtenida)) {
                            $rootScope.dataPersonaSSOObtenida = undefined;
                        }
                    }
                } else {
                    var docu = angular.copy(vm.dataPersona.documento);
                    vm.dataPersona = PersonaFactory.model();
                    vm.dataPersona.documento = angular.copy(docu);
                    dialogMensaje();
                    /*toastr.error(response.mensaje);*/
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                vm.dataEntidad = EntidadFactory.model();
                /*  if(vm.dataPersona.departamento==null || vm.dataPersona.departamento=='' || vm.dataPersona.provincia==null || vm.dataPersona.provincia=='' || vm.dataPersona.distrito==null || vm.dataPersona.distrito=='' || vm.dataPersona.direccion==null || vm.dataPersona.direccion.length==0){
                        listarProvincia();
                    }*/
            });
        }

        function validaDatosEntidad() {
            if (vm.dataEntidad.departamento == null || vm.dataEntidad.departamento == '' || vm.dataEntidad.departamento.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
            if (vm.dataEntidad.provincia == null || vm.dataEntidad.provincia == '' || vm.dataEntidad.provincia.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
            if (vm.dataEntidad.distrito == null || vm.dataEntidad.distrito == '' || vm.dataEntidad.distrito.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
            if (vm.dataEntidad.direccion == null || vm.dataEntidad.direccion == '' || vm.dataEntidad.direccion.length == 0) {
                vm.flagUbigeoDenunciante = 1;
            }
        }

        function buscarEntidad() {
            EntidadFactory.buscarXEntidadNumeroDocumento(vm.dataEntidad).then(function(response) {
                if (response.valido) {
                    vm.dataEntidad = response.data;
                    if (response.data.idEntidad > 0) {
                        vm.dataEntidad = response.data;
                        vm.denuncia.tipo_responsable = vm.dataDenuncia.tipoPersona;
                        validaDatosEntidad();
                        vm.denuncia.responsableProblema = vm.dataEntidad.idEntidad;
                        if (vm.dataEntidad != null && vm.dataEntidad.departamento != null && vm.dataEntidad.departamento.length == 2) {
                            listarProvincia();
                        } else {
                            vm.listaProvincia2 = [];
                        }
                        if (angular.isDefined($rootScope.dataPersonaSSOObtenida)) {
                            $rootScope.dataPersonaSSOObtenida = undefined;
                        }
                    }
                } else {
                    var docu = angular.copy(vm.dataEntidad.ruc);
                    vm.dataEntidad = EntidadFactory.model();
                    vm.dataEntidad.ruc = angular.copy(docu);
                    dialogMensaje();
                    /*toastr.error(response.mensaje);*/
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function registrarEntidadOefa() {
            EntidadFactory.registrarEntidadOefa(vm.dataEntidad).then(function(response) {
                if (response.valido) {
                    toastr.success('Se registró correctamente.');
                    vm.flagRegistrarDenunciado = 0;
                    vm.flagUbigeoDenunciante = 0;
                    vm.flagAgregarManualmenteRepresentanteLegal = 0;
                    vm.flagAgregarRepresentanteLegal = 0;
                    buscarEntidad();
                    /*vm.dataEntidad= response.data;*/
                    /*  if(response.data.idEntidad>0){
                                vm.dataEntidad= response.data;
                                vm.denuncia.tipo_responsable=vm.dataDenuncia.tipoPersona;
                                vm.denuncia.responsableProblema=vm.dataEntidad.idEntidad;
                        }*/
                } else {
                    /*  vm.dataEntidad=EntidadFactory.model();
                        dialogMensaje();*/
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                //                          vm.dataEntidad=EntidadFactory.model();
                //                          vm.dataEntidad=PersonaFactory.model();
            });
        }

        function registrarPersonaOefa() {
            PersonaFactory.registrarPersonaOefa(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    toastr.success('Se registró correctamente.');
                    vm.flagRegistrarDenunciado = 0;
                    vm.flagUbigeoDenunciante = 0;
                    vm.flagAgregarManualmenteRepresentanteLegal = 0;
                    vm.flagAgregarRepresentanteLegal == 0;
                    buscarPersona();
                    /*vm.dataPersona= response.data;
                                
                    if(response.data.idPersona>0){
                            vm.dataPersona= response.data;
                            vm.denuncia.tipo_responsable=vm.dataDenuncia.tipoPersona;
                            vm.denuncia.responsableProblema=vm.dataPersona.idPersona;
                        
                    }*/
                } else {
                    /*  vm.dataEntidad=EntidadFactory.model();
                        dialogMensaje();*/
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function buscarPersonaXId(id) {
            vm.dataPersona.idPersona = id;
            PersonaFactory.buscarXId(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.dataPersona = response.data;
                        vm.denuncia.tipo_responsable = vm.dataDenuncia.tipoPersona;
                        vm.denuncia.responsableProblema = vm.dataPersona.idPersona;
                        if (vm.dataPersona != null && vm.dataPersona.departamento != null && vm.dataPersona.departamento.length == 2) {
                            listarProvincia();
                        }
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                vm.dataEntidad = EntidadFactory.model();
            });
        }

        function buscarEntidadXId(id) {
            vm.dataEntidad.idEntidad = id;
            EntidadFactory.buscarXId(vm.dataEntidad).then(function(response) {
                if (response.valido) {
                    vm.dataEntidad = response.data;
                    if (response.data.idEntidad > 0) {
                        vm.dataEntidad = response.data;
                        vm.denuncia.tipo_responsable = vm.dataDenuncia.tipoPersona;
                        vm.denuncia.responsableProblema = vm.dataEntidad.idEntidad;
                        if (vm.dataEntidad != null && vm.dataEntidad.departamento != null && vm.dataEntidad.departamento.length == 2) {
                            listarProvincia();
                        }
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                vm.dataPersona = PersonaFactory.model();
            });
        }

        function limpiar() {
            vm.dataPersona = PersonaFactory.model();
            vm.dataEntidad = EntidadFactory.model();
            vm.listaProvincia = [];
            vm.listaDistrito = [];
            vm.listaProvincia2 = [];
            vm.listaDistrito2 = [];
            vm.flagUbigeoDenunciante = 0;
            vm.flagAgregarManualmenteRepresentanteLegal = 0;
            vm.flagAgregarRepresentanteLegal = 0;
            vm.flagRegistrarDenunciado = 0;
            vm.flagUbigeoDenunciante = 0;
        }

        function siguiente() {
            //vm.denuncia.tipo_responsable
            console.log(vm.dataPersona);
            if (vm.dataDenuncia.tipoPersona == 1 && (vm.dataPersona.departamento == null || vm.dataPersona.departamento == '')) {
                toastr.warning("Debe seleccionar departamento");
                return;
            }
            if (vm.dataDenuncia.tipoPersona == 1 && (vm.dataPersona.provincia == null || vm.dataPersona.provincia == '')) {
                toastr.warning("Debe seleccionar provincia");
                return;
            }
            if (vm.dataDenuncia.tipoPersona == 1 && (vm.dataPersona.distrito == null || vm.dataPersona.distrito == '')) {
                toastr.warning("Debe seleccionar distrito");
                return;
            }
            if (vm.dataDenuncia.tipoPersona == 1 && (vm.dataPersona.direccion == null || vm.dataPersona.direccion == '')) {
                toastr.warning("Debe seleccionar dirección");
                return;
            }
            $rootScope.ValidaPaso6 = '1';
            localStorage.removeItem("objPreguntaResLocal");
            localStorage.setItem("objPreguntaResLocal", angular.toJson(vm.objPreguntaRes));
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            $state.go('invitado.registro.paso6');
        }

        function regresar() {
            localStorage.removeItem("objPreguntaResLocal");
            localStorage.setItem("objPreguntaResLocal", angular.toJson(vm.objPreguntaRes));
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            $state.go('invitado.registro.paso3');
        }
        /*fin de implementacion de metodos*/
        function buscarDepartamento(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                if (listado[i].codigoDepartamento == codigo) {
                    return listado[i];
                }
            }
        }

        function buscarProvincia(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                if (listado[i].codigoProvincia == codigo) {
                    return listado[i];
                }
            }
        }

        function buscarDistrito(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                if (listado[i].codigoDistrito == codigo) {
                    return listado[i];
                }
            }
        }

        function init() {
            $rootScope.tituloBtnRegistrar = "Registrar";
            $rootScope.swBuscar = true;
            if ($rootScope.ValidaPaso5 == '1') {
                debugger;
                if (angular.isDefined(localStorage.objDenuncia)) {
                    debugger;
                    if (angular.isDefined(localStorage.objPreguntaResLocal)) {
                        vm.objPreguntaRes = angular.fromJson(localStorage.objPreguntaResLocal);
                        procesoPreguntaData(vm.objPreguntaRes.validaPregunta);
                    } else {
                        vm.responsableProblema = 2;
                    }
                    vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                    if (vm.denuncia.responsableProblema != 0) {
                        vm.responsableProblema = 1;
                        vm.dataDenuncia.tipoPersona = parseInt(angular.copy(vm.denuncia.tipo_responsable));
                        if (vm.dataDenuncia.tipoPersona == '1') {
                            vm.dataDenuncia.tipoDocumento = 1;
                            buscarPersonaXId(vm.denuncia.responsableProblema);
                        }
                        if (vm.dataDenuncia.tipoPersona == '2') {
                            buscarEntidadXId(vm.denuncia.responsableProblema);
                            vm.dataDenuncia.tipoDocumento = 2;
                        }
                    }
                }
            } else {
                localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                localStorage.removeItem("objPreguntaResLocal");
                $state.go('invitado.registro.paso1');
            }
            cargarCombos();
            listarDepartamento();
        }
        init();
        /*fin de controller*/
    }
})();