(function() {
  'use strict';

    angular
        .module('spapp')
        .directive('inputRestrict',inputRestrict);

    function inputRestrict(){
       var directive = {
            restrict : 'A',
            require: 'ngModel',
            link: link
         };

         return directive;

         function link($scope, element, attrs, modelCtrl)
         {
            modelCtrl.$parsers.push(function (inputValue)
            {
              // this next if is necessary for when using ng-required on your input.
              // In such cases, when a letter is typed first, this parser will be called
              // again, and the 2nd time, the value will be undefined

              if (angular.isUndefined(inputValue)) return '';

              //inicializar tipo de restriccion
              var tipoRestriccion = (angular.isUndefined(attrs) || angular.isUndefined(attrs.inputRestrict)) ? '' : attrs.inputRestrict;
              
              if (tipoRestriccion==='') return inputValue;
              /*
              var customRestriccion = (angular.isUndefined(attrs) || angular.isUndefined(attrs.appRegex)) ? '' : attrs.appRegex;

              // console.log('tipoRestriccion',tipoRestriccion);
              // console.log('customRestriccion',customRestriccion)
              var customRegex = null;

              if (tipoRestriccion==='custom' && customRestriccion) {
                customRegex = new RegExp(customRestriccion);
                //console.log('new RegExp', customRegex);
              }*/

              var regex = null;
              
              switch(tipoRestriccion)
              {
                case 'texto': regex = /[^A-Za-z]/g; break;
                case 'texto-espacio': regex = /[^a-zA-ZñÑáéíóúÁÉÍÓÚ\s]/g; break;
                case 'numero': regex = /[^0-9]/g; break;
                case 'numero-guion': regex = /[^0-9\-]/g; break;
                case 'numero-espacio': regex = /[^0-9\s]/g; break;
                case 'alfanumerico': regex = /[^0-9A-Za-z]/g; break;
                case 'alfanumerico-guion': regex = /[^0-9A-Za-z\-]/g; break;
                case 'alfanumerico-email': regex = /[^0-9A-Za-z_.@\-]/g; break;
                case 'alfanumerico-espacio': regex = /[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s]/g; break;
                case 'texto-variado': regex = /[^0-9a-zA-ZñÑáéíóúÁÉÍÓÚ\s\-.]/g; break;
                case 'decimal': regex = /[^0-9.]/g; break;
                case 'email' : regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g; break;
                case 'url': regex = /[^A-Za-z_.0-9-]/g; break;
                case 'hora': regex = /[^0-9:]/g; break;
                case 'custom' : regex = customRegex; break;
            	case 'telefono': regex = /[^0-9\-()]/g; break;
                default : break;
              }

              var transformedInput = inputValue.replace(regex, '');
              transformedInput = transformedInput.trim();
              if (transformedInput!=inputValue)
              {
                  modelCtrl.$setViewValue(transformedInput);
                  modelCtrl.$render();
              }
              return transformedInput;
            });
          }
      };

})();