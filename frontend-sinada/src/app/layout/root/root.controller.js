(function() {
    'use strict';
    angular.module('spapp').controller('RootController', RootController);
    /** @ngInject */
    function RootController($location, $rootScope, ngDialog, $state, BandejaFactory, $window, UsuarioFactory, toastr, PersonaFactory, AccesoFactory, $log, API_CONFIG, $cookies, CookiesFactory, $timeout, $interval) {
        var vmRoot = this;
        vmRoot.dataPrueba = '';
        vmRoot.dataBandeja = BandejaFactory.modelBandeja();
        vmRoot.bandeja = BandejaFactory.bandeja();
        vmRoot.rutaSSO = API_CONFIG.urlSSO;
        vmRoot.cookieSesSinaWeb = null;
        vmRoot.sessionUsuario = {};
        vmRoot.dataPersonaSinada = {};
        $rootScope.validarLogeo = validarLogeo;
        $rootScope.lstNumeroPagina = [];
        $rootScope.aplicacionSinada = {
            idAplicacion: API_CONFIG.idAplicacion,
            perfiles: {
                idPerfilDenunciante: API_CONFIG.idPerfilDenunciante,
                idPerfilEspecialista: API_CONFIG.idPerfilEspecialista,
                idPerfilCoordinador: API_CONFIG.idPerfilCoordinador,
                idPerfilEntidad: API_CONFIG.idPerfilEntidad
            },
            opciones: {
                idMantenimientoMaestro: API_CONFIG.idMantenimientoMaestro,
                idCasosDenuncias: API_CONFIG.idCasosDenuncias,
                idDenuncianteBandejaDenuncias: API_CONFIG.idDenuncianteBandejaDenuncias,
                idAsignacionBandejaDenuncias: API_CONFIG.idAsignacionBandejaDenuncias,
                idEspecialistaBandejaDenuncias: API_CONFIG.idEspecialistaBandejaDenuncias,
                idEntidadBandejaDenuncias: API_CONFIG.idEntidadBandejaDenuncias
            }
        };
        /*declaracion de variables globales*/
        $rootScope.btnRegresar = {
            valor: 'Regresar',
            icon: 'glyphicon glyphicon-arrow-left',
            tooltip: 'Regresar'
        };
        $rootScope.btnCerrar = {
            valor: 'Cerrar',
            icon: 'glyphicon glyphicon-remove',
            tooltip: 'Cerrar'
        };
        $rootScope.btnOcultarUsuario = {
            valor: 'Ocultar usuarios',
            icon: ' glyphicon glyphicon-eye-close',
            tooltip: 'Ocultar usuarios'
        };
        $rootScope.btnVerUsuario = {
            valor: 'Ver usuarios',
            icon: 'glyphicon glyphicon-eye-open',
            tooltip: 'Ver usuarios'
        };
        $rootScope.btnCancelar = {
            valor: 'Cancelar',
            icon: 'glyphicon glyphicon-remove',
            tooltip: 'Cancelar'
        };
        $rootScope.menus = [{
            indice: '1',
            icono: 'glyphicon glyphicon-home',
            titulo: 'Inicio',
            desplegado: false,
            hijos: [],
            state: 'spapp.home',
            tipo: 'link',
            activo: false
        }, {
            indice: '2',
            icono: 'glyphicon glyphicon-wrench',
            titulo: 'Asistencial',
            desplegado: false,
            hijos: [{
                indice: '2.1',
                icono: '',
                titulo: 'Inicio',
                desplegado: false,
                hijos: [],
                state: 'spapp.evento.inicio',
                tipo: 'link',
                activo: false
            }, {
                indice: '2.2',
                icono: '',
                titulo: 'Emergencia',
                desplegado: false,
                hijos: [{
                    indice: '2.2.1',
                    icono: '',
                    titulo: 'Adulto',
                    desplegado: false,
                    hijos: [{
                        indice: '2.2.1.1',
                        icono: '',
                        titulo: 'Filiación',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.2',
                        icono: '',
                        titulo: 'Registro de Atención',
                        desplegado: false,
                        hijos: [{
                            indice: '2.2.1.2.1',
                            icono: '',
                            titulo: 'Antecedentes',
                            desplegado: false,
                            hijos: [],
                            state: 'spapp.evento.registro-atencion-adulto.antecedentes',
                            tipo: 'link',
                            activo: false
                        }, {
                            indice: '2.2.1.2.2',
                            icono: '',
                            titulo: 'Enfermedad Actual',
                            desplegado: false,
                            hijos: [],
                            state: '',
                            tipo: 'link',
                            activo: false
                        }],
                        state: 'spapp.evento.registro-atencion-adulto.antecedentes',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.3',
                        icono: '',
                        titulo: 'Evaluación Médica',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.4',
                        icono: '',
                        titulo: 'Procedimiento',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.5',
                        icono: '',
                        titulo: 'Dosis Unitaria',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.6',
                        icono: '',
                        titulo: 'Receta Médica Electrónica',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.7',
                        icono: '',
                        titulo: 'Registro de Cita',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.8',
                        icono: '',
                        titulo: 'Interconsulta',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }, {
                        indice: '2.2.1.9',
                        icono: '',
                        titulo: 'Referencia y Contrareferencia',
                        desplegado: false,
                        hijos: [],
                        state: '',
                        tipo: 'link',
                        activo: false
                    }],
                    state: '',
                    tipo: 'link',
                    activo: false
                }, {
                    indice: '2.2.2',
                    icono: '',
                    titulo: 'Pediátrica',
                    desplegado: false,
                    hijos: [],
                    state: '',
                    tipo: 'link',
                    activo: false
                }],
                state: 'spapp.evento.inicio',
                tipo: 'link',
                activo: false
            }],
            state: 'spapp.evento.inicio',
            tipo: 'link',
            activo: false
        }, {
            indice: '3',
            icono: 'glyphicon glyphicon-home',
            titulo: 'Administración',
            desplegado: false,
            hijos: [],
            state: 'spapp.administracion.inicio',
            tipo: 'link',
            activo: false
        }, {
            indice: '4',
            icono: 'glyphicon glyphicon-lock',
            titulo: 'Seguridad',
            desplegado: false,
            hijos: [],
            state: 'spapp.seguridad.inicio',
            tipo: 'link',
            activo: false
        }];
        vmRoot.subSistemaActual = null;
        vmRoot.moduloActual = null;
        vmRoot.procesoActual = null;
        vmRoot.subProcesoActual = null;
        vmRoot.tabActual = null;
        /*declaración de métodos enlazados a la vista*/
        vmRoot.menuClick = menuClick;
        /*implementación de métodos*/
        function generarListaPaginacion(cantidad, numeroItem) {
            var itemsNumeroPagina = numeroItem;
            for (var i = 0; i < cantidad; i++) {
                var numero = {
                    numero: itemsNumeroPagina
                };
                $rootScope.lstNumeroPagina.push(numero);
                itemsNumeroPagina = itemsNumeroPagina + numeroItem;
            }
        }

        function validaDatosPersonaSinada(ref) {
            /* var data = {

                tipoPersona :tipo,
                idPersona :id

             };*/
            UsuarioFactory.validarDatosUsuarioPersona(ref).then(function(response) {
                if (response.valido) {
                    vmRoot.dataPersonaSinada = response.data;
                    if (vmRoot.dataPersonaSinada.tipoPersona == 1) {
                        //                      vmRoot.sessionUsuario.persona = vmRoot.dataPersonaSinada.persona;
                        $rootScope.nombreCompletoUsuario = vmRoot.dataPersonaSinada.persona.nombreCompleto;
                    } else if (vmRoot.dataPersonaSinada.tipoPersona == 2) {
                        //                      vmRoot.sessionUsuario.entidad = vmRoot.dataPersonaSinada.entidad;
                        $rootScope.nombreCompletoUsuario = vmRoot.dataPersonaSinada.entidad.razonSocial;
                    }
                    vmRoot.sessionUsuario.bandeja = vmRoot.dataPersonaSinada.bandeja;
                    vmRoot.sessionUsuario.tipoEntidadCompetente = vmRoot.dataPersonaSinada.tipoEntidadCompetente;
                    console.log('vmRoot.sessionUsuario.tipoEntidadCompetente' + vmRoot.sessionUsuario.tipoEntidadCompetente);
                    if (vmRoot.sessionUsuario.tipoEntidadCompetente != 0) {
                        vmRoot.sessionUsuario.idEfa = vmRoot.dataPersonaSinada.idEfa;
                        vmRoot.sessionUsuario.idPersonaOefa = vmRoot.dataPersonaSinada.idPersonaOefa;
                        vmRoot.sessionUsuario.bandejaEntidad = vmRoot.dataPersonaSinada.bandejaEntidad;
                    }
                    localStorage.setItem('oSuSinWebDataSys', angular.toJson(vmRoot.dataPersonaSinada));
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                var dataUsuario;
                if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                    /*************  Obtenemos los datos de usuario  **************/
                    dataUsuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                    /*console.log("dataUsuario TRAE EL TIPO DE USUARIO?",dataUsuario    );*/
                    buscaUsuario(dataUsuario.bandeja.idResponsable, dataUsuario);
                    if (angular.isDefined($rootScope.ref_tmp)) {
                        $rootScope.ref = $rootScope.ref_tmp;
                        localStorage.setItem("dataObjUsuIdEncrypSinadaWeb", $rootScope.ref);
                        leerOpciones($rootScope.ref_tmp);
                    } else {
                        if (angular.isDefined(localStorage.dataObjUsuIdEncrypSinadaWeb)) {
                            $rootScope.ref = localStorage.dataObjUsuIdEncrypSinadaWeb;
                            leerOpciones($rootScope.ref);
                        } else {
                            $state.go('ingreso');
                        }
                    }
                }
            });
        }

        function buscaUsuario(idUsuario, dataUsuario) {
            var UsuarioExtraido;
            UsuarioFactory.obtener(angular.copy(idUsuario)).then(function(response) {
                if (response.valido) {
                    UsuarioExtraido = angular.copy(response.data);
                    //console.log("UsuarioExtraido",UsuarioExtraido);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (UsuarioExtraido.tipoPersona == 1) {
                    //console.log("ENTRO AL IF? buscarDatosDePersona");
                    buscarDatosDePersona(dataUsuario);
                }
            });
        }

        function buscarDatosDePersona(dataUsuario) {
            var flagGuardar = false;
            if (dataUsuario.bandeja.tipoResponsable == 1) {
                var dataPersona = PersonaFactory.model();
                dataPersona.idPersona = angular.copy(dataUsuario.bandeja.idResponsable);
                dataPersona.idPersona = angular.copy(dataUsuario.bandeja.idResponsable);
                PersonaFactory.buscarXId(angular.copy(dataPersona)).then(function(response) {
                    if (response.valido) {
                        if (response.data.idPersona > 0) {
                            dataPersona = angular.copy(response.data);
                            flagGuardar = false;
                            //console.log("dataPersona DATOS DESDE LA BASE",dataPersona);
                        } else {
                            flagGuardar = true;
                        }
                    } else {
                        toastr.error(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error('Error al consultar');
                }).finally(function() {
                    //console.log("dataPersona DESPUES DE BUSCARLO", dataPersona);
                    dataUsuario.persona = angular.copy(dataPersona);
                    if (dataUsuario.persona.distrito == null || dataUsuario.persona.distrito == 0 || dataUsuario.persona.distrito == "") {
                        //console.log("ENTRO AL IF DE usuarioActualizacionDatos");
                        usuarioActualizacionDatos(dataUsuario, flagGuardar);
                    }
                });
            }
        }

        function usuarioActualizacionDatos(dataUsuario, flagGuardar) {
            ngDialog.openConfirm({
                template: 'app/modules/inicio/modal-inicio/usuario-actualizacion-datos.html',
                controller: 'UsuariosActualizacionDatosController',
                controllerAs: 'vm',
                data: {
                    dataUsuario: dataUsuario,
                    flagGuardar: flagGuardar
                },
                width: '80%'
            }).then(function(okValue) {
                //eliminarCaso(item);
            });
        }
        $window.onbeforeunload = function(e) {
            BandejaFactory.closeConnect();
            //              var confirmation = {};
            //              var event = $rootScope.$broadcast('onBeforeUnload', confirmation);
            //              if (event.defaultPrevented) {
            //                  return confirmation.message;
            //              }
        };

        function validarLogeo() {
            var res = 0;
            if (CookiesFactory.obtenerObjCookie() != null) {
                res = 1;
            }
            return res;
        }

        function menuClick(indiceMenuActual) {
            //debugger;
            switch (indiceMenuActual.length) {
                case 1:
                    vmRoot.subSistemaActual = indiceMenuActual;
                    localStorage.setItem('subSistemaActual', indiceMenuActual);
                    iteracionSubSistemas(vmRoot.subSistemaActual);
                    break;
                case 3:
                    vmRoot.moduloActual = indiceMenuActual;
                    localStorage.setItem('moduloActual', indiceMenuActual);
                    iteracionModulos(vmRoot.subSistemaActual, vmRoot.moduloActual);
                    break;
                case 5:
                    vmRoot.procesoActual = indiceMenuActual;
                    localStorage.setItem('procesoActual', indiceMenuActual);
                    iteracionProcesos(vmRoot.subSistemaActual, vmRoot.moduloActual, vmRoot.procesoActual);
                    break;
                case 7:
                    vmRoot.subProcesoActual = indiceMenuActual;
                    localStorage.setItem('subProcesoActual', indiceMenuActual);
                    iteracionSubProcesos(vmRoot.subSistemaActual, vmRoot.moduloActual, vmRoot.procesoActual, vmRoot.subProcesoActual);
                    break;
                case 9:
                    vmRoot.tabActual = indiceMenuActual;
                    localStorage.setItem('tabActual', indiceMenuActual);
                    iteracionTabs(vmRoot.subSistemaActual, vmRoot.moduloActual, vmRoot.procesoActual, vmRoot.subProcesoActual, vmRoot.tabActual);
                    break;
            }
        }

        function iteracionSubSistemas(indiceSubsistema) {
            for (var i = 0; i < $rootScope.menus.length; i++) {
                if ($rootScope.menus[i].indice == indiceSubsistema) {
                    if ($rootScope.menus[i].state !== '') {
                        $state.go($rootScope.menus[i].state);
                    }
                    $rootScope.menus[i].desplegado = !$rootScope.menus[i].desplegado;
                    $rootScope.menus[i].activo = true;
                } else {
                    $rootScope.menus[i].activo = false;
                }
            }
        }

        function iteracionModulos(indiceSubsistema, indiceModulo) {
            for (var i = 0; i < $rootScope.menus.length; i++) {
                if ($rootScope.menus[i].indice == indiceSubsistema && angular.isDefined($rootScope.menus[i].hijos)) {
                    for (var j = 0; j < $rootScope.menus[i].hijos.length; j++) {
                        if ($rootScope.menus[i].hijos[j].indice === indiceModulo) {
                            if ($rootScope.menus[i].hijos[j].state !== '') {
                                $state.go($rootScope.menus[i].hijos[j].state);
                            }
                            $rootScope.menus[i].hijos[j].desplegado = !$rootScope.menus[i].hijos[j].desplegado;
                            $rootScope.menus[i].hijos[j].activo = true;
                        }
                        $rootScope.menus[i].hijos[j].activo = false;
                    }
                    return;
                }
            }
        }

        function iteracionProcesos(indiceSubsistema, indiceModulo, indiceProceso) {
            for (var i = 0; i < $rootScope.menus.length; i++) {
                if ($rootScope.menus[i].indice == indiceSubsistema && angular.isDefined($rootScope.menus[i].hijos)) {
                    for (var j = 0; j < $rootScope.menus[i].hijos.length; j++) {
                        if ($rootScope.menus[i].hijos[j].indice === indiceModulo) {
                            for (var k = 0; k < $rootScope.menus[i].hijos.length; k++) {
                                if ($rootScope.menus[i].hijos[j].hijos[k].indice === indiceProceso) {
                                    if ($rootScope.menus[i].hijos[j].hijos[k].state !== '') {
                                        $state.go($rootScope.menus[i].hijos[j].hijos[k].state);
                                    }
                                    $rootScope.menus[i].hijos[j].hijos[k].desplegado = !$rootScope.menus[i].hijos[j].hijos[k].desplegado;
                                    $rootScope.menus[i].hijos[j].hijos[k].activo = true;
                                }
                                $rootScope.menus[i].hijos[j].hijos[k].activo = false;
                            }
                            return;
                        }
                    }
                }
            }
        }

        function iteracionSubProcesos(indiceSubsistema, indiceModulo, indiceProceso, indiceSubProceso) {
            for (var i = 0; i < $rootScope.menus.length; i++) {
                if ($rootScope.menus[i].indice == indiceSubsistema && angular.isDefined($rootScope.menus[i].hijos)) {
                    for (var j = 0; j < $rootScope.menus[i].hijos.length; j++) {
                        if ($rootScope.menus[i].hijos[j].indice === indiceModulo) {
                            for (var k = 0; k < $rootScope.menus[i].hijos.length; k++) {
                                if ($rootScope.menus[i].hijos[j].hijos[k].indice === indiceProceso) {
                                    for (var l = 0; l < $rootScope.menus[i].hijos.length; l++) {
                                        if ($rootScope.menus[i].hijos[j].hijos[k].hijos[l].indice === indiceSubProceso) {
                                            if ($rootScope.menus[i].hijos[j].hijos[k].hijos[l].state !== '') {
                                                $state.go($rootScope.menus[i].hijos[j].hijos[k].hijos[l].state);
                                            }
                                            $rootScope.menus[i].hijos[j].hijos[k].hijos[l].desplegado = !$rootScope.menus[i].hijos[j].hijos[k].hijos[l].desplegado;
                                            $rootScope.menus[i].hijos[j].hijos[k].hijos[l].activo = true;
                                        }
                                        $rootScope.menus[i].hijos[j].hijos[k].hijos[l].activo = false;
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        function iteracionTabs(indiceSubsistema, indiceModulo, indiceProceso, indiceSubProceso, indiceTab) {
            for (var i = 0; i < $rootScope.menus.length; i++) {
                if ($rootScope.menus[i].indice == indiceSubsistema && angular.isDefined($rootScope.menus[i].hijos)) {
                    for (var j = 0; j < $rootScope.menus[i].hijos.length; j++) {
                        if ($rootScope.menus[i].hijos[j].indice === indiceModulo) {
                            for (var k = 0; k < $rootScope.menus[i].hijos.length; k++) {
                                if ($rootScope.menus[i].hijos[j].hijos[k].indice === indiceProceso) {
                                    for (var l = 0; l < $rootScope.menus[i].hijos.length; l++) {
                                        if ($rootScope.menus[i].hijos[j].hijos[k].hijos[l].indice === indiceSubProceso) {
                                            for (var m = 0; m < $rootScope.menus[i].hijos.length; m++) {
                                                if ($rootScope.menus[i].hijos[j].hijos[k].hijos[l].indice === indiceSubProceso) {
                                                    if ($rootScope.menus[i].hijos[j].hijos[k].hijos[l].hijos[m].state !== '') {
                                                        $state.go($rootScope.menus[i].hijos[j].hijos[k].hijos[l].hijos[m].state);
                                                    }
                                                    $rootScope.menus[i].hijos[j].hijos[k].hijos[l].hijos[m].desplegado = !$rootScope.menus[i].hijos[j].hijos[k].hijos[l].hijos[m].desplegado;
                                                    $rootScope.menus[i].hijos[j].hijos[k].hijos[l].hijos[m].activo = true;
                                                }
                                                $rootScope.menus[i].hijos[j].hijos[k].hijos[l].hijos[m].activo = false;
                                            }
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        function leerOpciones(ref) {
            AccesoFactory.opciones(ref).then(function(response) {
                $log.log(response);
                //localStorage.setItem("acceso",angular.toJson(response));
                $rootScope.accesos = response;
                $rootScope.validarOpcion = validarOpcion;
            }).catch(function(error) {
                //toastr.error('xx');
            });
        }

        function validarOpcion(opcion) {
            for (var int = 0; int < $rootScope.accesos.length; int++) {
                var _opcion = $rootScope.accesos[int];
                //$log.log('_opcion.elemento '+_opcion.elemento);
                //$log.log('opcion'+opcion);
                if (_opcion.elemento == opcion) {
                    return true;
                }
            }
            return false;
        }

        function obtenerUsuarioLogeado() {
            if (angular.isDefined($rootScope.ref_tmp)) {
                validaDatosPersonaSinada($rootScope.ref_tmp);
                $rootScope.ref = $rootScope.ref_tmp;
            } else {
                validaDatosPersonaSinada($rootScope.ref);
            }
        }

        function pruebadsa() {
            var data = CookiesFactory.obtenerObjCookie();
            var fechaActual = new Date();
            var fechaSession = new Date(data.tiempoSession);
            var restaTiempo = ((fechaSession - fechaActual) / 900);
            var segundos = parseInt(restaTiempo);
            var milisegundos = segundos * 1000;
            return milisegundos;
        }

        function init() {
            localStorage.removeItem("objDenuncia");
            if (CookiesFactory.obtenerCookie() != null) {
                $rootScope.ref_tmp = CookiesFactory.obtenerCookie();
                obtenerUsuarioLogeado();
            } else {
                sessionStorage.removeItem('objSesSinadaWebRefRD');
                localStorage.removeItem('oSuSinWebDataSys');
                localStorage.removeItem('dataObjUsuIdEncrypSinadaWeb');
                $state.go('ingreso');
            }
            //            pruebadsa();
        }
        generarListaPaginacion(10, 10);
        init();
        $interval(function() {
            CookiesFactory.validaCookie();
        }, 30000);
        /*fin de controller*/
    }
})();