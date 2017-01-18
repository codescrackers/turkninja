angular.module('blog').factory('UserService', ['$http', '$q', '$window' , function($http, $q, $window){
	
	var factory = {
    		getAllUsers: getAllUsers,
    		getPageUsers:getPageUsers
    };
    
    return factory;
	
        function getAllUsers() {
    		var users = $http.get(rootContext + '/getAllUsers');
        	return users;
        }
        
        function getPageUsers(page) {
    		var users = $http.get(rootContext + '/getAllUsers?page=' + page);
        	return users;
        }
        
}]);
