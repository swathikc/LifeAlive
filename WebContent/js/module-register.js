/**
 * 
 */

var registerModule = angular.module("registerModule");
registerModule.controller('registerController',function($scope, $rootScope, $http, $location, registerService) 
{
	$scope.registerUser = function() 
	{
		
		var newUser = {

			"firstName" : $scope.firstName,
			"lastName" : $scope.lastName,
			"username" : $scope.username,
			"password" : $scope.password,
			"dob" : $scope.dob,
			"country" : $scope.country,
			"emailId" : $scope.email,
			"phoneNo" : $scope.phone
		};
		registerService.registerUser(newUser,callbackSuccess,callbackError);
		
	};
	var callbackSuccess = function() 
	{
		$location.path('/login');
	};

	var callbackError = function() 
	{
		$scope.error = "Username already exists";   
	};
});

registerModule.factory('registerService',function($http, $rootScope)
{
	var registerService = {};
	
	registerService.registerUser = function(newUser,callbackSuccess,callbackError)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/user/registerUser", newUser)
		.success(function(data, status) 
		{
			if (data)
				callbackSuccess();
				
			else
				callbackError();
				
		});
	}
	return registerService;


});