'use strict';

angular.module('quinzaineDuLivreApp').controller('CategorieDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Categorie',
        function($scope, $stateParams, $modalInstance, entity, Categorie) {

        $scope.categorie = entity;
        $scope.load = function(id) {
            Categorie.get({id : id}, function(result) {
                $scope.categorie = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('quinzaineDuLivreApp:categorieUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.categorie.id != null) {
                Categorie.update($scope.categorie, onSaveFinished);
            } else {
                Categorie.save($scope.categorie, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
