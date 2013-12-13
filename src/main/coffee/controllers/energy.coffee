'use strict'

app = angular.module('beeSolarApp')

app.controller 'EnergyCtrl', ($scope, Energy, Auth) ->
    $scope.doEnergy = ->  
        $scope.isEditing = false
        $scope.isWorking = true
        $scope.energys   = Energy.query(
            () ->
                $scope.isWorking = false
            (e) ->
                Auth.manage(e)
        )

    $scope.add = (el) ->
        el = new Energy({
                device: $scope.device,
                start: new Date()
            })
        el.$save()
        $scope.energys.push( el )

    $scope.remove = (el) ->
        el.$remove( () -> 
            $scope.energys.splice( $scope.energys.indexOf(el), 1)
            console.info "Removed from server"
        )

    $scope.edit = (el) ->
        $scope.current = el
        $scope.isEditing = true

    $scope.stop = (el) ->
        el.end = new Date()
        el.$save()

    $scope.doFilter = (criteria) ->
        switch criteria
            when 'today' then


    $scope.doUpdate = ->
        $scope.current.$save()
        $scope.isEditing = false

    $scope.doEnergy()
