'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',
  'myApp.filters',
  'myApp.services',
  'myApp.directives',
  'myApp.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/home', {templateUrl: 'partials/home.html', controller: 'nullCtrl'});
  $routeProvider.when('/helpUs', {templateUrl: 'partials/helpUs.html', controller: 'nullCtrl'});
  $routeProvider.when('/impressum', {templateUrl: 'partials/impressum.html', controller: 'nullCtrl'});
  $routeProvider.otherwise({redirectTo: '/home'});
}]);
