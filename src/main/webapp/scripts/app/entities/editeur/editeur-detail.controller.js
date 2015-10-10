'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('EditeurDetailController', function ($scope, $rootScope, $stateParams, entity, Editeur) {
        $scope.editeur = entity;
        $scope.load = function (id) {
            Editeur.get({id: id}, function(result) {
                $scope.editeur = result;
            });
        };
        $rootScope.$on('quinzaineDuLivreApp:editeurUpdate', function(event, result) {
            $scope.editeur = result;
        });
    });
