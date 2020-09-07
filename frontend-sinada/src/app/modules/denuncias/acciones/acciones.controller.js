(function() {
	'use strict';

	angular
	.module('spapp.denuncias')
	.controller('AccionesController',AccionesController);


	/** @ngInject */
	function AccionesController($state, ngDialog) {
		var vm = this;
		
		/*declaracion de variables globales*/
		vm.itemTabActivo = 1;//Primer Tab por defecto
		
		/*declaración de metodos enlazados a la vista*/
		vm.mostrarModalRechazar = mostrarModalRechazar;
		vm.mostrarModalAceptar = mostrarModalAceptar;
		/*implementación de metodos*/

    	function mostrarModalRechazar() {
            ngDialog.open({
							template : 'app/modules/denuncias/acciones/dialog-rechazado/dialog-rechazado.html',
							controller : 'AccionesRechazadoDialogController',
							data : {

							},
							controllerAs : 'vm',
							width : '650px'
						})
						.closePromise
						.then(function (dataDialog) {
							/*if (dataDialog.value.cliente) {
								vm.dataPedido.cliente = angular.copy(dataDialog.value.cliente);
							}*/
						});
        }
        function mostrarModalAceptar(item) {

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
			                    /** save the contact form **/
			                	//eliminarExamen(parametro); // llamada a la funcion que se ejecutara al confirmar accion. se envia parametro de ser necesario.
			                },
			                function(value) {
			                    /** Cancel or do nothing **/
			                    //console.log("cancel");
			                }
            			);
        }
    	
		/*fin de metodos*/
	}
})();