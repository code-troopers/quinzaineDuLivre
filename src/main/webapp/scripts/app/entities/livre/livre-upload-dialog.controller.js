'use strict';

angular.module('quinzaineDuLivreApp').controller('LivreUploadDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$timeout', '$resource', 'LivreFile',
        function ($scope, $stateParams, $modalInstance, $timeout, $resource, LivreFile) {

            var onSaveFinished = function (result) {
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.livre.id != null) {
                    Livre.update($scope.livre, onSaveFinished);
                } else {
                    Livre.save($scope.livre, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };

            $scope.uploadFile = function () {

                var newFile = new LivreFile();
                newFile.name = "aaaa";
                newFile.content = $scope.file.base64;
                newFile.$save();
            }
            //FIME show result
        }]);
