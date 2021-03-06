(function() {
    'use strict';
    angular.module('spapp.denuncias').controller('dialogVisualizarNoAtencion', dialogVisualizarNoAtencion);
    /* @ngInject */
    function dialogVisualizarNoAtencion(API_CONFIG, $state, $controller, $scope, toastr, ngDialog, AtencionDenuncia, BandejaFactory) {
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
        vm.listaArchivos = [];
        vm.usuario = {};
        vm.dataDenunciaNoAtencion = AtencionDenuncia.model();
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
        /*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;
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
        function listarArchivos() {
            var data = {
                idAtencionDenuncia: vm.dataDenunciaNoAtencion.idAtencionDenuncia,
                tipoTabla: 1,
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

        function cancelar() {
            ngDialog.close();
        }

        function init() {
            if (angular.isDefined($scope.ngDialogData)) {
                var data = angular.copy($scope.ngDialogData.prmData);
                vm.dataDenunciaNoAtencion = data;
                listarArchivos();
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