(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('DialogEncuestaDenuncianteController', DialogEncuestaDenuncianteController);
    /* @ngInject */
    function DialogEncuestaDenuncianteController(API_CONFIG, $state, $controller, $scope, toastr, ngDialog, AtencionDenuncia, BandejaFactory, MaestroFactory, EncuestaFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.dataEncuestaRevision = EncuestaFactory.model();
        vm.usuario = {};
        vm.dataDenunciaDenunciate = {};
        vm.rutaBase = API_CONFIG.url;
        /**************** Archivo  ******************/
        vm.archivoAdjunto = [];
        /*vm.listaDataArchivoAdjunto = [];
        vm.dataArchivoAdjunto = AtencionDenuncia.modelArchivoAtencion();*/
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
        vm.listapregunta1 = [];
        vm.listapregunta2 = [];
        vm.listapregunta3 = [];
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        /*  vm.limpiar = limpiar;
		vm.buscar = buscar;
		vm.seleccionar = seleccionar;
*/
        /*implementación de metodos*/
        vm.terminarEncuesta = terminarEncuesta;
        /*
		
		function limpiar() {
			vm.prmProducto= ProductoFactory.modelProducto();
			vm.prmProducto.nombre = '';
		}*/
        function terminarEncuesta() {
            vm.dataEncuestaRevision.tipoEncuesta.codigoRegistro = 3;
            vm.dataEncuestaRevision.bandejaDetalle.idBandejaDetalle = vm.dataDenunciaDenunciate.idBandejaDetalle;
            EncuestaFactory.registrar(angular.copy(vm.dataEncuestaRevision)).then(function(response) {
                if (response.valido) {
                    vm.dataEncuestaRevision.idEncuesta = response.data;
                    toastr.success('Gracias por su calificación');
                    ngDialog.close();
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function cargarCombos() {
            var codigosCombos = ['EncuestaAtencionPregunta1', 'EncuestaAtencionPregunta2', 'EncuestaAtencionPregunta3'];
            MaestroFactory.buscarMaestros(angular.copy(codigosCombos)).then(function(response) {
                if (response != null && response.valido) {
                    for (var x in response.data) {
                        var tipoCombo = response.data[x].Key;
                        var datosCombo = response.data[x].Value;
                        switch (tipoCombo) {
                            case 'EncuestaAtencionPregunta1':
                                vm.listapregunta1 = datosCombo;
                                break;
                            case 'EncuestaAtencionPregunta2':
                                vm.listapregunta2 = datosCombo;
                                break;
                            case 'EncuestaAtencionPregunta3':
                                vm.listapregunta3 = datosCombo;
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

        function init() {
            cargarCombos();
            if (angular.isDefined($scope.ngDialogData)) {
                var data = angular.copy($scope.ngDialogData.prmData);
                vm.dataDenunciaDenunciate = data;
            }
            /*
            			if(angular.isDefined(localStorage.oSuSinWebDataSys)){


            					vm.usuario =  angular.fromJson(localStorage.oSuSinWebDataSys);
                    			vm.dataBandeja  = vm.usuario.bandeja;

                    			
            				
            					if (angular.isDefined($scope.ngDialogData)) 
            					{	
            						var data = angular.copy($scope.ngDialogData.data);
            						vm.dataDenunciaDetalle = data;	
            						vm.dataRechazarDenuncia.denuncia.idDenuncia = data.idDenuncia;

            						
            						
            						
            					}

                    	}

            		*/
        }
        init();
        /*fin de metodos*/
    }
})();