(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('AsingnarEspecialistaDialogController', AsingnarEspecialistaDialogController);
    /* @ngInject */
    function AsingnarEspecialistaDialogController($state, $controller, $scope, toastr, ngDialog, AtencionDenuncia, BandejaFactory, UsuarioFactory, API_CONFIG) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        vm.dataIdEspecialistaSinada = API_CONFIG.idPerfilEspecialista;
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.flagAsignarActivo = 0;
        vm.dataBandejaDetalleUsuario = BandejaFactory.detalleBandeja();
        vm.dataDenunciaCoordinador = {};
        vm.prmUsuarioFiltro = UsuarioFactory.modelUsuario();
        vm.listaUsuariosEspecialistas = [];
        vm.listaUsuarioEspecialistaAsignado = [];
        vm.dataRechazarDenuncia = AtencionDenuncia.model();
        vm.archivoAdjunto = [];
        vm.dataBandeja = {};
        /*vm.listaProducto = [];
        vm.prmProducto = ProductoFactory.modelProducto();*/
        /*variables de paginacion*/
        vm.opcionesPaginacion = {
            itemsPorPag: 10,
            cantidades: [10, 25, 50, 100]
        };
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        vm.asignarEspecialista = asignarEspecialista;
        vm.confirmarEliminarEspecialistaDenuncia = confirmarEliminarEspecialistaDenuncia;
        vm.listarUsuariosEspecialistas = listarUsuariosEspecialistas;
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
        function validaDocumento(prmFileList) {
            if (!prmFileList) {
                toastr.warning('Debe seleccionar un archivo');
                return;
            }
            vm.archivoAdjunto = prmFileList;
            //console.log("vm.archivoAdjunto" + vm.archivoAdjunto);
            /*$rootScope.archivoMedio=(prmFileList);*/
            for (var i = 0; i < prmFileList.length; i++) {
                var archivo = prmFileList[i];
                //      console.log(archivo);
                if (archivo.size / 1024 > 102400) {
                    toastr.warning('El tamaño del archivo no debe superar 100MB. Intente comprimir el tamaño del archivo y vuelva a intentarlo.');
                    document.getElementById("fileArchivoMedio").value = "";
                    return;
                }
                if (archivo.name.length > 20) {
                    toastr.warning('El nombre del archivo no debe mayor a 20 caracteres');
                    document.getElementById("fileArchivoMedio").value = "";
                    return;
                }
            }
        }

        function confirmarEliminarEspecialistaDenuncia(item) {
            ngDialog.openConfirm({
                template: 'app/base/template/dialog-confirm/dialog-confirm.html',
                controller: 'DialogConfirmController',
                controllerAs: 'vm',
                data: {
                    Titulo: 'Confirmar eliminación',
                    Mensaje: '¿Está seguro de eliminar al especialista' + item.nombrePersona + ' de la denuncia?'
                },
                width: '380px'
            }).then(function(okValue) {
                EliminarEspecialistaDenuncia(item);
            });
        }

        function EliminarEspecialistaDenuncia(data) {
            vm.dataBandejaDetalleUsuario.bandeja.idResponsable = data.idUsuario;
            vm.dataBandejaDetalleUsuario.idDenuncia = vm.dataDenunciaCoordinador.idDenuncia;
            BandejaFactory.eliminarEspecialistaAsignado(angular.copy(vm.dataBandejaDetalleUsuario)).then(function(response) {
                if (response.valido) {
                    toastr.success('Se eliminó al especialista de la denuncia');
                    listarEspecialistaAsignado();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar el oefa.');
            }).finally(function() {});
        }

        function asignarEspecialista(data) {
            vm.dataBandejaDetalleUsuario.bandeja.idResponsable = data.idUsuario;
            vm.dataBandejaDetalleUsuario.idDenuncia = vm.dataDenunciaCoordinador.idDenuncia;
            BandejaFactory.asignarBandejaBandeja(angular.copy(vm.dataBandejaDetalleUsuario)).then(function(response) {
                if (response.valido) {
                    toastr.success('Se asignó correctamente el usuario a la denuncia');
                    ngDialog.close();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al registrar el oefa.');
            }).finally(function() {});
        }

        function listarEspecialistaAsignado() {
            vm.prmUsuarioFiltro.flagFiltroUsuario = 2;
            vm.prmUsuarioFiltro.idDenuncia = vm.dataDenunciaCoordinador.idDenuncia;
            vm.prmUsuarioFiltro.idPerfil = vm.dataIdEspecialistaSinada;
            UsuarioFactory.listar(angular.copy(vm.prmUsuarioFiltro)).then(function(response) {
                if (response.valido) {
                    vm.listaUsuarioEspecialistaAsignado = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al obtener la información.');
            }).finally(function() {
                if (vm.listaUsuarioEspecialistaAsignado.length == 0) {
                    vm.flagAsignarActivo = 1;
                    listarUsuariosEspecialistas();
                }
            });
        }

        function listarUsuariosEspecialistas() {
            vm.prmUsuarioFiltro.flagFiltroUsuario = 1;
            vm.prmUsuarioFiltro.idDenuncia = vm.dataDenunciaCoordinador.idDenuncia;
            vm.prmUsuarioFiltro.idPerfil = vm.dataIdEspecialistaSinada;
            UsuarioFactory.listar(angular.copy(vm.prmUsuarioFiltro)).then(function(response) {
                if (response.valido) {
                    vm.listaUsuariosEspecialistas = response.data;
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Ocurrió un error al obtener la información.');
            }).finally(function() {});
        }

        function cancelar() {
            ngDialog.close();
        }

        function init() {
            if (angular.isDefined($scope.ngDialogData)) {
                var data = angular.copy($scope.ngDialogData.data);
                vm.dataDenunciaCoordinador = data;
                listarEspecialistaAsignado();
            } else {
                ngDialog.close();
            }
            /*if(angular.isDefined(localStorage.objDataBandeja)){

                    vm.dataBandeja  = angular.fromJson(localStorage.objDataBandeja);

                    
                
                    if (angular.isDefined($scope.ngDialogData)) 
                    {   
                        var data = angular.copy($scope.ngDialogData.data);

                        vm.dataRechazarDenuncia.denuncia.idDenuncia = data.idDenuncia;

                        
                        
                        
                    }

            }*/
        }
        init();
        /*fin de metodos*/
    }
})();