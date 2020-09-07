(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('ExportFactory', ExportFactory);

	/* @ngInject */
	function ExportFactory($window) {
		
		var uri='data:application/vnd.ms-excel;base64,';
        var template ='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>';
        
        return {
            tableToExcel : tableToExcel
        };

        /*implementacion de metodos*/

        function tableToExcel(idTabla, nombreHojaExcel) {
        	
        	var table = angular.element(idTabla);
            var ctx = {
            	worksheet : nombreHojaExcel,
            	table : table.html()
            };
            var href = uri+base64(format(template,ctx));
            
            return href;
        }

        function base64(s) {
        	return $window.btoa(unescape(encodeURIComponent(s)));
        }

        function format(s, c) {
        	return s.replace(/{(\w+)}/g,function(m,p){return c[p];})
        }

        /*fin de factory*/
	}

})();