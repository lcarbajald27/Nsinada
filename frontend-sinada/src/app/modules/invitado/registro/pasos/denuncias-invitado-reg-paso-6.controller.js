(function() {
    'use strict';
    angular.module('spapp.invitado').controller('DenunciasInvitadoRegPaso6Controller', DenunciasInvitadoRegPaso6Controller);
    /* @ngInject */
    function DenunciasInvitadoRegPaso6Controller($scope, $rootScope, $state, toastr, ngDialog, DenunciaFactory, MaestroFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.extensiones = [];
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "500";
        vm.caracteresMaximoArchivo = 36;
        vm.caracteresMaximoArchivo2 = 36;
        vm.dataArchivoInforma = DenunciaFactory.modelArchivo();
        vm.denuncia = DenunciaFactory.model();
        vm.validaArchivoDoc = '0';
        vm.filearch = [];
        vm.archivoAdjunto = [];
        vm.dataDenuncia = {
            descripcion: 0
        };
        $rootScope.pasoActivo = 6;
        vm.listaTipoDescripcion = [];
        /*declaracion de metodos */
        vm.siguiente = siguiente;
        vm.regresar = regresar;
        vm.validaDocumento = validaDocumento;
        vm.cambiarTipoArchivo = cambiarTipoArchivo;
        vm.agregarMedio = agregarMedio;
        vm.eliminarArchivo = eliminarArchivo;
        /*implementacion de metodos*/
        function cambiarTipoArchivo() {
            $rootScope.archivoMedio = null;
            vm.validaArchivoDoc = '0';
            var tipoArchivo = buscarMaestroIndice(angular.copy(vm.listaTipoDescripcion), angular.copy(vm.denuncia.tipoMedio));
            vm.extension = angular.copy(vm.extensiones[tipoArchivo]);
            if ((vm.archivoAdjunto.length != vm.denuncia.lstArchivoMedio.length) && vm.archivoAdjunto.length > 0) {
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                document.getElementById("fileArchivoMedio").value = "";
            }
        }

        function siguiente() {
            //debugger;
            // Cargar datos geograficos en el objeto denuncia
            Object.keys($rootScope.infoCapasGeograficas).forEach( function(property ){
                vm.denuncia[property] = $rootScope.infoCapasGeograficas[property];
            });
            DenunciaFactory.obtenerDatosFichaDenuncia(angular.copy(vm.denuncia)).then(function(response) {
                if (response.data != null) {
                    console.log(response.data);
                    vm.denuncia.adminLocales = response.data.adminLocales || "";
                    vm.denuncia.areaConservacion = response.data.areaConservacion || "";
                    vm.denuncia.oficinasDesconcentradas = response.data.oficinasDesconcentradas || "";
                    vm.denuncia.zonaAmortiguamiento = response.data.zonaAmortiguamiento || "";
                    vm.denuncia.adminLocalesNombre = response.data.adminLocalesNombre || "";
                    vm.denuncia.areaConservacionNombre = response.data.areaConservacionNombre || "";
                    vm.denuncia.oficinasDesconcentradasNombre = response.data.oficinasDesconcentradasNombre || "";
                    vm.denuncia.zonaAmortiguamientoNombre = response.data.zonaAmortiguamientoNombre || "";
                    $rootScope.prmDataFichaDenuncia = angular.copy(response.data);
                    //$rootScope.urlPRDTemp = "<embed  src='" + response.data + "' style='width:700px; height:700px;' > ";
                    $rootScope.ValidaPaso7 = '1';
                    $state.go('invitado.registro.paso7');
                    $rootScope.lstMediosAdjuntos = vm.archivoAdjunto;
                    localStorage.removeItem("objDenuncia");
                    localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
                } else {
                    toastr.warning('Error al crear PDF.');
                }
            }).catch(function(error) {
                $rootScope.urlPRDTemp = "";
                toastr.error('Sucedió un problema interno en la aplicación. Inténtelo más tarde.');
            });
        }
        /*  function siguiente() {
                DenunciaFactory
                .enviarTempPDF(angular.copy(vm.denuncia))
                .then(function (response) {
                    
                        if (response.data!=null) {
                            
                            $rootScope.urlPRDTemp = "<embed  src='" + response.data + "' style='width:700px; height:700px;' > ";
                            $rootScope.ValidaPaso7 = '1';
                            $state.go('invitado.registro.paso7');
                            $rootScope.lstMediosAdjuntos = vm.archivoAdjunto;
                            localStorage.removeItem("objDenuncia");
                            localStorage.setItem("objDenuncia",angular.toJson(vm.denuncia));
                        }
                        else{
                            $rootScope.urlPRDTemp ="";
                            toastr.warning('Error al crear PDF.');
                        }
                
                    
                })
                .catch(function (error) {
                    $rootScope.urlPRDTemp ="";
                    toastr.error('Sucedió un problema interno en la aplicación. Inténtelo más tarde.');
                });
        }*/
        function regresar() {
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            $state.go('invitado.registro.paso5');
        }

        function validaDocumento(prmFileList) {
            /*if (!['application/pdf'].includes(vm.fileModelDocumento.type)) {
                toastr.warning('El documento debe ser un archivo PDF.');
                return;
            }*/
            if ((vm.archivoAdjunto.length != vm.denuncia.lstArchivoMedio.length) && vm.archivoAdjunto.length > 0) {
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
            }
            var lstExtens = vm.extension.split(",");
            if (prmFileList == null) {
                toastr.warning('Debe seleccionar un archivo.');
                return;
            }
            if ($rootScope.archivoMedio == undefined) {
                /*$rootScope.archivoMedio =[];*/
            }
            /*var archivo=prmFileList[0];*/
            /*console.log("archivo" + angular.toJson(archivo.length));*/
            $rootScope.archivoMedio = (prmFileList);
            /*console.log("Numero de Archivos" + angular.toJson($rootScope.archivoMedio-));*/
            /*$rootScope.archivoMedio.push(prmFileList[0]);
            console.log("Numero de Archivos" + angular.toJson($rootScope.archivoMedio));*/
            /*console.log(vm.denuncia.archivoMedio);*/
            for (var i = 0; i < prmFileList.length; i++) {
                var tipoArchivo = buscarMaestroIndice(angular.copy(vm.listaTipoDescripcion), angular.copy(vm.denuncia.tipoMedio));
                var archivo = prmFileList[i];
                for (var x in vm.archivoAdjunto) {
                    if (vm.archivoAdjunto[x].name == archivo.name) {
                        toastr.warning('Debe ingresar un archivo con un nombre diferente');
                        document.getElementById("fileArchivoMedio").value = "";
                        /*  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoMedio.length, 1);*/
                        return;
                    }
                }
                if (archivo.size / 1000 > (parseInt(vm.tamanioArchivo) * 1000)) {
                    toastr.warning('El tamaño del archivo no debe superar ' + angular.copy(vm.tamanioArchivo) + 'MB. Comprima el archivo y vuelva a intentarlo.');
                    document.getElementById("fileArchivoMedio").value = "";
                    return;
                    vm.validaArchivoDoc = '0';
                }
                var str = archivo.name;
                var extensionArchivo = str.substring(str.lastIndexOf("."), str.length);
                if (!vm.mimeTypes[tipoArchivo].includes(archivo.type) && !lstExtens.includes(extensionArchivo)) {
                    toastr.warning('No es el tipo de archivo seleccionado.');
                    document.getElementById("fileArchivoMedio").value = "";
                    vm.validaArchivoDoc = '0';
                    return;
                }
				
                if (archivo.name.length > parseInt(vm.caracteresMaximoArchivo) + 4) {
                    toastr.warning('El nombre del archivo no debe superar los ' + vm.caracteresMaximoArchivo + ' caracteres. Renombre el archivo y vuelva a cargarlo.');
                    document.getElementById("fileArchivoMedio").value = "";
                    vm.validaArchivoDoc = '0';
                    return;
                }
                /*if(vm.denuncia.tipoMedio==2 && !['audio/mp3','audio/3gpp2','audio/3gpp','audio/webm','audio/x-wav','audio/ogg','audio/midi','audio/aac'].includes(archivo.type)){
                        toastr.warning('No es el Tipo de Archivo Seleccionado');
                     document.getElementById("fileArchivoMedio").value = "";
                     vm.validaArchivoDoc='0';
                     return;
            

                }

                if(vm.denuncia.tipoMedio==3 && !['video/mp4','video/x-msvideo','video/mpeg','video/ogg','video/3gpp2','video/3gpp','video/webm'].includes(archivo.type)){
                        toastr.warning('No es el Tipo de Archivo Seleccionado');
                     document.getElementById("fileArchivoMedio").value = "";
                     vm.validaArchivoDoc='0';
                     return;
                

                }

                if(vm.denuncia.tipoMedio==4 && !['application/pdf'].includes(archivo.type)){
                        toastr.warning('No es el Tipo de Archivo Seleccionado');
                     document.getElementById("fileArchivoMedio").value = "";
                     vm.validaArchivoDoc='0';
                     return;
                    

                }*/
            }
            vm.archivoAdjunto.push(prmFileList[0]);
            vm.validaArchivoDoc = '1';
        }

        function cargarCombos() {
            var codigosCombos = ['TipoArchivoDenuncia'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoArchivoDenuncia':
                                vm.listaTipoDescripcion = datosCombo;
                                vm.extensiones = response.data[x].extensiones;
                                vm.mimeTypes = response.data[x].mimetypes;
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

        function agregarMedio() {
            document.getElementById("fileArchivoMedio").value
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.denuncia.lstArchivoMedio.length)) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            if (document.getElementById("fileArchivoMedio").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                return;
            }
            //  console.log(vm.archivoAdjunto.length);
            vm.dataArchivoInforma.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoInforma.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoInforma.posicionArchivo].name);
            vm.dataArchivoInforma.tipoArchivo = angular.copy(buscarMaestro(angular.copy(vm.listaTipoDescripcion), angular.copy(vm.denuncia.tipoMedio)));
            if (vm.denuncia.tipoMedio == 5) {
                vm.dataArchivoInforma.descripcionArchivo = angular.copy(vm.denuncia.descripcionArchivo);
            }
            vm.denuncia.lstArchivoMedio.push(angular.copy(vm.dataArchivoInforma));
            document.getElementById("fileArchivoMedio").value = "";
            vm.dataArchivoInforma.descripcionArchivo = '';
            vm.denuncia.tipoMedio = 0;
        }

        function buscarMaestro(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                console.log(listado[i], "=", codigo);
                if (listado[i].codigoRegistro == codigo) {
                    return listado[i];
                }
            }
        }

        function buscarMaestroIndice(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                if (listado[i].codigoRegistro == codigo) {
                    return i;
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
                vm.archivoAdjunto.splice(index, 1);
                vm.denuncia.lstArchivoMedio.splice(index, 1);
            });
        }

        function validaArchivos() {
            if (vm.archivoAdjunto.length == (vm.denuncia.lstArchivoMedio.length + 1)) {
                document.getElementById("fileArchivoMedio").value = "";
                vm.archivoAdjunto.splice(vm.denuncia.lstArchivoMedio.length - 1, 1);
            }
        }

        function obtenerParametros() {
            MaestroFactory.obtenerParametros().then(function(response) {
                vm.tamanioArchivo = angular.copy(response.data[0].tamanioArchivo);
                vm.caracteresMaximoArchivo = angular.copy(response.data[0].maximoCaracteresArchivo);
            }).catch(function(error) {
                toastr.error('No se pudo obtener los parametros.');
            });
        }
        /*fin de implementacion de metodos*/
        function init() {
            if ($rootScope.ValidaPaso6 == '1') {
                $rootScope.ValidaPaso6 = '1';
                if (angular.isDefined($rootScope.lstMediosAdjuntos)) {
                    vm.archivoAdjunto = $rootScope.lstMediosAdjuntos;
                }
                if (angular.isDefined(localStorage.objDenuncia)) {
                    vm.denuncia = angular.copy(angular.fromJson(localStorage.objDenuncia));
                } else {}
            } else {
                localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                localStorage.removeItem("objPreguntaResLocal");
                $state.go('invitado.registro.paso1');
            }
            obtenerParametros();
            cargarCombos();
            vm.denuncia.tipoMedio = 0;
        }
        init();
        /*fin de controller*/
    }
})();