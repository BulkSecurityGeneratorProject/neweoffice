(function() {
    'use strict';

    angular
        .module('neweofficeApp')
        .controller('FiletosendDetailController', FiletosendDetailController);

    FiletosendDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Filetosend'];

    function FiletosendDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Filetosend) {
        var vm = this;

        vm.filetosend = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('neweofficeApp:filetosendUpdate', function(event, result) {
            vm.filetosend = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
