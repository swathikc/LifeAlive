/**
 * 
 */
var myProjectsModule = angular.module("myProjectsModule");

myProjectsModule.controller('myProjectsController', function($scope, $routeParams, $rootScope, myProjectsService) 
{
	$scope.userData = $rootScope.userData;
	$scope.userId = $scope.userData.id;
	$scope.getMyProjects = function()
	{
		myProjectsService.getMyProjects(callbackSuccess,callbackError);
	}
	var callbackSuccess = function(response) 
	{
		$scope.projects = response;
	};

	var callbackError = function() 
	{  
		$scope.error = "You have no projects currently!!";
	};
	
});

myProjectsModule.factory('myProjectsService',function($http, $rootScope)
		{
			var myProjectsService = {};
			
			myProjectsService.getMyProjects = function(callbackSuccess,callbackError)
			{
				$http.get("http://localhost:8080/Final_web_project/rest/projects/projectList")
				.success(function(response) 
				{
					console.log("myProjects :"+response);
					if(response.length > 0 || response != "")
					{
						callbackSuccess(response);
					}
					else
					{
						console.log("callbackError");
						callbackError();
					}
					
				});
			}
			return myProjectsService;
		});