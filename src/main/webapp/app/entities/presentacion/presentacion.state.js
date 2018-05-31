(function () {
    'use strict';

    angular
            .module('escribanosApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('presentacion', {
                    parent: 'entity',
                    url: '/presentacion',
                    data: {
                        authorities: ['ROLE_USER','ROLE_ESCRIBANIA'],
                        pageTitle: 'escribanosApp.presentacion.home.title'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/presentacion/presentacions.html',
                            controller: 'PresentacionController',
                            controllerAs: 'vm'
                        }
                    },
                    resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('presentacion');
                                $translatePartialLoader.addPart('global');
                                return $translate.refresh();
                            }]
                    }
                })
                .state('presentacion-detail', {
                    parent: 'presentacion',
                    url: '/presentacion/{id}',
                    data: {
                        authorities: ['ROLE_USER'],
                        pageTitle: 'escribanosApp.presentacion.detail.title'
                    },
                    views: {
                        'content@': {
                            templateUrl: 'app/entities/presentacion/presentacion-detail.html',
                            controller: 'PresentacionDetailController',
                            controllerAs: 'vm'
                        }
                    },
                    resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('presentacion');
                                return $translate.refresh();
                            }],
                        entity: ['$stateParams', 'Presentacion', function ($stateParams, Presentacion) {
                                return Presentacion.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'presentacion',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('presentacion-detail.edit', {
                    parent: 'presentacion-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER','ROLE_ESCRIBANIA']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'app/entities/presentacion/presentacion-dialog.html',
                                controller: 'PresentacionDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Presentacion', function (Presentacion) {
                                            return Presentacion.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('presentacion.new', {
                    parent: 'presentacion',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER','ROLE_ESCRIBANIA']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'app/entities/presentacion/presentacion-dialog.html',
                                controller: 'PresentacionDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            cuitEscribano: null,
                                            nombre: null,
                                            apellido: null,
                                            id: null
                                        };
                                    },
                                    entityArchivosDetalle: function () {
                                        return {
                                            estado: null,
                                            id: null

                                        };
                                    },
                                    entityTramite: function () {
                                        return {
                                            fecha: null,
                                            fechaFin: null,
                                            observaciones: null,
                                            id: null
                                        };
                                    },
                                    entityArchivos: function () {
                                        return {
                                            archivo: null,
                                            archivoContentType: null,
                                            id: null
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('presentacion', null, {reload: 'presentacion'});
                            }, function () {
                                $state.go('presentacion');
                            });
                        }]
                })
                .state('presentacion.edit', {
                    parent: 'presentacion',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER','ROLE_ESCRIBANIA']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'app/entities/presentacion/presentacion-dialog.html',
                                controller: 'PresentacionDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Presentacion', function (Presentacion) {
                                            return Presentacion.get({id: $stateParams.id}).$promise;
                                        }],
                                    entityArchivosDetalle: ['ArchivosDetalle', function (ArchivosDetalle) {
                                            return ArchivosDetalle.get({id: $stateParams.id}).$promise;
                                        }],
                                    entityTramite: ['Tramite', function (Tramite){
                                            return Tramite.get({id: $stateParams.id}).$promise;
                                        }],
                                    entityArchivos: ['Archivos', function (Archivos){
                                            return Archivos.get({id: $stateParams.id}).$promise;
                                    }]
                                    

                                }
                            }).result.then(function () {
                                $state.go('presentacion', null, {reload: 'presentacion'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('presentacion.delete', {
                    parent: 'presentacion',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER','ROLE_ESCRIBANIA']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'app/entities/presentacion/presentacion-delete-dialog.html',
                                controller: 'PresentacionDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Presentacion', function (Presentacion) {
                                            return Presentacion.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('presentacion', null, {reload: 'presentacion'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
