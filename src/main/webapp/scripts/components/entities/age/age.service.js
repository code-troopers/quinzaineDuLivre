'use strict';

angular.module('quinzaineDuLivreApp')
    .factory('Age', function ($resource, DateUtils) {
        return $resource('api/ages/:id', {}, {
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
    })
    .factory('AllAges', function ($resource, DateUtils) {
        return $resource('api/public/ages/', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
