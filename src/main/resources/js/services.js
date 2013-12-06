var module = angular.module( 'ngResourceRest', [ 'ngResource' ] );

module.factory( 'Resource', [ '$resource', function( $resource ) {
  return function( url, params, methods ) {
    var defaults = {
      update: { method: 'put', isArray: false },
      create: { method: 'post' }
    };
    
    methods = angular.extend( defaults, methods );

    var resource = $resource( url, params, methods );

    resource.prototype.$save = function() {
      if ( !this.id ) {
        return this.$create();
      }
      else {
        return this.$update();
      }
    };

    return resource;
  };
}]);

var service = angular.module( 'services', [ 'ngResourceRest' ] );

service.factory( 'User', [ 'Resource', function( $resource ) {
  return $resource( 'api/users/:id', { id: '@id' } );
}]);

service.factory( 'Hotspot', [ 'Resource', function( $resource ) {
  return $resource( 'api/hotspots/:id', { id: '@id' } );
}]);

service.factory( 'Energy', [ 'Resource', function( $resource ) {
  return $resource( 'api/energys/:id', { id: '@id' } );
}]);

service.factory( 'Alert', [ 'Resource', function( $resource ) {
  return $resource( 'api/alerts/:id', { id: '@id' } );
}]);

service.factory( 'Stats', [ 'Resource', function( $resource ) {
  return $resource( 'api/stats/:id', { id: '@id' } );
}]);