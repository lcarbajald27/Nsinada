(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('EvaluacionConAccionesDialogController', EvaluacionConAccionesDialogController);
    /** @ngInject */
    function EvaluacionConAccionesDialogController($state, toastr, $scope, ngDialog, EvaluacionInformeFactory, AtencionDenuncia, MaestroFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /****  Variables Archivo  ***/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        $scope.serial = 1;
        vm.dataArchivoEvaluacion = AtencionDenuncia.modelArchivoAtencion();
        vm.archivoAdjunto = [];
        /* Fin */
        vm.dataInformeAtencion = {}; // Variable que almacena el dato del Informe de accion o aTencion 
        vm.dataEvaluacionAccion = EvaluacionInformeFactory.model();
        vm.informeSeguimiento = {
            motivo: 0,
            tipoArchivo: 0,
            listaTiposMotivo: 0,
            listaTiposArchivo: 0,
            listaTiposDestinatario: 0,
            otroMotivo: ''
        };
        vm.data = {
            tipoAccion: 0
        };
        vm.listaTiposArchivo = [];
        vm.listaTiposMotivo = [];
        vm.listaTiposDestinatario = [];
        vm.listaEfa = [];
        vm.listadoMotivo = [];
        vm.listadoConformidad = [];
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "500";
        vm.caracteresMaximoArchivo = 50;
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.validaDocumento = validaDocumento;
        vm.agregarArchivoEvaluacion = agregarArchivoEvaluacion;
        vm.enviarAprobacion = enviarAprobacion;
        vm.validaArchivos = validaArchivos;
        vm.eliminarArchivo = eliminarArchivo;
        vm.registrarEvaluacionAtencion = registrarEvaluacionAtencion;
        vm.confirmarEliminarArchivo = confirmarEliminarArchivo;
        /*implementación de metodos*/
        function confirmarEliminarArchivo(index, item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar el archivo: ' + item.nombreArchivo + '?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarArchivo(index);
            });
        }
        $scope.obtenerValorIndex = function(a, b) {
            return (a + b) - 1;
        }
        $scope.indexCount = function(newPageNumber) {
            $scope.serial = newPageNumber * ($scope.config.itemsPerPage) - ($scope.config.itemsPerPage - 1);
        }

        function registrarEvaluacionAtencion() {
            if (vm.dataEvaluacionAccion.tipoOpcion.codigoRegistro == 1) {
                observarInformeAccion();
            } else if (vm.dataEvaluacionAccion.tipoOpcion.codigoRegistro == 2) {
                aprobarInformeAccion();
            }
        }

        function aprobarInformeAccion() {
            vm.dataEvaluacionAccion.tipoInforme.codigoRegistro = vm.dataInformeAtencion.tipoAtencionAccion;
            vm.dataEvaluacionAccion.informeAccion.idInformeAccion = vm.dataInformeAtencion.idAccion;
            EvaluacionInformeFactory.aprobarAccion(vm.dataEvaluacionAccion).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluacionAccion.idEvaluacionInforme = response.data;
                    if (vm.dataEvaluacionAccion.tipoInforme.codigoRegistro == 1) {
                        toastr.success("Se aprobó la acción de la denuncia");
                    } else {
                        toastr.success("Se aprobó la atención de la denuncia");
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al observar la Acción');
            }).finally(function() {
                cancelar();
            });
        }

        function observarInformeAccion() {
            if (vm.dataEvaluacionAccion.lstArchivoAtencion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataEvaluacionAccion.tipoInforme.codigoRegistro = vm.dataInformeAtencion.tipoAtencionAccion;
            vm.dataEvaluacionAccion.informeAccion.idInformeAccion = vm.dataInformeAtencion.idAccion;
            EvaluacionInformeFactory.registrar(vm.archivoAdjunto, vm.dataEvaluacionAccion).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluacionAccion.idEvaluacionInforme = response.data;
                    if (vm.dataEvaluacionAccion.tipoInforme.codigoRegistro == 1) {
                        toastr.success("Se observó la acción correctamente");
                    } else {
                        toastr.success("Se observó la atención correctamente");
                    }
                    //toastr.success("Se realizó el informe de no atencion número :" + vm.dataEvaluacionAccion.idEvaluacionInforme);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al observar la acción');
            }).finally(function() {
                cancelar();
            });
        }

        function eliminarArchivo(index) {
            vm.archivoAdjunto.splice(vm.dataEvaluacionAccion.lstArchivoAtencion[index].posicionArchivo, 1);
            vm.dataEvaluacionAccion.lstArchivoAtencion.splice(index, 1);
        }

        function validaArchivos() {
            if (vm.archivoAdjunto.length == (vm.dataEvaluacionAccion.lstArchivoAtencion.length + 1)) {
                document.getElementById("fileArchivoSeguimientoEvaluacionAccion").value = "";
                vm.archivoAdjunto.splice(vm.dataEvaluacionAccion.lstArchivoAtencion.length - 1, 1);
            }
        }

        function obtenerDatoMaestro(listaMaestro, codigoReg) {
            var data = null
            for (var x in listaMaestro) {
                if (listaMaestro[x].codigoRegistro == codigoReg) {
                    data = listaMaestro[x];
                }
            }
            return data;
        }

        function agregarArchivoEvaluacion() {
            var objData = obtenerDatoMaestro(vm.listaTiposArchivo, vm.dataArchivoEvaluacion.tipoArchivo.codigoRegistro);
            vm.dataArchivoEvaluacion.tipoArchivo = angular.copy(objData);
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.dataEvaluacionAccion.lstArchivoAtencion.length)) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            if (document.getElementById("fileArchivoSeguimientoEvaluacionAccion").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                return;
            }
            //      console.log(vm.archivoAdjunto.length);
            vm.dataArchivoEvaluacion.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoEvaluacion.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoEvaluacion.posicionArchivo].name);
            vm.dataEvaluacionAccion.lstArchivoAtencion.push(angular.copy(vm.dataArchivoEvaluacion));
            document.getElementById("fileArchivoSeguimientoEvaluacionAccion").value = "";
            vm.dataArchivoEvaluacion.descripcionArchivo = '';
            vm.dataArchivoEvaluacion.tipoArchivo.codigoRegistro = 0;
        }

        function validaDocumento(prmFileList) {
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            var lstExtens = vm.extension.split(",");
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                for (var x in vm.archivoAdjunto) {
                    if (vm.archivoAdjunto[x].name == archivo.name) {
                        toastr.warning('Debe ingresar un archivo con un nombre diferente');
                        document.getElementById("fileArchivoSeguimientoEvaluacionAccion").value = "";
                        /*  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
                        return;
                    }
                }
                if (archivo.size / 1024 > (parseInt(vm.tamanioArchivo) * 1000)) {
                    toastr.warning('El tamaño del archivo no debe superar ' + angular.copy(vm.tamanioArchivo) + 'MB. Comprima el archivo y vuelva a intentarlo');
                    document.getElementById("fileArchivoSeguimientoEvaluacionAccion").value = "";
                    /*vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
                    return;
                }
                var str = archivo.name;
                var extensionArchivo = str.substring(str.lastIndexOf("."), str.length);
                if (!vm.mimeTypes.includes(archivo.type) && !lstExtens.includes(extensionArchivo)) {
                    toastr.warning('No es el tipo de archivo seleccionado');
                    document.getElementById("fileArchivoSeguimientoEvaluacionAccion").value = "";
                    return;
                }
                if (archivo.name.length > parseInt(vm.caracteresMaximoArchivo)) {
                    toastr.warning('El nombre del archivo no debe superar los ' + vm.caracteresMaximoArchivo + ' caracteres. Renombre el archivo y vuelva a cargarlo');
                    document.getElementById("fileArchivoSeguimientoEvaluacionAccion").value = "";
                    /* vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
                    return;
                }
            }
            //  console.log(vm.archivoAdjunto.length);
            vm.archivoAdjunto.push(prmFileList[0]);
        }

        function cargarCombos() {
            var codigosCombos = ['MotivoNoAtencionDenuncia', 'TipoArchivoAdjuntoEspecialistaSinada'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoArchivoAdjuntoEspecialistaSinada':
                                vm.listaTiposArchivo = datosCombo;
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

        function cancelar() {
            ngDialog.close();
        }

        function enviarAprobacion() {
            $scope.closeThisDialog({
                enviado: true,
                aprobado: (vm.data.tipoAccion == 2)
            });
        }

        function obtenerParametros() {
            MaestroFactory.obtenerParametros().then(function(response) {
                vm.tamanioArchivo = angular.copy(response.data[0].tamanioArchivo);
                vm.caracteresMaximoArchivo = angular.copy(response.data[0].maximoCaracteresArchivo);
                vm.extension = angular.copy(response.data[0].extensionArchivoDocumento);
                var tipos = angular.copy(response.data[0].tipoArchivoDocumento);
                vm.mimeTypes = angular.copy(tipos.split(","));
            }).catch(function(error) {
                toastr.error('No se pudo obtener los parametros');
            });
        }
        /*fin de metodos*/
        function init() {
            if (angular.isDefined($scope.ngDialogData)) {
                var data = angular.copy($scope.ngDialogData.data);
                vm.dataInformeAtencion = data;
            }
            cargarCombos();
            obtenerParametros();
            /*vm.listaTiposMotivo.push({codigoRegistro:1,descripcion:'No Recepcionó Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:2,descripcion:'No Validó Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:3,descripcion:'No Atendió Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:4,descripcion:'No Levantó Observaciones'});
            vm.listaTiposMotivo.push({codigoRegistro:5,descripcion:'Otros'});

            vm.listaTiposDestinatario.push({codigoRegistro:1,descripcion:'EFA'});
            vm.listaTiposDestinatario.push({codigoRegistro:2,descripcion:'OEFA'});

            vm.listaEfa.push({codigoRegistro:1,descripcion:'Ámbito Nacional'});
            vm.listaEfa.push({codigoRegistro:2,descripcion:'Ámbito Regional'});
            vm.listaEfa.push({codigoRegistro:3,descripcion:'Ámbito Local'});
                
            vm.listadoMotivo.push({codigoRegistro:1,descripcion:'No es Materia Ambiental'});
            vm.listadoMotivo.push({codigoRegistro:2,descripcion:'Datos Inexactos y/o Insuficientes'});*/
            vm.listadoConformidad.push({
                codigoRegistro: 1,
                descripcion: 'Conforme'
            });
            vm.listadoConformidad.push({
                codigoRegistro: 2,
                descripcion: 'No conforme'
            });
        }
        init();
        /*fin de controller*/
    }
})();