/**
 * 
 */
var cardDetailsModule = angular.module("cardDetailsModule");

cardDetailsModule.controller('cardDetailsController', function($scope, $routeParams, $rootScope, $location, cardDetailsService) 
{
	$scope.userData = $rootScope.userData;
	$scope.postCardDetails = function()
	{
		$scope.date = new Date();
		var cardNDonationInfo = {

				"userId" : $rootScope.userData.id,
				"cardType" : $scope.cardType,
				"nameOnCard" : $scope.name,
				"cardNo" : $scope.cardNumber,
				"cvc" : $scope.cvc,
				"expDate" : $scope.exp,
				"projectId" : $routeParams.id,
				"amount" : $scope.amount,
				"dateOfPayment" : $scope.date,
				"comment" : $scope.comment
			};
		cardDetailsService.cardDetails(cardNDonationInfo, callbackSuccess,callbackError);
	}
	var callbackSuccess = function() 
	{
		$location.path('/categoryList');
	};

	var callbackError = function() 
	{
	};
});

cardDetailsModule.factory('cardDetailsService',function($http, $rootScope)
{
	var cardDetailsService = {};
	
	cardDetailsService.cardDetails = function(cardNDonationInfo, callbackSuccess,callbackError)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/user/cardNDonation", cardNDonationInfo)
		.success(function(data, status) 
		{
			callbackSuccess();
				
		});
	}
	return cardDetailsService;

});