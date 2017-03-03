angular.module('blog').controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
	
	var vm = this;
	
	vm.userList = [];
	vm.pageNumber=0;
	vm.totalPageNumber=0;
	vm.isFirst=false;
	vm.isLast=false;
	vm.getAllUsers = function() {
		
			UserService.getAllUsers()
			.then(function(response) {
				vm.totalPageNumber=response.data.totalPageNumber;
				vm.isFirst=response.data.first;
				vm.isLast=response.data.last;
				vm.pageNumber=response.data.pageNumber;
				response.data.data.forEach(function(user) {
				    vm.userList.push(user);
				});
				console.log(vm.userList);
	        })
			.catch(function(response) {
				var errObj = response.data || {};	
			});
	
		
	}
	
	vm.getAllUsers();
	
	vm.getPageUsers= function(page){
		UserService.getPageUsers(page)
		.then(function(response) {
			console.log(response);
			vm.totalPageNumber=response.data.totalPageNumber;
			vm.isFirst=response.data.first;
			vm.isLast=response.data.last;
			vm.pageNumber=response.data.pageNumber;
			vm.userList = [];
			response.data.data.forEach(function(user) {
			    vm.userList.push(user);
			});
			console.log(vm.userList);
        })
		.catch(function(response) {
			var errObj = response.data || {};	
		});
	}
	
	

}]);