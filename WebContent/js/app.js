'use strict'; 
angular.module('appCoreModule', ['ngRoute','ngCookies']);
 
//Declare modules
 angular.module("loginModule",[]);
 angular.module("registerModule",[]);
 angular.module("projectModule",[]);
 angular.module("welcomeModule",[]);
 angular.module("projectListModule",[]);
 angular.module("categoryListModule",[]);
 angular.module("categoryModule",[]);
 angular.module("addProjectModule",[]);
 angular.module("cardDetailsModule",[]);
 angular.module("donationsModule",[]);
 angular.module("reportModule",[]);
 angular.module("donateTimeModule",[]);
 angular.module("editProfileModule",[]);
 angular.module("myProjectsModule",[]);
 angular.module("cartModule",[]);
 
 //Register App
 var app = angular.module("app", [
	 'appCoreModule',
	 'loginModule',
	 'registerModule',
	 'projectModule',
	 'welcomeModule',
	 'projectListModule',
	 'categoryListModule',
	 'categoryModule',
	 'addProjectModule',
	 'cardDetailsModule',
	 'donationsModule',
	 'reportModule',
	 'donateTimeModule',
	 'editProfileModule',
	 'myProjectsModule',
	 'cartModule'
	 ]);