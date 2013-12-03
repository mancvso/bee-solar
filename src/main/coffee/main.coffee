console.info "we have coffee!"
window.beeSolarModule = angular.module "beeSolarModule", ["ngResource"]

window.Authorization = ($scope, $resource, $http, $rootScope) ->
  $rootScope.isLogged = false
  $scope.doLogin = ->
    #encoded = Base64.encode($scope.username + ':' + $scope.password)
    encoded = window.btoa($scope.username + ':' + $scope.password)
    console.info encoded
    $http.defaults.headers.common.Authorization = 'Basic ' + encoded

    $http(
      method: "GET"
      url: "/auth"
    ).success((data, status, headers, config) ->
      $scope.status = status
      $rootScope.isLogged = true
      console.info "logged in"
    ).error (data, status, headers, config) ->
      $scope.status = status
      if status == 401
        $rootScope.isLogged = false
        console.info "not logged in"
      else
        console.info status + " Server error"

window.Reminders = ($scope, $resource, $rootScope) ->
  Reminder = $resource('/api/reminders/:id', id: '@id')

  $scope.isLogged = $rootScope.isLogged
  $scope.reminders = Reminder.query()
  $scope.addReminder = ->
    toAdd = new Reminder({title: $scope.reminderText, remindAt: new Date().getTime()})
    toAdd.$save()
    $scope.reminders.push toAdd
    $scope.reminderText = null

  $scope.updateReminder = (reminder) -> reminder.$save()
  $scope.removeReminder = (reminder) ->
    index = $scope.reminders.indexOf(reminder)
    reminder.$remove()
    $scope.reminders.splice(index, 1)

window.Energys = ($scope, $resource, $rootScope) ->
  Energy = $resource('/api/energys/:id', id: '@id')

  $scope.isLogged = $rootScope.isLogged
  $scope.energys = Energy.query()
  $scope.addEnergy = ->
    toAdd = new Energy({device: $scope.energyText, start: new Date().getTime()})
    toAdd.$save()
    $scope.energys.push toAdd
    $scope.energyText = null

  $scope.updateEnergy = (energy) -> energy.$save()
  $scope.removeEnergy = (energy) ->
    index = $scope.energys.indexOf(energy)
    energy.$remove()
    $scope.energys.splice(index, 1)

window.Hotspots = ($scope, $resource, $rootScope) ->
  Hotspot = $resource('/api/hotspots/:id', id: '@id')

  $scope.isLogged = $rootScope.isLogged
  $scope.hotspots = Hotspot.query()
  $scope.addHotspot = ->
    toAdd = new Hotspot({device: $scope.hotspotDevice, user: $scope.hotspotUser, start: new Date().getTime()})
    toAdd.$save()
    $scope.hotspots.push toAdd
    $scope.hotspotText = null

  $scope.updateHotspot = (hotspot) -> hotspot.$save()
  $scope.removeHotspot = (hotspot) ->
    index = $scope.hotspots.indexOf(hotspot)
    hotspot.$remove()
    $scope.hotspots.splice(index, 1)
