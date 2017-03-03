angular.module('blog').controller('TagController', ['$scope', 'TagService', function($scope, TagService) {
	
	var vm = this;
	
	vm.tagList = [];
	vm.pageNumber=0;
	vm.totalPageNumber=0;
	vm.isFirst=false;
	vm.isLast=false;
	vm.getAllTags = function() {
		
			TagService.getAllTags()
			.then(function(response) {
				vm.totalPageNumber=response.data.totalPageNumber;
				vm.isFirst=response.data.first;
				vm.isLast=response.data.last;
				vm.pageNumber=response.data.pageNumber;
				response.data.data.forEach(function(tag) {
				    vm.tagList.push(tag);
				});
	        })
			.catch(function(response) {
				var errObj = response.data || {};	
			});
	
		
	}
	
	vm.getAllTags();
	
	vm.getPageTags= function(page){
		TagService.getPageTags(page)
		.then(function(response) {
			vm.totalPageNumber=response.data.totalPageNumber;
			vm.isFirst=response.data.first;
			vm.isLast=response.data.last;
			vm.pageNumber=response.data.pageNumber;
			vm.tagList = [];
			response.data.data.forEach(function(tag) {
			    vm.tagList.push(tag);
			});
        })
		.catch(function(response) {
			var errObj = response.data || {};	
		});
	}
	
	

}]);