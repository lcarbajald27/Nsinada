(function() {
	'use strict';

	angular
		.module('spapp')
		.factory('ConvertFactory', ConvertFactory);

	/* @ngInject */
	function ConvertFactory() {
		
		var factory = {
			toDate : toDate,
			toString : toString,
			toStringDate : toStringDate,
			toTime : toTime,
			isNotNull : isNotNull,
			isNotEmpty : isNotEmpty,
			isNotNoE : isNotNoE
		};
		
		return factory;
		
		function toDate(prmStringDate) {
			if (angular.isDefined(prmStringDate) && prmStringDate!=null && prmStringDate!='') {	
				try{
					var dateArray = prmStringDate.split('/');
					var date = new Date(dateArray[2]+'/'+dateArray[1]+'/'+dateArray[0]);
					return date;
				}
				catch(e){
					return null;
				}
			}
		}

		function toTime(prmStringTime) {
			var result = false, m;
		    var regEx = /^\s*([01]?\d|2[0-3]):?([0-5]\d)\s*$/;
		    if ((m = prmStringTime.match(regEx))) {
		        result = (m[1].length === 2 ? "" : "0") + m[1] + ":" + m[2];
		    }
		    return result;
		}


		function toStringDate(prmObject) {
			
			if(isNotNoE(prmObject)){
				return moment(new Date(prmObject)).format('DD/MM/YYYY');
			}
			return null;
		}

		function toString(prmObject) {
			
			if(angular.isDate(prmObject)){
				return moment(prmObject).format('DD/MM/YYYY');
			}
			return null;
		}


		/*validaciones*/

		function isNotNull(obj) {
			return obj != null;
		}

		function isNotDefined(obj) {
			return obj != undefined;
		}

		function isNotEmpty(obj) {
			return obj.toString().length > 0;
		}

		function isNotNoE(obj) {
			return  isNotNull(obj) && isNotDefined(obj) && isNotEmpty(obj);
		}

	}	
})();