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
                $scope.loaded = true;
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
        $scope.resetFilter = function () {
            $scope.filter = {};
        };

        function isFilterSetted(input) {
            return angular.isDefined(input) && input != null && input != "";
        }

        function isObjectValueDefined(field) {
            return angular.isDefined(field) && field != null;
        }

        $scope.filterFunction = function (element) {
            var matchAllFilters = true;
            if (isFilterSetted($scope.filter.categorie)) {
                matchAllFilters = false;
                if (isObjectValueDefined(element.categorie) && element.categorie.id == $scope.filter.categorie.id) {
                    matchAllFilters = true;
                } else {
                    return false;
                }
            }
            if (isFilterSetted($scope.filter.age)) {
                matchAllFilters = false;
                if (isObjectValueDefined(element.age) && element.age.id == $scope.filter.age.id) {
                    matchAllFilters = true;
                } else {
                    return false;
                }
            }
            if (isFilterSetted($scope.filter.coupCoeur)) {
                matchAllFilters = false;
                if (element.coupCoeur == true) {
                    matchAllFilters = true;
                } else {
                    return false;
                }
            }
            if (isFilterSetted($scope.filter.titre)) {
                matchAllFilters = false;
                if (isObjectValueDefined(element.titre) && element.titre.match(new RegExp($scope.filter.titre, "i"))) {
                    matchAllFilters = true;
                } else {
                    return false;
                }
            }
            if (isFilterSetted($scope.filter.editeur)) {
                matchAllFilters = false;
                if (isObjectValueDefined(element.editeur) && element.editeur.id == $scope.filter.editeur.id) {
                    matchAllFilters = true;
                } else {
                    return false;
                }
            }
            if (isFilterSetted($scope.filter.auteurs)) {
                matchAllFilters = false;
                if (isObjectValueDefined(element.auteurs) && element.auteurs.match(new RegExp($scope.filter.auteurs, "i"))) {
                    matchAllFilters = true;
                } else {
                    return false;
                }
            }
            if (isFilterSetted($scope.filter.illustrateur)) {
                matchAllFilters = false;
                if (isObjectValueDefined(element.illustrateur) && element.illustrateur.match(new RegExp($scope.illustrateur.auteurs, "i"))) {
                    matchAllFilters = true;
                } else {
                    return false;
                }
            }
            return matchAllFilters;
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
