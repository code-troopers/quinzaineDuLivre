'use strict';

angular.module('quinzaineDuLivreApp').controller('LivreUploadDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$timeout', '$resource',
        function ($scope, $stateParams, $modalInstance, $timeout, $resource) {

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

            var Files = $resource('/api/upload/');

            var model= { file: null, name: 'myfilename' }

            $scope.uploadFile = function () {

                $.ajax( {
                    url: '/api/upload',
                    type: 'POST',
                    file: model.file
                } );

                //upload({
                //    url: '/api/upload',
                //    method: 'POST',
                //    data: {
                //        file: $scope.myFile // a jqLite type="file" element, upload() will extract all the files from the input and put them into the FormData object before sending.
                //    }
                //}).then(
                //    function (response) {
                //        console.log(response.data); // will output whatever you choose to return from the server on a successful upload
                //    },
                //    function (response) {
                //        console.error(response); //  Will return if status code is above 200 and lower than 300, same as $http
                //    }
                //);
            }
        }]);
