'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('AgeController', function ($scope, Age) {
        $scope.ages = [];
        $scope.loadAll = function() {
            Age.query(function(result) {
               $scope.ages = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Age.get({id: id}, function(result) {
                $scope.age = result;
                $('#deleteAgeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Age.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAgeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.age = {
                libelle: null,
                aPartirDe: null,
                id: null
            };
        };
    });
