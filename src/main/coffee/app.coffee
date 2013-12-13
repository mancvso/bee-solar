'use strict'

# == Convention ==
# FooBar        : Class, Reference
# t             : Tuple
# a             : Array, Single-value Secuence, ie, List, Vector
# m             : Multi-array, Multiple-value Secuence, ie, Map or Dictionary
# s             : String
# i/d/l/n/x/X   : Number (Int, Double, Long, x:lower non-native numeric type, X:greater..., ie, BigInt, BigLong)
# is/has        : Boolean
# do/get/set/on : Action (Method or Function, Getter, Setter, Callback)
# _             : Private.

angular.module('beeSolarApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ui.router',
  'angularMoment',
  'services'
])
  .config ($stateProvider, $routeProvider, $urlRouterProvider) ->

    $urlRouterProvider.when('', '/main').otherwise("/main")

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
      .state 'alert',
        url: '/alert',
        templateUrl: 'html/views/alert.html',
        controller: 'AlertCtrl'
      .state 'login',
        url: '/login',
        templateUrl: 'html/views/login.html',
        controller: 'LoginCtrl'
      .state 'logout',
        url: '/logout',
        templateUrl: 'html/views/logout.html',
        controller: 'LogoutCtrl'
      .state 'stats',
        url: '/stats',
        templateUrl: 'html/views/stats.html',
        controller: 'StatsCtrl'
