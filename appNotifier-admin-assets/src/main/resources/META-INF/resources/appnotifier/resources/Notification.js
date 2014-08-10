define([ 'modules/appnotifierModule' ], function(appnotifierModule) {
	appnotifierModule.factory('Notification', function($resource) {
		return $resource('rs/notifications/:id', {
			id : '@id'
		}, {
			queryForApp : {
				method : 'GET',
				url : 'rs/notifications/app/:appUID',
				params : {
					appUID : '@appUID'
				},
				isArray : true
			},
			toggleEnabled : {
				method : 'POST',
				url : 'rs/notifications/enabled/:id',
				params : {
					id : '@id'
				},
				isArray : false
			},
			saveWithAppUID : {
				method : 'POST',
				url : 'rs/notifications/app/:appUID',
				params : {
					appUID : '@appUID'
				},
				isArray : false
			}
		});
	});
});