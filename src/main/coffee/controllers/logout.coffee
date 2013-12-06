'use strict'

angular.module('beeSolarApp')
  .controller 'LogoutCtrl', ($scope, $location, Auth) ->
    $scope.doAuth = ->
      console.info( $scope.username + ":" + $scope.password )
      Auth.setCredentials($scope.username, $scope.password)
      $location.path('/main')
