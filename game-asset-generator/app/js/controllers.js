'use strict';

var controllerDefinitions = angular.module('myApp.controllers', []);

controllerDefinitions.factory('gAnalyticsTrackService',
    [ function gAnalyticsTrackService() {
	    return function() {
		    // ga('send', 'pageview');
		    console.log('tracked something...');
	    };
    } ]);

controllerDefinitions.factory('authService', [ '$firebase',

function authServiceFactory($firebase) {
	var ref = new Firebase("https://game-asset-generator.firebaseio.com/");

	var anAuthServiceInstance = {

	  firebaseRef : ref,

	  loginWith : function(provider) {
		  console.log('logging in with: ' + provider);
		  // use the defined object to authenticate via facebook
		  auth.login(provider);
	  },

	  isLoggedIn : function() {
		  return this.user !== null;
	  },

	  getProvider : function() {
		  return this.user.provider;
	  },

	  getUserDisplayName : function() {
		  return this.user.displayName;
	  },

	  user : null
	};

	// define an object to handle authentication at firebase
	var auth = new FirebaseSimpleLogin(ref, function(error, user) {
		if (error) {
			// an error occurred while
			// attempting login
			console.log(error);
		} else if (user) {
			// user authenticated with Firebase
			console.log('User ID: ' + user.id + ', Provider: ' + user.provider);
			anAuthServiceInstance.user = user;
			// $scope.$parent.naviLoginText = 'Welcome '
			// + user.displayName + ' logged in with: ';
		} else {
			// user is logged out
		}
	});

	return anAuthServiceInstance;
} ]);

controllerDefinitions.controller('nullCtrl', [ 'gAnalyticsTrackService',
    function nullCtrl(gAnalyticsTrackService) {
	    gAnalyticsTrackService();
    } ]);

controllerDefinitions.controller('firebaseExperimentsCtrl', [
    '$scope',
    '$firebase',
    'gAnalyticsTrackService',
    'authService',
    function firebaseExperimentsCtrl($scope, $firebase, gAnalyticsTrackService,
        authService) {
	    gAnalyticsTrackService();

	    $scope.messages = $firebase(authService.firebaseRef);

	    $scope.addMessage = function(e) {
		    if (e.keyCode != 13)
			    return;
		    $scope.messages.$add({
		      name : $scope.newVarName,
		      value : $scope.newVarValue
		    });
		    $scope.msg = "";
	    };
    } ]);

controllerDefinitions
    .controller(
        'gDriveExperimentsCtrl',
        [
            'gAnalyticsTrackService',
            '$http',
            '$scope',
            function gDriveExperimentsCtrl(gAnalyticsTrackService, $http,
                $scope) {
	            gAnalyticsTrackService();
	            console.log('gDriveExperimentsCtrl entered');

	            $scope.googleServerResponseData = 'Use the button to send a request to Google App Server .';

	            $scope.sendHttpToGoogleAppEngine = function() {
		            var googleHttpService = $http({
		              method : 'GET',
		              url : 'http://1-dot-expanded-system-536.appspot.com/game_asset_generator_server/uploadResource'
		            });
		            googleHttpService.success(function(data, status, headers,
		                config) {
			            // this callback will be called
			            // asynchronously
			            // when the response is
			            // available
			            console.log('Call to UploadResourceServlet with success: ');
			            console.log('data:', data);
			            console.log('status:', status);
			            console.log('headers:', headers);
			            console.log('config:', config);

			            $scope.googleServerResponseData = data;
		            });

		            googleHttpService
		                .error(function(data, status, headers, config) {
			                // called asynchronously if an
			                // error occurs
			                // or server returns response
			                // with an error status.
			                console.log('Call to UploadResourceServlet with error: ');
			                console.log('data:', data);
			                console.log('status:', status);
			                console.log('headers:', headers);
			                console.log('config:', config);

			                $scope.googleServerResponseData = 'Error, see console';
		                });
	            }

            } ]);

