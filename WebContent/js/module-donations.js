/**
 * 
 */
var donationsModule = angular.module("donationsModule");

donationsModule.controller('donationsController', function($scope, $routeParams, $rootScope, donationsService) 
{
	$scope.userData = $rootScope.userData;
	$scope.userData = $rootScope.userData;
	$scope.getDonations = function()
	{
		if($scope.userData.role == "user")
			donationsService.getDonations($scope.userData.id, callbackSuccess,callbackError);
		else
			$scope.errorMessage = "You are not an authorised user!!";
	}
	var callbackSuccess = function(response) 
	{
		console.log("response :"+response);
		if(response.length > 0)
			$scope.donations = response;
		else
			$scope.error = "You have no donations currently!!";
	};

	var callbackError = function() 
	{  
		$scope.error = "You have no donations currently!!";
	};
	
});

donationsModule.factory('donationsService',function($http, $rootScope)
{
	var donationsService = {};
	
	donationsService.getDonations = function(userId, callbackSuccess,callbackError)
	{
		console.log("userId :"+userId);
		$http.get("http://localhost:8080/Final_web_project/rest/user/getDonations?userId="+userId)
		.success(function(response) 
		{
			callbackSuccess(response);
		});
	}
	return donationsService;
});