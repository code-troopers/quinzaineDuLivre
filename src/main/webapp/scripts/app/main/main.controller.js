'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    })
    .controller('TableListeController', function ($scope, Principal, AllLivres, ParseLinks) {
        $scope.filter = {};
        $scope.livres = [];
        $scope.page = 0;
        $scope.loadAll = function () {
            AllLivres.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.livres.push(result[i]);
                }
            });
        };
        $scope.reset = function () {
            $scope.page = 0;
            $scope.livres = [];
            $scope.loadAll();
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
        //    probably loadAll() here
        }
    })
    .directive('booksFilter', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/main/filter.html',
            controller: 'TableListeController'
        };
    })
    .directive('tableListe', function () {
        return {
            restrict: 'E',
            templateUrl: 'scripts/app/main/table.html',
            controller: 'TableListeController'
        };
    });
