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
			toggleActif : {
				method : 'POST',
				url : 'rs/notifications/actif/:id',
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