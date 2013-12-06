'use strict'

app = angular.module('beeSolarApp')

app.controller 'AlertCtrl', ($scope, Alert, Auth) ->

    $scope.doAlert = ->  
        $scope.isEditing = false
        $scope.isWorking = true
        $scope.alerts    = Alert.query(
            () ->
                $scope.isWorking = false
            (e) ->
                Auth.manage(e)
        )

    $scope.add = (el) ->
        el = new Alert({
                message: $scope.message,
                date: 6272638373262 # XXX: fecha real
            })
        el.$save()
        $scope.alerts.push( el )

    $scope.remove = (el) ->
        el.$remove( () ->
            $scope.alerts.splice( $scope.alerts.indexOf(el), 1)
            console.info "Removed from server"
        )

    $scope.edit = (el) ->
        $scope.current = el
        $scope.isEditing = true

    $scope.doUpdate = ->
        $scope.current.$save()
        $scope.isEditing = false

    $scope.doAlert()

