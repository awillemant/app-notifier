define([ 'angular' ], function(angular) {

	var appNotifierModule = angular.module('appnotifier', [ 'ngRoute', 'ngResource', 'ui.bootstrap' ]);

	appNotifierModule.factory('UrlRootPromise', function($http, $q) {
		var urlRoot = $q.defer();

		$http.get("rs/properties/urlRoot").success(function(data) {
			urlRoot.resolve(data);
		});

		return urlRoot.promise;
	});

	return appNotifierModule;
});