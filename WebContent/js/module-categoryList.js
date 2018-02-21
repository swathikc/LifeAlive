/**
 * 
 */

var categoryListModule = angular.module("categoryListModule");

categoryListModule.controller('categoryListController', function($scope,$rootScope,$location,categoryListService,$timeout) 
{
	$scope.userData = $rootScope.userData;
	$scope.getCategoryList = function()
	{
		$scope.userData = $rootScope.userData;
		categoryListService.getCategoryList(callbackSuccess,callbackError);
	}
	$scope.deleteCategory = function(category)
	{
		categoryListService.deleteCategory(category.id,callbackSuccess2,callbackError2);
	}
	$scope.enableCategory = function(category)
	{
		categoryListService.enableCategory(category.id);
		alert("Successfully enabled category!");
		$scope.successTextAlert = "Successfully enabled category!!!";
		$scope.showSuccessAlert = true;
		$timeout(function () { $scope.showSuccessAlert = false; }, 3000);
		category.status = "valid";
	}
	
	$scope.addNewCategory = function()
	{
		console.log("category controller");
		var newCategory = {

				"categoryName" : $scope.categoryName,
				"description" : $scope.description,
				"image" : $scope.image
			};
		categoryListService.addCategory(newCategory,callbackSuccess1,callbackError1);
	};
	var callbackSuccess = function(response) 
	{
		$scope.categoryList = response;
		console.log("CategoryList :"+$scope.categoryList);
		$rootScope.categoryList = $scope.categoryList;
	};

	var callbackError = function() 
	{  
	};
	var callbackSuccess1 = function(response) 
	{
		if(response)
		{
			$('#myModal').modal('toggle');
			$scope.getCategoryList();
			$scope.successTextAlert = "Successfully added category!!!";
			$scope.showSuccessAlert = true;
			$timeout(function () { $scope.showSuccessAlert = false; }, 3000);
		}
		else
		{
			$scope.categoryExists = "Category already exists";
		}
			
		
	};

	var callbackError1 = function() 
	{  
	};
	var callbackSuccess2 = function() 
	{
		$scope.getCategoryList();
		$scope.successTextAlert = "Successfully Deleted!!";
		$scope.showSuccessAlert = true;
		$timeout(function () { $scope.showSuccessAlert = false; }, 3000);
	};
	// switch flag
	$scope.switchBool = function(value) {
	   $scope[value] = !$scope[value];
	};

	var callbackError2 = function() 
	{  
	};
});

categoryListModule.factory('categoryListService',function($http, $rootScope)
{
	var categoryListService = {};
	
	categoryListService.addCategory = function(newCategory,callbackSuccess1,callbackError1)
	{
		console.log("category controller service");
		$http.post("http://localhost:8080/Final_web_project/rest/category/addCategory", newCategory)
		.success(function(data, status) 
		{
			callbackSuccess1(data);
		});
	}
	
	categoryListService.getCategoryList = function(callbackSuccess,callbackError)
	{
		$http.get("http://localhost:8080/Final_web_project/rest/category/getCategoryList")
		.success(function(response) 
		{
			if(response.length > 0)
			{
				console.log("categoryList :"+response);
				callbackSuccess(response);
			}
			else
			{
				callbackError();
			}
			
		});
	}
	
	categoryListService.deleteCategory = function(id,callbackSuccess2,callbackError2)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/category/deleteCategory", id)
		.success(function(data, status) 
		{
			callbackSuccess2();
		});
	}
	
	categoryListService.enableCategory = function(id)
	{
		$http.post("http://localhost:8080/Final_web_project/rest/category/enableCategory", id)
	}
	return categoryListService;
});