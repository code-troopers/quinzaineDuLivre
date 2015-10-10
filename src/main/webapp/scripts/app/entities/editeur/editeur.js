'use strict';

angular.module('quinzaineDuLivreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('editeur', {
                parent: 'entity',
                url: '/editeurs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.editeur.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/editeur/editeurs.html',
                        controller: 'EditeurController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('editeur');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('editeur.detail', {
                parent: 'entity',
                url: '/editeur/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.editeur.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/editeur/editeur-detail.html',
                        controller: 'EditeurDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('editeur');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Editeur', function($stateParams, Editeur) {
                        return Editeur.get({id : $stateParams.id});
                    }]
                }
            })
            .state('editeur.new', {
                parent: 'editeur',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/editeur/editeur-dialog.html',
                        controller: 'EditeurDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nom: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('editeur', null, { reload: true });
                    }, function() {
                        $state.go('editeur');
                    })
                }]
            })
            .state('editeur.edit', {
                parent: 'editeur',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/editeur/editeur-dialog.html',
                        controller: 'EditeurDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Editeur', function(Editeur) {
                                return Editeur.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('editeur', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
