<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">

<title>Translator Tool Login Page</title>
<link rel="icon" href="data:;base64,=">

<%@ page isELIgnored="false"%>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


	<body ng-app="app" ng-controller="loginController">
	<div align="center">
		<h1>Login Page</h1>
	</div>
	
	<div align="center">
		<form class="form-horizontal" name="login">
			<table align="center"
				style="border-bottom-color: green; border-style: groove;"
				width="30%" border="1">
				<tr align="center">
					<td>Name</td>
					<td><input id="name" name="name" type="text"
						size="40" ng-model="name" ng-required="true"></input>
					</td>
				</tr>
				<tr align="center">
					<td>Password</td>
					<td><input id="password" name="password" type="password"
						size="40" ng-model="password" ng-required="true"></input></td>
				</tr>
				<tr align="center">
					<td colspan=2>
						<button type="submit" class="btn btn-default"
							ng-click="validValues()">Upload Page</button>
					</td>
				</tr>
			</table>
			
		</form>
		
		<hr />
		<div class="col-sm-offset-2">
			<p ng-bind="loginMessage"></p>
		</div>
	</div>
	
</body>


<script type="text/javascript">
	var app = angular.module('app', []);
	
	app
			.controller(
					'loginController',
					[
							'$scope',
							'$window',
							'$http',
							function($scope, $window, $http) {
								console.log("Inside login controller");
								$scope.loginMessage = "";
								
								$scope.validValues=function(){
									console.log("Inside uploadPage");
									
																	
									
									if (angular.isUndefined($scope.name)
											|| angular.isUndefined($scope.password)
										) {
										$window.alert('Please insert values for all the fields');
										
										return false;
									}
									else{ 
										var url = "/login";
										var data = new FormData();
										data.append('name', $scope.name);
										data.append('password', $scope.password);
										console.log($scope.password)
										var config = {
											transformRequest : angular.identity,
											transformResponse : angular.identity,
											headers : {
												'Content-Type' : undefined
											}
										}
										
										$http
										.post(url,data,config)
										.then(
														function(response) {
															console.log(response.data);
															$window.location.href="/uploadPage";
														},
														function(response) {
															console.log(response.data);
															$scope.loginMessage = "Invalid credentials";
														});
										}
									};
									
									
					} ]);
</script>
</html>
