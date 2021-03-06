(function() {
    'use strict';
    angular.module('spapp.bandeja').controller('HistorialDenunciaInvitadoController', HistorialDenunciaInvitadoController);
    /** @ngInject */
    function HistorialDenunciaInvitadoController($scope, $state, toastr, ngDialog, MaestroFactory, InformeAccionFactory, $filter, BandejaFactory, $rootScope, AtencionDenuncia, EvaluacionRechazoFactory, EvaluacionInformeFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.dataDenunciaBandeja = {};
        vm.dataInformeAccion = InformeAccionFactory.model();
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.dataInformarAccion = InformeAccionFactory.model();
        vm.dataInformarAtencion = InformeAccionFactory.model();
        vm.dataDenunciaInformacion = BandejaFactory.bandejaDenuncia();
        vm.archivoAdjunto = [];
        vm.fechaInicio = '';
        vm.fechaFin = '';
        vm.listaAccionesRegistradas = [];
        vm.listaTipoAccion = [];
        vm.listaTipoSupervicion = [];
        vm.asignadasInformarTabActivo = 1;
        /*declaración de metodos enlazados a la vista*/
        vm.validaDocumento = validaDocumento;
        vm.registrarAccion = registrarAccion;
        vm.registrarAtencion = registrarAtencion;
        vm.limpiarDatos = limpiarDatos;
        vm.limpiarSelect = limpiarSelect;
        /*  modales */
        vm.mostrarModalVisualizarDenunciaRechazada = mostrarModalVisualizarDenunciaRechazada;
        vm.mostrarModalVisualizarEvaluacionRechazo = mostrarModalVisualizarEvaluacionRechazo;
        vm.mostrarModalVisualizarAccion = mostrarModalVisualizarAccion;
        vm.mostrarModalVisualizarEvaluacionAccion = mostrarModalVisualizarEvaluacionAccion;
        vm.regresar = regresar;
        /*          */
        /*implementación de metodos*/
        /******************** Modales Vista de Datos ********************************/
        function mostrarModalVisualizarDenunciaRechazada(data) {
            var dataDenunciaRechazada = AtencionDenuncia.model();
            dataDenunciaRechazada.idAtencionDenuncia = data.idAccion;
            dataDenunciaRechazada.tipoAtencion.codigoRegistro = data.tipoAtencionAccion;
            dataDenunciaRechazada.motivo.codigoRegistro = data.motivoAccion;
            dataDenunciaRechazada.fechaRegistro = data.fechaRegistro;
            ngDialog.open({
                template: 'app/modules/dialog-acciones/dialog-visualizar-denuncia-rechazada/dialog-visualizar-denuncia-rechazada.html',
                controller: 'DialogVisualizarDenunciaRechazadaController',
                data: {
                    prmData: dataDenunciaRechazada,
                },
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                listarAccionesRegistradas();
            });
        }
        /*

                function mostrarModalVisualizarNoAtencion(data){

                    var dataDenunciaNoAtendida = AtencionDenuncia.model();
                        dataDenunciaNoAtendida.idAtencionDenuncia = data.idAccion;
                        dataDenunciaNoAtendida.tipoAtencion.codigoRegistro = data.tipoAtencionAccion;
                        dataDenunciaNoAtendida.motivo.codigoRegistro= data.motivoAccion;
                        dataDenunciaNoAtendida.motivo.descripcion= data.nombreMotivoAccion;
                        dataDenunciaNoAtendida.fechaRegistro= data.fechaRegistro;
                        dataDenunciaNoAtendida.descripcionMotivo = data.descripcionMotivo;


                        ngDialog
                    .open({
                        template : 'app/modules/dialog-acciones/dialog-visualizar-no-atencion/dialog-visualizar-no-atencion.html',
                        controller : 'dialogVisualizarNoAtencion',
                        data : {

                            prmData : dataDenunciaNoAtendida,
                        },
                        controllerAs : 'vm',
                        width : '700px'
                    }).closePromise
                    .then(function (dataDialog) {

                        listarAccionesRegistradas();

                    });

                }*/
        function mostrarModalVisualizarEvaluacionRechazo(data) {
            var dataEvaluacionRechazo = EvaluacionRechazoFactory.model();
            dataEvaluacionRechazo.atencionDenuncia.idAtencionDenuncia = data.idAccion;
            /*var dataCorregirAccion = InformeAccionFactory.model();
            dataCorregirAccion.denuncia.idDenuncia = data.idDenuncia;
            dataCorregirAccion.detalleBandeja.idBandejaDetalle =data.idBandejaDetalle;
            dataCorregirAccion.tipoInforme.codigoRegistro = data.tipoAtencionAccion;
            dataCorregirAccion.idInformeCorregido = data.idAccion;*/
            ngDialog.open({
                template: 'app/modules/dialog-acciones/dialog-visualizar-evaluacion-rechazo/dialog-visualizar-evaluacion-rechazo.html',
                controller: 'DialogVisualizarEvaluacionRechazo',
                data: {
                    prmData: dataEvaluacionRechazo,
                },
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                listarAccionesRegistradas();
            });
        }
        /************************************************************/
        function mostrarModalVisualizarAccion(data) {
            //           debugger;
            var dataAccionRealizada = InformeAccionFactory.model();
            dataAccionRealizada.denuncia.idDenuncia = data.idDenuncia;
            dataAccionRealizada.idInformeAccion = data.idAccion;
            dataAccionRealizada.detalleBandeja.idBandejaDetalle = data.idBandejaDetalle;
            dataAccionRealizada.tipoInforme.codigoRegistro = data.tipoAtencionAccion;
            dataAccionRealizada.tipoAccion.codigoRegistro = data.tipoAccion;
            dataAccionRealizada.tipoAccion.descripcion = data.nombreTipoAccion;
            dataAccionRealizada.tipoSupervicion.codigoRegistro = data.tipoSupervicion;
            dataAccionRealizada.tipoSupervicion.descripcion = data.nombreTipoSupervicion;
            dataAccionRealizada.descripcionAccion = data.descripcionAccion;
            dataAccionRealizada.fechaInicio = data.fechaInicio;
            dataAccionRealizada.fechaFin = data.fechaFin;
            dataAccionRealizada.fechaRegistro = data.fechaRegistro;
            dataAccionRealizada.estado.codigoRegistro = data.estado;
            dataAccionRealizada.estado.descripcion = data.nombreEstado;
            ngDialog.open({
                template: 'app/modules/dialog-acciones/dialog-visualizar-accion/dialog-visualizar-accion.html',
                controller: 'DialogVisualizarAccionController',
                data: {
                    prmData: dataAccionRealizada,
                },
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                listarAccionesRegistradas();
            });
        }
        /*********************************************************************/
        function mostrarModalVisualizarEvaluacionAccion(data) {
            var dataEvaluacionInforme = EvaluacionInformeFactory.model();
            dataEvaluacionInforme.informeAccion.idInformeAccion = data.idAccion;
            /*var dataCorregirAccion = InformeAccionFactory.model();
            dataCorregirAccion.denuncia.idDenuncia = data.idDenuncia;
            dataCorregirAccion.detalleBandeja.idBandejaDetalle =data.idBandejaDetalle;
            dataCorregirAccion.tipoInforme.codigoRegistro = data.tipoAtencionAccion;
            dataCorregirAccion.idInformeCorregido = data.idAccion;*/
            ngDialog.open({
                template: 'app/modules/dialog-acciones/dialog-visualizar-evaluacion-accion/dialog-visualizar-evaluacion-accion.html',
                controller: 'DialogVisualizarEvaluacionAccion',
                data: {
                    prmData: dataEvaluacionInforme,
                },
                controllerAs: 'vm',
                width: '700px'
            }).closePromise.then(function(dataDialog) {
                listarAccionesRegistradas();
            });
        }
        /**********************************************************************/
        /******************** Modales Vista de Datos ********************************/
        /*  function levantarObservacionAtencion(data){

                var dataCorregirAccion = InformeAccionFactory.model();
                dataCorregirAccion.denuncia.idDenuncia = data.idDenuncia;
                dataCorregirAccion.detalleBandeja.idBandejaDetalle =data.idBandejaDetalle;
                dataCorregirAccion.tipoInforme.codigoRegistro = data.tipoAtencionAccion;
                dataCorregirAccion.idInformeCorregido = data.idAccion;

                    ngDialog
                .open({
                    template : 'app/modules/bandeja/asignadas/dialog-levantar-observacion/dialog-levantar-observacion.html',
                    controller : 'DialogLevantarObservacionController',
                    data : {

                        prmData : dataCorregirAccion,
                    },
                    controllerAs : 'vm',
                    width : '700px'
                }).closePromise
                .then(function (dataDialog) {

                    listarAccionesRegistradas();

                });

            }*/
        function limpiarDatos() {
            //          console.log("sadsads");
            vm.dataInformarAccion = InformeAccionFactory.model();
            vm.archivoAdjunto = [];
            vm.fechaInicio = '';
            vm.fechaFin = '';
            document.getElementById("fileArchivoMedio").value = "";
        }

        function limpiarSelect() {
            //      console.log("sadsads");
            vm.dataInformarAccion.tipoSupervicion.codigoRegistro = 0;
            vm.dataInformarAccion.descripcionAccion = '';
            vm.archivoAdjunto = [];
            vm.fechaInicio = '';
            vm.fechaFin = '';
            document.getElementById("fileArchivoMedio").value = "";
        }

        function convertDateToString(prmdate) {
            if (!angular.isUndefined(prmdate) && prmdate != null && prmdate != "") {
                try {
                    var strDate = $filter('date')(prmdate, "dd/MM/yyyy");
                    return strDate;
                } catch (e) {
                    return "";
                }
            }
        }

        function regresar() {
            $state.go('invitado.listado');
        }

        function validaDocumento(prmFileList) {
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.archivoAdjunto = prmFileList;
            //  console.log("vm.archivoAdjunto" + vm.archivoAdjunto);
            /*$rootScope.archivoMedio=(prmFileList);*/
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                //          console.log(archivo);
                if (archivo.size / 1024 > 256000) {
                    toastr.warning('El tamaño del archivo no debe superar 250MB. Intente comprimir el tamaño del archivo y vuelva a intentarlo');
                    document.getElementById("fileArchivoMedio").value = "";
                    return;
                }
                if (archivo.name.length > 50) {
                    toastr.warning('El nombre del archivo no debe mayor a 50 caracteres');
                    document.getElementById("fileArchivoMedio").value = "";
                    return;
                }
            }
        }

        function registrarAccion() {
            if (vm.archivoAdjunto.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataInformarAccion.denuncia.idDenuncia = vm.dataDenunciaInformacion.idDenuncia;
            vm.dataInformarAccion.fechaInicio = convertDateToString(vm.fechaInicio);
            vm.dataInformarAccion.fechaFin = convertDateToString(vm.fechaFin);
            vm.dataInformarAccion.tipoInforme.codigoRegistro = 1;
            vm.dataInformarAccion.estado.codigoRegistro = 1;
            InformeAccionFactory.registrar(vm.archivoAdjunto, vm.dataInformarAccion).then(function(response) {
                if (response.valido) {
                    vm.dataInformarAccion.idInformeAccion = response.data;
                    toastr.success("Se generó la acción número : " + vm.dataInformarAccion.idInformeAccion);
                    limpiarDatos();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar');
            }).finally(function() {
                cancelar();
            });
        }
        /*  function listarAccionesRegistradas(){
                        console.log(vm.dataInformeAccion);
            InformeAccionFactory
                        .listar(vm.dataInformeAccion)
                        .then(function (response) {
                            if (response.valido) {
                                vm.listaAccionesRegistradas= angular.copy(response.data);
                                //console.log(" vm.listaAccionesRegistradas" + vm.listaAccionesRegistradas);
                                console.log(vm.listaAccionesRegistradas);
                                /*vm.dataDenuncia.tipoPersona=0;*/
        /*  }
                            else {
                                toastr.error(response.mensaje);
                            }
                        })
                        .catch(function (error) {
                            toastr.error('Error al consultar');
                        })
                        .finally(function () {

                        });

        }*/
        function listarAccionesRegistradas() {
            // debugger;
            var data = {
                idDenuncia: vm.dataDenunciaBandeja.idDenuncia,
            }
            AtencionDenuncia.listarAccionPorIdDenuncia(data).then(function(response) {
                if (response.valido) {
                    vm.listaAccionesRegistradas = [];
                    for (var x in response.data) {
                        if ((response.data[x].tipoTablaAccion == 2 && response.data[x].estado == 3) || (response.data[x].tipoTablaAccion == 1 && response.data[x].estado == 4)) {
                            vm.listaAccionesRegistradas.push(angular.copy(response.data[x]));
                        }
                    }
                    console.log(vm.listaAccionesRegistradas);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function registrarAtencion() {
            if (vm.archivoAdjunto.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataInformarAccion.denuncia.idDenuncia = vm.dataDenunciaInformacion.idDenuncia;
            vm.dataInformarAccion.fechaInicio = convertDateToString(vm.fechaInicio);
            vm.dataInformarAccion.fechaFin = convertDateToString(vm.fechaFin);
            vm.dataInformarAccion.tipoInforme.codigoRegistro = 2;
            vm.dataInformarAccion.estado.codigoRegistro = 1;
            InformeAccionFactory.registrar(vm.archivoAdjunto, vm.dataInformarAccion).then(function(response) {
                if (response.valido) {
                    vm.dataInformarAccion.idInformeAccion = response.data;
                    toastr.success("Se generó la acción número : " + vm.dataInformarAccion.idInformeAccion);
                    limpiarDatos();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar');
            }).finally(function() {
                cancelar();
            });
        }

        function cargarCombos() {
            var codigosCombos = ['TipoAccionDenuncia', 'TipoSupervicion'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoAccionDenuncia':
                                vm.listaTipoAccion = datosCombo;
                                break;
                            case 'TipoSupervicion':
                                vm.listaTipoSupervicion = datosCombo;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function init() {
            if ($rootScope.dataDenunciaHistorialInvitado != null) {
                vm.dataDenunciaBandeja = $rootScope.dataDenunciaHistorialInvitado;
                /*vm.dataInformeAccion.denuncia.idDenuncia = $rootScope.dataDenunciaHistorial.idDenuncia;*/
                listarAccionesRegistradas();
            } else {
                $state.go('invitado.listado');
            }
            cargarCombos();
        }
        init();
        /*fin de metodos*/
    }
})();