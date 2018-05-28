(function () {
    'use strict';

    angular
            .module('escribanosApp')
            .controller('PresentacionController', PresentacionController);

    PresentacionController.$inject = ['Presentacion', 'DataUtils'];

    function PresentacionController(Presentacion, DataUtils) {

        var vm = this;

        vm.presentacions = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        loadAll();

        function loadAll() {
            Presentacion.query(function (result) {
                vm.presentacions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
