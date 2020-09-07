(function() {
	'use strict';

	angular
	.module('spapp')
	.config(config);

	/** @ngInject  */
	function config(toastrConfig) {
		
		/*
	    toastrConfig.allowHtml = true;
	    toastrConfig.timeOut = 3000;
	    toastrConfig.positionClass = 'toast-top-right';
	    toastrConfig.preventDuplicates = true;
	    toastrConfig.progressBar = true;
	    */
		var options = {
	    		closeButton: false,
	    		debug: false,
	    		newestOnTop: false,
	    		progressBar: true,
	    		maxOpened: 1, 
	    		positionClass: 'toast-top-right',
	    		preventDuplicates: false,
	    		preventOpenDuplicates:true,
	    		onclick: null,
	    		showDuration: 300,
	    		hideDuration: 1000,
	    		timeOut: 3000,
	    		extendedTimeOut: 1000,
	    		showEasing: 'swing',
	    		hideEasing: 'linear',
	    		showMethod: 'fadeIn',
	    		hideMethod: 'fadeOut'
	    		};

		angular.extend(toastrConfig, options);
		
	}

})();