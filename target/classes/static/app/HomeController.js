app.controller('homeController',['$scope','$window','$http', function($scope) {
	console.log("inside controller");
	$scope.firstName= "John";
	  $scope.lastName= "Doe";
	  
	  console.log("inside controller");
	  
	  $scope.uploadFile = function() {
		  console.log('inside function ' );
          var file = $scope.myFile;
          console.log('file is ' );
          //console.dir(file);
          var uploadUrl = "/fileUpload";
          //fileUpload.uploadFileToUrl(file, uploadUrl);
       };
}]);
 