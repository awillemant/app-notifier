define([ 'modules/appnotifierModule' ], function(appnotifierModule) {

	appnotifierModule.service("ModalConfirmationService", function($modal) {

		this.openModal = function(confirmationText, callbackIfYes, callbackIfNo) {
			var modalInstance = $modal.open({
				template : '<div class="modal-body"><p class="lead">' + confirmationText + '</p></div>' + '<div class="modal-footer">' + '<button type="button" class="btn btn-success" x-ng-click="yes()">Yes</button>' + '<button type="button" class="btn btn-danger" x-ng-click="no()" >No</button>' + '</div>',
				controller : modalConfirmationController,
				resolve : {
					confirmationText : function() {
						return confirmationText;
					}
				}
			});

			modalInstance.result.then(function(result) {
				if (result) {
					callIfExists(callbackIfYes);
				} else {
					callIfExists(callbackIfNo);
				}
			});
		};

		var modalConfirmationController = function($scope, $modalInstance, confirmationText) {

			$scope.confirmationText = confirmationText;

			$scope.yes = function() {
				$modalInstance.close(true);
			};

			$scope.no = function() {
				$modalInstance.close(false);
			};
		};

		var callIfExists = function(callback) {
			if (callback) {
				callback();
			}
		};

	});

});