(function() {
	'use strict';

	angular
	.module('spapp.reporte')
	.controller('ReporteAnioController',ReporteAnioController);


	/** @ngInject */
	function ReporteAnioController($state, ReporteFactory, INTERNAL_HOME_PAGE) {
		var vm = this;

		/*declaracion de variables globales*/

		vm.anioReporte = new Date().getFullYear();

		vm.chartDataPedidosAnio = {};

		/*declaración de metodos enlazados a la vista*/

		vm.cargarReporte = cargarReporte;

		/*implementación de metodos*/

		function cargarReporte() {
			ReporteFactory
			.buscarPorAnio(angular.copy(vm.anioReporte))
			.then(function (response) {
	//			console.log('response',response);
				var seriesData = [0,0,0,0,0,0,0,0,0,0,0,0];
				for (var i = 0; i < response.data.length; i++) {
					switch(response.data[i].mesPedido)
					{
						case "01" : seriesData[0]  = response.data[i].cantidadPedidos; break;
						case "02" : seriesData[1]  = response.data[i].cantidadPedidos; break;
						case "03" : seriesData[2]  = response.data[i].cantidadPedidos; break;
						case "04" : seriesData[3]  = response.data[i].cantidadPedidos; break;
						case "05" : seriesData[4]  = response.data[i].cantidadPedidos; break;
						case "06" : seriesData[5]  = response.data[i].cantidadPedidos; break;
						case "07" : seriesData[6]  = response.data[i].cantidadPedidos; break;
						case "08" : seriesData[7]  = response.data[i].cantidadPedidos; break;
						case "09" : seriesData[8]  = response.data[i].cantidadPedidos; break;
						case "10" : seriesData[9]  = response.data[i].cantidadPedidos; break;
						case "11" : seriesData[10] = response.data[i].cantidadPedidos; break;
						case "12" : seriesData[11] = response.data[i].cantidadPedidos; break;
						default : break;
					}
				}
				//vm.chartDataPedidosAnio.series[0].data = [];
				vm.chartDataPedidosAnio.series[0].data = seriesData;
			})
			.catch(function (error) {
				toastr.error('Error al generar reporte');
			});
		}

		function init() {
			/*vm.chartDataPedidosAnio = {
				chart: { type: 'column'},
		        title: { text: '' },
		        xAxis: { type: 'category',
		            labels: {
		                rotation: -45, style: { fontSize: '13px', fontFamily: 'Segoe UI Light, sans-serif'
		                }
		            }
		        },
		        yAxis: { min: 0, title: { text: '' } },
		        legend: { enabled: false },
		        tooltip: { pointFormat: ' <b>{point.y}</b>' },
		        colors: ['#652D4F'],
		        series: [{
		            name: 'Population',
		            data: [
		                ['ENE', 0],
		                ['FEB', 0],
		                ['MAR', 0],
		                ['ABR', 0],
		                ['MAY', 0],
		                ['JUN', 0],
		                ['JUL', 0],
		                ['AGO', 0],
		                ['SEP', 0],
		                ['OCT', 0],
		                ['NOV', 0],
		                ['DIC', 0]
		            ],
		            dataLabels: {
		                enabled: true,
		                rotation: -90,
		                color: '#FFFFFF',
		                align: 'right',
		                y: 10,
		                style: { fontSize: '13px', fontFamily: 'Segoe UI Light, sans-serif' }
		            }
		        }]
			};*/
			vm.chartDataPedidosAnio = {
					responsive: {
					  rules: [{
					    condition: {
					      maxWidth: 500
					    },
					    chartOptions: {
					      legend: {
					        enabled: false
					      }
					    }
					  }]
					},
                    title: {
                        text: 'Pedidos por Año'
                    },
                    xAxis: {
                        categories: ['ENE', 'FEB', 'MAR', 'ABR', 'MAY', 'JUN',
                            'JUL', 'AGO', 'SEP', 'OCT', 'NOV', 'DIC']
                    },
                    yAxis: {
                        title: {
                            text: 'Cantidad de Pedidos'
                        }
                    },
                    tooltip: {
                        pointFormat: "Cantidad: {point.y:f}"
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                        data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                        name: 'Meses',
                        dataLabels: {
			                enabled: true,
			                /*rotation: -90,*/
			                color: '#FFFFFF',
			                align: 'right',
			                y: 10,
			                
			                style: { fontSize: '13px', fontFamily: 'Segoe UI Light, sans-serif' }
			            }
                    }]
                };
		}

		init();
		/*fin de metodos*/
	}
})();