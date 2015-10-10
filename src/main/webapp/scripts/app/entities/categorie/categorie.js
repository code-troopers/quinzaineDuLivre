'use strict';

angular.module('quinzaineDuLivreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('categorie', {
                parent: 'entity',
                url: '/categories',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.categorie.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/categorie/categories.html',
                        controller: 'CategorieController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('categorie');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('categorie.detail', {
                parent: 'entity',
                url: '/categorie/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.categorie.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/categorie/categorie-detail.html',
                        controller: 'CategorieDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('categorie');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Categorie', function($stateParams, Categorie) {
                        return Categorie.get({id : $stateParams.id});
                    }]
                }
            })
            .state('categorie.new', {
                parent: 'categorie',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/categorie/categorie-dialog.html',
                        controller: 'CategorieDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    libelle: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('categorie', null, { reload: true });
                    }, function() {
                        $state.go('categorie');
                    })
                }]
            })
            .state('categorie.edit', {
                parent: 'categorie',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/categorie/categorie-dialog.html',
                        controller: 'CategorieDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Categorie', function(Categorie) {
                                return Categorie.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('categorie', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
