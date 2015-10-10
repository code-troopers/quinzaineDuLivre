'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('LivreDetailController', function ($scope, $rootScope, $stateParams, entity, Livre, Age, Categorie, Editeur) {
        $scope.livre = entity;
        $scope.load = function (id) {
            Livre.get({id: id}, function(result) {
                $scope.livre = result;
            });
        };
        $rootScope.$on('quinzaineDuLivreApp:livreUpdate', function(event, result) {
            $scope.livre = result;
        });
    });
