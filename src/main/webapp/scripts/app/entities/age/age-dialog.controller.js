'use strict';

angular.module('quinzaineDuLivreApp').controller('AgeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Age',
        function($scope, $stateParams, $modalInstance, entity, Age) {

        $scope.age = entity;
        $scope.load = function(id) {
            Age.get({id : id}, function(result) {
                $scope.age = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('quinzaineDuLivreApp:ageUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.age.id != null) {
                Age.update($scope.age, onSaveFinished);
            } else {
                Age.save($scope.age, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
