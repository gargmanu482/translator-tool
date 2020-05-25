
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">

<title>Spring boot and Angularjs Tutorial</title>


<%@ page isELIgnored="false"%>
<link rel="icon" href="data:;base64,=">

</head>

<style type="text/css">
#main {
	width: 500px;
	border: 1px solid black;
	margin: 10px;
}

#scrolling {
	overflow-y: scroll;
	height: 500px;
}

.highlight {
	background-color: black;
	color: white;
	font-weight: bold;
}
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<body ng-app="app" ng-controller="mappingController">
	<table align="center"
		style="border-bottom-color: black; border-style: groove;" width="50%"
		border="1">
		<tr align="center">
			<td style="margin: 20px;">Client <!-- <input type="text" ng-model="client"> -->

				<select ng-model="clientName" ng-change="fetchLayout()">
					<option ng-value="-1">Please select</option>
					<option ng-repeat="x in clientList" ng-value="{{x}}">{{x}}</option>
			</select>
			</td>
			<td></td>

			<td><span style="margin: 15px;">Auto Generated Mapping Id</span>
			</td>
		</tr>
		<tr align="center">

			<td><span style="border: 1px solid black; margin: 15px;">Input
					Layout Name</span> <select ng-model="selectedInputLayout"
				ng-options="x.name for x in layoutData ">
			</select>

				<div id=main>
					<div id=scrolling>
						<table  border="1" width="100%">
							<thead>
								<tr>
									<td ng-if="selectedInputLayout.fields[0].name">Name</td>
									<td ng-if="selectedInputLayout.fields[0].tagName">Type</td>
									<td ng-if="selectedInputLayout.fields[0].minOccurs">Req/Opt</td>
								</tr>
							</thead>
							<tr ng-repeat="x in selectedInputLayout.fields"
								ng-click="inputLayout(x)" ng-class="{'highlight':x==input}">
								<td ng-if="x.name">{{x.name}}</td>
								<td ng-if="x.tagName">{{x.tagName}}</td>
								<td ng-if="x.minOccurs">{{x.minOccurs}}</td>
								<!-- <td ng-if="x.fileExtension == 'xsd'">{{x.fileExtension}} {{x.minOccurs}}</td> -->
								<!-- <td>{{x.isOutputfile}}</td> -->

							</tr>
						</table>
						<!-- 					<pre>{{selectedInputLayout.fields | json}}</pre> -->
					</div>
				</div>
				<button>Field Identification</button></td>
			<td valign="top"><span
				style="border: 1px solid black; margin: 15px;">Output Layout
					Name</span> <select ng-model="selectedOutputLayout"
				ng-options="x.name for x in layoutData">
			</select>

				<div id=main>
					<div id=scrolling>
						<table border="1" width="100%">
							<thead>
								<tr>
									<td>Name</td>
									<td>Type</td>
								</tr>
							</thead>
							<tr ng-click="outputLayout(x)"
								ng-repeat="x in selectedOutputLayout.fields"
								ng-class="{'highlight':x==output}">
								<td>{{x.name}}</td>
								<td>{{x.tagName}}</td>
								<!-- <td ng-if="x.fileExtension == 'xsd'">{{x.fileExtension}}
									{{x.minOccurs}}</td> -->
							</tr>
						</table>
						<!-- 				<pre>{{selectedOutputLayout.fields | json}}</pre> -->
						<!-- 				<div ng-repeat="x in selectedOutputLayout.fields">{{x.name}}&emsp;|&emsp;{{x.tagName}}&emsp;|&emsp;{{x.length}}</div> -->
					</div>
				</div>
				<button>Mapping Options</button> &nbsp;&nbsp;&nbsp;&nbsp;
				<button ng-click="addToMapping()">-></button></td>
			<td valign="top">
				<div id=main>
					<div id=scrolling>
						Mapping Name</br>
						<!-- <p>{{input.$$watchers[1].last}}{{out.$$watchers[1].last}}</p> -->
						<p ng-repeat="x in mappingArray">{{x}}</p>
					</div>
				</div>

			</td>
		</tr>
	</table>

	<script>
		var app = angular.module('app', []);
		app
				.controller(
						'mappingController',
						[
								'$scope',
								'$window',
								'$http',
								'$filter',
								function($scope, $window, $http, $filter) {

									/* $scope.inputLayout = [{
										"layoutId": "1",
									    "name": "MT202",
									    "client": "HSBC",
									    "fields": null
										}, {

										"layoutId": "2",
										 "name": "MT203",
										 "client": "Citi",
										 "fields": null

										}];
									$scope.outputLayout = [{
										"layoutId": "1",
									    "name": "MT201",
									    "client": "HSBC",
									    "fields": null
										}, {

										"layoutId": "2",
										 "name": "MT204",
										 "client": "Citi",
										 "fields": null

										}]; */
									console.log("Inside mapping controller");
									//fetch client list
									var clientUrl = "/client/all";
									$scope.clientName = "-1";
									$http({
										method : "GET",
										url : clientUrl
									})
											.then(
													function(response) {
														//								$scope.uploadResult = response.data;
														console
																.log("Client Data----"
																		+ response.data);
														$scope.clientList = response.data;
														$scope.clientName = "-1";

													},
													function(response) {
														console
																.log(response.data);
														$scope.clientList = "Error occurred, Please try again.";
													});
									//fetch layout for selected client
									$scope.fetchLayout = function() {
										console
												.log("Layout Selected for client::: "
														+ $scope.clientName);
										var client = $scope.clientName;
										var layoutUrl = "/client/" + client
												+ "/layouts";

										$http({
											method : "GET",
											url : layoutUrl
										})
												.then(
														function(response) {
															//									
															console
																	.log("Layout Data----"
																			+ JSON
																					.stringify(response.data));

															$scope.layoutData = response.data;
															console
																	.log("filtered data::: "
																			+ $filter(
																					'filter')
																					(
																							$scope.layoutData,
																							{
																								name : "INPUT"
																								
																							}));

														},
														function(response) {
															console
																	.log(response.data);
															$scope.clientList = "Error occurred, Please try again.";
														});
										$scope.inputLayout=(e)=>{ 
											 $scope.input=e;
											 console.log(e.name)
											
										}
										$scope.outputLayout=(e)=>{ 

											$scope.output=e;
											console.log(e.name)
											
										}
										$scope.mappingArray=[];
										$scope.addToMapping=()=>{
											 console.log($scope.input.name+"-->"+$scope.output.name);
											$scope.mappingArray.push($scope.input.name+"-->"+$scope.output.name+"="+$scope.input.name.substring(0,2)+$scope.output.name.substring(0,2))
											console.log($scope.mappingArray)
										}

									}
								} ]);
	</script>

</body>
</html>