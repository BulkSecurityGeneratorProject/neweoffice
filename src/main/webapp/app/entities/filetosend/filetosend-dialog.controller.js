(function() {
    'use strict';

    angular
        .module('neweofficeApp')
        .controller('FiletosendDialogController', FiletosendDialogController);

    FiletosendDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Filetosend'];

    function FiletosendDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Filetosend) {
        var vm = this;

        vm.filetosend = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.filetosend.id !== null) {
                Filetosend.update(vm.filetosend, onSaveSuccess, onSaveError);
            } else {
                Filetosend.save(vm.filetosend, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('neweofficeApp:filetosendUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFiletoupload = function ($file, filetosend) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        filetosend.filetoupload = base64Data;
                        filetosend.filetouploadContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.fileinitiatedon = false;
        vm.datePickerOpenStatus.fileclosedon = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
