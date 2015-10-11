'use strict';

angular.module('quinzaineDuLivreApp').controller('LivreUploadDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$timeout', '$resource', 'LivreFile',
        function ($scope, $stateParams, $modalInstance, $timeout, $resource, LivreFile) {

            $scope.loading = false;
            $scope.importSucces = false;
            $scope.importFail = false;
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
                if ($scope.file != null && $scope.file.base64 != null) {
                    $scope.loadingStarted = true;
                    $scope.loading = true;
                    var newFile = new LivreFile();
                    newFile.name = "fileName";
                    newFile.content = $scope.file.base64;
                    newFile.$save().then(
                        function (response) {
                            $scope.loading = false;
                            $scope.importSucces = true;
                            $scope.loadingEnded = true;
                            $scope.file = null;
                            //console.log(response.data); // will output whatever you choose to return from the server on a successful upload
                        },
                        function (response) {
                            $scope.loading = false;
                            $scope.importFail = true;
                            $scope.file = null;
                            //console.error(response); //  Will return if status code is above 200 and lower than 300, same as $http
                        }
                    )
                }
            };
        }]);
