(function() {
    'use strict';

    angular
        .module('neweofficeApp')
        .controller('FiletosendDeleteController',FiletosendDeleteController);

    FiletosendDeleteController.$inject = ['$uibModalInstance', 'entity', 'Filetosend'];

    function FiletosendDeleteController($uibModalInstance, entity, Filetosend) {
        var vm = this;

        vm.filetosend = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Filetosend.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
