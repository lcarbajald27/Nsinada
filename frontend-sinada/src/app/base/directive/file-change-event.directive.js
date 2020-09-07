(function() {
	'use strict';

	angular
		.module('spapp')
		.directive('fileChange', fileChange);

	/* @ngInject */
	function fileChange() {
		
		var directive = {
			restrict : 'A',
			link : link
		};

		return directive;

		function link(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.fileChange);
			element.bind('change', function (event) {
				var attachedFiles = event.target.files;
				
				onChangeHandler(attachedFiles);
				/*if (attachedFiles.length>1) {
					console.log('multiples archivos');
				}
				else {
					console.log('archivo único');
				}*/
			});

			element.bind('click', function () {
				element.val('');
			});
		}
	}

})();