controllerDefinitions.controller('navigationCtrl', [
    '$scope',
    '$location',
    'authService',
    function navigationCtrl($scope, $location, authService) {

	    /**
			 * returns the 'active' css class if the given page matches the current
			 * page.
			 */
	    $scope.getCssNaviClass = function(page) {
		    var currentRoute = $location.path().substring(1) || 'home';
		    return page === currentRoute ? 'active' : '';
	    };

	    $scope.userAuth = {};
	    $scope.naviLoginText = 'Login: ';

	    /**
			 * returns the 'active' css class if the given (authentication) provider
			 * matches the provider with is currently used for authentication.
			 */
	    $scope.getCssLoginClass = function(provider) {
		    if (authService.isLoggedIn()) {
			    var curProvider = authService.getProvider();
			    return provider === authService.getProvider() ? 'active' : '';
		    } else {
			    return '';
		    }
	    };

	    $scope.login = function(provider) {
		    authService.loginWith(provider);

		    if (authService.isLoggedIn()) {
			    $scope.naviLoginText = 'Welcome ' + authService.getUserDisplayName()
			        + '. Logged in with: ';
		    }
	    };
    } ]);

controllerDefinitions.controller('pixi01Ctrl', [ 'gAnalyticsTrackService',
    function pixi01Ctrl(gAnalyticsTrackService) {
	    gAnalyticsTrackService();

	    // create an new instance of a pixi stage
	    var stage = new PIXI.Stage(0x66FF99);

	    // create a renderer instance
	    // var renderer = new PIXI.WebGLRenderer(400, 300);
	    var renderer = new PIXI.autoDetectRenderer(400, 300);

	    // add the renderer view element to the DOM
	    document.getElementById('pixiCanvas').appendChild(renderer.view);

	    requestAnimFrame(animate);

	    // create a texture from an image path
	    var texture = PIXI.Texture.fromImage("img/gears.png");
	    // create a new Sprite using the texture
	    var bunny = new PIXI.Sprite(texture);

	    // center the sprites anchor point
	    bunny.anchor.x = 0.5;
	    bunny.anchor.y = 0.5;

	    // move the sprite t the center of the screen
	    bunny.position.x = 200;
	    bunny.position.y = 150;

	    stage.addChild(bunny);

	    function animate() {

		    requestAnimFrame(animate);

		    // just for fun, lets rotate mr rabbit a little
		    bunny.rotation += 0.1;

		    // render the stage
		    renderer.render(stage);
	    }
    } ]);

controllerDefinitions.controller('pixi02Ctrl', [ 'gAnalyticsTrackService',
    function pixi02Ctrl(gAnalyticsTrackService) {
	    gAnalyticsTrackService();
	    // create an array of assets to load
	    var assetsToLoader = [ "img/pixiExperiments/ownSpriteSheet.json" ];

	    // create a new loader
	    var loader = new PIXI.AssetLoader(assetsToLoader);

	    // use callback
	    loader.onComplete = onAssetsLoaded

	    // begin load
	    loader.load();

	    // holder to store aliens
	    var aliens = [];
	    var alienFrames = [ "flag1.png", "Passage.png" ];

	    var count = 0;

	    // create an new instance of a pixi stage
	    var stage = new PIXI.Stage(0xABCDEF);
	    ;

	    // create a renderer instance.
	    var renderer = PIXI.autoDetectRenderer(800, 600);

	    // add the renderer view element to the DOM
	    document.getElementById('pixiCanvas').appendChild(renderer.view);

	    // create an empty container
	    var alienContainer = new PIXI.DisplayObjectContainer();
	    alienContainer.position.x = 400;
	    alienContainer.position.y = 300;

	    stage.addChild(alienContainer);

	    function onAssetsLoaded() {

		    // create a texture from an image path
		    // add a bunch of aliens
		    for (var i = 0; i < 10; i++) {
			    var frameName = alienFrames[i % 2];

			    // create an alien using the frame name..
			    var alien = PIXI.Sprite.fromFrame(frameName);

			    /*
					 * fun fact for the day :) another way of doing the above would be var
					 * texture = PIXI.Texture.fromFrame(frameName); var alien = new
					 * PIXI.Sprite(texture);
					 */

			    alien.position.x = Math.random() * 800 - 400;
			    alien.position.y = Math.random() * 600 - 300;
			    alien.anchor.x = 0.5;
			    alien.anchor.y = 0.5;
			    aliens.push(alien);
			    alienContainer.addChild(alien);

			    // TODO: remove git test
		    }

		    // start animating
		    requestAnimFrame(animate);

	    }

	    function animate() {

		    requestAnimFrame(animate);

		    // just for fun, lets rotate mr rabbit a little
		    for (var i = 0; i < 10; i++) {
			    var alien = aliens[i];
			    alien.rotation += 0.1;
		    }

		    count += 0.01;
		    alienContainer.scale.x = Math.sin(count);
		    alienContainer.scale.y = Math.sin(count);

		    alienContainer.rotation += 0.01;
		    // render the stage
		    renderer.render(stage);
	    }
    } ]);