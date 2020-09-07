(function() {
	'use strict';

	angular
		.module('spapp')
		.filter('percentage', percentageFilter);

	/* @ngInject */
	function percentageFilter($filter) {
		var filter = function (input, decimals) {
			return $filter('number')(input * 100, decimals) + ' %';
		}
		return filter;
	}
})();