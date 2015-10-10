'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('MainController', function ($scope, Principal) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
    })
    .controller('TableListeController', function ($scope, Principal, AllLivres, AllAges, AllCategories, AllEditeurs, ParseLinks) {
        AllCategories.query({}, function (result) {
            $scope.categories = result;
        });
        AllAges.query({}, function (result) {
            $scope.ages = result;
        });
        AllEditeurs.query({}, function (result) {
            $scope.editeurs = result;
        });

        $scope.filter = {};
        $scope.livres = [];
        $scope.page = 0;
        $scope.loadAll = function () {
            AllLivres.query({page: $scope.page, size: 20}, function (result) {
                $scope.livres = result;
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
        };

        $scope.filterFunction = function (element) {
            if (angular.isDefined(element.titre) && element.titre != null && element.titre.match(new RegExp($scope.filter.titre, "i"))) {
                return true;
            }
            return false;
        };
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
