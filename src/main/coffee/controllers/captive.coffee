app = angular.module('beeSolarApp')

app.controller 'CaptiveCtrl', ($scope) ->
  $scope.isSending = false
  $scope.isReady = false
  $scope.wasRejected = false
  $scope.mac = "asdasdqw4e4"
  $scope.ip = "192.164.172.253"

  $scope.doPrepare = ->
    console.info("roll")

  $scope.doAuthorize = ->
    console.info($scope.mac + " @ " + $scope.ip)

  $scope.doPrepare()