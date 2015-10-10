'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('CategorieController', function ($scope, Categorie) {
        $scope.categories = [];
        $scope.loadAll = function() {
            Categorie.query(function(result) {
               $scope.categories = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Categorie.get({id: id}, function(result) {
                $scope.categorie = result;
                $('#deleteCategorieConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Categorie.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCategorieConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.categorie = {
                libelle: null,
                id: null
            };
        };
    });
