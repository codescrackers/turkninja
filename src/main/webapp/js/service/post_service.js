angular.module('blog').factory('PostService', ['$http', '$q', '$window' , function($http, $q, $window){
	
	var factory = {
			makeMainpageVisible: makeMainpageVisible,
			makeMainpageUnvisible: makeMainpageUnvisible
    };
    
    return factory;
	
        function makeMainpageVisible(postId) {
    		var result=$http.get(rootContext + '/changeMainpageVisible/'+postId+'/makeMainpageVisible');
    		return result;
        }
        
        function makeMainpageUnvisible(postId) {
    		var result=$http.get(rootContext + '/changeMainpageVisible/'+postId+'/makeMainpageUnvisible');
    		return result;
        }
        
        
}]);
