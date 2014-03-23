define([ 'modules/appnotifierModule', 'controllers/subcontrollers/ModalApplicationFormController', 'services/ModalConfirmationService', 'resources/Application' ], function(appnotifierModule) {

	appnotifierModule.controller('ApplicationController', function($scope, $modal, $log, Application, ModalApplicationFormController, ModalConfirmationService) {

		$scope.applications = [];

		var refreshApplications = function() {
			$log.debug("Rafraîchissement de la liste des applications");
			$scope.futureApplications = Application.query().$promise;
			$scope.futureApplications.then(function(data) {
				$scope.applications = data;
			});
		};

		$scope.createApp = function() {
			$log.debug("Création d'une nouvelle application");
			openModal(new Application(), "Créer une application");
		};

		$scope.editApp = function(currentApplication) {
			$log.debug("Edition de l'application " + currentApplication.id);
			openModal(angular.copy(currentApplication), "Editer une application");
		};

		$scope.deleteApp = function(currentApplication) {
			ModalConfirmationService.openModal("L'application ainsi que toutes ses notifications vont être supprimées définitivement.<br/> Confirmer la suppression ?", function() {
				$log.debug("Suppression de l'application " + currentApplication.id);
				currentApplication.$delete(function() {
					refreshApplications();
				});
			}, function() {
				$log.debug("Annulation de la suppression d'application");
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