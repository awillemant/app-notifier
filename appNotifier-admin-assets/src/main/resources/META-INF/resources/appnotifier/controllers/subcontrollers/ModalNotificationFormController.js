define([ 'jquery', 'modules/appnotifierModule' ], function($, appnotifierModule) {

	appnotifierModule.value("ModalNotificationFormController", function($scope, $log, $modalInstance, notification, mode, appUID) {

		$scope.typesOfNotification = [ {
			label : "Message d'information",
			value : "INFO"
		}, {
			label : "Message d'avertissement",
			value : "WARNING"
		}, {
			label : "Message d'erreur",
			value : "ERROR"
		} ];

		$scope.dateOptions = {
			'year-format' : "'yyyy'",
			'starting-day' : 1
		};

		if (mode == "create") {
			$scope.modalTitle = "Créer une notification";
		} else if (mode == "edit") {
			$scope.modalTitle = "Editer une notification";
		}

		$scope.notification = notification;

		if ($scope.notification.type == undefined) {
			$log.debug("Initialisation des date et time pickers");
			$scope.notification.type = "INFO";
			$scope.notification.dateDebut = new Date();
			$scope.notification.dateDebut.setHours(12, 0, 0, 0);
			$scope.notification.dateFin = new Date();
			$scope.notification.dateFin.setHours(13, 0, 0, 0);
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