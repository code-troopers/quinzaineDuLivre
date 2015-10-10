'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    })
    .directive('filtreTable', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/main/filter.html'
        };
    })
    .directive('tableListe', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/main/table.html'
        };
    });
