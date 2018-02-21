/**
 * 
 */
var loginModule = angular.module("loginModule");

loginModule.controller('loginController', function($scope,$rootScope,$location,loginService,$cookieStore) 
{
	$scope.$on('login-success', function(evt, args)
	{
		console.log("$on(login-success), $rootScope.userData = "+JSON.stringify($rootScope.userData));
		$scope.init();
	});
	
	$scope.init = function()
	{
		$scope.userData = $rootScope.userData;
		console.log("$scope.init, $scope.userData = "+JSON.stringify($scope.userData));
	}
	
	$scope.validateLogin = function()
	{
		var loginCredentials = {
				"username" : $scope.username,
				"password" : $scope.password
		}
		loginService.validateUser(loginCredentials,callbackSuccess,callbackError);
	}
	
	$scope.logout = function()	
	{
		$rootScope.userData = null;
		$rootScope.redirectPage = null;
		$cookieStore.remove('globals');
		$rootScope.globals = {};
		$scope.userData = null;
		$rootScope.$broadcast("logout-success");
		$location.path("/");
	}
	var callbackSuccess = function(data,headers) 
	{
		$rootScope.userData = data;
		loginService.setCredentials(data,headers);
		$rootScope.$broadcast("login-success");
		if($rootScope.redirectPage == "" || $rootScope.redirectPage == null)
			$location.path('/categoryList');
		else
			$location.path($rootScope.redirectPage);
	};

	var callbackError = function() 
	{
		$scope.errorMessage = "Invalid Credentials";   
	};
	
});

loginModule.factory('loginService',function($rootScope, $location, $cookieStore,$window, $http)
{
	var loginService = {};
	
	loginService.validateUser = function(loginCredentials,callbackSuccess,callbackError)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/user/validateUser", loginCredentials)
		.success(function(data, status,headers) 
		{
			if (data != "") 
			{
				callbackSuccess(data, headers);
			} 
			else 
			{
				callbackError();
			}
				
		});
	}
	loginService.setCredentials = function (data,headers) {
   	 //Setting of Auth ID
   	var authToken = headers('Authorization');
    
        $rootScope.globals = {
        		 				userData: {
		        							id: data.id,
						                    role:data.role,
						                    token:authToken
        		 							}
        					};
        $http.defaults.headers.common['Authorization'] = authToken;
	             
	    $cookieStore.put('globals', $rootScope.globals);
	}
	
	return loginService;
});
