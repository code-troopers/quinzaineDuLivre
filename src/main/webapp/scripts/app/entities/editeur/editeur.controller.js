'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('EditeurController', function ($scope, Editeur) {
        $scope.editeurs = [];
        $scope.loadAll = function() {
            Editeur.query(function(result) {
               $scope.editeurs = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Editeur.get({id: id}, function(result) {
                $scope.editeur = result;
                $('#deleteEditeurConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Editeur.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEditeurConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.editeur = {
                nom: null,
                id: null
            };
        };
    });
