angular.module('blog').controller('PostController', ['$scope', 'PostService', function($scope, PostService) {
	
	var vm = this;
	
	vm.changeMainpageVisibleStatus=function(statusVal,postId){
		if(statusVal=="Ana Sayfa Görünür Yap"){
			PostService.makeMainpageVisible(postId).then(function(response) {
				if(response.data.result=="success"){
					$scope.statusVal="Ana Sayfa Görünmez Yap";
				}
			})
		}else if(statusVal=="Ana Sayfa Görünmez Yap"){
			PostService.makeMainpageUnvisible(postId).then(function(response) {
				if(response.data.result=="success"){
					$scope.statusVal="Ana Sayfa Görünür Yap";
				}
			})
		}
	}
	

}]);