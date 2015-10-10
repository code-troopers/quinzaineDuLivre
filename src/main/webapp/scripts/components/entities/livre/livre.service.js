'use strict';

angular.module('quinzaineDuLivreApp')
    .factory('Livre', function ($resource, DateUtils) {
        return $resource('api/livres/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
