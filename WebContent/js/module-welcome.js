/**
 * 
 */
var welcomeModule = angular.module("welcomeModule");

welcomeModule.controller('welcomeController', function($scope,$rootScope,$location) 
{
	$scope.userData = $rootScope.userData;
});