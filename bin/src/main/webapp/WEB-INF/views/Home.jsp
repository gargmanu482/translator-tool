
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">

<title>Translator Tool</title>
<link rel="icon" href="data:;base64,=">

<!-- <link rel="stylesheet" href="src/main/resources/static/app.css">
<script src="src/main/resources/static/angular.js"
	type="text/javascript"></script>
<script src="src/main/resources/static/angular.min.js"
	type="text/javascript"></script>
<script src="src/main/resources/static/angular-resource.js"
	type="text/javascript"></script>
<script src="src/main/resources/static/angular-route.js"
	type="text/javascript"></script>
<script src="src/main/resources/static/app/app.js"
	type="text/javascript"></script>
<script src="src/main/resources/static/app/HomeController.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="src/main/resources/static/bootstrap.css"> -->
<%@ page isELIgnored="false"%>
</head>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>


<body ng-app="app" ng-controller="homeController">
	<div align="center">
		<h1>Translator tool</h1>
	</div>

	<div align="center">
		<form class="form-horizontal">
			<table align="center"
				style="border-bottom-color: green; border-style: groove;"
				width="30%" border="1">
				<tr align="center">
					<td>Layout Name</td>
					<td><input id="layoutName" name="layoutName" type="text"
						size="40" ng-model="layoutName" ng-required="true"></input></td>
				</tr>
				<tr align="center">
					<td>Client Name</td>
					<td><input id="clientName" name="clientName" type="text"
						size="40" ng-model="clientName" ng-required="true"></input></td>
				</tr>
				<tr align="center">


					<td align="left" colspan="2">
						<div class="form-group">
							<div class="col-sm-5">
								<input class="form-control" type="file"
									file-model="uploadedFile" placeholder="Upload File"
									ng-required="true"></input>

							</div>
						</div>
					</td>
				</tr>
				<tr align="center">
					<td align="center" >
						<button type="submit" class="btn btn-default"
							ng-click="doUploadFile()">Upload</button>
					</td>
					<td align="center" >
						<button type="submit" class="btn btn-default"
							ng-click="fnRedirect()">Redirect</button>
					</td>
				</tr>
			</table>

		</form>

		<hr />
		<div class="col-sm-offset-2">
			<p ng-bind="uploadResult"></p>
		</div>
	</div>


</body>

<script type="text/javascript">
	var app = angular.module('app', []);
	//directive
	app.directive('fileModel', [ '$parse', function($parse) {
		return {
			restrict : 'A',
			link : function(scope, element, attrs) {
				var model = $parse(attrs.fileModel);
				var modelSetter = model.assign;

				element.bind('change', function() {
					scope.$apply(function() {
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		};
	} ]);

	//Controller
	app
			.controller(
					'homeController',
					[
							'$scope',
							'$window',
							'$http',
							function($scope, $window, $http) {
								console.log("inside controller");
								$scope.firstName = "John";
								$scope.lastName = "Doe";

								$scope.uploadResult = "";

								$scope.doUploadFile = function() {
									console.log("layoutName::"+ $scope.layoutName);
									console.log("clientName::"+ $scope.clientName);
									console.log("uploadedFile::"+ $scope.uploadedFile);
									console.log(" undefined layoutName::"+ angular.isUndefined($scope.layoutName));
									console.log("undefined clientName::"+ angular.isUndefined($scope.clientName));
									console.log("undefined uploadedFile::"+ angular.isUndefined($scope.uploadedFile));

									if (angular.isUndefined($scope.layoutName)
											|| angular.isUndefined($scope.clientName)
											|| angular.isUndefined($scope.uploadedFile)) {
										$window.alert('Please insert values for all the fields');
										
										return false;
									} else {
										var file = $scope.uploadedFile;
										var url = "/layout";

										var data = new FormData();
										data.append('uploadfile', file);
										data.append('layoutName', $scope.layoutName);
										data.append('clientName', $scope.clientName);

										var config = {
											transformRequest : angular.identity,
											transformResponse : angular.identity,
											headers : {
												'Content-Type' : undefined
											}
										}

										$http
												.post(url, data, config)
												.then(
														function(response) {
// 															$scope.uploadResult = response.data;
															console.log(response.data);
															$scope.uploadResult = "File uploaded successfully!!";
															//$window.location.href="/fetch"

														},
														function(response) {
															console.log(response.data);
															$scope.uploadResult = "Error occurred, Please try again.";
														});
									}
								};

								$scope.fnRedirect=function(){
									console.log("Inside redirect");
									$window.location.href="/fetch";
									}

							} ]);
</script>
</html>