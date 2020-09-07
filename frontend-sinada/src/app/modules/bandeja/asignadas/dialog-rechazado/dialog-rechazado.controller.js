(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('AccionesRechazadoDialogController', AccionesRechazadoDialogController);
    /* @ngInject */
    function AccionesRechazadoDialogController($state, $controller, $scope, toastr, ngDialog, AtencionDenuncia, BandejaFactory, MaestroFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.usuario = {};
        vm.dataRechazarDenuncia = AtencionDenuncia.model();
        /**************** Archivo  ******************/
        vm.archivoAdjunto = [];
        /*vm.listaDataArchivoAdjunto = [];
        vm.dataArchivoAdjunto = AtencionDenuncia.modelArchivoAtencion();*/
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "100";
        vm.caracteresMaximoArchivo = 50;
        /********************** Fin *********************/
        vm.dataBandeja = {};
        vm.dataDenunciaDetalle = {};
        /*vm.listaProducto = [];
        vm.prmProducto = ProductoFactory.modelProducto();*/
        /*variables de paginacion*/
        vm.opcionesPaginacion = {
            itemsPorPag: 10,
            cantidades: [10, 25, 50, 100]
        };
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.validaDocumento = validaDocumento;
        vm.rechazarDenuncia = rechazarDenuncia;
        /*  vm.limpiar = limpiar;
        vm.buscar = buscar;
        vm.seleccionar = seleccionar;
*/
        /*implementación de metodos*/
        /*
            
            function limpiar() {
                vm.prmProducto= ProductoFactory.modelProducto();
                vm.prmProducto.nombre = '';
            }*/
        function cambiarEstadoDetalleDenuncia(data) {
            var detalleBandeja = BandejaFactory.detalleBandeja();
            detalleBandeja.idBandejaDetalle = data.idBandejaDetalle;
            detalleBandeja.estado = 3;
            BandejaFactory.actualizaEstadoDetalleBandeja(angular.copy(detalleBandeja)).then(function(response) {
                $state.go('spapp.home.bandeja.asignadas');
            }).catch(function(error) {
                /*toastr.error('Ocurrió un error al registrar la OEFA.');*/
            }).finally(function() {});
        }

        function validaDocumento(prmFileList) {
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            /*$rootScope.archivoMedio=(prmFileList);*/
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                //          console.log(archivo);
                if (archivo.size / 1024 > 102400) {
                    toastr.warning('El tamaño del archivo no debe superar 100MB. Intente comprimir el tamaño del archivo y vuelva a intentarlo');
                    document.getElementById("fileArchivoMedio").value = "";
                    return;
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
            vm.archivoAdjunto = prmFileList;
        }

        function rechazarDenuncia() {
            vm.dataRechazarDenuncia.detalleBandeja.idBandejaDetalle = vm.dataDenunciaDetalle.idBandejaDetalle;
            if (vm.archivoAdjunto.length == 0) {
                toastr.warning('Debe seleccionar un archivo para continuar con el rechazo de la denuncia');
                return;
            }
            if (document.getElementById("fileArchivoMedio").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                for (var i = 0; i < vm.archivoAdjunto.length; i++) {
                    vm.archivoAdjunto.splice(i, 1);
                }
                return;
            }
            /*  vm.dataRechazarDenuncia.tipoEntidadCompetente.codigoRegistro = vm.usuario.tipoEntidadCompetente;
                if(vm.dataRechazarDenuncia.tipoEntidadCompetente.codigoRegistro == 1){

                }

                if(vm.dataRechazarDenuncia.tipoEntidadCompetente.codigoRegistro == 2){

                        vm.dataRechazarDenuncia.efa.idEfa = vm.usuario.idEfa;

                }*/
            vm.dataRechazarDenuncia.tipoAtencion.codigoRegistro = 2;
            vm.dataRechazarDenuncia.estado.codigoRegistro = 1;
            AtencionDenuncia.rechazarDenuncia(vm.archivoAdjunto, vm.dataRechazarDenuncia).then(function(response) {
                if (response.valido) {
                    vm.dataRechazarDenuncia.idAtencionDenuncia = response.data;
                    toastr.success("Se rechazó la denuncia");
                    cambiarEstadoDetalleDenuncia(vm.dataDenunciaDetalle);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al rechazar la denuncia');
            }).finally(function() {
                cancelar();
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
            obtenerParametros();
            if (angular.isDefined(localStorage.oSuSinWebDataSys)) {
                vm.usuario = angular.fromJson(localStorage.oSuSinWebDataSys);
                vm.dataBandeja = vm.usuario.bandeja;
                if (angular.isDefined($scope.ngDialogData)) {
                    var data = angular.copy($scope.ngDialogData.data);
                    vm.dataDenunciaDetalle = data;
                    vm.dataRechazarDenuncia.denuncia.idDenuncia = data.idDenuncia;
                }
            }
        }
        init();
        /*fin de metodos*/
    }
})();