/**
 * 
 */
var configModule = angular.module('app')
.config(function($routeProvider) {
		$routeProvider
		.when("/", {
			templateUrl : "partial/welcome.html",
			controller : "welcomeController",
			controllerAs : "welcomeCtrl"
		}).when("/welcome", {
			templateUrl : "partial/welcome.html",
			controller : "welcomeController",
			controllerAs : "welcomeCtrl"
		}).when("/login", {
			templateUrl : "partial/login.html",
			controller : "loginController",
			controllerAs : "loginCtrl"
		}).when("/categoryList", {
			templateUrl : "partial/categoryList.html",
			controller : "categoryListController"
		}).when("/category/:id", {
			templateUrl : "partial/category.html",
			controller : "categoryController",
			controllerAs : "categoryCtrl"
		}).when("/project/:id", {
			templateUrl : "partial/project.html",
			controller : "projectController",
			controllerAs : "projectCtrl"
		}).when("/projectList", {
			templateUrl : "partial/projectList.html",
			controller : "projectListController"
		}).when("/register", {
			templateUrl : "partial/register.html",
			controller : "registerController",
			controllerAs : "registerCtrl"
		}).when("/addProject", {
			templateUrl : "partial/addProject.html",
			controller : "addProjectController",
			controllerAs : "addProjectCtrl"
		}).when("/cardDetails/:id", {
			templateUrl : "partial/cardDetails.html",
			controller : "cardDetailsController",
			controllerAs : "cardDetailsCtrl"
		}).when("/donations", {
			templateUrl : "partial/donations.html",
			controller : "donationsController",
			controllerAs : "donationsCtrl"
		}).when("/reports", {
			templateUrl : "partial/reports.html",
			controller : "reportController",
			controllerAs : "reportCtrl"
		}).when("/donateTime/:id", {
			templateUrl : "partial/donateTime.html",
			controller : "donateTimeController",
			controllerAs : "donateTimeCtrl"
		}).when("/editProfile", {
			templateUrl : "partial/editProfile.html",
			controller : "editProfileController"
		}).when("/myProjects", {
			templateUrl : "partial/myProjects.html",
			controller : "myProjectsController"
		}).when("/Cart", {
			templateUrl : "partial/Cart.html",
			controller : "cartController"
		}).otherwise({
			redirectTo : '/'
		});

	} ).run();

