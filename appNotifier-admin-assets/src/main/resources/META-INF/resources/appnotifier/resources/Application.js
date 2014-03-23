define([ 'modules/appnotifierModule' ], function(appnotifierModule) {
	appnotifierModule.factory('Application', function($resource) {
		return $resource('rs/applications/:id', {
			id : '@id'
		});
	});
});