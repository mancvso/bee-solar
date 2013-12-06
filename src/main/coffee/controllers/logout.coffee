'use strict'

app = angular.module('beeSolarApp')

app.controller 'LogoutCtrl', ($scope, Auth) ->
    $scope.isAuth = () -> Auth.hasCredentials()

    $scope.deAuth = -> 
        Auth.clearCredentials()