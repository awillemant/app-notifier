define([ 'modules/appnotifierModule', 'controllers/subcontrollers/ModalNotificationFormController', 'controllers/subcontrollers/ModalIntegrationCodeController', 'resources/Notification', 'resources/Application', 'services/ModalConfirmationService' ], function(appnotifierModule) {

	appnotifierModule.controller('NotificationController', function($scope, $modal, $log, $routeParams, Notification, Application, ModalNotificationFormController, ModalIntegrationCodeController, ModalConfirmationService) {

		$scope.notifications = [];

		Application.get({
			id : $routeParams.appUID
		}, function(data) {
			$scope.applicationName = data.nom;
			$scope.applicationUrl = data.url;
		});

		var refreshNotifications = function() {
			var appUID = $routeParams.appUID;
			$log.debug("Rafraîchissement de la liste des notifications pour l'application " + appUID);

			$scope.futureNotifications = Notification.queryForApp({
				appUID : appUID
			}).$promise;

			$scope.futureNotifications.then(function(data) {
				$scope.notifications = data;
			});
		};

		$scope.toggleActif = function(notifIdx) {
			var id = $scope.notifications[notifIdx].id;
			$log.debug("Changement de l'état de la notification " + id);
			var futureNotif = Notification.toggleActif({
				id : id
			}).$promise;

			futureNotif.then(function(data) {
				$scope.notifications[notifIdx] = data;
			});
		};

		$scope.createNotif = function() {
			$log.debug("Création d'une nouvelle notification");
			openModal(new Notification(), "create");
		};

		$scope.editNotif = function(currentNotification) {
			$log.debug("Edition de la notification " + currentNotification.id);
			openModal(angular.copy(currentNotification), "edit");
		};

		$scope.deleteNotif = function(currentNotification) {
			ModalConfirmationService.openModal("La notification va être supprimée définitivement.<br/> Confirmer la suppression ?", function() {
				$log.debug("Suppression de la notification " + currentNotification.id);
				currentNotification.$delete(function() {
					$log.debug("Suppression réussie");
					refreshNotifications();
				});
			}, function() {
				$log.debug("Annulation de la suppression de notification");
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