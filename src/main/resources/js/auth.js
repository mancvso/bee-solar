angular.module('beeSolarApp').factory('Auth', ['Base64', '$cookieStore', '$http', '$location', function (Base64, $cookieStore, $http, $location) {
    // initialize to whatever is in the cookie, if anything
    $http.defaults.headers.common['Authorization'] = 'Basic ' + $cookieStore.get('authdata');
 
    return {
        setCredentials: function (username, password) {
            var encoded = Base64.encode(username + ':' + password);
            $http.defaults.headers.common.Authorization = 'Basic ' + encoded;
            $cookieStore.put('authdata', encoded);
        },
        clearCredentials: function () {
            document.execCommand("ClearAuthenticationCache");
            $cookieStore.remove('authdata');
            $cookieStore.put('authdata', "-");
            $http.defaults.headers.common.Authorization = 'Basic ';
        },
        hasCredentials: function () {
            var isAuth = false;
            authData = $cookieStore.get('authdata') || '';
            //console.log($http.defaults.headers.common.Authorization);
            isAuth = ($http.defaults.headers.common.Authorization != 'Basic '); //$http.defaults.headers.common.Authorization == ('Basic ' + authData )
            //console.log( "auth? " + isAuth)
            return isAuth

        },
        manage: function(e){
            if (e.status == 401) {
                $location.path("/login");
            } else {
                $location.path("/500x");
            };
        }

    };
}]);