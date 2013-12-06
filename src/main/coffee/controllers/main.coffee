'use strict'

angular.module('beeSolarApp')
  .controller 'MainCtrl', ($scope, $http, Auth) ->
    $scope.isAuth = ->
      Auth.hasCredentials()

    $scope.doAuth = ->
      Auth.setCredentials($scope.username, $scope.password)
    
    $scope.deAuth = ->
      Auth.clearCredentials()
