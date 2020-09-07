(function() {
    'use strict';
    angular.module('spapp.invitado').controller('DenunciasInvitadoRegPaso1Controller', DenunciasInvitadoRegPaso1Controller);
    /* @ngInject */
    function DenunciasInvitadoRegPaso1Controller($window, $rootScope, toastr, ngDialog, $state, MaestroFactory, DenunciaFactory, BandejaFactory, AccesoFactory, API_CONFIG, $log) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.idPerfilEspecialista = API_CONFIG.idPerfilEspecialista;
        $rootScope.ValidaPaso1 = '1';
        vm.archivoHojaTrami = [];
        vm.denuncia = DenunciaFactory.model();
        $rootScope.pasoActivo = 1;
        vm.tipoDenuncia = [];
        vm.dataDenuncia = {
            problematica: 0,
            debidoA: 0,
            sucede: 0,
            tipoDenuncia: 0,
            medioRecepcion: 0
        };
        vm.listaProblematica = [];
        vm.listaDebidoA = [];
        vm.listaDondeSucede = [];
        vm.dataBandeja = {};
        /*declaracion de metodos */
        vm.salir = salir;
        vm.cambiarPaso = cambiarPaso;
        vm.mostrarModalHojaTramite = mostrarModalHojaTramite;
        vm.validarPerfil = validarPerfil;
        /*implementacion de metodos*/
        vm.listaDenunciante = [];

        function cambiarPaso(numero) {
            $rootScope.ValidaPaso2 = 1;
            if (vm.tipoDenuncia != null && vm.tipoDenuncia.length > 0) {
                for (var i = 0; i < vm.tipoDenuncia.length; i++) {
                    if (vm.tipoDenuncia[i].codigoRegistro == vm.denuncia.tipoDenuncia) {
                        vm.denuncia.maestroTipoDenuncia = angular.copy(vm.tipoDenuncia[i]);
                    }
                }
            }
            $rootScope.ValidaPaso2 = '1';
            vm.pasoActivo = numero;
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            $state.go('invitado.registro.paso' + numero);
            if (numero == 4 && vm.denuncia.tipoDenuncia) {
                $rootScope.ValidaPaso4 = '1';
                //vm.listaDenunciante = vm.denuncia.lstDenunciante;
                //vm.denuncia = null;
                //localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                vm.denuncia.lstDenunciante = [];
                localStorage.removeItem("objDenuncia");
                localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            }
        }

        function salir() {
            localStorage.removeItem("objDenuncia");
            localStorage.removeItem("objDenunciaCorreo");
            localStorage.removeItem("objPreguntaResLocal");
            if (angular.isDefined(localStorage.dataBandeja)) {
                localStorage.removeItem("dataBandeja");
                if (angular.isDefined(localStorage.bandejaProcedencia)) {
                    var bandejaProcedencia = angular.fromJson(localStorage.bandejaProcedencia);
                    localStorage.removeItem("bandejaProcedencia");
                    if (bandejaProcedencia == 1) {
                        $state.go('spapp.home.bandeja.asignacion');
                    }
                    if (bandejaProcedencia == 2) {
                        $state.go('spapp.home.denuncias.seguimiento');
                    }
                    if (bandejaProcedencia == 3) {
                        $state.go('spapp.home.bandeja.asignadas');
                    }
                    if (bandejaProcedencia == 4) {
                        $state.go('spapp.home.bandeja.denunciante');
                    }
                }
            } else {
                $state.go('ingreso');
            }
            $rootScope.ValidaPaso2 = '';
            $rootScope.ValidaPaso3 = '';
            $rootScope.ValidaPaso4 = '';
            $rootScope.ValidaPaso5 = '';
            $rootScope.ValidaPaso6 = '';
            $rootScope.ValidaPaso7 = '';
            $rootScope.ValidaPaso8 = '';
            //$state.go('ingreso');
        }

        function cargarCombos() {
            var codigosCombos = ['TipoDenunciaSinada'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoDenunciaSinada':
                                vm.tipoDenuncia = datosCombo;
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

        function mostrarModalHojaTramite() {
            ngDialog.open({
                template: 'app/modules/invitado/registro/dialog/hoja-tramite/hoja-tramite-dialog.html',
                controller: 'HojaTramiteDialogController',
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dialogData) {
                $log.info(dialogData);
                if (dialogData.value && dialogData.value.successData) {
                    //alert(dialogData.value.successData.denuncia.descripcion);
                    vm.denuncia.hojaTramite = dialogData.value.successData.denuncia.expediente.trim();
                }
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

        function validarPerfil(idPerfil) {
            var a = 0;
            for (var x in $rootScope.lstPerfilesUsuario) {
                if ($rootScope.lstPerfilesUsuario[x].pk_eIdPerfil == idPerfil) {
                    a = a + 1;
                }
            }
            return a;
        }

        function validarPerfiles(ref) {
            AccesoFactory.validaPerfiles({
                prm1: ref
            }).then(function(response) {
                if (response.valido) {
                    //vm.dataPedido.idPedido = response.data;
                    $rootScope.lstPerfilesUsuario = response.data;
                    /*siguiente();*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al consultar la información.');
            });
        }
        /*fin de implementacion de metodos*/
        function init() {
            $rootScope.ValidaPaso1 = 1;
            cargarCombos();
            if (angular.isDefined(localStorage.dataObjUsuIdEncrypSinadaWeb)) {
                validarPerfiles(localStorage.dataObjUsuIdEncrypSinadaWeb);
            } else {
                $rootScope.lstPerfilesUsuario = null;
            }
            if (angular.isDefined(localStorage.objDenuncia)) {
                vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                localStorage.removeItem("objDenuncia");
            } else {
                localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                localStorage.removeItem("objPreguntaResLocal");
            }
            //localStorage.removeItem("dataBandeja");
            if (angular.isDefined(localStorage.dataBandeja)) {
                vm.dataBandeja = angular.fromJson(localStorage.dataBandeja);
                //console.log("vm.dataBandeja",vm.dataBandeja);
                /* vm.denuncia.tipoDenuncia = 2;*/
            }
            vm.listaProblematica.push({
                codigoRegistro: 1,
                descripcion: 'Problematica 1'
            });
            vm.listaDebidoA.push({
                codigoRegistro: 1,
                descripcion: 'Causa 1'
            });
            vm.listaDondeSucede.push({
                codigoRegistro: 1,
                descripcion: 'Los Olivos'
            });
        }
        init();
        /*fin de controller*/
    }
})();