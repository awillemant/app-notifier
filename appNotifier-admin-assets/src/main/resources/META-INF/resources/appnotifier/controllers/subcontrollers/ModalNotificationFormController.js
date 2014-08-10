define([ 'jquery', 'modules/appnotifierModule' ], function($, appnotifierModule) {

	appnotifierModule.value("ModalNotificationFormController", function($scope, $log, $modalInstance, notification, mode, appUID) {

		$scope.typesOfNotification = [ {
			label : "Info",
			value : "INFO"
		}, {
			label : "Warning",
			value : "WARNING"
		}, {
			label : "Error",
			value : "ERROR"
		} ];

		$scope.dateOptions = {
			'year-format' : "'yyyy'",
			'starting-day' : 1
		};

		if (mode == "create") {
			$scope.modalTitle = "Create a notification";
		} else if (mode == "edit") {
			$scope.modalTitle = "Edit a notification";
		}

		$scope.notification = notification;

		if ($scope.notification.type == undefined) {
			$log.debug("Initialization of date and time pickers");
			$scope.notification.type = "INFO";
			$scope.notification.enabled = true;
			$scope.notification.startDate = new Date();
			$scope.notification.startDate.setHours(12, 0, 0, 0);
			$scope.notification.endDate = new Date();
			$scope.notification.endDate.setHours(13, 0, 0, 0);
		}

		$scope.original = angular.copy(notification);

		$scope.save = function() {
			if (mode == "create") {
				$scope.notification.$saveWithAppUID({
					appUID : appUID
				}, function() {
					$modalInstance.close();
				});
			} else if (mode == "edit") {
				$scope.notification.$save(function() {
					$modalInstance.close();
				});
			}
		};

		$scope.close = function() {
			$modalInstance.dismiss();
		};

		$scope.hasChanged = function() {
			return !angular.equals($scope.notification, $scope.original);
		};

		$scope.badDateOrder = function() {
			var result = $scope.notification.dateDebut >= $scope.notification.dateFin;
			return result;

		};

	});

});