var app=angular.module("catAdoption", ["ngRoute"]);

app.controller("homeController", function($scope, $http, $location, $routeParams){
	//$scope.poruka="daca";
	var urlCats="/api/cats";
	var urlColors="/api/colors";
	var urlLocations="/api/locations";
	var urlBreeds="/api/breeds";
	var urlAges="/api/ages";


	$scope.cats=[];
	$scope.locations=[];
	$scope.breeds=[];
	$scope.ages=[];
	$scope.colors=[];

	$scope.locationSearch="";
	$scope.breedSearch="";
	$scope.sexSearch="";
	$scope.ageSearch="";
	$scope.colorSearch="";

	$scope.pageNum=0;
	$scope.totalPages=1;

	var getCats=function(){
		var config={params:{}};
		config.params.page=$scope.pageNum;
		if($scope.sexSearch!=""){
			config.params.sex=$scope.sexSearch;
		}
		if($scope.colorSearch!=""){
			config.params.colorId=$scope.colorSearch;
		}
		if($scope.locationSearch!=""){
			config.params.locationId=$scope.locationSearch;
		}
		if($scope.breedSearch!=""){
			config.params.breedId=$scope.breedSearch;
		}
		if($scope.ageSearch!=""){
			config.params.ageId=$scope.ageSearch;
		}
		$http.get(urlCats, config).then(
			function success(res){
            
				$scope.cats=res.data;
				//console.log($scope.url);
				$scope.totalPages=res.headers("totalPages");
				//console.log(res);
			},
			function error(res){
				alert("Error happened while loading data about cats.");
			}
		);
	};
	getCats();

	$scope.reset=function(){
		$scope.locationSearch="";
		$scope.breedSearch="";
		$scope.sexSearch="";
		$scope.ageSearch="";
		$scope.colorSearch="";
		getCats();
	};

	var getLocations=function(){
		$http.get(urlLocations).then(
			function success(res){
				$scope.locations=res.data;
			},
			function error(res){
				alert("Error happened while loading data about locations.");
			}
		);
	};
	getLocations();

	var getBreeds=function(){
		$http.get(urlBreeds).then(
			function success(res){
				$scope.breeds=res.data;
			},
			function error(res){
				alert("Error happened while loading data about breeds.");
			}
		);
	};
	getBreeds();

	var getAges=function(){
		$http.get(urlAges).then(
			function success(res){
				$scope.ages=res.data;
			},
			function error(res){
				alert("Error happened while loading data about ages.");
			}
		);
	};
	getAges();

	var getColors=function(){
		$http.get(urlColors).then(
			function success(res){
				$scope.colors=res.data;
			},
			function error(res){
				alert("Error happened while loading data about colors.");
			}
		);
	};
	getColors();

	$scope.changePage=function(n){
		$scope.pageNum=$scope.pageNum+n;
		getCats();
	};

	$scope.search=function(){
		getCats();
	};

	$scope.moreInfo=function(id){
		$location.path("/cats/"+id+"/info");
	};



});



app.controller("editController", function($scope, $http, $location, $routeParams){
	if(localStorage.getItem("user")==null || localStorage.getItem("user")==""){
	return;
	}
	var urlCats="/api/users/cats";
	var urlLocations="/api/locations";
	var urlAges="/api/ages";

	$scope.cat={};
	$scope.locations=[];
	$scope.ages=[];
	$scope.age="";


	var getCat=function(){
		$http.get(urlCats+"/"+$routeParams.id+"/update").then(
			function success(res){
				$scope.cat=res.data;
			},
			function error(res){
				if(res.status===404){
					alert("Content does not exist.");
						$location.path("/profile");
						return;
				}
				if(res.status===403){
					alert("Access denied.");
						$location.path("/profile");
						return;					
				}
				alert("Error happened while loading data about cat.");
				
			}
		);
	};
	getCat();

	var getLocations=function(){
		$http.get(urlLocations).then(
			function success(res){
				$scope.locations=res.data;
			},
			function error(res){
				alert("Error happened while loading data about locations.");
			}
		);
	};
	getLocations();

	var getAges=function(){
		$http.get(urlAges).then(
			function success(res){
				$scope.ages=res.data;
			},
			function error(res){
				alert("Error happened while loading data about ages.");
			}
		);
	};
	getAges();

	var validationForUpdate=function(){
		if($scope.cat.name=="" || $scope.cat.description=="" || $scope.cat.locationId=="" || $scope.cat.ageId==""){
			alert("Please enter all data.");
			return false;
		}
		return true;
	};

	$scope.edit=function(){
		if(!validationForUpdate()){
			return;
		}
		var confirmation=confirm("Are you sure you want to edit data?");
		if(!confirmation){
			return;
		}
		$http.put(urlCats+"/"+$routeParams.id, $scope.cat).then(
			function success(res){
				//alert("Uspješno ste izmijenili podatke.");
				$location.path("/profile");
			},
			function error(res){
				if(res.status===403){
					alert("Access denied.");
						$location.path("/profile");
						return;
				}
				else{
					alert("Error happened.");
				}
				
			}
		);
	};

	$scope.back=function(){
		$location.path("/profile");
	};


});

