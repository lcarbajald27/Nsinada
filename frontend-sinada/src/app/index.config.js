(function() {
  'use strict';

  angular
    .module('spapp')
    .config(config);

  /** @ngInject */
  function config($logProvider, toastrConfig) {
    // Enable log
    $logProvider.debugEnabled(true);

  }

})();
