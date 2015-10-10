'use strict';

angular.module('quinzaineDuLivreApp').controller('LivreDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Livre', 'Age', 'Categorie', 'Editeur',
        function($scope, $stateParams, $modalInstance, entity, Livre, Age, Categorie, Editeur) {

        $scope.livre = entity;
        $scope.ages = Age.query();
        $scope.categories = Categorie.query();
        $scope.editeurs = Editeur.query();
        $scope.load = function(id) {
            Livre.get({id : id}, function(result) {
                $scope.livre = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('quinzaineDuLivreApp:livreUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.livre.id != null) {
                Livre.update($scope.livre, onSaveFinished);
            } else {
                Livre.save($scope.livre, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
