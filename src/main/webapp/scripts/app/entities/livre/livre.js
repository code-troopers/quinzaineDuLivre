'use strict';

angular.module('quinzaineDuLivreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('livre', {
                parent: 'entity',
                url: '/livres',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.livre.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/livre/livres.html',
                        controller: 'LivreController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('livre');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('livre.detail', {
                parent: 'entity',
                url: '/livre/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'quinzaineDuLivreApp.livre.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/livre/livre-detail.html',
                        controller: 'LivreDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('livre');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Livre', function($stateParams, Livre) {
                        return Livre.get({id : $stateParams.id});
                    }]
                }
            })
            .state('livre.new', {
                parent: 'livre',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/livre/livre-dialog.html',
                        controller: 'LivreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
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
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('livre', null, { reload: true });
                    }, function() {
                        $state.go('livre');
                    })
                }]
            })
            .state('livre.edit', {
                parent: 'livre',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/livre/livre-dialog.html',
                        controller: 'LivreDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Livre', function(Livre) {
                                return Livre.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('livre', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('livre.upload', {
                parent: 'livre',
                url: '/upload',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/livre/livre-upload-dialog.html',
                        controller: 'LivreUploadDialogController',
                        size: 'lg'
                    }).result.then(function(result) {
                        $state.go('livre', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('livre.uploadLogo', {
                parent: 'livre',
                url: '/upload/logo',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/livre/livre-logo-upload-dialog.html',
                        controller: 'LivreLogoUploadDialogController',
                        size: 'lg'
                    }).result.then(function(result) {
                        $state.go('livre', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
