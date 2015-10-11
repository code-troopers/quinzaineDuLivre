'use strict';

angular.module('quinzaineDuLivreApp').controller('LivreLogoUploadDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$timeout', '$resource', 'MainLogoFile',
        function ($scope, $stateParams, $modalInstance, $timeout, $resource, MainLogoFile) {

            $scope.loading = false;
            $scope.importSucces = false;
            $scope.importFail = false;

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };

            $scope.uploadFile = function () {
                if ($scope.file != null && $scope.file.base64 != null) {
                    $scope.loadingStarted = true;
                    $scope.loading = true;
                    var newFile = new MainLogoFile();
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
