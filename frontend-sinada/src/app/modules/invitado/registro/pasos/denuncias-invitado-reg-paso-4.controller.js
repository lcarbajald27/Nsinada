(function() {
    'use strict';
    angular.module('spapp.invitado').controller('DenunciasInvitadoRegPaso4Controller', DenunciasInvitadoRegPaso4Controller);
    /* @ngInject */
    function DenunciasInvitadoRegPaso4Controller($rootScope, $scope, $state, $element, toastr, ngDialog, DenunciaFactory, UbigeoFactory, CasoFactory, DetalleCasoFactory, MaestroFactory, esriLoader, ARCGIS_CONFIG) {
        var vm = this;

        /*declaracion de variables globales*/
        vm.prmDataCaso = CasoFactory.model();
        $rootScope.pasoActivo = 4;
        vm.denuncia = DenunciaFactory.model();
        vm.departamento = UbigeoFactory.departamento();
        vm.provincia = UbigeoFactory.provincia();
        vm.distrito = UbigeoFactory.distrito();
        vm.listaZonaSucesonivel1 = [];
        vm.listaZonaSucesonivel2 = [];
        vm.listaZonaSucesonivel3 = [];
        vm.listaCentroPoblado = [];
        vm.listaDepartamento = [];
        vm.listaProvincia = [];
        vm.listaDistrito = [];
        vm.esriModules = null;
        vm.mapView = null;
        vm.puntoDenuncia = null;
        vm.infoCoordenadas = {};
        vm.seleccionarPuntoDenunciaHabilitado = false;
        /*declaracion de metodos */
        vm.siguiente = siguiente;
        vm.regresar = regresar;
        vm.listarProvincia = listarProvincia;
        vm.listarDistrito = listarDistrito;
        vm.listarCasosGenerales = listarCasosGenerales;
        vm.validarCasoRegistrado = validarCasoRegistrado;
        vm.listarCentroPoblado = listarCentroPoblado;
        vm.onMapViewLoaded = onMapViewLoaded;
        /*implementacion de metodos*/
        function validarCasoRegistrado() {
            CasoFactory.validaCasoRegistrado(angular.copy(vm.prmDataCaso)).then(function(response) {
                if (response.valido) {
                    vm.prmDataCaso = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                    toastr.success("Caso Encontrado");
                } else {
                    vm.prmDataCaso.idCaso = 0;
                    /*toastr.error(response.mensaje);*/
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        var layerUrl = "https://pifa.oefa.gob.pe/arcgis/rest/services/ESPECIALES/SERVICIO_EFAS_SINADA/MapServer";
        var layerCuerposAgua = "https://pifa.oefa.gob.pe/arcgis/rest/services/APP_SINADA/SERV_LIM_CUERPOS_AGUA_DES/FeatureServer/"
        var layerDefinitions = [
            {id: 0, url: layerUrl, name: 'adminLocales', fields: ['CODE_ALA', 'NAME_ALA']},
            {id: 1, url: layerUrl, name: 'areaConservacion', fields: ['ANP_CODI', 'ANP_NOMB', 'ANP_CATE']},
            {id: 2, url: layerUrl, name: 'oficinasDesconcentradas', fields: ['COD_OD', 'NOM_OD']},
            {id: 3, url: layerUrl, name: 'departamento', fields: ['IDDPTO'], isUbigeo: true},
            {id: 4, url: layerUrl, name: 'provincia', fields: ['IDPROV'], isUbigeo: true},
            {id: 5, url: layerUrl, name: 'distrito', fields: ['IDDIST'], isUbigeo: true},
            {id: 7, url: layerUrl, name: 'zonaAmortiguamiento', fields: ['COD_ZAMORT', 'ZA_NOMB']},
            {id: 0, url: layerCuerposAgua, name: 'cuerposAgua', fields: ['IDDPTO']},
        ];
        var buscandoCapas = false;
        $rootScope.cuerpoAguaSeleccionado = $rootScope.cuerpoAguaSeleccionado || false;
        
        function obtenerListaSeleccione(lstDataMaestra) {
            var listaMaestra = [{
                idDetalleCaso: 0,
                descripcion: 'Seleccione'
            }];
            for (var x in lstDataMaestra) {
                listaMaestra.push(angular.copy(lstDataMaestra[x]));
            }
            return listaMaestra;
        }

        function listarCasosGenerales(tipoCaso, codigoPadre, nivel) {
            var dataProblematica = {
                tipoCaso: tipoCaso,
                codigoPadre: codigoPadre,
                tipoNivel: nivel
            };
            DetalleCasoFactory.listarDetalleCasoRegistrado(angular.copy(dataProblematica)).then(function(response) {
                if (response.valido) {
                    var dataObj = obtenerListaSeleccione(response.data);
                    if (tipoCaso == 2 && nivel == 1) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaDebidoANivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 2 && nivel == 2) {
                        limpiarGenerico(tipoCaso, nivel);
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel2 = angular.copy(dataObj);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 2 && nivel == 3) {
                        limpiarGenerico(tipoCaso, nivel);
                        if (response.data.length > 0) {
                            vm.listaDebidoANivel3 = angular.copy(dataObj);
                        } else {
                            vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                            listarCasosGenerales(3, codigoPadre, 1);
                        }
                    }
                    if (tipoCaso == 3 && nivel == 1) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaZonaSucesonivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 2) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaZonaSucesonivel2 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 3) {
                        limpiarGenerico(tipoCaso, nivel);
                        vm.listaZonaSucesonivel3 = angular.copy(dataObj);
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function limpiarGenerico(tipo, nivel) {
            if (tipo == 0 && nivel == 0) {
                /*	vm.prmDetalleCaso = DetalleCasoFactory.model();*/
                vm.listaDebidoANivel1 = [];
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
                vm.listaZonaSucesonivel1 = [];
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 2 && nivel == 1) {
                vm.prmDataCaso.debidoA1.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel1 = [];
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 2 && nivel == 2) {
                vm.prmDataCaso.debidoA2.idDetalleCaso = 0;
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel2 = [];
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 2 && nivel == 3) {
                vm.prmDataCaso.debidoA3.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaDebidoANivel3 = [];
            }
            if (tipo == 3 && nivel == 1) {
                vm.prmDataCaso.zonasuceso1.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel1 = [];
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 3 && nivel == 2) {
                vm.prmDataCaso.zonasuceso2.idDetalleCaso = 0;
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel2 = [];
                vm.listaZonaSucesonivel3 = [];
            }
            if (tipo == 3 && nivel == 3) {
                vm.prmDataCaso.zonasuceso3.idDetalleCaso = 0;
                vm.listaZonaSucesonivel3 = [];
            }
        }
        /********************************************************************/
        function listarCasosGeneralesConDatos(tipoCaso, codigoPadre, nivel) {
            var dataProblematica = {
                tipoCaso: tipoCaso,
                codigoPadre: codigoPadre,
                tipoNivel: nivel
            };
            DetalleCasoFactory.listarDetalleCasoRegistrado(angular.copy(dataProblematica)).then(function(response) {
                if (response.valido) {
                    var dataObj = obtenerListaSeleccione(response.data);
                    if (tipoCaso == 3 && nivel == 1) {
                        vm.padreDondeSucede.idDetalleCaso = codigoPadre;
                        vm.listaZonaSucesonivel1 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 2) {
                        vm.listaZonaSucesonivel2 = angular.copy(dataObj);
                    }
                    if (tipoCaso == 3 && nivel == 3) {
                        vm.listaZonaSucesonivel3 = angular.copy(dataObj);
                    }
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (tipoCaso == 3 && nivel == 1 && vm.prmDataCaso.zonasuceso1.idDetalleCaso > 0) {
                    vm.prmDataCaso.zonasuceso1.idDetalleCaso = angular.copy(parseInt(vm.prmDataCaso.zonasuceso1.idDetalleCaso));
                    listarCasosGeneralesConDatos(3, vm.prmDataCaso.zonasuceso1.idDetalleCaso, 2);
                }
                if (tipoCaso == 3 && nivel == 2 && vm.prmDataCaso.zonasuceso2.idDetalleCaso > 0) {
                    vm.prmDataCaso.zonasuceso2.idDetalleCaso = angular.copy(parseInt(vm.prmDataCaso.zonasuceso2.idDetalleCaso));
                    listarCasosGeneralesConDatos(3, vm.prmDataCaso.zonasuceso2.idDetalleCaso, 3);
                }
                /*if(tipoCaso==3 && nivel==3 && vm.prmDataCaso.zonasuceso2.idDetalleCaso>0){
                	
                	listarCasosGeneralesConDatos(3,vm.prmDataCaso.debidoA1.idDetalleCaso,3);

                }*/
            });
        }
        /***********************************************************************/
        function listarDepartamento() {
            return UbigeoFactory.listarDepartamento(angular.copy(vm.departamento)).then(function(response) {
                if (response.valido) {
                    vm.listaDepartamento = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {});
        }

        function listarProvincia() {
            vm.listaCentroPoblado = [];
            if (vm.denuncia.departamento == '' && vm.denuncia.departamento == '00') {
                vm.denuncia.provincia = '0';
                vm.denuncia.distrito = '0';
            }
            vm.provincia.codigoDepartamento = vm.denuncia.departamento;
            vm.denuncia.ubDepartamento = angular.copy(buscarDepartamento(angular.copy(vm.listaDepartamento), angular.copy(vm.denuncia.departamento)));
            return UbigeoFactory.listarProvincia(angular.copy(vm.provincia)).then(function(response) {
                if (response.valido) {
                    vm.listaProvincia = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {

            });
        }

        function listarDistrito() {
            vm.listaCentroPoblado = [];
            //vm.denuncia.distrito =0;
            vm.distrito.codigoDepartamento = vm.denuncia.departamento;
            vm.distrito.codigoProvincia = vm.denuncia.provincia;
            vm.denuncia.ubProvincia = angular.copy(buscarProvincia(angular.copy(vm.listaProvincia), angular.copy(vm.denuncia.provincia)));
            return UbigeoFactory.listarDistrito(angular.copy(vm.distrito)).then(function(response) {
                if (response.valido) {
                    vm.listaDistrito = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
                if (vm.denuncia.codigoCentroPoblado.length == 10) {
                    listarCentroPoblado();
                }
            });
        }

        function listarCentroPoblado() {
            var dataCentroPoblado = UbigeoFactory.centroPoblado();
            dataCentroPoblado.codigoDistrito = vm.denuncia.departamento + vm.denuncia.provincia + vm.denuncia.distrito;
            return UbigeoFactory.listarCentroPoblado(angular.copy(dataCentroPoblado)).then(function(response) {
                if (response.valido) {
                    vm.listaCentroPoblado = response.data;
                    /*vm.dataDenuncia.tipoPersona=0;*/
                } else {
                    toastr.error(response.mensaje);
                }
            }).catch(function(error) {
                toastr.error('Error al consultar');
            }).finally(function() {
            });
        }
        /*	function cargarCombos() 
        {
        	var codigosCombos = ['CentroPoblado'];
        	MaestroFactory
        	.buscarMaestros(angular.copy(codigosCombos))
        	.then(function (response) 
        	{
        		
        		if (response!=null&&response.valido)
        		{
        			for(var x in response.data)
        			{
        				var tipoCombo = response.data[x].Key;
        				var datosCombo = response.data[x].Value;

        				switch(tipoCombo)
        				{
        					case 'CentroPoblado' : vm.listaCentroPoblado 		= datosCombo; break;
        					
        					
        					default : break;
        				}
        			}
        		}
        	}).catch(function (error) 
        	{
        		toastr.error('No se pudo obtener los datos para los combos.');
        	});
        }*/
        function siguiente() {
            if (!vm.denuncia.departamento || !vm.denuncia.provincia || !vm.denuncia.distrito){
                toastr.error('Debe establecer el valor para departamento, provincia y distrito');
                vm.widgetActivo = 'formulario-datos-geograficos';
                return;
            }

            if (!vm.denuncia.referencia ){
                toastr.error('Falta ingresar campo obligatorio de Referencia en Datos geográficos');
                vm.widgetActivo = 'formulario-datos-geograficos';
                setTimeout( function(){
                    var referenciaInput = document.getElementById("referencia");
                    if (referenciaInput){
                        referenciaInput.scrollIntoView();
                        referenciaInput.focus();
                    }
                }, 500);
                return;
            }
            vm.denuncia.direccion = vm.denuncia.direccion || " ";
            vm.denuncia.caso = vm.prmDataCaso;
            vm.denuncia.ubDistrito = angular.copy(buscarDistrito(angular.copy(vm.listaDistrito), angular.copy(vm.denuncia.distrito)));
            $rootScope.ValidaPaso3 = '1';
            $state.go('invitado.registro.paso3');
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
        }

        function regresar() {
            /*if(vm.prmDataCaso!=null){
            	vm.denuncia.caso=vm.prmDataCaso;
            }*/
            vm.denuncia.caso = angular.copy(vm.prmDataCaso);
            localStorage.removeItem("objDenuncia");
            localStorage.setItem("objDenuncia", angular.toJson(vm.denuncia));
            $state.go('invitado.registro.paso2');
            if (vm.denuncia.tipoDenuncia == 1) {
              $state.go('invitado.registro.paso1');
          }
        }
        /*fin de implementacion de metodos*/
        function buscarDepartamento(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                if (listado[i].codigoDepartamento == codigo) {
                    return listado[i];
                }
            }
        }

        function buscarProvincia(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                if (listado[i].codigoProvincia == codigo) {
                    return listado[i];
                }
            }
        }

        function buscarDistrito(listado, codigo) {
            for (var i = 0; i < listado.length; i++) {
                if (listado[i].codigoDistrito == codigo) {
                    return listado[i];
                }
            }
        }

        function onMapViewLoaded(view){
            view.on('click', mapClick);
            view.on('pointer-move', updateCoordinates);
            vm.mapView = view;
            view.constraints.maxScale = 445;
            // Agregar widgets
            var searchWidget = new vm.esriModules.SearchWidget({
                view: view
            });

            var basemapGallery = new vm.esriModules.BasemapGallery({
                view: view,
                container: document.getElementById('basemap-selector-container'),
            });

            setTimeout(function () {
                basemapGallery.source.basemaps.add({
                    baseLayers: new vm.esriModules.WebTileLayer({
                        urlTemplate : 'https://mts1.google.com/vt/lyrs=s@186112443&hl=x-local&src=app&x={col}&y={row}&z={level}&s=Galile',
                        copyright: 'Google Maps: Satelital',
                    }),
                    id: 'googlesat',
                    thumbnailUrl: 'https://maps.ngdc.noaa.gov/viewers/dijits/agsjs/xbuild/agsjs/dijit/images/googlesatellite.png',
                    title: 'Google Satelital',
                });
            }, 4000);

            var legend = new vm.esriModules.Legend({
                view: view,
                container: document.getElementById('legend-container'),
            });

            var layerList = new vm.esriModules.LayerList({
                view: view,
                container: document.getElementById('layer-list-container'),
            });

            var locateWidget = new vm.esriModules.Locate({view:view});

            initSelectorCoordenadas();

            view.ui.add([
                locateWidget,
                document.querySelector('#legend-widget-toggle'),
                document.querySelector('#basemap-selector-widget-toggle'),
                document.querySelector('#layer-list-widget-toggle'),
                document.querySelector('#formulario-datos-geograficos-widget-toggle'),
                document.querySelector('#formulario-buscador-coordenadas-widget-toggle'),
                document.querySelector('#activar-seleccion-punto'),
            ], 'top-left');

            view.ui.add([
                searchWidget,
            ], 'top-right');

            view.ui.add([
                document.querySelector('#activar-full-screen'),
            ], 'bottom-right');

            if (vm.denuncia && vm.denuncia.latitude && vm.denuncia.longitude){
                agregarPunto({
                    type: 'point',
                    latitude: vm.denuncia.latitude,
                    longitude: vm.denuncia.longitude
                });
                $scope.capasCargadas = true;
            }

            vm.widgetActivo = 'formulario-datos-geograficos';

            $rootScope.cargandoInformacion--;
        }

        function initSelectorCoordenadas(){
            var baseNode = document.querySelector('#coordenada-selector');
            var selectSystem = baseNode.querySelector('#coordenada-selector-codigo-sistema');
            var zoneSelector = baseNode.querySelector('#coordenada-selector-zona');
            var selectZona = zoneSelector.querySelector('select');
            var inputX = baseNode.querySelector('#coordenada-input-coord-x');
            var inputY = baseNode.querySelector('#coordenada-input-coord-y');
            var btnLocalizar = baseNode.querySelector('#coordenada-btn-localizar-coord');

            selectSystem.addEventListener('change', function(evt){
                zoneSelector.style.display = evt.target.value === '1' ? 'none' : 'block';
            });



            btnLocalizar.addEventListener('click', function(){
                // Ubicar y mostrar coordenadas
                var coordenadas;

                if (isNaN(inputX.value) || isNaN(inputY.value)){
                    toastr.error('Coordenadas inválidas');
                    return;
                }

                if (selectSystem.value === '2'){
                    coordenadas = UTMConverter.toLatLon(inputX.value, inputY.value, selectZona.value, false);
                }else{
                    coordenadas = {latitude: Number(inputY.value), longitude: Number(inputX.value)};
                }

                limpiarGraficos();
                mostrarGeometria({
                    type: 'point',
                    latitude: coordenadas.latitude,
                    longitude: coordenadas.longitude,
                });
                vm.mapView.goTo({
                    target: [coordenadas.longitude, coordenadas.latitude],
                    zoom: 15,
                });
            });
        }

        function agregarPunto(geometry){
            vm.puntoDenuncia = new vm.esriModules.Graphic({
                geometry: geometry,
                symbol: {
                    type: "picture-marker",
                    url: "assets/images/map_pin.png",
                    width: 24,
                    height: 24,
                    yoffset: 12
                }
            });
            vm.mapView.graphics.removeAll();
            vm.mapView.graphics.add(vm.puntoDenuncia);
        }

        function mostrarGeometria(geometry){
            var symbol;
            if (geometry.type === 'point'){
                symbol = {
                    type: 'simple-marker',
                    color: [20, 74, 166, 0.9],
                }
            }else{
                symbol = {
                    type: 'simple-fill',
                    color: [20, 74, 166, 0.3],
                    outline: {
                        color: [20, 74, 166, 0.9],
                        width: "0.5px"
                    }
                };
            }

            var graphic = new vm.esriModules.Graphic({
                geometry: geometry,
                symbol: symbol
            });

            vm.mapView.graphics.add(graphic);

        }

        function limpiarGraficos(){
            vm.mapView.graphics.removeAll();
            $scope.capasCargadas = false;
        }

        function acercarUbicacion(tipo, codigo){

            var idCapa, codeField;
            var zoom;
            switch (tipo) {
                case 'departamento':
                    idCapa = 3;
                    codeField = 'IDDPTO';
                    break;
                case 'provincia':
                    idCapa = 4;
                    codeField = 'IDPROV';
                    break;
                case 'distrito':
                    idCapa = 5;
                    codeField = 'IDDIST';
                    break;
                case 'centroPoblado':
                    idCapa = 6;
                    codeField = 'IDCCPP_15';
                    zoom = 15;
                    break;
            }
            var lyr = layerUrl + '/' + idCapa;
            var queryTask = vm.esriModules.QueryTask({url: lyr});
            queryTask.execute({
                outFields: ["OBJECTID"],
                returnGeometry: true,
                where: codeField + "='" + codigo + "'",
            }).then(function(result){
                if (result.features && result.features.length > 0){
                    var targetObj = {
                        target: result.features[0].geometry,
                    };
                    if(tipo !== 'centroPoblado'){
                        targetObj.target.extent.expand(1.5);
                    }

                    if (zoom){
                        targetObj.zoom = zoom;
                    }
                    mostrarGeometria(result.features[0].geometry);
                    vm.mapView.goTo(targetObj);
                }
            });
        }

        function mapClick(event){
            if (!vm.seleccionarPuntoDenunciaHabilitado){
                return;
            }
            vm.seleccionarPuntoDenunciaHabilitado = !vm.seleccionarPuntoDenunciaHabilitado;
            if(!vm.esriModules){
                return;
            }
            var resolvePromiseBusqueda;
            var promiseBusqueda = new Promise(function(res){
                resolvePromiseBusqueda = res;
            });
            // $scope.$apply(function () {
            //     $scope.capasCargadas = false;
            // });
            $rootScope.$apply(function(){
                $rootScope.cargandoInformacion++;
            });

            $rootScope.cuerpoAguaSeleccionado = false;
            buscandoCapas = true;
            promiseBusqueda.then( function () {
                buscandoCapas = false;
            });

            queryLayers(event.mapPoint).then( function(results){
                var algunResultado = results.some( function ( result ) {
                    return result.features.length > 0;
                });
                if (!algunResultado){
                    toastr.error("No se puede registrar una denuncia fuera del ámbito nacional.");
                    return;
                }

                vm.denuncia['latitude'] = event.mapPoint.latitude;
                vm.denuncia['longitude'] = event.mapPoint.longitude;

                var utmPoint = UTMConverter.toUtm(event.mapPoint.latitude, event.mapPoint.longitude);
                vm.denuncia['coord_x_utm'] = utmPoint.easting;
                vm.denuncia['coord_y_utm'] = utmPoint.northing;
                vm.denuncia['zona'] = utmPoint.zoneNum;
                
                return results.map( function(result){
                    var lyrName = result.layerDefinition.name;
                    var lyrField = result.layerDefinition.fields[0];
                    if (result.features.length > 0){
                        if (result.layerDefinition.name === 'distrito') {
                            var codDistrito = result.features[0].attributes[lyrField];
                            vm.denuncia['departamento'] = codDistrito.substr(0, 2);
                            listarProvincia().then( function () {
                                vm.denuncia['provincia'] = codDistrito.substr(2, 2);
                                listarDistrito().then(function () {
                                    vm.denuncia['distrito'] = codDistrito.substr(4, 2);
                                    listarCentroPoblado().then(function(){
                                        agregarPunto(event.mapPoint);
                                        toastr.success("Se ha ubicado exitosamente su registro de Denuncia. Por favor haga click en siguiente");
                                        resolvePromiseBusqueda();
                                        setTimeout(function(){
                                            $scope.$apply(function () {
                                                $scope.capasCargadas = true;
                                            });
                                        }, 300)
                                    });
                                });
                            });
                        }else{
                            // Cambio realizado para manejar los cuerpos de agua                            
                            if (lyrName === 'cuerposAgua'){
                                $rootScope.cuerpoAguaSeleccionado = true;
                                vm.denuncia['departamento'] = result.features[0].attributes[lyrField];
                                vm.denuncia.provincia = '';
                                vm.denuncia.distrito = '';
                                vm.denuncia.codigoCentroPoblado = '';
                                agregarPunto(event.mapPoint);
                                listarProvincia().then(listarDistrito).then(listarCentroPoblado);
                                resolvePromiseBusqueda();
                                setTimeout(function(){
                                    $scope.$apply(function () {
                                        $scope.capasCargadas = true;
                                    });
                                }, 300);
                            }else if (!result.layerDefinition.isUbigeo){
                                $rootScope.infoCapasGeograficas[lyrName] = result.features[0].attributes[lyrField];
                                // Cambio realizado para enviar nombres de las entidades
                                if (result.layerDefinition.fields.length >= 2){
                                    $rootScope.infoCapasGeograficas[lyrName + 'Nombre'] = result.features[0].attributes[result.layerDefinition.fields[1]];
                                }                                
                            }
                        }
                    }else if(!result.layerDefinition.isUbigeo){
                        $rootScope.infoCapasGeograficas[lyrName] = '';
                        if (result.layerDefinition.fields.length >= 2){
                            $rootScope.infoCapasGeograficas[lyrName + 'Nombre'] = '';
                        }
                    }
                });

            }).finally(function(){
                $rootScope.$apply(function(){
                    $rootScope.cargandoInformacion--;
                });
            });
        }

        function updateCoordinates(evt){
            var point = vm.mapView.toMap({x: evt.x, y: evt.y});
            var utmInfo = UTMConverter.toUtm(point.latitude, point.longitude);
            vm.infoCoordenadas = {
                geograficas: ' ' + point.latitude.toFixed(6) + ', ' + point.longitude.toFixed(6),
                utm: ' ' + utmInfo.easting.toFixed(0) + 'E, ' + utmInfo.northing.toFixed(0),
                zona: ' ' + utmInfo.zoneNum,
            };
            $scope.$apply();
        }

        function queryLayers(point){
            var promises = [];
            layerDefinitions.forEach(function (layer) {
                var queryTask = vm.esriModules.QueryTask({url: layer.url + '/' + layer.id});
                promises.push(queryTask.execute({
                    geometry: point,
                    outFields: layer.fields,
                }).then(function(result){
                    result.layerDefinition = layer;
                    if (layer.name === 'areaConservacion'){
                        result.features.forEach(function(f){
                            // Cambio solicitado para tener nombre de ANP como concatenacion de nombre y categoria
                            f.attributes[layer.fields[1]] = f.attributes[layer.fields[2]] + ' ' + f.attributes[layer.fields[1]];
                        })
                    }
                    return result;
                })) ;
            });

            return Promise.all(promises);
        }

        function init() {
            /*cargarCombos();*/
            if ($rootScope.ValidaPaso4 == '1') {
                $rootScope.ValidaPaso4 = '1';
                $rootScope.infoCapasGeograficas = $rootScope.infoCapasGeograficas || {};
                if (angular.isDefined(localStorage.objDenuncia)) {
                    listarDepartamento();
                    vm.denuncia = angular.fromJson(localStorage.objDenuncia);
                    vm.listaZonaSucesonivel1 = $rootScope.localListaZonaSuceso;
                    vm.prmDataCaso = angular.copy(vm.denuncia.caso);
                    if (vm.denuncia.departamento != '' && vm.denuncia.departamento != '00') {
                        listarProvincia();
                    }
                    if (vm.denuncia.provincia != '' && vm.denuncia.provincia != '00') {
                        listarDistrito();
                    }
                    if (vm.denuncia.distrito != '' && vm.denuncia.distrito != '00'){
                        listarCentroPoblado();
                    }
                    listarCasosGeneralesConDatos(3, vm.prmDataCaso.zonasuceso1.idDetalleCaso, 2);
                } else {
                    vm.denuncia.provincia = '';
                    vm.denuncia.distrito = '';
                    vm.denuncia.codigoCentroPoblado = '';
                    listarDepartamento();
                }
                var P = new Promise( function (resolve, reject){
                    $rootScope.cargandoInformacion++;
                    if (esriLoader.isLoaded()){
                        resolve();
                    }else{
                        esriLoader.bootstrap({url: 'https://js.arcgis.com/4.5'}).then(resolve);
                    }
                });
                P.then( function () {
                    esriLoader.require([
                        'esri/Map',
                        'esri/Graphic',
                        "esri/layers/MapImageLayer",
                        'esri/layers/WebTileLayer',
                        "esri/tasks/QueryTask",
                        "esri/widgets/Search",
                        "esri/widgets/BasemapGallery",
                        "esri/widgets/BasemapGallery/support/LocalBasemapsSource",
                        "esri/widgets/Legend",
                        "esri/widgets/LayerList",
                        "esri/widgets/Locate",
                    ], function(Map, Graphic, MapImageLayer, WebTileLayer, QueryTask, SearchWidget, BasemapGallery, LocalBasemapsSource,Legend,LayerList,Locate) {
                        vm.map = new Map({
                            basemap: 'hybrid'
                        });

                        ARCGIS_CONFIG.mapLayers.forEach(function(layerConfig){
                            layerConfig.visible = layerConfig.visible || false;
                            var layer = new MapImageLayer(layerConfig);
                            vm.map.add(layer);
                        });

                        vm.esriModules = {
                            Graphic: Graphic,
                            QueryTask: QueryTask,
                            SearchWidget: SearchWidget,
                            BasemapGallery: BasemapGallery,
                            LocalBasemapsSource: LocalBasemapsSource,
                            Legend: Legend,
                            LayerList: LayerList,
                            Locate: Locate,
                            WebTileLayer: WebTileLayer,
                        };

                        $scope.$watch('vm.denuncia.departamento', function(codDepartamento) {
                            if (vm.mapView && !!codDepartamento && !buscandoCapas && !$rootScope.cuerpoAguaSeleccionado){
                                var codigo = codDepartamento;
                                limpiarGraficos();
                                acercarUbicacion('departamento', codigo)
                            }
                        });
                        $scope.$watch('vm.denuncia.provincia', function(codProvincia) {
                            if (vm.mapView && !!codProvincia && !buscandoCapas && !$rootScope.cuerpoAguaSeleccionado){
                                var codigo = vm.denuncia.departamento + codProvincia;
                                limpiarGraficos();
                                acercarUbicacion('provincia', codigo);
                            }
                        });
                        $scope.$watch('vm.denuncia.distrito', function(codDistrito) {
                            if (vm.mapView && !!codDistrito && !buscandoCapas && !$rootScope.cuerpoAguaSeleccionado){
                                var codigo = vm.denuncia.departamento + vm.denuncia.provincia + codDistrito;
                                limpiarGraficos();
                                acercarUbicacion('distrito', codigo);
                            }
                        });
                        $scope.$watch('vm.denuncia.codigoCentroPoblado', function(codCentroPoblado) {
                            if (vm.mapView && !!codCentroPoblado && !buscandoCapas && !$rootScope.cuerpoAguaSeleccionado){
                                var codigo = codCentroPoblado;
                                limpiarGraficos();
                                acercarUbicacion('centroPoblado', codigo);
                            }
                        });
                    });
                });

            } else {
                $rootScope.infoCapasGeograficas = {};
                localStorage.removeItem("objDenuncia");
                localStorage.removeItem("objDenunciaCorreo");
                localStorage.removeItem("objPreguntaResLocal");
                $state.go('invitado.registro.paso1');
            }
            /*vm.listaDistrito.push({codigoRegistro:'03',descripcion:'Lima'});*/
        }
        init();
        /*fin de controller*/
    }
})();