app.controller("infoController", function($scope, $http, $location, $routeParams){
	var urlCats="/api/cats";
	$scope.cat={};

	var getCat=function(){
		$http.get(urlCats+"/"+$routeParams.id+"/info").then(
			function success(res){
				$scope.cat=res.data;
			},
			function error(res){
				alert("Error happened while loading data about cat.");
				$location.path("/#/");
			}
		);
	};
	getCat();

	$scope.back=function(){
		$location.path("/#/");
	};

});

app.controller("registerController", function($scope, $http, $location, $routeParams){
	$scope.user={};
	$scope.user.username="";
	$scope.user.password="";
	$scope.user.phone="";

	var validationForRegistration=function(){
		if($scope.user.username=="" || $scope.user.password=="" || $scope.user.phone==""
			|| isNaN($scope.user.phone)){
			return false;
		}
		return true;
	};

	$scope.register=function(){
		if(!validationForRegistration()){
			alert("Please enter all valid data.");
			return;
		}
		$http.post("/api/users/register", $scope.user).then(
			function success(){
				alert("You registered successfully.\nPlease log in.");
				//sessionStorage.setItem("user", $scope.korisnik.korisnickoIme);
				$location.path("/login");
			},
			function error(res){
				if(res.status===409){
					alert("Username is already taken.");
				}
				else{
					alert("Error happened.");
				}
			}
		);
	};


});

app.controller("loginController", function($scope, $http, $location, $routeParams){
	$scope.user={};
	$scope.user.username="";
	$scope.user.password="";

	var validacijaZaPrijavu=function(){
		if($scope.user.username=="" || $scope.user.password==""){
			return false;
		}
		return true;
	};	

	$scope.login=function(){
		if(!validacijaZaPrijavu()){
			alert("Please enter username and password.");
			return;
		}
		$http.post("/api/users/login", $scope.user).then(
			function success(res){
				localStorage.setItem("user", $scope.user.username);
				$location.path("/profile");
			},
			function error(res){
					alert("No user with given username and password.");
			}
		);
	};


});

app.controller("profileController", function($scope, $http, $location, $routeParams){
	if(localStorage.getItem("user")==null || localStorage.getItem("user")==""){
		return;
	}
	if(localStorage.getItem("user")=="admin"){
		$location.path("/admin");
		return;
	}
	$scope.user=localStorage.getItem("user");
	$scope.cats=[];
	var urlCats="/api/users/cats";
	var urlUsers="/api/users";
	var urlColors="/api/colors";
	var urlLocations="/api/locations";
	var urlBreeds="/api/breeds";
	var urlAges="/api/ages";

	$scope.hide;

	$scope.newCat={};
	$scope.newCat.name="";
	$scope.newCat.description="";
	$scope.newCat.locationId="";
	$scope.newCat.breedId="";
	$scope.newCat.sex="";
	$scope.newCat.ageId="";
	$scope.newCat.colorId="";



	var getCats=function(){
		$http.get(urlCats).then(
			function success(res){
				$scope.cats=res.data;
				if($scope.cats.length==0){
					$scope.hide=true;
				}
				else{
					$scope.hide=false;
				}
			},
			function error(res){
				alert("Error happened while loading data about cats.");
			}
		);
	};
	getCats();

	var getLocations=function(){
		$http.get(urlLocations).then(
			function success(res){
				$scope.locations=res.data;
			},
			function error(res){
				alert("Error happened while loading data about locations.");
			}
		);
	};
	getLocations();

	var getBreeds=function(){
		$http.get(urlBreeds).then(
			function success(res){
				$scope.breeds=res.data;
			},
			function error(res){
				alert("Error happened while loading data about breeds.");
			}
		);
	};
	getBreeds();

	var getAges=function(){
		$http.get(urlAges).then(
			function success(res){
				$scope.ages=res.data;
			},
			function error(res){
				alert("Error happened while loading data about ages.");
			}
		);
	};
	getAges();

	var getColors=function(){
		$http.get(urlColors).then(
			function success(res){
				$scope.colors=res.data;
			},
			function error(res){
				alert("Error happened while loading data about colors.");
			}
		);
	};
	getColors();

	var validationForNewCat=function(){
		if($scope.newCat.name=="" || $scope.newCat.description=="" || $scope.newCat.locationId=="" || $scope.newCat.breedId==""
			|| $scope.newCat.sex=="" || $scope.newCat.ageId=="" || $scope.newCat.colorId=="" 
			|| document.getElementById('imageToAdd').value==""){
			alert("Please enter all data.");
			return false;
		}
		return true;
	};

	var encodeImageInBase64=function(image, callback){
		//var image=document.getElementById('imageToAdd').files[0];
		var reader=new FileReader();

		reader.onloadend=function() {
		    callback(reader.result);
		}
 		reader.readAsDataURL(image);
	};

	$scope.add=function(){
		if(!validationForNewCat()){
			return;
		}
		var image=document.getElementById('imageToAdd').files[0];
		encodeImageInBase64(image, function(base64String){
		$scope.newCat.imageBase64=base64String.substr(base64String.indexOf(',')+1);

			$http.post(urlCats, $scope.newCat).then(
				function success(){
					getCats();
					alert("Data about cat added successfully.\nAdmin must approve content.");
					$scope.newCat.name="";
					$scope.newCat.description="";
					$scope.newCat.locationId="";
					$scope.newCat.breedId="";
					$scope.newCat.sex="";
					$scope.newCat.ageId="";
					$scope.newCat.colorId="";
					document.getElementById('imageToAdd').value="";
					$scope.newCat.imageBase64="";
				},
				function error(){
					alert("Error happened while adding data about cat.");

				}
			);
		});
	};

	$scope.edit=function(id){
		$location.path("/cats/"+id+"/edit");
	};


	$scope.delete=function(id){
		var confirmation=confirm("Are you sure you want to delete data about cat?");
		if(!confirmation){
			return;
		}
		$http.delete(urlCats+"/"+id).then(
			function succes(){
				//alert("Uspješno ste izbrisali podatke o maci.");
				getCats();
			},
			function error(res){
				if(res.status===403){
					alert("Access denied.");
					return;
				}				
				alert("Error happened while deleting data about cat.");
			}
		);
	};

	$scope.logout=function(){
		$http.get(urlUsers+"/logout").then(
			function success(){
				localStorage.setItem("user", "");
				$location.path("/login");
			},
			function error(){
				alert("Error happened.");
			}
		);
	};


});

