(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('EvaluacionRechazoDialogController', EvaluacionRechazoDialogController);
    /** @ngInject */
    function EvaluacionRechazoDialogController(toastr, $scope, ngDialog, AtencionDenuncia, EvaluacionRechazoFactory, MaestroFactory, CasoEfaFactory, API_CONFIG, BandejaFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        $scope.opcionesPaginacion = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        $scope.opcionPaginacionEntidad = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        $scope.serial = 1;
        $scope.serial1 = 1;
        /*fin de configuracion de tablas*/
        vm.rutaBase = API_CONFIG.url;
        /* Flag para Activacion de Modulos */
        vm.modelMaestro = MaestroFactory.model();
        vm.flagModuloBuscarEfa = 0;
        /* ******************************* */
        vm.dataDerivarDenunciaOefa = EvaluacionRechazoFactory.modelDerivacionDenuncia();
        vm.prmFiltroEfa = CasoEfaFactory.getEfa(); // variable filtro para la busqueda de efas
        vm.listaEfaFiltro = []; // variable para el resultado de la busqueda por filtros
        vm.dataArchivoEvaluacionRechazo = AtencionDenuncia.modelArchivoAtencion(); // variable del archivo de la evaluacion
        vm.dataObtenidaBandejaDer = {}; // Variable que Captura dato del Controller Seguimiento registro
        vm.listaDataRechazoDenuncia = []; // lista de los datos y/o motivo del rechazo
        vm.flagEvaluarRechazoDenuncia = 0; // Flag para ocultar los campos para la Evaluacion del Rechazo 
        vm.dataEvaluarRechazoDenuncia = EvaluacionRechazoFactory.model(); // variable que almacena los datos de la evaluacion del rechazo de la denuncia
        vm.archivoAdjunto = []; // lista de Archivos 
        /** Lista Combos EFA **/
        vm.listaTipoNivel = [];
        vm.listaTipoGobierno = [];
        /* fin lista combo EFA */
        /* lista Direcciones OEFA */
        vm.listaDireccion = [];
        vm.listaSubDireccion = [];
        /****************************/
        vm.informeSeguimiento = {
            motivo: 0,
            tipoArchivo: 0,
            listaTiposMotivo: 0,
            listaTiposArchivo: 0,
            listaTiposDestinatario: 0,
            otroMotivo: ''
        };
        vm.mimeTypes = [];
        vm.extension = "";
        vm.tamanioArchivo = "500";
        vm.caracteresMaximoArchivo = 50;
        vm.listaTiposArchivo = [];
        vm.listaTiposMotivo = [];
        vm.listaTiposDestinatario = [];
        vm.listaEfa = [];
        vm.listadoMotivo = [];
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.dataEvaluarDenuncia = dataEvaluarDenuncia;
        vm.registrarEvaluacionRechazoDenuncia = registrarEvaluacionRechazoDenuncia;
        vm.validaArchivos = validaArchivos;
        vm.validaDocumento = validaDocumento;
        vm.agregarArchivoEvaluacion = agregarArchivoEvaluacion;
        vm.eliminarArchivo = eliminarArchivo;
        vm.registrarAachivarDenuncia = registrarAachivarDenuncia;
        vm.buscarEfa = buscarEfa;
        vm.habilitarBusquedaEfa = habilitarBusquedaEfa;
        vm.agregarEfaComodestinatario = agregarEfaComodestinatario;
        vm.agregarOefaComodestinatario = agregarOefaComodestinatario;
        vm.listarSubDireccion = listarSubDireccion;
        vm.registrarDerivacionDenuncia = registrarDerivacionDenuncia;
        vm.terminarBusquedaEfa = terminarBusquedaEfa;
        vm.limpiarDatos = limpiarDatos;
        vm.eliminarEntidadAsignada = eliminarEntidadAsignada;
        vm.confirmarEliminarArchivo = confirmarEliminarArchivo;
        vm.confirmarEliminarEntidadAsignada = confirmarEliminarEntidadAsignada;
        vm.validarOefaRegistradaAlaDenuncia = validarOefaRegistradaAlaDenuncia;
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

        function confirmarEliminarEntidadAsignada(index, item) {
            var organo = 'EFA';
            var nombre = item.efa.nombre;
            if (item.tipoDestinatario.codigoRegistro == 1) {
                nombre = item.direccionSupervicion.descripcion + ' - ' + item.subDireccion.descripcion;
                organo = 'OEFA';
            }
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar la ' + organo + ': ' + nombre + '?'
                },
                width: '380px'
            }).then(function(okValue) {
                eliminarEntidadAsignada(index);
            });
        }
        $scope.obtenerValorIndex = function(a, b) {
            return (a + b) - 1;
        }
        $scope.indexCount = function(newPageNumber) {
            $scope.serial = newPageNumber * ($scope.opcionesPaginacion.itemsPerPage) - ($scope.opcionesPaginacion.itemsPerPage - 1);
        }
        $scope.indexCount2 = function(newPageNumber) {
            $scope.serial1 = newPageNumber * ($scope.opcionPaginacionEntidad.itemsPerPage) - ($scope.opcionPaginacionEntidad.itemsPerPage - 1);
        }

        function limpiarDatos() {
            vm.dataEvaluarRechazoDenuncia.idEvaluacionRechazo = 0; //id Evaluacion Rechazo
            //vm.dataEvaluarRechazoDenuncia.tipoEvaluacionRechazo =MaestroFactory.model(),// Reiterar - derivar - Archivar
            vm.dataEvaluarRechazoDenuncia.motivoDescripcion = ''; // Motivo campo de texto
            vm.dataEvaluarRechazoDenuncia.motivo = MaestroFactory.model();
            vm.dataEvaluarRechazoDenuncia.estado = MaestroFactory.model();
            vm.dataEvaluarRechazoDenuncia.flagActivo = '';
            vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion = [];
            vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia = []
        }

        function habilitarBusquedaEfa() {
            vm.flagModuloBuscarEfa = 1;
            vm.flagEvaluarRechazoDenuncia = 0;
            buscarEfa();
        }

        function terminarBusquedaEfa() {
            vm.flagModuloBuscarEfa = 0;
            vm.flagEvaluarRechazoDenuncia = 1;
        }

        function agregarEfaComodestinatario(data) {
            var dataEfaDerivar = EvaluacionRechazoFactory.modelDerivacionDenuncia();
            dataEfaDerivar.tipoDestinatario.codigoRegistro = 2;
            dataEfaDerivar.efa = data;
            vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia.push(dataEfaDerivar);
            buscarEfa();
        }

        function validarOefaRegistradaAlaDenuncia(objDataOEFA) {
            debugger;
            var data = BandejaFactory.bandejaDenuncia();
            data.idDenuncia = vm.dataObtenidaBandejaDer.idDenuncia;
            data.direccionSupervicion = vm.dataDerivarDenunciaOefa.direccionSupervicion.codigoRegistro;
            data.subDireccion = vm.dataDerivarDenunciaOefa.subDireccion.codigoRegistro;
            BandejaFactory.buscarDenunciaOefaPorIdDenunciaDireccionSupSubDireccion(angular.copy(data)).then(function(response) {
                if (response.valido) {
                    toastr.warning('La OEFA ya se encuentra registrada.');
                } else {
                    agregarOefaComodestinatario(objDataOEFA);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function agregarOefaComodestinatario(data) {
            var listaOefa = angular.copy(vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia);
            for (var x in listaOefa) {
                if (listaOefa[x].tipoDestinatario.codigoRegistro == 1 && (listaOefa[x].direccionSupervicion.codigoRegistro == vm.dataDerivarDenunciaOefa.direccionSupervicion.codigoRegistro && listaOefa[x].subDireccion.codigoRegistro == vm.dataDerivarDenunciaOefa.subDireccion.codigoRegistro)) {
                    toastr.warning('La OEFA ya se encuentra agregada.');
                    return;
                }
            }
            vm.dataDerivarDenunciaOefa.tipoDestinatario.codigoRegistro = 1;
            vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia.push(angular.copy(vm.dataDerivarDenunciaOefa));
            /*var dataEfaDerivar = EvaluacionRechazoFactory.modelDerivacionDenuncia();
                dataEfaDerivar.tipoDestinatario.codigoRegistro = 1;
                dataEfaDerivar.efa= data;

                    vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia.push(dataEfaDerivar);
                    buscarEfa();
*/
            vm.dataDerivarDenunciaOefa = EvaluacionRechazoFactory.modelDerivacionDenuncia();
        }

        function buscarEfa() {
            vm.prmFiltroEfa.denuncia.idDenuncia = vm.dataObtenidaBandejaDer.idDenuncia;
            EvaluacionRechazoFactory.listarEfaDerivar(vm.prmFiltroEfa).then(function(response) {
                vm.listaEfaFiltro = [];
                if (response.valido) {
                    for (var x in response.data) {
                        var a = 0;
                        for (var i in vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia) {
                            if (vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia[i].tipoDestinatario.codigoRegistro == 2) {
                                if (vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia[i].efa.idEfa == response.data[x].idEfa) {
                                    a = a + 1;
                                }
                            }
                        }
                        if (a == 0) {
                            vm.listaEfaFiltro.push(response.data[x]);
                            /*var dataDerivacionEfa = EvaluacionRechazoFactory.modelDerivacionDenuncia();
                            dataDerivacionEfa.tipoDestinatario.codigoRegistro==2;
                                dataDerivacionEfa.efa.idEfa =   response.data[x].idEfa;
                            vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia.push(dataDerivacionEfa);*/
                        }
                        /*
                                                vm.entidad = response.data[x].Value;
                                                vm.listaEfas.push(angular.copy(vm.entidad)); */
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar.');
            }).finally(function() {});
        }

        function cargarCombos() {
            vm.listaDireccion = [{
                codigoRegistro: 0,
                descripcion: '--Seleccione--'
            }];
            var codigosCombos = ['TipoArchivoAdjuntoEspecialistaSinada', 'MotivoArchivarEvaluacionRechazoDenuncia', 'TipoNivelSinada', 'TipoGobiernoSinada', 'DireccionSupervision'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'TipoArchivoAdjuntoEspecialistaSinada':
                                vm.listaTiposArchivo = datosCombo;
                                break;
                            case 'MotivoArchivarEvaluacionRechazoDenuncia':
                                vm.listadoMotivo = datosCombo;
                                break;
                            case 'TipoNivelSinada':
                                vm.listaTipoNivel = datosCombo;
                                break;
                            case 'TipoGobiernoSinada':
                                vm.listaTipoGobierno = datosCombo;
                                break;
                            case 'DireccionSupervision':
                                for (var i in datosCombo) {
                                    vm.listaDireccion.push(datosCombo[i]);
                                }
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

        function listarSubDireccion() {
            vm.dataDerivarDenunciaOefa.subDireccion.codigoRegistro = 0;
            vm.listaSubDireccion = [{
                codigoRegistro: 0,
                descripcion: '--Seleccione--'
            }];
            vm.modelMaestro.codigoMaestro = 'DireccionEvaluacion';
            vm.modelMaestro.codigoRegistro = vm.dataDerivarDenunciaOefa.direccionSupervicion.codigoRegistro;
            //  console.log("vm.modelMaestro "+vm.modelMaestro);
            MaestroFactory.listarHijos(vm.modelMaestro).then(function(response) {
                //  console.log('response cargarCombos',response);
                if (response != null && response.valido) {
                    /*vm.listaSubDireccion = angular.copy(response.data);*/
                    for (var i in response.data) {
                        vm.listaSubDireccion.push(response.data[i]);
                    }
                    //                  for(var x in response.data)
                    //                  {
                    //                      var tipoCombo = response.data[x].Key;
                    //                      var datosCombo = response.data[x].Value;
                    //                      switch(tipoCombo)
                    //                      {
                    //                          case 'DireccionSupervision' : vm.listaDireccion = datosCombo; break;
                    //                          default : break;
                    //                      }
                    //                  }
                }
            }).catch(function(error) {
                toastr.error('No se pudo obtener los datos para los combos');
            });
        }

        function dataEvaluarDenuncia(data) {
            vm.flagEvaluarRechazoDenuncia = 1;
            vm.dataEvaluarRechazoDenuncia.atencionDenuncia.idAtencionDenuncia = data.idAtencionDenuncia;
        }

        function eliminarEntidadAsignada(index) {
            vm.dataEvaluarRechazoDenuncia.lstDerivacionDenuncia.splice(index, 1);
        }

        function eliminarArchivo(index) {
            vm.archivoAdjunto.splice(vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion[index].posicionArchivo, 1);
            vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.splice(index, 1);
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
            var objData = obtenerDatoMaestro(vm.listaTiposArchivo, vm.dataArchivoEvaluacionRechazo.tipoArchivo.codigoRegistro);
            vm.dataArchivoEvaluacionRechazo.tipoArchivo = angular.copy(objData);
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length)) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataArchivoEvaluacionRechazo.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoEvaluacionRechazo.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoEvaluacionRechazo.posicionArchivo].name);
            vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.push(angular.copy(vm.dataArchivoEvaluacionRechazo));
            document.getElementById("fileArchivoMedio").value = "";
            vm.dataArchivoEvaluacionRechazo.descripcionArchivo = '';
            vm.dataArchivoEvaluacionRechazo.tipoArchivo.codigoRegistro = 0;
        }

        function validaArchivos() {
            if (vm.archivoAdjunto.length == (vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length + 1)) {
                document.getElementById("fileArchivoMedio").value = "";
                vm.archivoAdjunto.splice(vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length - 1, 1);
            }
        }

        function validaDocumento(prmFileList) {
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                for (var x in vm.archivoAdjunto) {
                    if (vm.archivoAdjunto[x].name == archivo.name) {
                        toastr.warning('Debe ingresar un archivo con un nombre diferente');
                        document.getElementById("fileArchivoMedio").value = "";
                        return;
                    }
                }
                if (archivo.size / 1024 > 102400) {
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
            vm.archivoAdjunto.push(prmFileList[0]);
        }

        function listarDatosDenunciaRechazada() {
            var data = AtencionDenuncia.model();
            data.denuncia.idDenuncia = vm.dataObtenidaBandejaDer.idDenuncia;
            data.detalleBandeja.idBandejaDetalle = vm.dataObtenidaBandejaDer.idBandejaDetalle;
            AtencionDenuncia.listarDatosDenunciaRechazada(data).then(function(response) {
                if (response.valido) {
                    vm.listaDataRechazoDenuncia = angular.copy(response.data);
                    //          console.log("vm.listaDataRechazoDenuncia" + angular.toJson(vm.listaDataRechazoDenuncia));
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function registrarEvaluacionRechazoDenuncia() {
            if (vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataEvaluarRechazoDenuncia.tipoEvaluacionRechazo.codigoRegistro = 1;
            EvaluacionRechazoFactory.registrar(vm.archivoAdjunto, vm.dataEvaluarRechazoDenuncia).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluarRechazoDenuncia.idAtencionDenuncia = response.data;
                    toastr.success('Se reiteró con éxito la denuncia');
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar el OEFA');
            }).finally(function() {
                cancelar();
            });
        }

        function registrarAachivarDenuncia() {
            if (vm.archivoAdjunto.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataEvaluarRechazoDenuncia.tipoEvaluacionRechazo.codigoRegistro = 3;
            EvaluacionRechazoFactory.registrar(vm.archivoAdjunto, vm.dataEvaluarRechazoDenuncia).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluarRechazoDenuncia.idAtencionDenuncia = response.data;
                    toastr.success("Se archivo con éxito la denuncia.");
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar el OEFA');
            }).finally(function() {
                cancelar();
            });
        }

        function eliminarArchivo(index) {
            vm.archivoAdjunto.splice(vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion[index].posicionArchivo, 1);
            vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.splice(index, 1);
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
            var objData = obtenerDatoMaestro(vm.listaTiposArchivo, vm.dataArchivoEvaluacionRechazo.tipoArchivo.codigoRegistro);
            vm.dataArchivoEvaluacionRechazo.tipoArchivo = angular.copy(objData);
            if (vm.archivoAdjunto.length == 0 || (vm.archivoAdjunto.length == vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length)) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            if (document.getElementById("fileArchivoMedio").value == "") {
                toastr.warning('Debe seleccionar un archivo');
                vm.archivoAdjunto.splice(vm.archivoAdjunto.length - 1, 1);
                return;
            }
            vm.dataArchivoEvaluacionRechazo.posicionArchivo = vm.archivoAdjunto.length - 1;
            vm.dataArchivoEvaluacionRechazo.nombreArchivo = angular.copy(vm.archivoAdjunto[vm.dataArchivoEvaluacionRechazo.posicionArchivo].name);
            vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.push(angular.copy(vm.dataArchivoEvaluacionRechazo));
            document.getElementById("fileArchivoMedio").value = "";
            vm.dataArchivoEvaluacionRechazo.descripcionArchivo = '';
            vm.dataArchivoEvaluacionRechazo.tipoArchivo.codigoRegistro = 0;
        }

        function validaArchivos() {
            if (vm.archivoAdjunto.length == (vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length + 1)) {
                document.getElementById("fileArchivoMedio").value = "";
                vm.archivoAdjunto.splice(vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length - 1, 1);
            }
        }

        function validaDocumento(prmFileList) {
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                for (var x in vm.archivoAdjunto) {
                    if (vm.archivoAdjunto[x].name == archivo.name) {
                        toastr.warning('Debe ingresar un archivo con un nombre diferente');
                        document.getElementById("fileArchivoMedio").value = "";
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
            vm.archivoAdjunto.push(prmFileList[0]);
        }

        function listarDatosDenunciaRechazada() {
            var data = AtencionDenuncia.model();
            data.denuncia.idDenuncia = vm.dataObtenidaBandejaDer.idDenuncia;
            data.detalleBandeja.idBandejaDetalle = vm.dataObtenidaBandejaDer.idBandejaDetalle;
            AtencionDenuncia.listarDatosDenunciaRechazada(data).then(function(response) {
                if (response.valido) {
                    vm.listaDataRechazoDenuncia = angular.copy(response.data);
                    console.log("vm.listaDataRechazoDenuncia" + angular.toJson(vm.listaDataRechazoDenuncia));
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function registrarEvaluacionRechazoDenuncia() {
            if (vm.dataEvaluarRechazoDenuncia.lstArchivoAtencion.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.dataEvaluarRechazoDenuncia.tipoEvaluacionRechazo.codigoRegistro = 1;
            EvaluacionRechazoFactory.registrar(vm.archivoAdjunto, vm.dataEvaluarRechazoDenuncia).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluarRechazoDenuncia.idAtencionDenuncia = response.data;
                    toastr.success('Se reiteró con éxito la denuncia');
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar el OEFA');
            }).finally(function() {
                cancelar();
            });
        }

        function registrarAachivarDenuncia() {
            if (vm.archivoAdjunto.length == 0) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            /*if(document.getElementById("fileArchivoMedio").value==""){
                    toastr.warning('Debe seleccionar un archivo');
                    vm.archivoAdjunto.splice(vm.archivoAdjunto.length-1, 1);
                    return;
                }*/
            vm.dataEvaluarRechazoDenuncia.tipoEvaluacionRechazo.codigoRegistro = 3;
            EvaluacionRechazoFactory.registrar(vm.archivoAdjunto, vm.dataEvaluarRechazoDenuncia).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluarRechazoDenuncia.idAtencionDenuncia = response.data;
                    toastr.success("Se archivo con éxito la denuncia");
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar el OEFA');
            }).finally(function() {
                cancelar();
            });
        }

        function registrarDerivacionDenuncia() {
            vm.dataEvaluarRechazoDenuncia.tipoEvaluacionRechazo.codigoRegistro = 2;
            EvaluacionRechazoFactory.derivarDenuncia(vm.dataEvaluarRechazoDenuncia).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluarRechazoDenuncia.idAtencionDenuncia = response.data;
                    toastr.success("Se derivó con éxito la denuncia");
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar el OEFA');
            }).finally(function() {
                cancelar();
            });
        }

        function cancelar() {
            ngDialog.close();
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
                vm.dataObtenidaBandejaDer = angular.copy($scope.ngDialogData.prmBandeja);
                listarDatosDenunciaRechazada();
            }
            cargarCombos();
            buscarEfa();
            listarSubDireccion();
            obtenerParametros();
            /*vm.listaTiposMotivo.push({codigoRegistro:1,descripcion:'No Recepcionó Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:2,descripcion:'No Validó Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:3,descripcion:'No Atendió Denuncia'});
            vm.listaTiposMotivo.push({codigoRegistro:4,descripcion:'No Levantó Observaciones'});
            vm.listaTiposMotivo.push({codigoRegistro:5,descripcion:'Otros'});*/
            vm.listaTiposDestinatario.push({
                codigoRegistro: 1,
                descripcion: 'EFA'
            });
            vm.listaTiposDestinatario.push({
                codigoRegistro: 2,
                descripcion: 'OEFA'
            });
            /*vm.listaEfa.push({codigoRegistro:1,descripcion:'Ámbito Nacional'});
            vm.listaEfa.push({codigoRegistro:2,descripcion:'Ámbito Regional'});
            vm.listaEfa.push({codigoRegistro:3,descripcion:'Ámbito Local'});*/
        }
        init();
        /*fin de controller*/
    }
})();