'use strict'
app = angular.module('beeSolarApp')
app.directive('devices', () ->
    template: '<div></div>'
    restrict: 'E'
    link: (scope, element, attrs) ->
      element.text 'this is the devicesChart directive'
  )
