
<%@ page contentType="text/html;charset=UTF-8"%>


<!DOCTYPE html>
 <html lang="en"  class="no-js">
<head>
    <meta charset="utf-8">
    
    <title>Spring boot and Angularjs Tutorial</title>
    
   
    <%@ page isELIgnored="false" %>
</head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.3.0-beta.3/angular.min.js"></script>

<script type="text/javascript">
var app = angular.module('app', []);
app.controller('TestController', function($scope) {
	console.log("inside controller");
	$scope.firstName= "John";
	  $scope.lastName= "Doe";
});
</script>
<body ng-app="app" ng-controller="TestController">
<h2>Test page :: </h2>{{firstName + " " + lastName}}


</body>
</html>