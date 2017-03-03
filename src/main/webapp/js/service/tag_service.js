angular.module('blog').factory('TagService', ['$http', '$q', '$window' , function($http, $q, $window){
	
	var factory = {
    		getAllTags: getAllTags,
    		getPageTags:getPageTags
    };
    
    return factory;
	
        function getAllTags() {
    		var tags = $http.get(rootContext + '/getAllTags');
        	return tags;
        }
        
        function getPageTags(page) {
    		var tags = $http.get(rootContext + '/getAllTags?page=' + page);
        	return tags;
        }
        
}]);