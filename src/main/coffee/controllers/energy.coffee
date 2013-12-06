'use strict'

app = angular.module('beeSolarApp')

app.controller 'EnergyCtrl', ($scope, Energy) ->
    $scope.doEnergy = ->  
        $scope.isEditing = false
        $scope.isWorking = true
        $scope.energys   = Energy.query( () -> $scope.isWorking = false )

    $scope.add = (el) ->
        el = new Energy({
                device: $scope.device,
                start: 6272638373262 # XXX: fecha real
            })
        el.$save()
        $scope.energys.push( el )

    $scope.remove = (el) ->
        el.$remove( () -> 
            # XXX: $scope.energys.remove( el )
            console.info "Removed from server"
        )

    $scope.edit = (el) ->
        $scope.current = el
        $scope.isEditing = true

    $scope.doUpdate = ->
        $scope.current.$save()
        $scope.isEditing = false

    $scope.doEnergy()
