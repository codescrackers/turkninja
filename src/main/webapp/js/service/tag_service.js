'use strict';
 
angular.module('blog').factory('TagService', ['$http', '$q', function($http, $q){
 
    var factory = {
    		getAllTags: getAllTags,
    };
    
    return factory;
	
        function getAllTags() {
    		var list = $http.get('http://localhost:8080/yazilim-okulu/tag/all');
        	return list;
        }
        
        
       
}]);