'use strict'

app = angular.module('beeSolarApp')

app.controller 'HotspotCtrl', ($scope, Hotspot, Auth) ->
    $scope.doHotspot = ->  
        $scope.isEditing = false
        $scope.isWorking = true
        $scope.hotspots  = Hotspot.query(
            () ->
                $scope.isWorking = false
                $scope.doChart()
            (e) ->
                Auth.manage(e)
        )

    $scope.add = (el) ->
        el = new Hotspot({
                device: $scope.device,
                mac: $scope.mac,
                user: $scope.user,
                start: new Date()
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

    $scope.doChart = ->

        laptops = $scope.hotspots.filter( (item) -> (item.device == 'laptop') )
        mobiles = $scope.hotspots.filter( (item) -> (item.device == 'mobile') )
        consoles = $scope.hotspots.filter( (item) -> (item.device == 'console') )
        tablets = $scope.hotspots.filter( (item) -> (item.device == 'tablet') )
        others = $scope.hotspots.filter( (item) -> (item.device == 'other') )

        data = [
             value: laptops.length
             color: "#006DCC"
             title: "Computadores"
            ,
             value: mobiles.length
             color: "#49AFCD"
             title: "Teléfonos"
            ,
             value: consoles.length
             color: "#5BB75B"
             title: "Consolas"
            ,
             value: tablets.length
             color: "#FAA732"
             title: "Tablets"
            ,
             value: others.length
             color: "#DA4F49"
             title: "Otros"
        ]

        ctx = $("#chart").get(0).getContext("2d")
        new Chart(ctx).Doughnut( data, { animation: false, showTooltips: true } )
        $("#chart-legend").empty()
        legend(document.getElementById("chart-legend"), data)

    $scope.backup = ->
        if !$scope._hotspots
            $scope._hotspots = $scope.hotspots

    $scope.filterByDevice = (sDevice) ->
        # backup
        $scope.backup()

        switch sDevice
            when '' then $scope.doHotspot() # reload
            else $scope.hotspots = $scope._hotspots.filter( (item) -> (item.device == sDevice) )

    $scope.filterByPeriod = (sPeriod) ->
        # backup
        $scope.backup()

        now = moment()

        switch sPeriod
            when '' then $scope.doHotspot() #reload
            else
                $scope.hotspots = $scope._hotspots.filter( (item) -> moment(item.start).isSame(now, sPeriod) )
                $scope.graphBy(sPeriod) # TODO: add callbacks. One animation at a time.

        $scope.doChart()

    $scope.graphBy = (sPeriod) ->
        switch sPeriod
            when 'year' then $scope.graphYearByMonths()
            when 'month' then $scope.graphMonthByDays()
            when 'week' then $scope.graphWeekByWeekdays()
            when 'day' then $scope.graphDayByHours()
            # when '_hour' then $scope.graphRealtime()
            else
                console.log sPeriod

    $scope.graphDayByHours = ->
        $scope.backup()

        data =
          labels: ( i + ":00" for i in [0..23] ) # i:00
          datasets: [
            fillColor: "rgba(220,220,220,0.5)"
            strokeColor: "rgba(220,220,220,1)"
            pointColor: "rgba(220,220,220,1)"
            pointStrokeColor: "#fff"
            data: (
                $scope.hotspots.filter( (item) -> ( moment(item.start).hour() == i) ).length
            ) for i in [0..23]
            title: "Esta semana"
          ,
            fillColor: "rgba(151,187,205,0.5)"
            strokeColor: "rgba(151,187,205,1)"
            pointColor: "rgba(151,187,205,1)"
            pointStrokeColor: "#fff"
            data: ( 1+i for i in [0..23] )
            title: "Hace un año"
          ]

        ctx = $("#chart-period").get(0).getContext("2d")
        new Chart(ctx).Line( data, { showTooltips: true } )
        $("#chart-period-legend").empty()
        legend(document.getElementById("chart-period-legend"), data)

    $scope.graphWeekByWeekdays = ->
        $scope.backup()

        data =
          labels: ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"]
          datasets: [
            fillColor: "rgba(220,220,220,0.5)"
            strokeColor: "rgba(220,220,220,1)"
            pointColor: "rgba(220,220,220,1)"
            pointStrokeColor: "#fff"
            data: (
                $scope.hotspots.filter( (item) -> ( moment(item.start).weekday() == i) ).length
            ) for i in [0..6]
            title: "Esta semana"
          ,
            fillColor: "rgba(151,187,205,0.5)"
            strokeColor: "rgba(151,187,205,1)"
            pointColor: "rgba(151,187,205,1)"
            pointStrokeColor: "#fff"
            data: ( 1+i for i in [0..6] )
            title: "Hace un año"
          ]

        ctx = $("#chart-period").get(0).getContext("2d")
        new Chart(ctx).Line( data, { showTooltips: true } )
        $("#chart-period-legend").empty()
        legend(document.getElementById("chart-period-legend"), data)

    $scope.graphMonthByDays = ->
        $scope.backup()

        data =
          labels: ( i for i in [1..31] )
          datasets: [
            fillColor: "rgba(220,220,220,0.5)"
            strokeColor: "rgba(220,220,220,1)"
            pointColor: "rgba(220,220,220,1)"
            pointStrokeColor: "#fff"
            data: (
                $scope.hotspots.filter( (item) -> ( moment(item.start).date() == i ) ).length
            ) for i in [1..31]
            title: "Este mes"
          ,
            fillColor: "rgba(151,187,205,0.5)"
            strokeColor: "rgba(151,187,205,1)"
            pointColor: "rgba(151,187,205,1)"
            pointStrokeColor: "#fff"
            data: ( 1+i for i in [1..31] )
            title: "Hace un año"
          ]

        ctx = $("#chart-period").get(0).getContext("2d")
        new Chart(ctx).Line( data, { showTooltips: true } )
        $("#chart-period-legend").empty()
        legend(document.getElementById("chart-period-legend"), data)

    $scope.graphYearByMonths = ->
        $scope.backup()

        data =
          labels: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
            "Noviembre", "Diciembre"]
          datasets: [
            fillColor: "rgba(220,220,220,0.5)"
            strokeColor: "rgba(220,220,220,1)"
            pointColor: "rgba(220,220,220,1)"
            pointStrokeColor: "#fff"
            data: (
                $scope.hotspots.filter( (item) -> ( moment(item.start).month() == i ) ).length
            ) for i in [0..11]
            title: "Este año"
          ,
            fillColor: "rgba(151,187,205,0.5)"
            strokeColor: "rgba(151,187,205,1)"
            pointColor: "rgba(151,187,205,1)"
            pointStrokeColor: "#fff"
            data: (1 + i for i in [0..11] )
            title: "Año anterior"
          ]

        ctx = $("#chart-period").get(0).getContext("2d")
        new Chart(ctx).Line( data )
        $("#chart-period-legend").empty()
        legend(document.getElementById("chart-period-legend"), data)


    # initial call
    $scope.doHotspot()
