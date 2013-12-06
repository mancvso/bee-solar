'use strict'

app = angular.module('beeSolarApp')

app.controller 'HotspotCtrl', ($scope, Hotspot) ->
    $scope.doHotspot = ->  
        $scope.isEditing = false
        $scope.isWorking = true
        $scope.hotspots  = Hotspot.query( () -> $scope.isWorking = false )

    $scope.add = (el) ->
        el = new Hotspot({
                device: $scope.device,
                mac: $scope.mac,
                user: $scope.user,
                start: 6272638373262 # XXX: fecha real
            })
        el.$save()
        $scope.hotspots.push( el )

    $scope.remove = (el) ->
        el.$remove( () -> 
            # XXX: $scope.hotspots.remove( el )
            console.info "Removed from server"
        )

    $scope.edit = (el) ->
        $scope.current = el
        $scope.isEditing = true

    $scope.doUpdate = ->
        $scope.current.$save()
        $scope.isEditing = false

    $scope.doHotspot()
