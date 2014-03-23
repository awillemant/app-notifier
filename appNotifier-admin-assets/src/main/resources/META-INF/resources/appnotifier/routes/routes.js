define([ 'modules/appnotifierModule', 'controllers/ApplicationController', 'controllers/NotificationController' ], function(mainApp) {

	return mainApp.config(function($routeProvider) {

		$routeProvider.when('/', {
			controller : 'ApplicationController',
			templateUrl : 'appnotifier/templates/Applications.html'
		});

		$routeProvider.when('/notifications/:appUID', {
			controller : 'NotificationController',
			templateUrl : 'appnotifier/templates/Notifications.html'
		});

	});

});