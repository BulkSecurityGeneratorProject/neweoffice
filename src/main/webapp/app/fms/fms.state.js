(function() {
    'use strict';

    angular
        .module('neweofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('fms', {
            parent: 'app',
            url: '/fms',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/fms/fms.html',
                    controller: 'FmsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
