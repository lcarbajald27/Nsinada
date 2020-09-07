(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('DialogVisualizarEvaluacionAccion', DialogVisualizarEvaluacionAccion);
    /* @ngInject */
    function DialogVisualizarEvaluacionAccion(API_CONFIG, EvaluacionInformeFactory, $state, $controller, $scope, toastr, ngDialog, AtencionDenuncia, BandejaFactory) {
        var vm = this;
        /*declaracion de variables globales*/
        /*Configuracion de paginacion de tablas*/
        $scope.config = {
            itemsPerPage: 5,
            fillLastPage: true,
            current: 1
        };
        /*fin de configuracion de tablas*/
        vm.rutaBase = API_CONFIG.url;
        vm.usuario = {};
        vm.dataEvaluacionInforme = AtencionDenuncia.model();
        vm.listaArchivos = [];
        /*vm.listaProducto = [];
        vm.prmProducto = ProductoFactory.modelProducto();*/
        /*variables de paginacion*/
        vm.opcionesPaginacion = {
            itemsPorPag: 10,
            cantidades: [10, 25, 50, 100]
        };
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
        /*  vm.limpiar = limpiar;
		vm.buscar = buscar;
		vm.seleccionar = seleccionar;
*/
        /*implementación de metodos*/
        function listarArchivos() {
            //				debugger;
            var data = {
                idAtencionDenuncia: vm.dataEvaluacionInforme.idEvaluacionInforme,
                tipoTabla: 3,
            }
            AtencionDenuncia.listarArchivoAtencion(data).then(function(response) {
                if (response.valido) {
                    vm.listaArchivos = angular.copy(response.data);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function buscarDataEvaluacion() {
            EvaluacionInformeFactory.buscarPorIdInformeAccion(vm.dataEvaluacionInforme).then(function(response) {
                if (response.valido) {
                    vm.dataEvaluacionInforme = angular.copy(response.data);
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                listarArchivos();
            });
        }
        /*
		
		function limpiar() {
			vm.prmProducto= ProductoFactory.modelProducto();
			vm.prmProducto.nombre = '';
		}*/
        function cancelar() {
            ngDialog.close();
        }

        function init() {
            //debugger;
            if (angular.isDefined($scope.ngDialogData)) {
                var data = angular.copy($scope.ngDialogData.prmData);
                vm.dataEvaluacionInforme = data;
                buscarDataEvaluacion();
            }
            /*if(angular.isDefined(localStorage.oSuSinWebDataSys)){


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