(function() {
  'use strict';

    angular
        .module('spapp')
        .directive('input',input);

    function input(){
      var directive = {
            restrict : 'E',
            /*require: 'ngModel',*/
            link: link
        };

        return directive;

        function link($scope, element, attrs/*, modelCtrl*/) 
        {
          var tiposSinTrim = ['texto','numero','numero-guion','alfanumerico','alfanumerico-guion',
                              'decimal','email','url','fecha','hora','telefono'];
          if (tiposSinTrim.includes(element.attr('input-restrict'))) 
          {
            attrs.$set('ngTrim', "false");
          }

        }
      };

})();