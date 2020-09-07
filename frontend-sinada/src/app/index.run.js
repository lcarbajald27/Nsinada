(function() {
  'use strict';

  angular
    .module('spapp')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log, $rootScope) {
	$rootScope.cargandoInformacion = 0;
	$rootScope.menus = [];
	
    $log.debug('runBlock end');
  }

})();
