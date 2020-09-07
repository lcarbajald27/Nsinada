(function() {
	'use strict';

	angular
		.module('spapp')
		.config(config);

	/* @ngInject */
	function config(ngDialogProvider) {
		
		ngDialogProvider.setDefaults({
		    className: 'ngdialog-theme-app',
		    closeByDocument: false
		});
	}
})();