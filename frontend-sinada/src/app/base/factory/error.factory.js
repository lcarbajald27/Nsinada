
(function() {
	'use strict';

		angular
		.module('spapp')
		.factory('ErrorFactory',ErrorFactory);

	/* @ngInject */
	function ErrorFactory($state, $log) {

		var factory = {
			redirect : redirect
		};

		return factory;
		
		/*implementación de métodos*/

		function redirect(error) {

			var val=error[0];

			$log.error(error);
	        if (error[1]===404) {
	          $state.go('mtp.404');
	        }
	        if (error[1]===500) {
	          $state.go('mtp.500');
	        }
	        if (error[1]===400) {
	          $state.go('mtp.400');
	        }
		}


		/*fin de factory*/
	}

})();