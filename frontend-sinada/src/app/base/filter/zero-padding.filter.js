(function() {
	'use strict';

	angular
		.module('spapp')
		.filter('zeroPadding', zeroPaddingFilter);

	/* @ngInject */
	function zeroPaddingFilter($filter) {
		var filter = function (input, length) {
			//return $filter('number')(input * 100, decimals) + ' %';
			var num = parseInt(input, 10);
            length = parseInt(length, 10);
            if (isNaN(num) || isNaN(length)) {
                return input;
            }
            num = '' + num;
            while (num.length < length) {
                num = '0' + num;
            }
            return num;
		}
		return filter;
	}
})();