'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('AgeDetailController', function ($scope, $rootScope, $stateParams, entity, Age) {
        $scope.age = entity;
        $scope.load = function (id) {
            Age.get({id: id}, function(result) {
                $scope.age = result;
            });
        };
        $rootScope.$on('quinzaineDuLivreApp:ageUpdate', function(event, result) {
            $scope.age = result;
        });
    });
