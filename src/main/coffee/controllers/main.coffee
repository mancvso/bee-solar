'use strict'

angular.module('beeSolarApp')
    .controller 'MainCtrl', ($scope, Auth, $location) ->
        $scope.isAuth = () -> Auth.hasCredentials()

        $scope.doAuth = ->
            Auth.setCredentials($scope.username, $scope.password)

        $scope.deAuth = -> 
        	Auth.clearCredentials()
        	$location.path("/logout")
