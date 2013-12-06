'use strict'

angular.module('beeSolarApp')
  .controller 'LoginCtrl', ($scope, Auth, $location, $http, Base64) ->
    $scope.isSending = false
    $scope.isReady = false
    $scope.wasRejected = false

    $scope.doRenew =  ->
        $scope.sToken = Base64.encode( (Math.random()*41 + 11) + "datactil" + (Math.random()*41 + 11) )


    $scope.doAuth =  ->
        $scope.isSending   = false
        $scope.isReady     = false
        $scope.wasRejected = false
        # SaltedAuth
        saltedPass = Base64.encode($scope.password + ":" + $scope.sToken)
        # TODO: SHA1
        # console.info CryptoJS.SHA1($scope.password + ":" + $scope.sToken)
        $scope.isSending = true
        $http.post('/auth', {token: $scope.sToken, username: $scope.username, saltedPass: saltedPass}).success((sData, iStatus, oHeaders) ->
            $scope.isSending = false
            if(sData == "OK")
                $scope.isReady = true
                Auth.setCredentials($scope.username, $scope.password)
                $location.path('/home')
            else
                $scope.sError = iStatus + " " + sData
                $scope.wasRejected = true
        ).error (iStatus, sData, oHeaders) ->
            console.info iStatus + sData
            $scope.sError = iStatus + " " + sData
            $scope.wasRejected = true


    $scope.doRenew()