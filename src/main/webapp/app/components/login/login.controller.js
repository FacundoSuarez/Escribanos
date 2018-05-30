(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', '$uibModalInstance', 'Principal', '$scope'];

    function LoginController ($rootScope, $state, $timeout, Auth, $uibModalInstance, Principal, $scope) {
        var vm = this;

        vm.authenticationError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;

        $timeout(function (){angular.element('#username').focus();});

        function cancel () {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
            $uibModalInstance.dismiss('cancel');
        }

        function login (event) {
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                $uibModalInstance.close();
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }
                

                $rootScope.$broadcast('authenticationSuccess');
                
                
                
               
                
                
                    Principal.identity().then(function(account) {
                        vm.account = account;
                        vm.isAuthenticated = Principal.isAuthenticated;
                        //console.log(account);
                       // console.log(account.authorities);
                        for (var i = 0; i < account.authorities.length; i++) {
                            if(account.authorities[i] === 'ROLE_ESCRIBANIA'){
                                $state.go('presentacin');
                            }else if(account.authorities[i] === 'ROLE_OPERADOR'){
                                $state.go('tramite');
                            }else{
                                $state.go('home');
                            }
                          //  console.log(account.authorities[i]);
                        }
                    });
                    
                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                }
                //$state.go('presentacion');
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function register () {
            $uibModalInstance.dismiss('cancel');
            $state.go('register');
        }

        function requestResetPassword () {
            $uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }
    }
})();
