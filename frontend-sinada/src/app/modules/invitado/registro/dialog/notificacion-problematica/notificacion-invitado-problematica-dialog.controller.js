(function() {
    'use strict';
    angular.module('spapp.invitado').controller('NotificacionInvitadoProblematicaDialogController', NotificacionInvitadoProblematicaDialogController);
    /** @ngInject */
    function NotificacionInvitadoProblematicaDialogController(toastr, $scope, DenunciaFactory, PersonaFactory, MaestroFactory, $state, ngDialog) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.agregarManualPersona = 0;
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "10";
        vm.caracteresMaximoArchivo = 50;
        vm.denuncia = DenunciaFactory.model();
        vm.dataPersona = PersonaFactory.model();
        vm.archivosProblematica;
        vm.dataDescripcionCaso = DenunciaFactory.descripcionCasoModel();
        /*declaración de metodos enlazados a la vista*/
        vm.validaEnvioCorreo = '0';
        vm.enviarCorreo = enviarCorreo;
        vm.validaArchivos = validaArchivos;
        vm.buscarPersona = buscarPersona;
        vm.validaCorreo = validaCorreo;
        vm.limpiarData = limpiarData;
        /*implementación de metodos*/
        function validaCorreo() {
            var miCadena = vm.dataDescripcionCaso.correo;
            var posicion = miCadena.indexOf("@");
            var cuenta = 0;
            while (posicion != -1) {
                cuenta++;
                posicion = miCadena.indexOf("@", posicion + 1);
            }
            return cuenta;
        }

        function validaArchivos(prmFileList) {
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            var lstExtens = vm.extension.split(",");
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                if (archivo.size / 1024 > (parseInt(vm.tamanioArchivo) * 1000)) {
                    toastr.warning('El tamaño del archivo no debe superar ' + angular.copy(vm.tamanioArchivo) + 'MB. Comprima el archivo y vuelva a intentarlo.');
                    document.getElementById("fileArchivoMedio").value = "";
                    /*vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
                    vm.validaEnvioCorreo = '0';
                    return;
                }
                var str = archivo.name;
                var extensionArchivo = str.substring(str.lastIndexOf("."), str.length);
                if (!vm.mimeTypes.includes(archivo.type) && !lstExtens.includes(extensionArchivo)) {
                    toastr.warning('No es el tipo de archivo seleccionado.');
                    document.getElementById("fileArchivoMedio").value = "";
                    vm.validaEnvioCorreo = '0';
                    return;
                }
                if (archivo.name.length > parseInt(vm.caracteresMaximoArchivo)) {
                    toastr.warning('El nombre del archivo no debe superar los ' + vm.caracteresMaximoArchivo + ' caracteres. Renombre el archivo y vuelva a cargarlo.');
                    document.getElementById("fileArchivoMedio").value = "";
                    vm.validaEnvioCorreo = '0';
                    return;
                }
            }
            vm.validaEnvioCorreo = '1';
            vm.archivosProblematica = (prmFileList);
        }

        function buscarPersona() {
            if (vm.dataPersona.documento.length == 8 && vm.agregarManualPersona == 0) {
                buscarPersonaXNumeroDocumento();
            }
        }

        function limpiarData() {
            vm.agregarManualPersona = 0;
            vm.dataPersona = PersonaFactory.model();
        }

        function buscarPersonaXNumeroDocumento() {
            PersonaFactory.buscarXNumeroDocumento(angular.copy(vm.dataPersona)).then(function(response) {
                if (response.valido) {
                    if (response.data.idPersona > 0) {
                        vm.dataPersona = response.data;
                        var nombres = "";
                        debugger;
                        if (vm.dataPersona.primerNombre != null && vm.dataPersona.primerNombre != ' ' && vm.dataPersona.primerNombre.length != 0) {
                            nombres = vm.dataPersona.primerNombre;
                        }
                        if (vm.dataPersona.segundoNombre != null && vm.dataPersona.segundoNombre != ' ' && vm.dataPersona.segundoNombre.length != 0) {
                            nombres = nombres + " " + vm.dataPersona.segundoNombre;
                        }
                        vm.dataPersona.nombreCompleto = nombres + ' ' + vm.dataPersona.apellidoPaterno + ' ' + vm.dataPersona.apellidoMaterno;
                    }
                } else {
                    toastr.warning('No se encontró a la persona.');
                    vm.agregarManualPersona = 1;
                    var doc = angular.copy(vm.dataPersona.documento);
                    vm.dataPersona = PersonaFactory.model();
                    vm.dataPersona.documento = angular.copy(doc);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function validarEmail(valor) {
            // debugger;
            var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;
            var res = 0;
            if (emailRegex.test(valor)) {
                res = 1;
            }
            return res;
        }

        function enviarCorreo() {
            // debugger;
            if (vm.dataDescripcionCaso.telefono.length < 7 || vm.dataDescripcionCaso.telefonolength > 15) {
                toastr.info('Debe ingresar un teléfono valido, de entre 7 a 15 dígitos.');
                return;
            }
            if (validarEmail(vm.dataDescripcionCaso.correo) == 0) {
                toastr.info('Debe ingresar un correo valido');
                return;
            }
            if (!angular.isDefined(vm.archivosProblematica)) {
                toastr.warning('Debe de subir un archivo');
                return;
            }
            if (document.getElementById("fileArchivoMedio").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataDescripcionCaso.nombreCompleto = vm.dataPersona.nombreCompleto;
            vm.dataDescripcionCaso.documento = vm.dataPersona.documento;
            vm.dataDescripcionCaso.mensajeHtml = document.getElementById("mensajeHtml").innerHTML;
            //          console.log("mensajeHtml",vm.dataDescripcionCaso.mensajeHtml);
            DenunciaFactory.registrarProblematicaNoEncontrada( /*vm.archivoTipoMedio*/ vm.archivosProblematica, vm.dataDescripcionCaso).then(function(response) {
                if (response.valido) {
                    //vm.dataPedido.idPedido = response.data;
                    vm.dataDescripcionCaso.idDescripcionCaso = response.data;
                    localStorage.removeItem("objDenuncia");
                    mensajeExitoRegistroProblematica();
                    /*siguiente();*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar problematica.');
            });
        }

        function obtenerParametros() {
            MaestroFactory.obtenerParametros().then(function(response) {
                // vm.tamanioArchivo = angular.copy(response.data[0].tamanioArchivo);
                vm.tamanioArchivo = angular.copy("10");
                vm.caracteresMaximoArchivo = angular.copy(response.data[0].maximoCaracteresArchivo);
                vm.extension = angular.copy(response.data[0].extensionArchivoDocumento);
                var tipos = angular.copy(response.data[0].tipoArchivoDocumento);
                vm.mimeTypes = angular.copy(tipos.split(","));
            }).catch(function(error) {
                toastr.error('No se pudo obtener los parametros.');
            });
        }

        function mensajeExitoRegistroProblematica() {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-mensaje/dialog-mensaje.html',
                controller: 'DialogMensajeController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Problemática enviada',
                    Mensaje: 'Se ha enviado con éxito la descripción de su problemática ambiental para su evaluación, por favor manténgase a la espera.'
                },
                width: '380px'
            }).finally(function() {
                //  toastr.success('Su problematica se ha enviado, por favor mantengase a la espera. ' + vm.denuncia.codigoDenuncia);
                $scope.closeThisDialog({
                    enviado: 1
                });
                $state.go('invitado.encuesta');
            });
        }
        /*fin de metodos*/
        function init() {
            obtenerParametros();
            if (angular.isDefined(localStorage.objDenuncia)) {
                vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                //  console.log("vm.denuncia"+vm.denuncia);
            }
            // body...
        }
        init();
        /*fin de controller*/
    }
})();