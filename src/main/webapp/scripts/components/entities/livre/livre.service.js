'use strict';

angular.module('quinzaineDuLivreApp')
    .factory('Livre', function ($resource, DateUtils) {
        return $resource('api/livres/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    })
    .factory('AllLivres', function ($resource, DateUtils) {
        return $resource('api/public/livres/', {}, {
            'query': {method: 'GET', isArray: true}
        });
    })
    .factory('LivreFile', ['$resource', function ($resource) {
        return $resource('/api/upload', {},
            {
                'create': {method: 'POST'},
                'update': {method: 'PUT'},
                'delete': {method: 'DELETE'}
            });
    }])
    .factory('MainLogoFile', ['$resource', function ($resource) {
        return $resource('/api/upload/mainLogo', {},
            {
                'create': {method: 'POST'},
                'update': {method: 'PUT'},
                'delete': {method: 'DELETE'}
            });
    }]);
