/**
 * 
 */
var cartModule = angular.module("cartModule");

cartModule.controller('cartController', function($scope, $routeParams, $rootScope,cartService) 
{
	$scope.userData = $rootScope.userData;
	$scope.getCart = function()
	{
		cartService.getCart($scope.userData.id,callBackSuccess);
	}
	$scope.postCardDetails = function()
	{
		$scope.date = new Date();
		var cardDetails = {
				"userId" : $scope.userData.id,
				"cardType" : $scope.cardType,
				"nameOnCard" : $scope.name,
				"cardNo" : $scope.cardNumber,
				"cvc" : $scope.cvc,
				"expDate" : $scope.exp
			};
		cartService.cardDetails(cardDetails, callBackSuccess1,callBackError1);
	}
	$scope.clearCart = function()
	{
		cartService.clearCart($scope.userData.id, callBackSuccess3);
	}
	
	var callBackSuccess = function(response) 
	{
		console.log("response :"+JSON.stringify(response.length));
		if(response.length > 0)
		{
			var totalAmount = 0;
			$scope.cartList = response;
			response.forEach(function(project, index)
			{
				totalAmount = totalAmount + project.amount;
			});
			$scope.totalAmount = totalAmount;
		}
		else
		{
			$scope.error = "You have no projects in the cart currently!!";
		}
			
	};
	var callBackSuccess1 = function(response) 
	{
		cartService.updateProjectAmount($scope.userData.id, callBackSuccess2,callBackError2);
	}
	var callBackSuccess2 = function() 
	{
		$('#myModal').modal('toggle');
		$scope.cartList = null;
		$scope.totalAmount = null;
		
	}
	var callBackSuccess3 = function() 
	{
		$scope.cartList = null;
		$scope.totalAmount = null;
		$location.path("/categoryList");
		
	}
	var callBackError1 = function(response) 
	{
	}
	var callBackError2 = function(response) 
	{
	}
});

cartModule.factory('cartService',function($http, $rootScope)
{
	var cartService = {};
	cartService.getCart = function(userId,callBackSuccess)
	{
		$http.get("http://localhost:8080/Final_web_project/rest/projects/getCart?id="+userId)
		.success(function(response) 
		{
			callBackSuccess(response);
		});
	}
	cartService.cardDetails = function(cardDetails, callBackSuccess1,callBackError1)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/user/addCardDetails", cardDetails)
		.success(function(data, status) 
		{
			callBackSuccess1();
				
		});
	}
	cartService.clearCart = function(userId,callBackSuccess3)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/clearCart", userId)
		.success(function(data, status) 
		{
			alert("Cart was successfully cleared");
			callBackSuccess3();
				
		});
	}
	cartService.updateProjectAmount = function(userId, callBackSuccess2,callBackError2)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/updateProjectNDonation", userId)
		.success(function(data, status) 
		{
			callBackSuccess2();
				
		});
	}
	return cartService;
});