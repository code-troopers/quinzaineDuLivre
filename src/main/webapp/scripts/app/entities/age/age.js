'use strict';

angular.module('quinzaineDuLivreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('age', {
                parent: 'entity',
                url: '/ages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.age.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/age/ages.html',
                        controller: 'AgeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('age');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('age.detail', {
                parent: 'entity',
                url: '/age/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.age.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/age/age-detail.html',
                        controller: 'AgeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('age');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Age', function($stateParams, Age) {
                        return Age.get({id : $stateParams.id});
                    }]
                }
            })
            .state('age.new', {
                parent: 'age',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/age/age-dialog.html',
                        controller: 'AgeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    libelle: null,
                                    aPartirDe: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('age', null, { reload: true });
                    }, function() {
                        $state.go('age');
                    })
                }]
            })
            .state('age.edit', {
                parent: 'age',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/age/age-dialog.html',
                        controller: 'AgeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Age', function(Age) {
                                return Age.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('age', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
