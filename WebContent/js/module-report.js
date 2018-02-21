/**
 * 
 */
var reportModule = angular.module("reportModule");

reportModule.controller('reportController', function($scope, $routeParams, $rootScope, countryDonationService) 
{
	$scope.userData = $rootScope.userData;
	$scope.generateReport = function()
	{
		countryDonationService.getCountryReport(callbackSuccess,callbackError);
		countryDonationService.getAllProjects(callbackSuccess1,callbackError)
	}

	var callbackSuccess = function(response) 
	{
		$scope.countryReport = response;
	};
	var callbackSuccess1 = function(response) 
	{
		$scope.projects = response;
	};

	var callbackError = function() 
	{  
		$scope.error = "You have no donations currently!!";
	};
});

reportModule.factory('countryDonationService',function($http, $rootScope)
{
	var countryDonationService = {};
	
	countryDonationService.getCountryReport = function(callbackSuccess,callbackError)
	{
		$http.get("http://localhost:8080/Final_web_project/rest/report/getCountryReport")
		.success(function(response) 
		{
			console.log("donations :"+response);
			if(response.length > 0)
			{
				callbackSuccess(response);
			}
			else
			{
				callbackError();
			}
			
		});
	}
	countryDonationService.getAllProjects = function(callbackSuccess1,callbackError)
	{
		$http.get("http://localhost:8080/Final_web_project/rest/projects/projectList")
		.success(function(response) 
		{
			console.log("report project response :"+response)
			if (response != "") 
			{
				callbackSuccess1(response);
			} 
			else
				callbackError();
		});
	}
	return countryDonationService;
});