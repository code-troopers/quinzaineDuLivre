'use strict';

angular.module('quinzaineDuLivreApp')
    .factory('Editeur', function ($resource, DateUtils) {
        return $resource('api/editeurs/:id', {}, {
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
