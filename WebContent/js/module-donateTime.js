/**
 * 
 */
var donateTimeModule = angular.module("donateTimeModule");
donateTimeModule.controller('donateTimeController', function($scope, $routeParams, $rootScope,$location, donateTimeService) 
{
	$scope.userData = $rootScope.userData;
	$scope.projectId = $routeParams.id;
	$scope.postTimeDetails = function()
	{

		var time = $scope.time;
		$scope.amount = time*100;
		console.log("time :"+$scope.time);
		console.log("amount :"+$scope.amount);
		$scope.date = new Date();
		var timeDonationInfo = {

				"userId" : $rootScope.userData.id,
				"projectId" : $routeParams.id,
				"amount" : $scope.amount,
				"dateOfPayment" : $scope.date,
				"comment" : $scope.comment
			};
		donateTimeService.donateTime(timeDonationInfo, callbackSuccess,callbackError);
	}

	var callbackSuccess = function() 
	{
		$location.path('/categoryList');
	};

	var callbackError = function() 
	{
	};
});

donateTimeModule.factory('donateTimeService',function($http, $rootScope)
{
	var donateTimeService = {};
	
	donateTimeService.donateTime = function(timeDonationInfo, callbackSuccess,callbackError)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/user/donateTime", timeDonationInfo)
		.success(function(data, status) 
		{
			callbackSuccess();
				
		});
	}
	return donateTimeService;

});