(function() {
    'use strict';
    angular
        .module('neweofficeApp')
        .factory('Filetosend', Filetosend);

    Filetosend.$inject = ['$resource', 'DateUtils'];

    function Filetosend ($resource, DateUtils) {
        var resourceUrl =  'api/filetosends/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fileinitiatedon = DateUtils.convertLocalDateFromServer(data.fileinitiatedon);
                        data.fileclosedon = DateUtils.convertLocalDateFromServer(data.fileclosedon);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fileinitiatedon = DateUtils.convertLocalDateToServer(copy.fileinitiatedon);
                    copy.fileclosedon = DateUtils.convertLocalDateToServer(copy.fileclosedon);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fileinitiatedon = DateUtils.convertLocalDateToServer(copy.fileinitiatedon);
                    copy.fileclosedon = DateUtils.convertLocalDateToServer(copy.fileclosedon);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
