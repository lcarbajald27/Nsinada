(function() {
    'use strict';

    angular
        .module('spapp')
        .directive('hcChart',hcChart);

    /* @ngInject */
    function hcChart() {
        var directive = {
            restrict: 'E',
            template: '<div></div>',
            scope: {
                options: '='
            },
            link: link
        };
        return directive;

        function link($scope, element) {
            var chart = Highcharts.chart(element[0], $scope.options);

            $scope.$watch('options',function (newOptions) {
                if (newOptions) {
                    //Highcharts.chart(element[0], $scope.options); destruye chart actual y genera uno nuevo
                    chart.update($scope.options);//actualiza el chart especificado
                }
            }, true);

        }
    }

})();