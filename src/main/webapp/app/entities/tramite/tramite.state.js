(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tramite', {
            parent: 'entity',
            url: '/tramite',
            data: {
                authorities: ['ROLE_USER', 'ROLE_OPERADOR'],
                pageTitle: 'escribanosApp.tramite.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tramite/tramites.html',
                    controller: 'TramiteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tramite');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tramite-detail', {
            parent: 'tramite',
            url: '/tramite/{id}',
            data: {
                authorities: ['ROLE_USER', 'ROLE_OPERADOR'],
                pageTitle: 'escribanosApp.tramite.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tramite/tramite-detail.html',
                    controller: 'TramiteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tramite');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tramite', function($stateParams, Tramite) {
                    return Tramite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tramite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tramite-detail.edit', {
            parent: 'tramite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_OPERADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tramite/tramite-dialog.html',
                    controller: 'TramiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tramite', function(Tramite) {
                            return Tramite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tramite.new', {
            parent: 'tramite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER', 'ROLE_OPERADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tramite/tramite-dialog.html',
                    controller: 'TramiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                fechaFin: null,
                                observaciones: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tramite', null, { reload: 'tramite' });
                }, function() {
                    $state.go('tramite');
                });
            }]
        })
        .state('tramite.edit', {
            parent: 'tramite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER', 'ROLE_OPERADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tramite/tramite-dialog.html',
                    controller: 'TramiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tramite', function(Tramite) {
                            return Tramite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tramite', null, { reload: 'tramite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tramite.delete', {
            parent: 'tramite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER', 'ROLE_OPERADOR']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tramite/tramite-delete-dialog.html',
                    controller: 'TramiteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tramite', function(Tramite) {
                            return Tramite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tramite', null, { reload: 'tramite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
