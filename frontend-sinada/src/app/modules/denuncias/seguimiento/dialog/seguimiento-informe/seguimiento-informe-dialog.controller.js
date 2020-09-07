(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('SeguimientoInformeDialogController', SeguimientoInformeDialogController);
    /** @ngInject */
    function SeguimientoInformeDialogController(toastr, $scope, ngDialog, AtencionDenuncia, MaestroFactory, $log) {
        var vm = this;
        /*declaracion de variables globales*/
        /****  Variables Archivo  ***/
        vm.dataArchivoInforma = AtencionDenuncia.modelArchivoAtencion();
        vm.archivoAdjunto = [];
        /*    Fin variable Archivos    */
        $scope.opcionPaginacionItemArc = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        $scope.serialItemArc = 1;
        vm.prmDataEntidadDenuncia = {};
        vm.prmDataNoAtencionDenuncia = AtencionDenuncia.model();
        vm.informeSeguimiento = {
            motivo: 0,
            tipoArchivo: 0,
            listaTiposMotivo: 0,
            listaTiposArchivo: 0,
            otroMotivo: ''
        };
        vm.listaTiposArchivo = [];
        vm.listaTiposMotivo = [];
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "500";
        vm.caracteresMaximoArchivo = 50;
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.validaDocumento = validaDocumento;
        vm.registrarNoAtencionDenuncia = registrarNoAtencionDenuncia;
        vm.agregarArchivoNoAtencion = agregarArchivoNoAtencion;
        vm.validaArchivos = validaArchivos;
        vm.eliminarArchivo = eliminarArchivo;
        vm.mostrarModalHojaTramite = mostrarModalHojaTramite;
        vm.confirmarEliminarArchivoData = confirmarEliminarArchivoData;
        /*implementación de metodos*/
        function confirmarEliminarArchivoData(index, item) {
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
        $scope.indexCountItemArc = function(newPageNumber) {
            $scope.serialItemArc = newPageNumber * ($scope.opcionPaginacionItemArc.itemsPerPage) - ($scope.opcionPaginacionItemArc.itemsPerPage - 1);
        }

        function cancelar() {
            ngDialog.close();
        }

        function obtenerDatoMaestro(lstMaestro, codigoRegistro) {
            var data = null;
            for (var x in lstMaestro) {
                if (lstMaestro[x].codigoRegistro == codigoRegistro) {
                    data = lstMaestro[x];
                }
            }
            return data;
        }

        function registrarNoAtencionDenuncia() {
            if (vm.prmDataNoAtencionDenuncia.hojaTramite == null || vm.prmDataNoAtencionDenuncia.hojaTramite == '' || vm.prmDataNoAtencionDenuncia.hojaTramite.length == 0) {
                toastr.warning('Debe vincular su hoja de trámite');
                return;
            }
            if (vm.prmDataNoAtencionDenuncia.lstArchivoAtencion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.prmDataNoAtencionDenuncia.tipoAtencion.codigoRegistro = 4;
            AtencionDenuncia.denunciaNoAtendida(vm.archivoAdjunto, vm.prmDataNoAtencionDenuncia).then(function(response) {
            //   console.log("response :",response);
                if (response.valido) {
                    vm.prmDataNoAtencionDenuncia.idAtencionDenuncia = response.data;
                    toastr.success("Se realizó el informe de no atención");
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
             //   console.log("Error fatal : ",error);
                toastr.error('Ocurrió un error al registrar el informe de no atención');
            }).finally(function() {
                cancelar();
            });
        }

        function cargarCombos() {
            var codigosCombos = ['MotivoNoAtencionDenuncia', 'TipoArchivoAdjuntoEspecialistaSinada'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'MotivoNoAtencionDenuncia':
                                vm.listaTiposMotivo = datosCombo;
                                break;
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

        function agregarArchivoNoAtencion() {
            var dataMaestroTipoArchivo = obtenerDatoMaestro(vm.listaTiposArchivo, vm.dataArchivoInforma.tipoArchivo.codigoRegistro);
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.prmDataNoAtencionDenuncia.lstArchivoAtencion.length)) {
                toastr.warning('Debe Seleccionar un archivo');
                return;
            }
            if (document.getElementById("fileArchivoMedio").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                return;
            }
            vm.dataArchivoInforma.tipoArchivo = angular.copy(dataMaestroTipoArchivo);
            //          console.log(vm.archivoAdjunto.length);
            vm.dataArchivoInforma.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoInforma.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoInforma.posicionArchivo].name);
            vm.prmDataNoAtencionDenuncia.lstArchivoAtencion.push(angular.copy(vm.dataArchivoInforma));
            document.getElementById("fileArchivoMedio").value = "";
            vm.dataArchivoInforma.descripcionArchivo = '';
            vm.dataArchivoInforma.tipoArchivo.codigoRegistro = 0;
        }

        function eliminarArchivo(index) {
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
                vm.archivoAdjunto.splice(vm.prmDataNoAtencionDenuncia.lstArchivoAtencion[index].posicionArchivo, 1);
                vm.prmDataNoAtencionDenuncia.lstArchivoAtencion.splice(index, 1);
            });
        }

        function validaArchivos() {
            if (vm.archivoAdjunto.length == (vm.prmDataNoAtencionDenuncia.lstArchivoAtencion.length + 1)) {
                document.getElementById("fileArchivoMedio").value = "";
                vm.archivoAdjunto.splice(vm.prmDataNoAtencionDenuncia.lstArchivoAtencion.length - 1, 1);
            }
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
                        document.getElementById("fileArchivoMedio").value = "";
                        /*  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
                        return;
                    }
                }
                if (archivo.size / 1024 > (parseInt(vm.tamanioArchivo) * 1000)) {
                    toastr.warning('El tamaño del archivo no debe superar ' + angular.copy(vm.tamanioArchivo) + 'MB. Comprima el archivo y vuelva a intentarlo');
                    document.getElementById("fileArchivoMedio").value = "";
                    /*vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
                    return;
                }
                var str = archivo.name;
                var extensionArchivo = str.substring(str.lastIndexOf("."), str.length);
                if (!vm.mimeTypes.includes(archivo.type) && !lstExtens.includes(extensionArchivo)) {
                    toastr.warning('No es el tipo de archivo seleccionado');
                    document.getElementById("fileArchivoMedio").value = "";
                    return;
                }
                if (archivo.name.length > parseInt(vm.caracteresMaximoArchivo)) {
                    toastr.warning('El nombre del archivo no debe superar los ' + vm.caracteresMaximoArchivo + ' caracteres. Renombre el archivo y vuelva a cargarlo');
                    document.getElementById("fileArchivoMedio").value = "";
                    /* vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
                    return;
                }
            }
            //      console.log(vm.archivoAdjunto.length);
            vm.archivoAdjunto.push(prmFileList[0]);
        }
        /*************************************************************************************************/
        /***                                    Modal Hoja Tramite                                      **/
        /*************************************************************************************************/
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
                    vm.prmDataNoAtencionDenuncia.hojaTramite = dialogData.value.successData.denuncia.expediente.trim();
                }
            });
        }
        /*
                function validaDocumento(prmFileList){
                    
                    if (!prmFileList) {
                        toastr.warning('Debe seleccionar un archivo');
                        return;
                    }
                    vm.archivoAdjunto = prmFileList;

                        for(var i = 0 ; i<prmFileList.length;i++){

                                var archivo=prmFileList[i];

                                console.log(archivo);
                                if (archivo.size/1024>102400) {
                                
                                    toastr.warning('El tamaño del archivo no debe superar 100MB. Intente comprimir el tamaño del archivo y vuelva a intentarlo');
                                     document.getElementById("fileArchivoMedio").value = "";
                                    return;
                                    
                                }

                                if (archivo.name.length>20) {
                                
                                    toastr.warning('El Nombre del archivo no debe mayor a 20 caracteres');
                                     document.getElementById("fileArchivoMedio").value = "";
                                    return;
                                    
                                }
                            

                                

                        }

                }*/
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
                vm.prmDataEntidadDenuncia = angular.copy($scope.ngDialogData.prmEntidadDenuncia);
                vm.prmDataNoAtencionDenuncia.denuncia.idDenuncia = vm.prmDataEntidadDenuncia.idDenuncia;
                vm.prmDataNoAtencionDenuncia.detalleBandeja.idBandejaDetalle = vm.prmDataEntidadDenuncia.idBandejaDetalle;
                /*  vm.prmDataNoAtencionDenuncia.tipoEntidadCompetente.codigoRegistro = vm.prmDataEntidadDenuncia.tipoEntidadComponente;
                    if(vm.prmDataNoAtencionDenuncia.tipoEntidadCompetente.codigoRegistro == 1){
                        
                    } */
                /*if(vm.prmDataNoAtencionDenuncia.tipoEntidadCompetente.codigoRegistro == 2){

                        vm.prmDataNoAtencionDenuncia.efa.idEfa = vm.prmDataEntidadDenuncia.idEfa;

                } */
            }
            cargarCombos();
            obtenerParametros();
            /*vm.listaTiposArchivo.push({codigoRegistro:1,descripcion:'Memorándum'});
            vm.listaTiposArchivo.push({codigoRegistro:2,descripcion:'Oficio'});
            vm.listaTiposArchivo.push({codigoRegistro:3,descripcion:'Informe'});
            vm.listaTiposArchivo.push({codigoRegistro:4,descripcion:'Otros'});*/
            /*vm.listaTiposMotivo.push({codigoRegistro:1,descripcion:'No Recepcionó Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:2,descripcion:'No Validó Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:3,descripcion:'No Atendió Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:4,descripcion:'No Levantó Observaciones'});
            vm.listaTiposMotivo.push({codigoRegistro:5,descripcion:'Otros'});*/
        }
        init();
        /*fin de controller*/
    }
})();