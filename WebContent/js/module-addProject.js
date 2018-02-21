/**
 * 
 */
var addProjectModule = angular.module("addProjectModule");
addProjectModule.controller('addProjectController',function($scope, $rootScope, $http, $location, addProjectService) 
{
	$scope.userData = $rootScope.userData;
	$scope.date = new Date();
	$scope.postCardDetails = function()
	{
		var cardDetails = {
				"userId" : $rootScope.userData.id,
				"cardType" : $scope.cardType,
				"nameOnCard" : $scope.name,
				"cardNo" : $scope.cardNumber,
				"cvc" : $scope.cvc,
				"expDate" : $scope.exp
			}
		addProjectService.cardDetails(cardDetails, callbackSuccess,callbackError);
	}
	$scope.addProject = function() 
	{		
		var category = JSON.parse($scope.selectedCategory);
		var newProject = {

			"projectName" : $scope.projectName,
			"categoryId" : category.id,
			"description" : $scope.projectDesc,
			"createdOn" : $scope.date,
			"creatorId" : $rootScope.userData.id,
			"amountRequired" : $scope.amountRequired,
			"deadline" : $scope.deadline
		};
		addProjectService.addProject(newProject,callbackSuccess,callbackError);
		
	};
	
	$scope.getCategoryList = function()
	{
		$scope.categoryList = $rootScope.categoryList;
	}
	var callbackSuccess = function() 
	{
		$location.path('/categoryList');
	};

	var callbackError = function() 
	{
		$scope.error = "Invalid credentials";   
	};
});

addProjectModule.factory('addProjectService',function($http, $rootScope)
{
	var addProjectService = {};
	
	addProjectService.addProject = function(newProject,callbackSuccess,callbackError)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/projects/addProject", newProject)
		.success(function(data, status) 
		{
			alert("Successfully added the project!")
			callbackSuccess();
		});
	}
	addProjectService.cardDetails = function(cardDetails, callbackSuccess,callbackError)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/user/addCardDetails", cardDetails)
		.success(function(data, status) 
		{
			$('#myModal').modal('toggle');
		});
	}
	return addProjectService;


});