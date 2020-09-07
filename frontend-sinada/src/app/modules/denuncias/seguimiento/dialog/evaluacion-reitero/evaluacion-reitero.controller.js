(function() {
	'use strict';

	angular
	.module('spapp.denuncias')
	.controller('EvaluacionReiteroDialogController',EvaluacionReiteroDialogController);


	/** @ngInject */
	function EvaluacionReiteroDialogController(toastr, $scope, ngDialog,MaestroFactory) {
		var vm = this;
		
		/*declaracion de variables globales*/

		vm.informeSeguimiento ={
			motivo : 0,
			tipoArchivo : 0,
			listaTiposMotivo : 0,
			listaTiposArchivo : 0,
			otroMotivo: ''
		};
		vm.mimeTypes = [];
		 vm.extension = "";
		 vm.tamanioArchivo = "500";
		 vm.caracteresMaximoArchivo = 50;
		 
		vm.listaTiposArchivo = [];
		vm.listaTiposMotivo = [];
		/*declaración de metodos enlazados a la vista*/
        vm.cancelar = cancelar;


		/*implementación de metodos*/
        function cancelar() {
			ngDialog.close();
		}

        function validaDocumento(prmFileList){
			
    		

			if (!prmFileList) {
				toastr.warning('Debe seleccionar un archivo');
				return;
			}   	
			
			var lstExtens = vm.extension.split(",");
			
				for(var i = 0 ; i<prmFileList.length;i++){

						var archivo=prmFileList[i];

						for(var x in vm.archivoAdjunto)
						{
							if(vm.archivoAdjunto[x].name==archivo.name){
								toastr.warning('Debe ingresar un archivo con un nombre diferente');
								 document.getElementById("fileArchivoMedio").value = "";
							/*  vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
							return;
							}
						}

						if (archivo.size/1024>(parseInt(vm.tamanioArchivo)*1000)) {
						
							toastr.warning('El tamaño del archivo no debe superar '+angular.copy(vm.tamanioArchivo)+'MB. Comprima el archivo y vuelva a intentarlo.');
							 document.getElementById("fileArchivoMedio").value = "";
							  /*vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
							return;
							
						}
						
						var str = archivo.name;
						var extensionArchivo = str.substring(str.lastIndexOf("."),str.length);
						
						if(!vm.mimeTypes.includes(archivo.type) && !lstExtens.includes(extensionArchivo)){
								toastr.warning('No es el tipo de archivo seleccionado.');
							 document.getElementById("fileArchivoMedio").value = "";
							 return;
						
						}
						
						if (archivo.name.length>parseInt(vm.caracteresMaximoArchivo)) {
						
							toastr.warning('El nombre del archivo no debe superar los '+vm.caracteresMaximoArchivo+' caracteres. Renombre el archivo y vuelva a cargarlo.');
							 document.getElementById("fileArchivoMedio").value = "";
							 /* vm.archivoAdjunto.splice(vm.dataInformarAccion.lstArchivoAtencion.length, 1);*/
							return;
							
						}
				}
			console.log(vm.archivoAdjunto.length);
			vm.archivoAdjunto.push(prmFileList[0]);
		}
        
        function obtenerParametros(){
			MaestroFactory
			.obtenerParametros()
			.then(function (response) 
			{
				vm.tamanioArchivo = angular.copy(response.data[0].tamanioArchivo);
				vm.caracteresMaximoArchivo = angular.copy(response.data[0].maximoCaracteresArchivo);
			 
				vm.extension = angular.copy(response.data[0].extensionArchivoDocumento);
				var tipos = angular.copy(response.data[0].tipoArchivoDocumento);
				vm.mimeTypes = angular.copy(tipos.split(","));
			}).catch(function (error) 
					{
				toastr.error('No se pudo obtener los parametros.');
			});
		}
		/*fin de metodos*/

		function init() {
			
			vm.listaTiposArchivo.push({codigoRegistro:1,descripcion:'Memorándum'});
			vm.listaTiposArchivo.push({codigoRegistro:2,descripcion:'Oficio'});
			vm.listaTiposArchivo.push({codigoRegistro:3,descripcion:'Informe'});
			vm.listaTiposArchivo.push({codigoRegistro:4,descripcion:'Otros'});

			vm.listaTiposMotivo.push({codigoRegistro:1,descripcion:'No recepcionó denuncia'});
			vm.listaTiposMotivo.push({codigoRegistro:2,descripcion:'No validó denuncia'});
			vm.listaTiposMotivo.push({codigoRegistro:3,descripcion:'No atendió denuncia'});
			vm.listaTiposMotivo.push({codigoRegistro:4,descripcion:'No levantó observaciones'});
			vm.listaTiposMotivo.push({codigoRegistro:5,descripcion:'Otros'});
			obtenerParametros();
		}

		init();

		/*fin de controller*/
	}
})();