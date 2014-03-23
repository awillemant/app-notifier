define([ 'modules/appnotifierModule' ], function(appnotifierModule) {

	appnotifierModule.value("ModalIntegrationCodeController", function($scope, $log, $modalInstance, appUID, UrlRootPromise) {

		$scope.appUID = appUID;

		UrlRootPromise.then(function(data) {
			$scope.urlRoot = data;
		});

		$scope.importStyles = true;

		$scope.close = function() {
			$modalInstance.dismiss();
		};

	});

	appnotifierModule.filter('undefined', function() {
		return function(input) {
			return input ? '"' + input + '"' : 'undefined';
		};
	});

	appnotifierModule.filter('zero', function() {
		return function(input) {
			return isNaN(input) || input == "" ? '0' : input;
		};
	});

});