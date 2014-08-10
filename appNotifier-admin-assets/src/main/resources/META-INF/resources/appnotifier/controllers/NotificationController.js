define([ 'modules/appnotifierModule', 'controllers/subcontrollers/ModalNotificationFormController', 'controllers/subcontrollers/ModalIntegrationCodeController', 'resources/Notification', 'resources/Application', 'services/ModalConfirmationService' ], function(appnotifierModule) {

	appnotifierModule.controller('NotificationController', function($scope, $modal, $log, $routeParams, Notification, Application, ModalNotificationFormController, ModalIntegrationCodeController, ModalConfirmationService) {

		$scope.notifications = [];

		Application.get({
			id : $routeParams.appUID
		}, function(data) {
			$scope.applicationName = data.name;
			$scope.applicationUrl = data.url;
		});

		var refreshNotifications = function() {
			var appUID = $routeParams.appUID;
			$log.debug("Refreshing list of notifications for application " + appUID);

			$scope.futureNotifications = Notification.queryForApp({
				appUID : appUID
			}).$promise;

			$scope.futureNotifications.then(function(data) {
				$scope.notifications = data;
			});
		};

		$scope.toggleEnabled = function(notifIdx) {
			var id = $scope.notifications[notifIdx].id;
			$log.debug("Changing state for notification " + id);
			var futureNotif = Notification.toggleEnabled({
				id : id
			}).$promise;

			futureNotif.then(function(data) {
				$scope.notifications[notifIdx] = data;
			});
		};

		$scope.createNotif = function() {
			$log.debug("Creation of a new notification");
			openModal(new Notification(), "create");
		};

		$scope.editNotif = function(currentNotification) {
			$log.debug("Editing notification " + currentNotification.id);
			openModal(angular.copy(currentNotification), "edit");
		};

		$scope.deleteNotif = function(currentNotification) {
			ModalConfirmationService.openModal("The notification will be erased definitely.<br/> Proceed anyway?", function() {
				$log.debug("Deleting notification " + currentNotification.id);
				currentNotification.$delete(function() {
					$log.debug("Deletion ok");
					refreshNotifications();
				});
			}, function() {
				$log.debug("Cancelling deletion");
			});
		};

		$scope.showIntegrationCode = function() {
			$modal.open({
				templateUrl : 'appnotifier/templates/modals/modalIntegrationCode.html',
				controller : ModalIntegrationCodeController,
				windowClass : 'largeModal',
				resolve : {
					appUID : function() {
						return $routeParams.appUID;
					}
				}
			});
		};

		var openModal = function(notificationForForm, mode) {
			var modalInstance = $modal.open({
				templateUrl : 'appnotifier/templates/modals/modalNotificationForm.html',
				controller : ModalNotificationFormController,
				resolve : {
					notification : function() {
						return notificationForForm;
					},
					mode : function() {
						return mode;
					},
					appUID : function() {
						return $routeParams.appUID;
					}
				}
			});
			modalInstance.result.then(function() {
				refreshNotifications();
			});

		};

		refreshNotifications();
	});

	appnotifierModule.filter('truncate', function() {
		return function(text, length, end) {
			if (isNaN(length))
				length = 10;

			if (end === undefined)
				end = "...";

			if (text.length <= length || text.length - end.length <= length) {
				return text;
			} else {
				return String(text).substring(0, length - end.length) + end;
			}

		};
	});

});