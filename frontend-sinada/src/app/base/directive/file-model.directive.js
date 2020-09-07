(function() {
	'use strict';

	angular
		.module('spapp')
		.directive('fileModel', fileModelDirective);

	/* @ngInject */
	function fileModelDirective($parse) {
		var directive = {
			restrict : 'A',
			link : link
		};

		return directive;

		function link($scope, element, attrs) {
			element.on('change',function (e) {
				$parse(attrs.fileModel).assign($scope, element[0].files[0]);	
			});
		}
	}
})();