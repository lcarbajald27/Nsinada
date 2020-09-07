(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('DialogLevantarObservacionController', DialogLevantarObservacionController);
    /* @ngInject */
    function DialogLevantarObservacionController($scope, $state, toastr, ngDialog, MaestroFactory, InformeAccionFactory, $filter, BandejaFactory, $rootScope, AtencionDenuncia) {
        var vm = this;
        /*declaracion de variables globales*/
        /*************** Archivo****************/
        vm.dataInformeObservacion = {};
        vm.dataArchivoInforma = AtencionDenuncia.modelArchivoAtencion();
        vm.archivoAdjunto = [];
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "500";
        vm.caracteresMaximoArchivo = 50;
        /*********************************/
        vm.usuario = {};
        vm.dataBandeja = {};
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.dataInformarAccion = InformeAccionFactory.model();
        /*vm.dataInformarAtencion = InformeAccionFactory.model();*/
        /*vm.dataDenunciaInformacion = BandejaFactory.bandejaDenuncia();*/
        vm.fechaInicio = '';
        vm.fechaFin = '';
        vm.listaTipoAccion = [];
        vm.listaTipoSupervicion = [];
        vm.asignadasInformarTabActivo = 1;
        /*declaración de metodos enlazados a la vista*/
        vm.validaDocumento = validaDocumento;
        vm.registrarAccion = registrarAccion;
        vm.registrarAtencion = registrarAtencion;
        vm.limpiarDatos = limpiarDatos;
        vm.limpiarSelect = limpiarSelect;
        vm.agregarArchivoInforme = agregarArchivoInforme;
        vm.eliminarArchivo = eliminarArchivo;
        vm.validaArchivos = validaArchivos;
        /*implementación de metodos*/
        function limpiarDatos() {
            //console.log("sadsads");
            vm.dataInformarAccion = InformeAccionFactory.model();
            vm.archivoAdjunto = [];
            vm.fechaInicio = '';
            vm.fechaFin = '';
            document.getElementById("fileArchivoMedio").value = "";
        }

        function limpiarSelect() {
            //  console.log("sadsads");
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

        function eliminarArchivo(index) {
            vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion[index].posicionArchivo, 1);
            vm.dataInformarAccion.lstArchivoInformeAccion.splice(index, 1);
        }

        function agregarArchivoInforme() {
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.dataInformarAccion.lstArchivoInformeAccion.length)) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            if (document.getElementById("fileArchivoMedio").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                return;
            }
            //console.log(vm.archivoAdjunto.length);
            vm.dataArchivoInforma.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoInforma.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoInforma.posicionArchivo].name);
            vm.dataInformarAccion.lstArchivoInformeAccion.push(angular.copy(vm.dataArchivoInforma));
            document.getElementById("fileArchivoMedio").value = "";
            vm.dataArchivoInforma.descripcionArchivo = '';
        }

        function validaArchivos() {
            if (vm.archivoAdjunto.length == (vm.dataInformarAccion.lstArchivoInformeAccion.length + 1)) {
                document.getElementById("fileArchivoMedio").value = "";
                vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length - 1, 1);
            }
        }

        function validaDocumento(prmFileList) {
            /*  if(vm.archivoAdjunto.length==(vm.dataInformarAccion.lstArchivoInformeAccion.length+1)){
                         document.getElementById("fileArchivoMedio").value = "";
                                  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length-1, 1);
                }*/
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            /*var prmArchivo = prmFileList[0];*/
            //  console.log("vm.archivoAdjunto" + vm.archivoAdjunto);
            /*$rootScope.archivoMedio=(prmFileList);*/
            //      console.log("prmFileList.length" + prmFileList.length);
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                for (var x in vm.archivoAdjunto) {
                    if (vm.archivoAdjunto[x].name == archivo.name) {
                        toastr.warning('Debe ingresar un archivo con un nombre diferente');
                        document.getElementById("fileArchivoMedio").value = "";
                        /*  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length, 1);*/
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

        function registrarAccion() {
            if (vm.dataInformarAccion.lstArchivoInformeAccion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            /*vm.dataInformarAccion= vm.dataInformeObservacion;*/
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

        function registrarAtencion() {
            if (vm.dataInformarAccion.lstArchivoInformeAccion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            /*    vm.dataInformarAccion=   vm.dataInformeObservacion;*/
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

        function cancelar() {
            ngDialog.close();
        }

        function init() {
            // debugger;
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                /*vm.usuario =  angular.fromJson(localStorage.oSuSinWebDataSys);
                    vm.dataBandeja  = vm.usuario.bandeja;
*/
                if (angular.isDefined($scope.ngDialogData)) {
                    var data = angular.copy($scope.ngDialogData.prmData);
                    vm.dataInformeObservacion = data;
                    vm.dataInformarAccion = vm.dataInformeObservacion;
                }
            }
            cargarCombos();
            obtenerParametros();
        }
        init();
        /*fin de metodos*/
    }
})();