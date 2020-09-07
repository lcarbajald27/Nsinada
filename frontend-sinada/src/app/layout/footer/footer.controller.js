(function() {
	'use strict';

	angular
	.module('spapp')
	.controller('FooterController',FooterController);


	/** @ngInject */
	function FooterController() {
		var vmFooter = this;
		
		/*declaracion de variables globales*/
		
		vmFooter.templateBy = '123456789';

		/*vmFooter.templateBy = 'Copyright &copy;' + new Date().getFullYear() +
		' galaxybis | Website template by <a href="http://www.galaxybis.com" target="_blank">galaxybis.com</a>';*/

		/*declaración de métodos enlazados a la vista*/


		/*implementación de métodos*/

		
		/*fin de controller*/
		
	}
})();