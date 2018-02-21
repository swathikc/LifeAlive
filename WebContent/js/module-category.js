/**
 * 
 */

var categoryModule = angular.module("categoryModule");

categoryModule.controller('categoryController', function($scope, $routeParams, $rootScope, categoryService,approveProjectService) 
{
	$scope.userData = $rootScope.userData;
	$scope.categoryId = $routeParams.id;

	$scope.getProjects = function()
	{
		categoryService.getProjects($scope.categoryId, callbackSuccess, callbackError);
	}
	$scope.deleteProject = function(project)
	{
		categoryService.deleteProject(project.id,callbackSuccess1);
	}
	$scope.approveProject = function(project)
	{
		approveProjectService.approveProject(project.id);
		alert("Successfully approved project!")
		project.status = 'valid';
	}
	
	var callbackSuccess = function(response) 
	{
		$scope.projectList = response;
	};

	var callbackError = function() 
	{  
		$scope.error = "No projects in this category currently!!";
	};
	var callbackSuccess1 = function() 
	{
		console.log("callbackSuccess1 was called")
		$scope.getProjects();
		alert("Successfully Deleted");
	};

	var callbackError1 = function() 
	{  
		$scope.error = "No project to display!!";
	};
});

categoryModule.factory('categoryService',function($http, $rootScope)
{
	var categoryService = {};
	
	categoryService.getProjects = function(categoryId, callbackSuccess,callbackError)
	{
		console.log("categoryId in controller :"+categoryId);
		$http.get("http://localhost:8080/Final_web_project/rest/projects/getProjects?id="+categoryId)
		.success(function(response) 
		{
			if(response.length > 0)
			{
				console.log("projects :"+response);
				callbackSuccess(response);
			}
			else
			{
				callbackError();
			}
			
		});
	}
	categoryService.deleteProject = function(id,callbackSuccess1)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/deleteProject", id)
		.success(function(data, status) 
		{
			callbackSuccess1();
		});
	}
	return categoryService;
});

categoryModule.factory('approveProjectService', function($http, $rootScope)
{
	var approveProjectService = {};
	
	approveProjectService.approveProject = function(id)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/approveProject", id)
		
	}
	return approveProjectService;
});