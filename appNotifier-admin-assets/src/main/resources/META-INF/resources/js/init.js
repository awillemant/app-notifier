require.config({
	baseUrl : 'appnotifier',
	'paths' : {
		'bootstrap' : '../webjars/bootstrap/3.1.1/js/bootstrap.min',
		'ui-bootstrap' : '../webjars/angular-ui-bootstrap/0.10.0/ui-bootstrap-tpls.min',
		'angular' : '../webjars/angularjs/1.2.14/angular.min',
		'angular-route' : '../webjars/angularjs/1.2.14/angular-route.min',
		'angular-resource' : '../webjars/angularjs/1.2.14/angular-resource.min',
		'angular-locale' : '../webjars/angularjs/1.2.14/i18n/angular-locale_fr-fr',
		'jquery' : '../webjars/jquery/1.11.0/jquery.min'
	},
	'shim' : {
		'angular' : {
			'deps' : [ 'jquery' ],
			'exports' : 'angular'
		},
		'angular-route' : {
			'deps' : [ 'angular' ]
		},
		'angular-resource' : {
			'deps' : [ 'angular' ]
		},
		'angular-locale' : {
			'deps' : [ 'angular' ]
		},
		'bootstrap' : {
			'deps' : [ 'jquery' ]
		},
		'ui-bootstrap' : {
			'deps' : [ 'angular', 'angular-locale' ]
		}

	},

});

require([ 'jquery', 'angular', 'bootstrap', 'angular-route', 'angular-resource', 'ui-bootstrap', 'routes/routes' ], function($, angular) {
	$(function() {
		angular.bootstrap(document, [ 'appnotifier' ]);
	});
});