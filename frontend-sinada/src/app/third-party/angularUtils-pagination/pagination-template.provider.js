(function() {
	'use strict';

	angular
		.module('spapp')
		.config(appPaginationTemplateProvider);

	function appPaginationTemplateProvider(paginationTemplateProvider) {
		
		paginationTemplateProvider.setPath('app/third-party/angularUtils-pagination/dirPagination.tpl.html');
	}
})();