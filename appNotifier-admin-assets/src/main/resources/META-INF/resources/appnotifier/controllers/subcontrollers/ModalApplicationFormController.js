define([ 'modules/appnotifierModule' ], function(appnotifierModule) {

	appnotifierModule.value("ModalApplicationFormController", function($scope, $modalInstance, application, modalTitle) {

		$scope.application = application;

		$scope.original = angular.copy(application);

		$scope.modalTitle = modalTitle;

		$scope.save = function() {
			$scope.application.$save(function() {
				$modalInstance.close();
			});
		};

		$scope.close = function() {
			$modalInstance.dismiss();
		};

		$scope.hasChanged = function() {
			return !angular.equals($scope.application, $scope.original);
		};

	});

});