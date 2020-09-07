(function() {
    'use strict';
    angular.module('spapp').controller('InicioController', InicioController);

    function InicioController(AccesoFactory, toastr, ngDialog, $rootScope, $log, $stateParams, $state, $scope, $location, PersonaFactory, EntidadFactory, UsuarioFactory) {
        var vm = this;
        vm.dataPersona = PersonaFactory.model();
        vm.dataUsuario = UsuarioFactory.modelUsuario();
        /**Slider numero 2**/
        vm.myInterval = 5000;
        vm.noWrapSlides = false;
        vm.noWrapSlides = false;
        vm.activeSliderImagenes2 = 0;
        vm.slider = [
            // {
            //   image: 'assets/images/fondo/ballena.jpg',
            //   title:'Nuevo Cambio',
            //   subtitulo: 'Sibtitulo Slider 1',
            //   id: 0
            // },
            {
                image: 'assets/images/fondo/albatros.jpg',
                title: 'SIN FRONTERAS',
                subtitulo: '',
                id: 0
            }, {
                image: 'assets/images/fondo/pelicanos.jpg',
                title: 'CUIDEMOS NUESTRO AMBIENTE',
                subtitulo: '',
                id: 1
            }
        ]
        /*declaracion de variables globales*/
        /*declaración de métodos enlazados a la vista*/
        /*implementación de métodos*/
        function leerOpciones(ref) {
            AccesoFactory.opciones(ref).then(function(response) {
                $log.log(response);
                //localStorage.setItem("acceso",angular.toJson(response));
                $rootScope.accesos = response;
                $rootScope.validarOpcion = validarOpcion;
            }).catch(function(error) {
                //toastr.error('xx');
            });
        }

        function validarOpcion(opcion) {
            for (var int = 0; int < $rootScope.accesos.length; int++) {
                var _opcion = $rootScope.accesos[int];
                //$log.log('_opcion.elemento '+_opcion.elemento);
                //$log.log('opcion'+opcion);
                if (_opcion.elemento == opcion) {
                    return true;
                }
            }
            return false;
        }
        /*
        $scope.$watch('$viewContentLoaded', function(){
        	$log.log('$scope.$watch');
        });
        */
        /*
        	    $rootScope.ref_tmp=$location.search().ref;
        	  
        	    if (angular.isDefined($rootScope.ref_tmp)) {
        	    	leerOpciones($rootScope.ref_tmp);
        	    	$rootScope.ref=$rootScope.ref_tmp;
        	    }else{
        	    	leerOpciones($rootScope.ref);
        	    }*/
        function usuarioActualizacionDatos() {
            ngDialog.openConfirm({
                template: 'app/modules/inicio/modal-inicio/usuario-actualizacion-datos.html',
                controller: 'UsuariosActualizacionDatosController',
                controllerAs: 'vm',
                data: {
                    dataUsuario: vm.dataUsuario
                },
                width: '80%'
            }).then(function(okValue) {
                //eliminarCaso(item);
            });
        }

        function buscarDatosDePersona() {
            if (vm.dataUsuario.bandeja.tipoResponsable == 1) {
                vm.dataPersona.idPersona = angular.copy(vm.dataUsuario.bandeja.idResponsable);
                vm.dataPersona.idPersona = angular.copy(vm.dataUsuario.bandeja.idResponsable);
                PersonaFactory.buscarXId(angular.copy(vm.dataPersona)).then(function(response) {
                    if (response.valido) {
                        if (response.data.idPersona > 0) {
                            vm.dataPersona = angular.copy(response.data);
                            //console.log("dataPersona DATOS DESDE LA BASE",vm.dataPersona);
                        }
                    } else {
                        toastr.error(response.mensaje);
                    }
                }).catch(function(error) {
                    toastr.error('Error al consultar');
                }).finally(function() {
                    vm.dataUsuario.persona = angular.copy(vm.dataPersona);
                    if (vm.dataPersona.distrito == null || vm.dataPersona.distrito == 0) {
                        usuarioActualizacionDatos();
                    }
                });
            }
        }

        function init() {
            //console.log("HOLA MUNDO ABRE DIALOG");	
            //if(angular.isDefined(localStorage.oSuSinWebDataSys)){
            /*************  Obtenemos los datos de usuario  **************/
            /*vm.dataUsuario = angular.fromJson(localStorage.oSuSinWebDataSys);
				console.log("vm.dataUsuario",vm.dataUsuario);
				buscarDatosDePersona();
	    	}
	    	*/
            //usuarioActualizacionDatos();
        }
        init();
    }
})();