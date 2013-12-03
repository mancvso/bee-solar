'use strict'

angular.module('beeSolarApp')
  .controller 'MainCtrl', ($scope, $http, Auth) ->
  	$scope.checkAuth = ->
      Auth.setCredentials($scope.username, $scope.password)
      console.info $http.defaults.headers.common.Authorization

    $scope.deAuth = ->
      Auth.clearCredentials()
      console.info $http.defaults.headers.common.Authorization

    $scope.awesomeThings = [
      'HTML5 Boilerplate'
      'AngularJS'
      'Karma'
    ]
