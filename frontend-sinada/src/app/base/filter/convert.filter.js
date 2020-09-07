(function() {
	'use strict';

	angular
		.module('spapp')
		.filter('convert', convertFilter);

	/* @ngInject */
	function convertFilter($filter) {
		var filter = function(input, len, pad){
	    if(input === null) return input;
	    input = input.toString();
	    if(input.length >= len) return input;
	    else{
	      pad = (pad || 0).toString();
	      return new Array(1 + len - input.length).join(pad) + input;
	    }
	  };
		return filter;
	}
})();