'use strict';
 
angular.module('blog').controller('TagController', ['$scope', 'TagService', function($scope, TagService) {
	
	var vm = this;
	
	var tagFocusControl = false;
	vm.tagName = "";
	vm.tagList = [];
	
	vm.getAllTags = function() {
		if (!tagFocusControl) {
			TagService.getAllTags()
			.then(function(response) {
				response.data.data.forEach(function(tag) {
				    console.log(tag);
				    vm.tagList.push(tag);
				});
	        })
			.catch(function(response) {
				var errObj = response.data || {};	
			});
			tagFocusControl = true;
		}
		
	}
	
	vm.getPostsByTagName = function(tagName) {
		TagService.getPostsByTagName(tagName);
	}
	
}]);