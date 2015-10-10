'use strict';

angular.module('quinzaineDuLivreApp')
    .controller('LivreController', function ($scope, Livre, ParseLinks) {
        $scope.livres = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Livre.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.livres.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 0;
            $scope.livres = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Livre.get({id: id}, function(result) {
                $scope.livre = result;
                $('#deleteLivreConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Livre.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteLivreConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.livre = {
                titre: null,
                sousTitre: null,
                auteurs: null,
                illustrateur: null,
                codeBarre: null,
                resume: null,
                commentaires: null,
                reserveHDV: null,
                coupCoeur: null,
                prix: null,
                urlImage: null,
                id: null
            };
        };
    });
