angular.module('blog').factory('ProfileService', ['$http', '$q', '$window' , function($http, $q, $window){
	
	var factory = {
			followUser: followUser,
			unfollowUser: unfollowUser
    };
    
    return factory;
	
        function followUser(currentUserId,followingUserId) {
    		var result=$http.get(rootContext + '/changeFollowing/'+currentUserId+'/'+followingUserId+'/follow');
    		return result;
        }
        
        function unfollowUser(currentUserId,followingUserId) {
    		var result=$http.get(rootContext + '/changeFollowing/'+currentUserId+'/'+followingUserId+'/unfollow');
    		return result;
        }
        
        
}]);
