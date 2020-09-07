(function() {
    'use strict';
    angular.module('spapp.invitado').controller('DenunciasInvitadoRegPaso7Controller', DenunciasInvitadoRegPaso7Controller);
    /* @ngInject */
    function DenunciasInvitadoRegPaso7Controller($window, $rootScope, $state, $scope, $sce, toastr, ngDialog, DenunciaFactory, MaestroFactory, PersonaFactory, EntidadFactory, BandejaFactory, $templateCache, $timeout, $compile) {
        var vm = this;
        /*declaracion de variables globales*/
        $scope.renderHtml = function(htmlCode) {
            return $sce.trustAsHtml(htmlCode);
        };
        vm.denuncia = DenunciaFactory.model();
        vm.dataFichaDenuncia = {};
        vm.archivoTipoMedio;
        vm.archivoHojaTramite = [];
        vm.listaEntidadCaso = [];
        vm.dataPersona = PersonaFactory.model();
        vm.dataEntidad = EntidadFactory.model();
        vm.listaProblematica = [];
        vm.listaDebidoA = [];
        vm.listaLugardeHechos = [];
        vm.listaTipoArchivo = [];
        vm.listaDenunciantes = [];
        vm.dataDenuncia = {};
        vm.tipoDenuncia = [];
        $rootScope.pasoActivo = 7;
        /*declaracion de metodos */
        vm.siguiente = siguiente;
        vm.regresar = regresar;
        vm.guardar = guardar;
        vm.generarPDFFichaDenuncia = generarPDFFichaDenuncia;
        /*implementacion de metodos*/
        function buscarPersonaXId(id) {
            vm.dataPersona.idPersona = id;
            PersonaFactory.buscarXId(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        console.log("response.data", response.data);
                        vm.dataPersona = response.data;
                        vm.denuncia.tipo_responsable = vm.dataDenuncia.tipoPersona;
                        vm.denuncia.responsableProblema = vm.dataPersona.idPersona;
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
        /*
                 function listarEntidadCasos()
               {    
                        var dataEntidadCaso ={
                            idProblematica  : vm.denuncia.problematica,
                            idDebioA        :   vm.denuncia.debido,
                            idZonaSuceso    : vm.denuncia.zonaSuceso

                        }
                
                    CasosFactory
                    .listarEntidadCaso(dataEntidadCaso)
                    .then(function (response)
                    {
                    
                        vm.listaEfas    = [];
                        if (response.valido) 
                        {
                                vm.listaEntidadCaso = angular.copy(response.data); 
                            
                        
                        }
                        else 
                        {
                            toastr.error(response.mensaje);
                        }
                    }).catch(function (error) 
                    {
                        toastr.error('Error al consultar.');
                    }).finally(function() 
                    {
                        
                    });
                }
        */
        function cargarCombos() {
            var codigosCombos = ['TipoDenunciaSinada', 'TipoArchivoDenuncia'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoDenunciaSinada':
                                vm.tipoDenuncia = datosCombo;
                                break;
                            case 'TipoArchivoDenuncia':
                                vm.listaTipoArchivo = datosCombo;
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
        /********************** Problematica  ***************************/
        /*****************************************************************/
        function guardar() {
            registrar();
            if (vm.denuncia.idCaso == 0) {}
        }
        $window.onbeforeunload = function(e) {
            BandejaFactory.closeConnect();
            //              var confirmation = {};
            //              var event = $rootScope.$broadcast('onBeforeUnload', confirmation);
            //              if (event.defaultPrevented) {
            //                  return confirmation.message;
            //              }
        };

        function registrar() {
            /*debugger;*/
            console.log(vm.denuncia);
            DenunciaFactory.registrar( /*vm.archivoTipoMedio*/ $rootScope.lstMediosAdjuntos, vm.denuncia).then(function(response) {
                if (response.valido) {
                    //vm.dataPedido.idPedido = response.data;
                    vm.dataFichaDenuncia = response.data;
                    localStorage.removeItem("objDenuncia");
                    localStorage.removeItem("objPreguntaResLocal");
                    delete $rootScope.lstMediosAdjuntos;
                    // Almacenar datos GIS
                    DenunciaFactory.enviarUbicacionDenuncia(vm.dataFichaDenuncia).then(function(){
                        toastr.success('Denuncia registrada con código ' + vm.dataFichaDenuncia.codigoDenuncia);
                        $rootScope.urlPDFFichaDenuncia = "<iframe src='" + angular.copy(vm.dataFichaDenuncia.rutaFichaDenuncia) + "' style='width:100%; height:600px;' frameborder='0'> </iframe>";
                        localStorage.setItem("urlPdfDenuncia", angular.toJson($rootScope.urlPDFFichaDenuncia));
                        siguiente();
                    }).catch(function(error){
                        toastr.error('La denuncia ' + vm.dataFichaDenuncia.codigoDenuncia + ' fue registrada, pero ocurrió un error al almacenar la información espacial.');
                        console.log(error);
                    });
                    /*generarPDFFichaDenuncia();*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar la denuncia.');
            }).finally(function() {});
        }

        function generarPDFFichaDenuncia() {
            DenunciaFactory.obtenerPDFFichaDenunciaHtml(angular.copy(vm.dataFichaDenuncia)).then(function(response) {
                if (response.valido) {
                    $rootScope.urlPDFFichaDenuncia = "<iframe src='" + angular.copy(response.data) + "' style='width:100%; height:600px;' frameborder='0'> </iframe>";
                    localStorage.setItem("urlPdfDenuncia", angular.toJson($rootScope.urlPDFFichaDenuncia));
                    siguiente();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al procesar la informacion');
            });
        }

        function siguiente() {
            $rootScope.ValidaPaso8 = '1';
            $state.go('invitado.registro.paso8');
            localStorage.setItem("objListaEntidadCaso", angular.toJson(vm.dataFichaDenuncia.lstEntidadCaso));
            localStorage.setItem("objFinalizarDenuncia", angular.toJson(vm.dataFichaDenuncia));
        }

        function regresar() {
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            $state.go('invitado.registro.paso6');
        }
        /*fin de implementacion de metodos*/
        function init() {
            if ($rootScope.ValidaPaso7 == '1') {
                vm.archivoTipoMedio = $rootScope.archivoMedio;
                vm.archivoHojaTramite = $rootScope.hojaTramite;
                $rootScope.ValidaPaso7 = '1';
                if (angular.isDefined(localStorage.objDenuncia)) {
                    vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                    vm.listaDenunciantes = vm.denuncia.lstDenunciante;
                    //console.log("vm.denuncia", vm.denuncia)
                    //console.log("vm.listaDenunciantes", vm.listaDenunciantes);
                    //listarEntidadCasos();
                    /*
                    if (vm.denuncia.responsableProblema != 0) {
                        vm.responsableProblema = 1;
                        vm.dataDenuncia.tipoPersona = vm.denuncia.tipo_responsable;
                        if (vm.dataDenuncia.tipoPersona == 1) {
                            vm.dataDenuncia.tipoDocumento == 1;
                            buscarPersonaXId(vm.denuncia.responsableProblema);
                        }
                        if (vm.dataDenuncia.tipoPersona == 2) {
                            buscarEntidadXId(vm.denuncia.responsableProblema);
                            vm.dataDenuncia.tipoDocumento == 2;
                        }
                    }
                    */
                }
            } else {
                localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                localStorage.removeItem("objPreguntaResLocal");
                $state.go('invitado.registro.paso1');
            }
            cargarCombos();
        }
        init();
        /*fin de controller*/
    }
})();
