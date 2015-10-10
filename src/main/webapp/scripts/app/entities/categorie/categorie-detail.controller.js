'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('CategorieDetailController', function ($scope, $rootScope, $stateParams, entity, Categorie) {
        $scope.categorie = entity;
        $scope.load = function (id) {
            Categorie.get({id: id}, function(result) {
                $scope.categorie = result;
            });
        };
        $rootScope.$on('quinzaineDuLivreApp:categorieUpdate', function(event, result) {
            $scope.categorie = result;
        });
    });
