(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('escribania', {
            parent: 'entity',
            url: '/escribania',
            data: {
                authorities: ['ROLE_USER','ROLE_ESCRIBANIA'],
                pageTitle: 'escribanosApp.escribania.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/escribania/escribanias.html',
                    controller: 'EscribaniaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('escribania');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('escribania-detail', {
            parent: 'escribania',
            url: '/escribania/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'escribanosApp.escribania.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/escribania/escribania-detail.html',
                    controller: 'EscribaniaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('escribania');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Escribania', function($stateParams, Escribania) {
                    return Escribania.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'escribania',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('escribania-detail.edit', {
            parent: 'escribania-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/escribania/escribania-dialog.html',
                    controller: 'EscribaniaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Escribania', function(Escribania) {
                            return Escribania.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('escribania.new', {
            parent: 'escribania',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/escribania/escribania-dialog.html',
                    controller: 'EscribaniaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                domicilio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('escribania', null, { reload: 'escribania' });
                }, function() {
                    $state.go('escribania');
                });
            }]
        })
        .state('escribania.edit', {
            parent: 'escribania',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/escribania/escribania-dialog.html',
                    controller: 'EscribaniaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Escribania', function(Escribania) {
                            return Escribania.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('escribania', null, { reload: 'escribania' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('escribania.delete', {
            parent: 'escribania',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/escribania/escribania-delete-dialog.html',
                    controller: 'EscribaniaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Escribania', function(Escribania) {
                            return Escribania.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('escribania', null, { reload: 'escribania' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
