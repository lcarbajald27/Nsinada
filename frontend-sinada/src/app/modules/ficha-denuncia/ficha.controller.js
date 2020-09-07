(function() {
    'use strict';
    angular.module('spapp.ficha').controller('FichaController', FichaController);
    /** @ngInject */
    function FichaController(API_CONFIG, $state, $sce, $scope, toastr, ngDialog, BandejaFactory, $rootScope, DenunciaFactory, CasoFactory, AtencionDenuncia) {
        var vm = this;
        /*declaracion de variables globales*/
        vm.flagActivoModulo = 0;
        vm.rutaBase = API_CONFIG.url;
        vm.diasOefa = 30;
        vm.diasHabilesEfa = 10;
        vm.usuario = {};
        vm.itemTabActivo = 1; //Primer Tab por defecto
        vm.dataDenunciaRevision = BandejaFactory.bandejaDenuncia();
        vm.dataAceptarDenuncia = AtencionDenuncia.model();
        vm.listaDenunciantes = [];
        vm.prmDataCaso = CasoFactory.model();
        vm.dataBandeja = {};
        vm.listaArchivos = [];
        vm.asignadasTabActivo = 1;
        /*declaración de metodos enlazados a la vista*/
        /*vm.mostrarModalRechazar = mostrarModalRechazar;*/
        /*vm.mostrarModalAceptar  = mostrarModalAceptar;*/
        /*	vm.aceptarDenuncia 		= aceptarDenuncia;*/
        /*implementación de metodos*/
        $scope.renderHtml = function(htmlCode) {
            return $sce.trustAsHtml(htmlCode);
        };

        function listarArchivoDenuncia() {
            var data = {
                idDenuncia: vm.dataDenunciaRevision.idDenuncia,
            }
            AtencionDenuncia.listarArchivoDenuncia(data).then(function(response) {
                if (response.valido) {
                    debugger;
                    vm.listaArchivos = response.data;
                } else {
                    /*toastr.error(response.mensaje);*/
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function obtenerDenuncia() {
            // debugger;
            var data = {
                idDenuncia: vm.dataDenunciaRevision.idDenuncia,
            }
            DenunciaFactory.obtenerDenuncia(data).then(function(response) {
                // debugger;
                if (response.valido) {
                    vm.denuncia = response.data;
                    if (vm.denuncia.rutaFicha != null && vm.denuncia.rutaFicha != "") {
                        $rootScope.urlPDFFichaDenuncia = "<iframe src='" + angular.copy(vm.denuncia.rutaFicha) + "' style='width:100%; height:600px;' frameborder='0'> </iframe>";
                    } else {
                        $rootScope.urlPDFFichaDenuncia = "<span>El archivo no se encuentra disponible</span>"
                    }
                } else {
                    /*toastr.error(response.mensaje);*/
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }
        /*function mensajePlazoDeAtencion()
        {
        		var dias = 0;
        	if(vm.usuario.tipoEntidadCompetente==3){
        		dias =vm.diasOefa;
        	}else if(vm.usuario.tipoEntidadCompetente==4){
        		vm.diasHabilesEfa
        	}
        	ngDialog
        		.openConfirm({
        			template : 'app/base/template/dialog-mensaje/dialog-mensaje.html',
        			controller : 'DialogMensajeController',
        			controllerAs : 'vm',
        			data : {
        				Titulo : 'Denuncia Aceptada',
        				Mensaje : 'Usted Tiene ' + dias + ' días para informar las acciones correspondientes'
        			},
        			width : '380px'
        		})
        		.finally(function () {

        				$state.go('spapp.home.bandeja.asignadas');
        			});

        }*/
        /*
		  function cambiarEstadoDetalleDenuncia(data)
       {

       		var detalleBandeja = BandejaFactory.detalleBandeja();

				detalleBandeja.idBandejaDetalle = data.idBandejaDetalle;
				detalleBandeja.estado = 5;
 				BandejaFactory
 				.actualizaEstadoDetalleBandeja(angular.copy(detalleBandeja))
 				.then(function (response)
 				{

 					mensajePlazoDeAtencion();
 				})
 				.catch(function (error)
 				{

 				})
 				.finally(function () {


						});

		}
*/
        /*
        		   function aceptarDenuncia()
               {

               		vm.dataAceptarDenuncia.detalleBandeja.idBandejaDetalle = vm.dataDenunciaRevision.idBandejaDetalle;


         				vm.dataAceptarDenuncia.tipoAtencion.codigoRegistro = 3;
         				vm.dataAceptarDenuncia.denuncia.idDenuncia = vm.dataDenunciaRevision.idDenuncia;
         				vm.dataAceptarDenuncia.estado.codigoRegistro = 1;

         				AtencionDenuncia
         				.registrar(angular.copy(vm.dataAceptarDenuncia))
         				.then(function (response)
         				{
         					if(response.valido)
         					{
         						vm.dataAceptarDenuncia.idAtencionDenuncia = response.data;
         						vm.dataDenunciaRevision.estado= 5;
        							cambiarEstadoDetalleDenuncia(vm.dataDenunciaRevision);

         					}
         					else
         					{
         						toastr.error(response.mensaje);
         					}
         				})
         				.catch(function (error)
         				{
         					toastr.error('Ocurrió un error al registrar la OEFA');
         				})
         				.finally(function () {


        						});

        		}
        		*/
        /*
		function buscarCasoXId(data){

					var data = {

							idCaso : data.idCaso

					}

					CasoFactory
						.buscarPorId(data)
						.then(function (response) {
							if (response.valido) {
								vm.prmDataCaso= response.data;


							}
							else {


							}
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {

						});

		}

*/
        /*

			function listarDenunciantesXIdDenuncia(data){
				console.log("data.idDenuncia" + data.idDenuncia);
				var data = {

					idDenuncia : data.idDenuncia,
				};

			DenunciaFactory
						.listarDenunciantes(data)
						.then(function (response) {
							if (response.valido) {
								vm.listaDenunciantes= angular.copy(response.data);



							}
							else {
								toastr.error(response.mensaje);
							}
						})
						.catch(function (error) {
							toastr.error('Error al consultar');
						})
						.finally(function () {

						});

		}
*/
        /*

            	function mostrarModalRechazar(item) {



            			var prmData =vm.dataDenunciaRevision;
                    ngDialog.open({
        							template : 'app/modules/bandeja/asignadas/dialog-rechazado/dialog-rechazado.html',
        							controller : 'AccionesRechazadoDialogController',
        							data : {
        								data :prmData
        							},
        							controllerAs : 'vm',
        							width : '650px'
        						})
        						.closePromise
        						.then(function (dataDialog) {

        						});
                }
        */
        /* function mostrarModalAceptar(item) {
        	var dias = 30
            ngDialog.open({
							template: '<div class="">'
                           +'<div class="modal-header">'
                             +'<h4 class="modal-title">Denuncia Aceptada</h4>'
                           +'</div>'
                           +'<div class="modal-body">'
                             +'<p id="msj_confirmacion">Usted tiene '+dias+' d&iacute;as para informar las acciones correspondientes </p>' // mensaje de confirmacion
                           +'</div>'
                             +'<div class="modal-footer">'
                               +'<button type="button" class="btn btn-default btn-sm" ng-click="vm.close()">Cerrar</button>'
                             +'</div>'
                         +'</div>',
							width : '650px',
			                plain : true
			            }).then(
			                function(value) {
			                	toastr.success('Exito en la operación');


			                },
			                function(value) {

			                    console.log("cancel");
			                }
            			);
        }*/
        function init() {
            // debugger;
            /*if(angular.isDefined(localStorage.oSuSinWebDataSys)){*/
            /*************  Obtenemos los datos de usuario  **************/
            /*	vm.usuario = angular.fromJson(localStorage.oSuSinWebDataSys);*/
            /*************** Pasamos los datos de Bandeja  *************/
            /*	vm.dataBandeja  = vm.usuario.bandeja;*/
            if (angular.isDefined(localStorage.objDenunciaFicha)) {
                var dataFicha = angular.fromJson(localStorage.objDenunciaFicha);
                vm.flagActivoModulo = dataFicha.flagActivo;
                /*if(vm.flagActivoModulo == 1){*/
                vm.dataDenunciaRevision = dataFicha.prmData;
                obtenerDenuncia();
                /*	listarDenunciantesXIdDenuncia(vm.dataDenunciaRevision);
                	buscarCasoXId(vm.dataDenunciaRevision);*/
                /*console.log(" vm.dataDenunciaRevision.idDenuncia *-- "+  vm.dataDenunciaRevision.idDenuncia);*/
                listarArchivoDenuncia();
            }
            /* 	}*/
        }
        init();
        /*fin de metodos*/
    }
})();