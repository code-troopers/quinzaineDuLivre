'use strict';

angular.module('quinzaineDuLivreApp').controller('EditeurDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Editeur',
        function($scope, $stateParams, $modalInstance, entity, Editeur) {

        $scope.editeur = entity;
        $scope.load = function(id) {
            Editeur.get({id : id}, function(result) {
                $scope.editeur = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('quinzaineDuLivreApp:editeurUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.editeur.id != null) {
                Editeur.update($scope.editeur, onSaveFinished);
            } else {
                Editeur.save($scope.editeur, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
