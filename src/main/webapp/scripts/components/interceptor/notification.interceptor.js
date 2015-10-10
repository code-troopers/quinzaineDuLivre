 'use strict';

angular.module('quinzaineDuLivreApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-quinzaineDuLivreApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-quinzaineDuLivreApp-params')});
                }
                return response;
            }
        };
    });
