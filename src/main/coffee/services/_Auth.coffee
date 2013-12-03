# 'use strict'

# angular.module('beeSolarApp')
#   .factory 'Auth', (Base64, $cookieStore, $http) ->
#     # Service logic
#     $http.defaults.headers.common['Authorization'] = 'Basic ' + $cookieStore.get('authdata')

#     meaningOfLife = 42

#     # Public API here
#     {
#     	setCredentials: (username, password) ->
# 	        encoded = Base64.encode(username + ':' + password)
# 	        $http.defaults.headers.common.Authorization = 'Basic ' + encoded
# 	        $cookieStore.put('authdata', encoded)

# 	    clearCredentials: () ->
#             document.execCommand("ClearAuthenticationCache")
#             $cookieStore.remove('authdata')
#             $http.defaults.headers.common.Authorization = 'Basic '


#     }