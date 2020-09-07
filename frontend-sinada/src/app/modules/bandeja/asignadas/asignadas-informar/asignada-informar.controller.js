(function() {
    'use strict';
    angular.module('spapp.bandeja').controller('AsignadaInformarController', AsignadaInformarController);
    /** @ngInject */
    function AsignadaInformarController($state, toastr, ngDialog, MaestroFactory, InformeAccionFactory, $filter, BandejaFactory, $rootScope, AtencionDenuncia) {
        var vm = this;
        /*declaracion de variables globales*/
        /*************** Archivo ****************/
        vm.dataArchivoInforma = AtencionDenuncia.modelArchivoAtencion();
        vm.archivoAdjunto = [];
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "500";
        vm.caracteresMaximoArchivo = 50;
        /*********************************/
        /* vm.dtFechaInicioOptions = {
             minDate: new Date,
             showWeeks: false
         };*/
        vm.usuario = {};
        vm.dataBandeja = {};
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.dataInformarAccion = InformeAccionFactory.model();
        /*vm.dataInformarAtencion = InformeAccionFactory.model();*/
        vm.dataDenunciaInformacion = BandejaFactory.bandejaDenuncia();
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
        vm.regresarBandejaEntidad = regresarBandejaEntidad;
        vm.agregarArchivoInformeAccionDenuncia = agregarArchivoInformeAccionDenuncia;
        vm.validaArchivosAccionDenuncia = validaArchivosAccionDenuncia;
        vm.validaDocumentoAccionDenuncia = validaDocumentoAccionDenuncia;
        vm.validarFechasSupervision = validarFechasSupervision;
        /*implementación de metodos*/
        /*********************************************************************/
        function validarFechasSupervision() {
            if (vm.fechaInicio == null || vm.fechaInicio.length == 0) {
                toastr.warning('Debe ingresar una fecha de inicio');
                vm.fechaFin = '';
            }
            if (vm.fechaInicio > vm.fechaFin) {
                toastr.warning('Debe ingresar una fecha mayor a la fecha de inicio');
                vm.fechaFin = '';
            }
        }

        function agregarArchivoInformeAccionDenuncia() {
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.dataInformarAccion.lstArchivoInformeAccion.length)) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            if (document.getElementById("fileArchivoInformeAccionDenuncia").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                return;
            }
            console.log(vm.archivoAdjunto.length);
            vm.dataArchivoInforma.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoInforma.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoInforma.posicionArchivo].name);
            vm.dataInformarAccion.lstArchivoInformeAccion.push(angular.copy(vm.dataArchivoInforma));
            document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
            vm.dataArchivoInforma.descripcionArchivo = '';
        }

        function validaArchivosAccionDenuncia() {
            if (vm.archivoAdjunto.length == (vm.dataInformarAccion.lstArchivoInformeAccion.length + 1)) {
                document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
                vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length - 1, 1);
            }
        }

        function validaDocumentoAccionDenuncia(prmFileList) {
            /*  if(vm.archivoAdjunto.length==(vm.dataInformarAccion.lstArchivoInformeAccion.length+1)){
                         document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
                                  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length-1, 1);
                }*/
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            var lstExtens = vm.extension.split(",");
            /*var prmArchivo = prmFileList[0];*/
            //  console.log("vm.archivoAdjunto" + vm.archivoAdjunto);
            /*$rootScope.archivoMedio=(prmFileList);*/
            //  console.log("prmFileList.length" + prmFileList.length);
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                for (var x in vm.archivoAdjunto) {
                    if (vm.archivoAdjunto[x].name == archivo.name) {
                        toastr.warning('Debe ingresar un archivo con un nombre diferente');
                        document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
                        /*  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length, 1);*/
                        return;
                    }
                }
                if (archivo.size / 1000 > (parseInt(vm.tamanioArchivo) * 1000)) {
                    toastr.warning('El tamaño del archivo no debe superar ' + angular.copy(vm.tamanioArchivo) + 'MB. Comprima el archivo y vuelva a intentarlo');
                    document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
                    return;
                }
                var str = archivo.name;
                var extensionArchivo = str.substring(str.lastIndexOf("."), str.length);
                if (!vm.mimeTypes.includes(archivo.type) && !lstExtens.includes(extensionArchivo)) {
                    toastr.warning('No es el tipo de archivo seleccionado');
                    document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
                    return;
                }
                if (archivo.name.length > parseInt(vm.caracteresMaximoArchivo)) {
                    toastr.warning('El nombre del archivo no debe superar los ' + vm.caracteresMaximoArchivo + ' caracteres. Renombre el archivo y vuelva a cargarlo');
                    document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
                    return;
                }
            }
            //console.log(vm.archivoAdjunto.length);
            vm.archivoAdjunto.push(prmFileList[0]);
        }
        /*********************************************************************/
        /********************************************************************/
        function regresarBandejaEntidad() {
            $state.go('spapp.home.bandeja.asignadas');
        }

        function limpiarDatos() {
            //console.log("sadsads");
            vm.dataInformarAccion = InformeAccionFactory.model();
            vm.archivoAdjunto = [];
            vm.fechaInicio = '';
            vm.fechaFin = '';
            document.getElementById("fileArchivoInformeAtencion").value = "";
            document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
            vm.dataInformarAccion.lstArchivoInformeAccion = [];
        }

        function limpiarSelect() {
            //console.log("sadsads");
            vm.dataInformarAccion.tipoSupervicion.codigoRegistro = 0;
            vm.dataInformarAccion.descripcionAccion = '';
            vm.archivoAdjunto = [];
            vm.fechaInicio = '';
            vm.fechaFin = '';
            document.getElementById("fileArchivoInformeAtencion").value = "";
            document.getElementById("fileArchivoInformeAccionDenuncia").value = "";
            vm.dataInformarAccion.lstArchivoInformeAccion = [];
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
                vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion[index].posicionArchivo, 1);
                vm.dataInformarAccion.lstArchivoInformeAccion.splice(index, 1);
            });
        }

        function agregarArchivoInforme() {
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.dataInformarAccion.lstArchivoInformeAccion.length)) {
                toastr.warning('Debe Seleccionar un archivo');
                return;
            }
            if (document.getElementById("fileArchivoInformeAtencion").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                return;
            }
            console.log(vm.archivoAdjunto.length);
            vm.dataArchivoInforma.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoInforma.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoInforma.posicionArchivo].name);
            vm.dataInformarAccion.lstArchivoInformeAccion.push(angular.copy(vm.dataArchivoInforma));
            document.getElementById("fileArchivoInformeAtencion").value = "";
            vm.dataArchivoInforma.descripcionArchivo = '';
        }

        function validaArchivos() {
            if (vm.archivoAdjunto.length == (vm.dataInformarAccion.lstArchivoInformeAccion.length + 1)) {
                document.getElementById("fileArchivoInformeAtencion").value = "";
                vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length - 1, 1);
            }
        }

        function validaDocumento(prmFileList) {
            /*  if(vm.archivoAdjunto.length==(vm.dataInformarAccion.lstArchivoInformeAccion.length+1)){
                         document.getElementById("fileArchivoInformeAtencion").value = "";
                                  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length-1, 1);
                }*/
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            var lstExtens = vm.extension.split(",");
            /*var prmArchivo = prmFileList[0];*/
            //  console.log("vm.archivoAdjunto" + vm.archivoAdjunto);
            /*$rootScope.archivoMedio=(prmFileList);*/
            //  console.log("prmFileList.length" + prmFileList.length);
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                for (var x in vm.archivoAdjunto) {
                    if (vm.archivoAdjunto[x].name == archivo.name) {
                        toastr.warning('Debe ingresar un archivo con un nombre diferente');
                        document.getElementById("fileArchivoInformeAtencion").value = "";
                        /*  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoInformeAccion.length, 1);*/
                        return;
                    }
                }
                if (archivo.size / 1000 > (parseInt(vm.tamanioArchivo) * 1000)) {
                    toastr.warning('El tamaño del archivo no debe superar ' + angular.copy(vm.tamanioArchivo) + 'MB. Comprima el archivo y vuelva a intentarlo');
                    document.getElementById("fileArchivoInformeAtencion").value = "";
                    return;
                }
                var str = archivo.name;
                var extensionArchivo = str.substring(str.lastIndexOf("."), str.length);
                if (!vm.mimeTypes.includes(archivo.type) && !lstExtens.includes(extensionArchivo)) {
                    toastr.warning('No es el tipo de archivo seleccionado');
                    document.getElementById("fileArchivoInformeAtencion").value = "";
                    return;
                }
                if (archivo.name.length > parseInt(vm.caracteresMaximoArchivo)) {
                    toastr.warning('El nombre del archivo no debe superar los ' + vm.caracteresMaximoArchivo + ' caracteres. Renombre el archivo y vuelva a cargarlo');
                    document.getElementById("fileArchivoInformeAtencion").value = "";
                    return;
                }
            }
            //console.log(vm.archivoAdjunto.length);
            vm.archivoAdjunto.push(prmFileList[0]);
        }

        function registrarAccion() {
            if (vm.dataInformarAccion.lstArchivoInformeAccion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            /*    if(vm.archivoAdjunto.length==0){

                      toastr.warning('Debe Seleccionar un Archivo');
              
                      return;
              }*/
            vm.dataInformarAccion.detalleBandeja.idBandejaDetalle = vm.dataDenunciaInformacion.idBandejaDetalle;
            /* vm.dataInformarAccion.tipoEntidadCompetente.codigoRegistro = vm.usuario.tipoEntidadCompetente;
            if(vm.dataInformarAccion.tipoEntidadCompetente.codigoRegistro == 1){
                    vm.dataInformarAccion.personaOefa.idPersonaOefa = vm.usuario.idPersonaOefa;

            }

            if(vm.dataInformarAccion.tipoEntidadCompetente.codigoRegistro == 2){
                    vm.dataInformarAccion.efa.idEfa = vm.usuario.idEfa;
            }*/
            vm.dataInformarAccion.denuncia.idDenuncia = vm.dataDenunciaInformacion.idDenuncia;
            vm.dataInformarAccion.fechaInicio = convertDateToString(vm.fechaInicio);
            vm.dataInformarAccion.fechaFin = convertDateToString(vm.fechaFin);
            vm.dataInformarAccion.tipoInforme.codigoRegistro = 1;
            vm.dataInformarAccion.estado.codigoRegistro = 1;
            InformeAccionFactory.registrar(vm.archivoAdjunto, vm.dataInformarAccion).then(function(response) {
                if (response.valido) {
                    vm.dataInformarAccion.idInformeAccion = response.data;
                    toastr.success("Se registró la acción correctamente");
                    //                      toastr.success("Se generó la acción número : " + vm.dataInformarAccion.idInformeAccion  );
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
            /*
                if(vm.archivoAdjunto.length==0){

                                toastr.warning('Debe Seleccionar un Archivo');
                        
                                return;
                        }*/
            if (vm.dataInformarAccion.lstArchivoInformeAccion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            /* vm.dataInformarAccion.tipoEntidadCompetente.codigoRegistro = vm.usuario.tipoEntidadCompetente;
            if(vm.dataInformarAccion.tipoEntidadCompetente.codigoRegistro == 1){

                    vm.dataInformarAccion.personaOefa.idPersonaOefa = vm.usuario.idPersonaOefa;
            
            }

            if(vm.dataInformarAccion.tipoEntidadCompetente.codigoRegistro == 2){
                    vm.dataInformarAccion.efa.idEfa = vm.usuario.idEfa;
            }*/
            vm.dataInformarAccion.detalleBandeja.idBandejaDetalle = vm.dataDenunciaInformacion.idBandejaDetalle;
            vm.dataInformarAccion.denuncia.idDenuncia = vm.dataDenunciaInformacion.idDenuncia;
            vm.dataInformarAccion.fechaInicio = convertDateToString(vm.fechaInicio);
            vm.dataInformarAccion.fechaFin = convertDateToString(vm.fechaFin);
            vm.dataInformarAccion.tipoInforme.codigoRegistro = 2;
            vm.dataInformarAccion.estado.codigoRegistro = 1;
            InformeAccionFactory.registrar(vm.archivoAdjunto, vm.dataInformarAccion).then(function(response) {
                if (response.valido) {
                    vm.dataInformarAccion.idInformeAccion = response.data;
                    toastr.success("Se registró la atención correctamente");
                    //                      toastr.success("Se generó la acción número : " + vm.dataInformarAccion.idInformeAccion  );
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

        function init() {
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                /*************  Obtenemos los datos de usuario  **************/
                vm.usuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                /*************** Pasamos los datos de Bandeja  *************/
                vm.dataBandeja = vm.usuario.bandeja;
                if ($rootScope.dataInformeDenuncia != null) {
                    vm.dataDenunciaInformacion = $rootScope.dataInformeDenuncia;
                } else {
                    $state.go('spapp.home.bandeja.asignadas');
                }
            }
            cargarCombos();
            obtenerParametros();
        }
        init();
        /*fin de metodos*/
    }
})();