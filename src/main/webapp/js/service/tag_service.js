'use strict';
 
angular.module('blog').factory('TagService', ['$http', '$q', '$window' , function($http, $q, $window){
 
    var factory = {
    		getAllTags: getAllTags,
    		getPostsByTagName: getPostsByTagName
    };
    
    return factory;
	
        function getAllTags() {
    		var list = $http.get('http://localhost:8080/yazilim-okulu/tag/all');
        	return list;
        }
        
        function getPostsByTagName(tagName) {
        	$window.location.href = rootContext + '/posts?tagged='+tagName;
        }
        
       
}]);