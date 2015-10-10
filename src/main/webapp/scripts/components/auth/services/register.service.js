'use strict';

angular.module('quinzaineDuLivreApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


