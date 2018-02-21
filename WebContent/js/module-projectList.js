/**
 * 
 */
var projectListModule = angular.module("projectListModule");

projectListModule.controller('projectListController',function($scope, $rootScope, $route, $http, projectListService, approveProjectService, deleteProjectService) 
{
	$scope.userData = $rootScope.userData;
	$scope.getAllProjects = function()
	{
		projectListService.getAllProjects(callbackSuccess,callbackError);
	}
	$scope.approveProject = function(project)
	{
		approveProjectService.approveProject(project.id,callbackSuccess1,callbackError1);
		alert("Successfully approved project!")
		project.status="valid";
	}
	$scope.deleteProject = function(project)
	{
		deleteProjectService.deleteProject(project.id,callbackSuccess1);
	}
	
	var callbackSuccess = function(response) 
	{
		$scope.projectList = response;
		console.log("Response :"+response);
		console.log("callbackSuccess was called");
	};

	var callbackError = function() 
	{  
		$scope.error = "No project to display!!";
	};
	var callbackSuccess1 = function() 
	{
		console.log("callbackSuccess1 was called")
		$scope.getAllProjects();
		alert("Successfully Deleted");
	};

	var callbackError1 = function() 
	{  
		$scope.error = "No project to display!!";
	};
});

projectListModule.factory('projectListService', function($http, $rootScope)
{
	var projectListService = {};
	var approveProjectService = {};
	
	projectListService.getAllProjects = function(callbackSuccess,callbackError)
	{
		$http.get("http://localhost:8080/Final_web_project/rest/projects/projectList")
		.success(function(response) 
		{
			if (response.length > 0) 
			{
				callbackSuccess(response);
			} 
			else
				callbackError();
		});
	}
	return projectListService;
});

projectListModule.factory('approveProjectService', function($http, $rootScope)
{
	var approveProjectService = {};
	
	approveProjectService.approveProject = function(id,callbackSuccess1,callbackError1)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/approveProject", id)
		.success(function(data, status) 
		{
			callbackSuccess1();
		});
		
	}
	return approveProjectService;
});

projectListModule.factory('deleteProjectService', function($http, $rootScope)
{
	var deleteProjectService = {};
	
	deleteProjectService.deleteProject = function(id,callbackSuccess1)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/deleteProject", id)
		.success(function(data, status) 
		{
			callbackSuccess1();
		});
	}
	return deleteProjectService;
});