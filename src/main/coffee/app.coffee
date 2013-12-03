'use strict'

angular.module('beeSolarApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ui.router'
])
  .config ($stateProvider, $routeProvider, $urlRouterProvider) ->

    $urlRouterProvider.when('', '/main').otherwise("/login")

    $stateProvider
      .state 'main',
        url: '/main',
        templateUrl: 'html/views/main.html',
        controller: 'MainCtrl'
      .state 'home',
        url: '/home',
        templateUrl: 'html/views/home.html',
        controller: 'HomeCtrl'
      .state 'energy',
        url: '/energy',
        templateUrl: 'html/views/energy.html',
        controller: 'EnergyCtrl'
      .state 'hotspot',
        url: '/hotspot',
        templateUrl: 'html/views/hotspot.html',
        controller: 'HotspotCtrl'
      .state 'login',
        url: '/login',
        templateUrl: 'html/views/login.html',
        controller: 'LoginCtrl'
    