app.controller("adminController", function($http, $scope, $location){
	var urlCats="/api/users/cats";
	var urlUsers="/api/users";
	$scope.cats=[];
	$scope.show;

	var getCats=function(){
		$http.get(urlCats+"/unapproved-content").then(
			function success(res){
				$scope.cats=res.data;
				if($scope.cats.length==0){
					$scope.show=true;
				}
				else{
					$scope.show=false;
				}
			},
			function error(res){
				alert("Error happened while loading data about unapproved content.");
			}
		);
	};
	getCats();

	$scope.approve=function(id){
		var confirmation=confirm("Are you sure you want to approve the content?");
		if(!confirmation){
			return;
		}
		
		$http.put(urlCats+"/"+id+"/approve").then(
			function success(){
				//alert("Sadrzaj uspjesno odobren.");
				getCats();
			},
			function error(res){
				if(res.status===403){
					alert("Access denied.");
					return;
				}
				alert("Error happened.");
			}
		);
	};

	$scope.delete=function(id){
		var confirmation=confirm("Are you sure you want to delete data?");
		if(!confirmation){
			return;
		}
		
		$http.delete(urlCats+"/"+id).then(
			function succes(){
				getCats();
			},
			function error(res){
				if(res.status===403){
					alert("Access denied.");
					return;
				}
				alert("Error happened.");
			}
		);
	};





	$scope.logout=function(){
		$http.get(urlUsers+"/logout").then(
			function success(){
				localStorage.setItem("user", "");
				$location.path("/login");
			},
			function error(){
				alert("Error happened.");
			}
		);
	};
});


app.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : '/app/html/home.html',
		})
		.when('/cats/:id/edit', {
			templateUrl : '/app/html/cat-update.html',
				resolve: {
					loggedIn: function($location){
						if(localStorage.getItem("user")==null || localStorage.getItem("user")==""){
							$location.path("/login");
						}
					}
				}			
		})
		.when('/cats/:id/info', {
			templateUrl : '/app/html/cat-info.html'
		})
		.when('/register', {
			templateUrl : '/app/html/registration.html'
		})
		.when('/login', {
			templateUrl : '/app/html/login.html'
		})
		.when('/admin', {
			templateUrl : '/app/html/admin-page.html',
				resolve: {
					loggedInAsAdmin: function($location){
						if(localStorage.getItem("user")!="admin"){
							$location.path("/login");
						}
					}
				}			
		})
		.when('/profile', {
				templateUrl : '/app/html/profile.html',
				resolve: {
					loggedIn: function($location){
						if(localStorage.getItem("user")==null || localStorage.getItem("user")==""){
							$location.path("/login");
							return;
						}
						if(localStorage.getItem("user")=="admin"){
							$location.path("/admin");
						}
					}
				}
		})	
		.otherwise({
			redirectTo: '/'
		});
}]);