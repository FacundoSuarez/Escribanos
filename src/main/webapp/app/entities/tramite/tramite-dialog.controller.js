(function() {
    'use strict';

    angular
        .module('escribanosApp')
        .controller('TramiteDialogController', TramiteDialogController);

    TramiteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tramite', 'Archivos', 'ArchivosDetalle', 'Operador'];

    function TramiteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tramite, Archivos, ArchivosDetalle, Operador) {
        var vm = this;

        vm.tramite = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.archivos = Archivos.query();
        vm.archivosdetalles = ArchivosDetalle.query();
        vm.operadors = Operador.query();
        
        
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tramite.id !== null) {
                Tramite.update(vm.tramite, onSaveSuccess, onSaveError);
            } else {
                Tramite.save(vm.tramite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('escribanosApp:tramiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecha = false;
        vm.datePickerOpenStatus.fechaFin = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
