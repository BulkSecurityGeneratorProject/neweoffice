(function() {
    'use strict';

    angular
        .module('neweofficeApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('filetosend', {
            parent: 'entity',
            url: '/filetosend',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'neweofficeApp.filetosend.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/filetosend/filetosends.html',
                    controller: 'FiletosendController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('filetosend');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('filetosend-detail', {
            parent: 'filetosend',
            url: '/filetosend/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'neweofficeApp.filetosend.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/filetosend/filetosend-detail.html',
                    controller: 'FiletosendDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('filetosend');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Filetosend', function($stateParams, Filetosend) {
                    return Filetosend.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'filetosend',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('filetosend-detail.edit', {
            parent: 'filetosend-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filetosend/filetosend-dialog.html',
                    controller: 'FiletosendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Filetosend', function(Filetosend) {
                            return Filetosend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('filetosend.new', {
            parent: 'filetosend',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filetosend/filetosend-dialog.html',
                    controller: 'FiletosendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                filename: null,
                                subject: null,
                                filetoupload: null,
                                filetouploadContentType: null,
                                recipientemailid: null,
                                recipientname: null,
                                addsigner: null,
                                addcc: null,
                                addbulk: null,
                                currentlocation: null,
                                destinationlocation: null,
                                fileinitiatedon: null,
                                fileclosedon: null,
                                dispatchno: null,
                                status: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('filetosend', null, { reload: 'filetosend' });
                }, function() {
                    $state.go('filetosend');
                });
            }]
        })
        .state('filetosend.edit', {
            parent: 'filetosend',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filetosend/filetosend-dialog.html',
                    controller: 'FiletosendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Filetosend', function(Filetosend) {
                            return Filetosend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('filetosend', null, { reload: 'filetosend' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('filetosend.delete', {
            parent: 'filetosend',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/filetosend/filetosend-delete-dialog.html',
                    controller: 'FiletosendDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Filetosend', function(Filetosend) {
                            return Filetosend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('filetosend', null, { reload: 'filetosend' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
