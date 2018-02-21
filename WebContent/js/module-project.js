/**
 * 
 */
var projectModule = angular.module("projectModule");
projectModule.controller('projectController', function($scope, $routeParams, $rootScope,$location,projectService,approveProjectService,$timeout) 
{
	$scope.projectId = $routeParams.id;
	$scope.userData = $rootScope.userData;

	$scope.addToCart = function(project)
	{
		var amount;
		if($scope.amount != null)
			amount = $scope.amount;
		else
			amount = $scope.time*100;
		var cartDetails = {
				"projectId" : project.id,
				"userId" : $scope.userData.id,
				"amount" : amount,
				"status" : "unpaid",
				"comment" : $scope.comment
			}
		projectService.addToCart(cartDetails,callBackSuccess3);
	}
	$scope.getProjectAndComments = function()
	{
		$scope.getProject();
		$scope.getDonations();
	}
	$scope.getProject = function()
	{
		projectService.getProject($scope.projectId, callbackSuccess, callbackError);
	}
	$scope.deleteProject = function(project)
	{
		projectService.deleteProject(project.id,callbackSuccess2);
	}
	$scope.forcedDeleteProject = function(project)
	{
		projectService.forcedDeleteProject(project.id,callbackSuccess2);
	}
	$scope.getDonations = function()
	{
		projectService.getDonations($scope.projectId, callbackSuccess1, callbackError1);
	}
	$scope.approveProject = function(project)
	{
		approveProjectService.approveProject(project.id);
		alert("Successfully approved project!")
		project.status = 'valid';
	}
	
	var callbackSuccess = function(response) 
	{
		$scope.project = response;
	};

	var callbackError = function() 
	{  
		$scope.error = "No project to display!!";
	};
	var callbackSuccess1 = function(response) 
	{
		$scope.donations = response;
	};

	var callbackError1 = function() 
	{  
	};
	var callbackSuccess2 = function() 
	{
		alert("Successfully Deleted");
		$scope.message = "Successfully Deleted!!";
		$scope.showSuccessAlert = true;
		$timeout(function () { $scope.showSuccessAlert = false; }, 3000);
		$location.path("/categoryList")
	};
	// switch flag
	$scope.switchBool = function(value) {
	   $scope[value] = !$scope[value];
	};
	var callbackError2 = function() 
	{  
		$scope.error = "No project to display!!";
	};
	var callBackSuccess3 = function() 
	{
		$('#myModal').modal('toggle');
		$scope.message = "Successfully added to cart";
		$scope.showSuccessAlert = true;
		$timeout(function () { $scope.showSuccessAlert = false; }, 3000);
	};
	// switch flag
	$scope.switchBool = function(value) {
	   $scope[value] = !$scope[value];
	};
	
	$scope.checkUser = function()
	{
		if (typeof ($rootScope.userData) == 'undefined'	|| $rootScope.userData == '' || $rootScope.userData == null) 
		{
			$rootScope.redirectPage = '/project/'+$scope.projectId;
			$location.path('/login');
		} 
		else 
		{
			projectService.checkServiceLimit($scope.userData.id, $scope.projectId, callbackServiceLimitSuccess, callbackServiceLimitError);
		}
	}
	$scope.checkUserToDonateTime = function()
	{
		if (typeof ($rootScope.userData) == 'undefined'	|| $rootScope.userData == '' || $rootScope.userData == null) 
		{
			$rootScope.redirectPage = '/project/'+$scope.projectId;
			$location.path('/login');
		} 
		else 
		{
			projectService.checkServiceLimit($scope.userData.id, $scope.projectId, callbackServiceLimitSuccess1, callbackServiceLimitError);
			
		}
	}
	var callbackServiceLimitSuccess = function(response) 
	{
		$location.path('/cardDetails/'+ $scope.projectId);
	};
	var callbackServiceLimitSuccess1 = function(response) 
	{
		$location.path('/donateTime/'+ $scope.projectId);
	};

	var callbackServiceLimitError = function() 
	{  
		$scope.errorMessage = "You have exceeded the donation limit for this project!!"
			$scope.showWarningAlert = true;
		$timeout(function () { $scope.showWarningAlert = false; }, 3000);
	};
	// switch flag
	$scope.switchBool = function(value) {
	   $scope[value] = !$scope[value];
	};
});

projectModule.factory('projectService',function($http, $rootScope)
{
	var projectService = {};
	
	projectService.getProject = function(projectId, callbackSuccess,callbackError)
	{
		$http.get("http://localhost:8080/Final_web_project/rest/projects/getProject?id="+projectId)
		.success(function(response) 
		{
			if(response != "")
			{
				console.log("project :"+response);
				callbackSuccess(response);
			}
			else
			{
				callbackError();
			}
			
		});
		
	}
	projectService.checkServiceLimit = function(userId, projectId, callbackServiceLimitSuccess, callbackServiceLimitError)
	{
		console.log("ServiceLimit was called")
		$http.get("http://localhost:8080/Final_web_project/rest/user/serviceLimit?userId="+userId+"&projectId="+projectId)
		.success(function(response) 
		{
			if(response)
			{
				console.log("permit :"+response);
				callbackServiceLimitSuccess();
			}
			else
			{
				callbackServiceLimitError();
			}
			
		});
	}
	projectService.deleteProject = function(id,callbackSuccess2)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/deleteProject", id)
		.success(function(data, status) 
		{
			callbackSuccess2();
		});
	}
	projectService.forcedDeleteProject = function(id,callbackSuccess2)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/forcedDeleteProject", id)
		.success(function(data, status) 
		{
			callbackSuccess2();
		});
	}
	projectService.addToCart = function(cartDetails,callBackSuccess3)
	{
		console.log("Add to cart service was called");
		$http.post("http://localhost:8080/Final_web_project/rest/projects/addToCart", cartDetails)
		.success(function(data, status) 
		{
			console.log("Add to cart service was success");
			callBackSuccess3();
		});
	}
	
	projectService.getDonations = function(projectId, callbackSuccess1,callbackError1)
	{
		$http.get("http://localhost:8080/Final_web_project/rest/user/getComments?projectId="+projectId)
		.success(function(response) 
		{
			if(response != "")
			{
				console.log("donations :"+response);
				callbackSuccess1(response);
			}
			else
			{
				callbackError1();
			}
			
		});
	}
	return projectService;
});

projectModule.factory('approveProjectService', function($http, $rootScope)
{
	var approveProjectService = {};
	
	approveProjectService.approveProject = function(id)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/approveProject", id)
		
	}
	return approveProjectService;
});