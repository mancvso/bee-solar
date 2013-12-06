'use strict'

app = angular.module('beeSolarApp')

app.controller 'HotspotCtrl', ($scope, Hotspot, Auth) ->
    $scope.doHotspot = ->  
        $scope.isEditing = false
        $scope.isWorking = true
        $scope.hotspots  = Hotspot.query(
            () ->
                $scope.isWorking = false
            (e) ->
                Auth.manage(e)
        )

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
            $scope.hotspots.splice( $scope.hotspots.indexOf(el), 1)
            console.info "Removed from server"
        )

    $scope.edit = (el) ->
        $scope.current = el
        $scope.isEditing = true

    $scope.doUpdate = ->
        $scope.current.$save()
        $scope.isEditing = false

    $scope.filter = (sDevice) ->
        if sDevice == ''
            doHotspot()
        #else
        #    $scope.energys = $scope.energys.filter($scope.energys, (item) -> 
        #        (item.device == 'sDevice')

    $scope.doHotspot()
