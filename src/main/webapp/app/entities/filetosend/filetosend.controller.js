(function() {
    'use strict';

    angular
        .module('neweofficeApp')
        .controller('FiletosendController', FiletosendController);

    FiletosendController.$inject = ['$scope', '$state', 'DataUtils', 'Filetosend'];

    function FiletosendController ($scope, $state, DataUtils, Filetosend) {
        var vm = this;

        vm.filetosends = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Filetosend.query(function(result) {
                vm.filetosends = result;
                vm.searchQuery = null;
            });
        }
    }
})();
