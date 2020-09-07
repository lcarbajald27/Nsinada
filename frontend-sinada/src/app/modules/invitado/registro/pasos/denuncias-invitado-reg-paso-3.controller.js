(function() {
    'use strict';
    angular.module('spapp.invitado').controller('DenunciasInvitadoRegPaso3Controller', DenunciasInvitadoRegPaso3Controller);
    /* @ngInject */
    function DenunciasInvitadoRegPaso3Controller($rootScope, $state, toastr, ngDialog, DenunciaFactory, DetalleCasoFactory, CasoFactory) {
        var vm = this;
        /* declaracion de variables globales */
        vm.prmDataCaso = CasoFactory.model();
        vm.padreDondeSucede = DetalleCasoFactory.model();
        vm.denuncia = DenunciaFactory.model();
        vm.listaProblematica = [];
        vm.listaDebidoANivel1 = [];
        vm.listaDebidoANivel2 = [];
        vm.listaDebidoANivel3 = [];
        vm.listaZonaSucesonivel1 = [];
        vm.listaDondeSucede = [];
        $rootScope.pasoActivo = 3;
        vm.validarCasoRegistrado = validarCasoRegistrado;
        /* declaracion de metodos */
        vm.siguiente = siguiente;
        vm.regresar = regresar;
        vm.mostrarNotificacionProblematicaDialog = mostrarNotificacionProblematicaDialog;
        vm.listarCasosGenerales = listarCasosGenerales;
        /* implementacion de metodos */
        /** ******************** Problematica ************************** */
        function obtenerListaSeleccione(lstDataMaestra) {
            var listaMaestra = [{
                idDetalleCaso: 0,
                descripcion: 'Seleccione'
            }];
            for (var x in lstDataMaestra) {
                listaMaestra.push(lstDataMaestra[x]);
            }
            return listaMaestra;
        }

        
        function validarCasoRegistrado() {
            CasoFactory.validaCasoRegistrado(angular.copy(vm.prmDataCaso)).then(function(response) {
                if (response.valido) {
                    vm.prmDataCaso = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                    toastr.success("Caso Encontrado");
                } else {
                    vm.prmDataCaso.idCaso = 0;
                    /*toastr.error(response.mensaje);*/
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        
        
        function listarProblematica() {
            var dataProblematica = {
                tipoCaso: 1,
                codigoPadre: 0,
                tipoNivel: 0
            };
            DetalleCasoFactory.listarDetalleCasoRegistrado(angular.copy(dataProblematica)).then(function(response) {
                if (response.valido) {
                    // vm.listaProblematica.push(response.data);
                    var dataObj = obtenerListaSeleccione(response.data);
                    vm.listaProblematica = angular.copy(dataObj);
                    /* vm.dataDenuncia.tipoPersona=0; */
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                /*
                 * if(vm.prmDataCaso.problematica.idDetalleCaso>0 &&
                 * vm.listaDebidoANivel1.length==0){
                 * 
                 * listarCasosGeneralesConDatos(2,vm.prmDataCaso.problematica.idDetalleCaso,1);
                 * vm.denuncia.caso.debidoA1.idDetalleCaso =
                 * vm.denuncia.caso.debidoA1.idDetalleCaso;
                 *  }
                 */
            });
        }
        /** ***************************************************** */
        function listarCasosGenerales(tipoCaso, codigoPadre, nivel, swInicio) {
            var dataProblematica = {
                tipoCaso: tipoCaso,
                codigoPadre: codigoPadre,
                tipoNivel: nivel
            };
            DetalleCasoFactory.listarDetalleCasoRegistrado(angular.copy(dataProblematica)).then(function(response) {
                if (response.valido) {
                    var dataObj = obtenerListaSeleccione(response.data);
                    if (tipoCaso == 2 && nivel == 1) {
                        if (!swInicio) {
                            limpiarGenerico(tipoCaso, nivel);
                        }
                        // vm.listaDebidoANivel1=response.data;
                        vm.listaDebidoANivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 2 && nivel == 2) {
                        if (!swInicio) {
                            limpiarGenerico(tipoCaso, nivel);
                        }
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel2 = angular.copy(dataObj);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 2 && nivel == 3) {
                        if (!swInicio) {
                            limpiarGenerico(tipoCaso, nivel);
                        }
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel3 = angular.copy(dataObj);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 3 && nivel == 1) {
                        if (!swInicio) {
                            limpiarGenerico(tipoCaso, nivel);
                        }
                        vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                        vm.listaZonaSucesonivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 2) {
                        if (!swInicio) {
                            limpiarGenerico(tipoCaso, nivel);
                        }
                        vm.listaZonaSucesonivel2 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 3) {
                        if (!swInicio) {
                            limpiarGenerico(tipoCaso, nivel);
                        }
                        vm.listaZonaSucesonivel3 = angular.copy(dataObj);
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /** *************************************************************************** */
        function listarCasosGeneralesConDatos(tipoCaso, codigoPadre, nivel) {
            var dataProblematica = {
                tipoCaso: tipoCaso,
                codigoPadre: codigoPadre,
                tipoNivel: nivel
            };
            DetalleCasoFactory.listarDetalleCasoRegistrado(angular.copy(dataProblematica)).then(function(response) {
                if (response.valido) {
                    var dataObj = obtenerListaSeleccione(response.data);
                    if (tipoCaso == 2 && nivel == 1) {
                        vm.listaDebidoANivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 2 && nivel == 2) {
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel2 = angular.copy(dataObj);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1, true);
                        }
                    }
                    if (tipoCaso == 2 && nivel == 3) {
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel3 = angular.copy(dataObj);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1, true);
                        }
                    }
                    if (tipoCaso == 3 && nivel == 1) {
                        vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                        vm.listaZonaSucesonivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 2) {
                        vm.listaZonaSucesonivel2 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 3) {
                        vm.listaZonaSucesonivel3 = angular.copy(dataObj);
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (tipoCaso == 2 && nivel == 1 && vm.prmDataCaso.debidoA1.idDetalleCaso > 0) {
                    vm.prmDataCaso.debidoA1.idDetalleCaso = angular.copy(parseInt(vm.prmDataCaso.debidoA1.idDetalleCaso));
                    listarCasosGeneralesConDatos(2, vm.prmDataCaso.debidoA1.idDetalleCaso, 2);
                }
                if (tipoCaso == 2 && nivel == 2 && vm.prmDataCaso.debidoA2.idDetalleCaso > 0) {
                    vm.prmDataCaso.debidoA2.idDetalleCaso = angular.copy(parseInt(vm.prmDataCaso.debidoA2.idDetalleCaso));
                    listarCasosGeneralesConDatos(2, vm.prmDataCaso.debidoA2.idDetalleCaso, 3);
                }
                if (tipoCaso == 2 && nivel == 3 && vm.prmDataCaso.debidoA3.idDetalleCaso > 0) {
                    vm.prmDataCaso.debidoA3.idDetalleCaso = angular.copy(parseInt(vm.prmDataCaso.debidoA3.idDetalleCaso));
                    listarCasosGeneralesConDatos(3, vm.prmDataCaso.debidoA3.idDetalleCaso, 1);
                }
            });
        }
        /** ***************************************************** */
        /** ********************************************************************** */
        function limpiarGenerico(tipo, nivel) {
            if (tipo == 0 && nivel == 0) {
                /* vm.prmDetalleCaso = DetalleCasoFactory.model(); */
                vm.padreDondeSucede = DetalleCasoFactory.model();
                vm.prmDataCaso = CasoFactory.model();
                vm.listaDebidoANivel1 = [];
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
                vm.listaZonaSucesonivel1 = [];
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 2 && nivel == 1) {
                vm.prmDataCaso.debidoA1.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel1 = [];
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 2 && nivel == 2) {
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 2 && nivel == 3) {
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 3 && nivel == 1) {
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel1 = [];
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 3 && nivel == 2) {
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 3 && nivel == 3) {
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel3 = [];
            }
        }
        /** ********************************************************************** */
        /** ************************************************************** */
        function siguiente() {
            /* $rootScope.localPrmDataCaso = vm.prmDataCaso; */
            $rootScope.localListaZonaSuceso = vm.listaZonaSucesonivel1;
            vm.denuncia.caso = angular.copy(vm.prmDataCaso);
            vm.denuncia.codigoDenuncia = '';
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(angular.copy(vm.denuncia)));
            $rootScope.ValidaPaso5 = '1';
            $state.go('invitado.registro.paso5');
        }

        function regresar() {
            vm.denuncia.caso = vm.prmDataCaso;
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            $state.go('invitado.registro.paso4');
//            if (vm.denuncia.tipoDenuncia == 1) {
//                $state.go('invitado.registro.paso1');
//            }
        }

        function mostrarNotificacionProblematicaDialog() {
            ngDialog.open({
                template: 'app/modules/invitado/registro/dialog/notificacion-problematica/notificacion-invitado-problematica-dialog.html',
                controller: 'NotificacionInvitadoProblematicaDialogController',
                data: {},
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                if (dataDialog.value.enviado) {
                    if (angular.isDefined(localStorage.objDenuncia)) {
                        vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                    }
                }
            });
        }
        /* fin de implementacion de metodos */
        function init() {
            if ($rootScope.ValidaPaso3 == '1') {
                $rootScope.ValidaPaso3 = '1';
                if (angular.isDefined(localStorage.objDenuncia)) {
                    vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                    listarProblematica();
                    if (vm.denuncia.caso != null) {
                        vm.prmDataCaso = angular.copy(vm.denuncia.caso);
                        if (parseInt(vm.prmDataCaso.problematica.idDetalleCaso) > 0 && vm.listaDebidoANivel1.length == 0) {
                            vm.prmDataCaso.problematica.idDetalleCaso = angular.copy(parseInt(vm.prmDataCaso.problematica.idDetalleCaso));
                            listarCasosGeneralesConDatos(2, parseInt(vm.prmDataCaso.problematica.idDetalleCaso), 1);
                            vm.denuncia.caso.debidoA1.idDetalleCaso = angular.copy(parseInt(vm.denuncia.caso.debidoA1.idDetalleCaso));
                        }
                    }
                } else {
                    listarProblematica();
                }
            } else {
                localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                localStorage.removeItem("objPreguntaResLocal");
                $state.go('invitado.registro.paso1');
            }
        }
        init();
        /* fin de controller */
    }
})();