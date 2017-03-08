angular.module('blog').controller('ProfileController', ['$scope', 'ProfileService', function($scope, ProfileService) {
	
	var vm = this;
	
	vm.changeFollowingStatus=function(statusVal,follwerId,followingId,fv){
		if(statusVal=="Takip et"){
			ProfileService.followUser(follwerId,followingId).then(function(response) {
				if(response.data.result=="success"){
					$scope.statusVal="Takibi bırak";
					$scope.followersVal=parseInt($scope.followersVal);
					$scope.followersVal=$scope.followersVal+1;
				}
			})
		}else if(statusVal=="Takibi bırak"){
			ProfileService.unfollowUser(follwerId,followingId).then(function(response) {
				if(response.data.result=="success"){
					$scope.statusVal="Takip et";
					if(parseInt($scope.followersVal)>0){
						$scope.followersVal=parseInt($scope.followersVal);
						$scope.followersVal=$scope.followersVal-1;
					}
				}
			})
		}
	}
	

}]);