define([ 'modules/appnotifierModule', 'controllers/subcontrollers/ModalApplicationFormController', 'services/ModalConfirmationService', 'resources/Application' ], function(appnotifierModule) {

	appnotifierModule.controller('ApplicationController', function($scope, $modal, $log, Application, ModalApplicationFormController, ModalConfirmationService) {

		$scope.applications = [];

		var refreshApplications = function() {
			$log.debug("Refreshing list of applications");
			$scope.futureApplications = Application.query().$promise;
			$scope.futureApplications.then(function(data) {
				$scope.applications = data;
			});
		};

		$scope.createApp = function() {
			$log.debug("Creation of a new application");
			openModal(new Application(), "Add an application");
		};

		$scope.editApp = function(currentApplication) {
			$log.debug("Editing application " + currentApplication.id);
			openModal(angular.copy(currentApplication), "Edit application");
		};

		$scope.deleteApp = function(currentApplication) {
			ModalConfirmationService.openModal("The application and all of its notifications will be erased definitely.<br/> Proceed anyway?", function() {
				$log.debug("Deleting application " + currentApplication.id);
				currentApplication.$delete(function() {
					refreshApplications();
				});
			}, function() {
				$log.debug("Cancelling application deletion");
			});
		};

		var openModal = function(applicationForForm, modalTitle) {

			var modalInstance = $modal.open({
				templateUrl : 'appnotifier/templates/modals/modalApplicationForm.html',
				controller : ModalApplicationFormController,
				resolve : {
					application : function() {
						return applicationForForm;
					},
					modalTitle : function() {
						return modalTitle;
					}
				}
			});

			modalInstance.result.then(function() {
				refreshApplications();
			});

		};

		refreshApplications();

	});

});