(function() {
  'use strict';

    angular
        .module('spapp')
        .directive('appTypeTime',input);//app-number

    function input(){
      var directive = {
            restrict : 'E',
            require: 'ngModel',
            link: link
        };

        return directive;

        function link($scope, element, attrs, modelCtrl, ConvertFactory) 
        {
          ngModel.$parsers.push(function (val) {
            return (val==null || isNaN(val)) ? null : parseInt(val);
          });
          ngModel.$formatters.push(function (val) {
            return val==null ? null : '' + val;
          });
        }
      };

})();