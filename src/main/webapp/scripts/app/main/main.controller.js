'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    })
    .controller('TableListeController', function ($scope, Principal, Livre, ParseLinks) {
        $scope.livres = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Livre.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.livres.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.livres = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();
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
            templateUrl: 'scripts/app/main/table.html',
            controller: 'TableListeController'
        };
    });
