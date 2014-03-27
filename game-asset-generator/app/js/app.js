'use strict';

// Declare app level module which depends on filters, and services
angular.module(
		'myApp',
		[ 'ngRoute', 'myApp.filters', 'myApp.services', 'myApp.directives',
				'myApp.controllers' ]).config(
		[ '$routeProvider', function($routeProvider) {
			$routeProvider.when('/home', {
				templateUrl : 'partials/home.html',
				controller : 'nullCtrl'
			});
			$routeProvider.when('/helpUs', {
				templateUrl : 'partials/helpUs.html',
				controller : 'nullCtrl'
			});
			$routeProvider.when('/impressum', {
				templateUrl : 'partials/impressum.html',
				controller : 'nullCtrl'
			});
			$routeProvider.when('/pixiExperiments', {
				redirectTo : '/pixiExample01'
			});
			$routeProvider.when('/pixiExample01', {
				templateUrl : 'partials/pixiExperiments.html',
				controller : 'pixi01Ctrl'
			});
			$routeProvider.when('/pixiExample02', {
				templateUrl : 'partials/pixiExperiments.html',
				controller : 'pixi02Ctrl'
			});
			$routeProvider.otherwise({
				redirectTo : '/home'
			});
		} ]);
