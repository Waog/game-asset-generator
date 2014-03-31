'use strict';

var controllerDefinitions = angular.module('myApp.controllers', []);

var gAnalyticsService = controllerDefinitions.factory('gAnalyticsTrack',
        [ function() {
	        return function() {
		        // ga('send', 'pageview');
		        console.log('tracked something...');
	        }
        } ]);

var nullController = controllerDefinitions.controller('nullCtrl', [
        'gAnalyticsTrack', function(gAnalyticsTrack) {
	        gAnalyticsTrack();
        } ]);

var firebaseExperimentsController = controllerDefinitions.controller(
        'firebaseExperimentsCtrl', [
                '$scope',
                '$firebase',
                'gAnalyticsTrack',
                function($scope, $firebase, gAnalyticsTrack) {
	                gAnalyticsTrack();

	                var ref = new Firebase(
	                        "https://game-asset-generator.firebaseio.com/");
	                $scope.messages = $firebase(ref);

	                $scope.addMessage = function(e) {
		                if (e.keyCode != 13)
			                return;
		                $scope.messages.$add({
		                    name : $scope.newVarName,
		                    value : $scope.newVarValue
		                });
		                $scope.msg = "";
	                };

	                // define an object to handle authentication at firebase
	                var auth = new FirebaseSimpleLogin(ref, function(error,
	                        user) {
		                if (error) {
			                // an error occurred while
			                // attempting login
			                console.log(error);
		                } else if (user) {
			                // user authenticated with Firebase
			                console.log('User ID: ' + user.id + ', Provider: '
			                        + user.provider);
			                $scope.$parent.userAuth = user;
			                $scope.$parent.naviLoginText = 'Welcome '
			                        + user.displayName + ' logged in with: ';
		                } else {
			                // user is logged out
		                }
	                });

	                // use the defined object to authenticate via facebook
	                auth.login('facebook');
                } ]);

var navigationController = controllerDefinitions.controller('navigationCtrl', [
        '$scope',
        '$location',
        function($scope, $location) {

	        /**
			 * returns the 'active' css class if the given page matches the
			 * current page.
			 */
	        $scope.getCssNaviClass = function(page) {
		        var currentRoute = $location.path().substring(1) || 'home';
		        return page === currentRoute ? 'active' : '';
	        };

	        $scope.userAuth = {};
	        $scope.naviLoginText = 'Login: ';

	        /**
			 * returns the 'active' css class if the given (authentication)
			 * provider matches the provider with is currently used for
			 * authentication.
			 */
	        $scope.getCssLoginClass = function(provider) {
		        if ($scope.userAuth) {
			        return provider === $scope.userAuth.provider ? 'active'
			                : '';
		        } else {
			        return '';
		        }
	        };

	        $scope.login = function(provider) {
		        console.log('TODO: login with: ' + provider);
	        };
        } ]);

var pixi01Controller = controllerDefinitions.controller('pixi01Ctrl', [
        'gAnalyticsTrack', function(gAnalyticsTrack) {
	        gAnalyticsTrack();

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

var pixi02Controller = controllerDefinitions.controller('pixi02Ctrl', [
        'gAnalyticsTrack', function(gAnalyticsTrack) {
	        gAnalyticsTrack();
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
					 * fun fact for the day :) another way of doing the above
					 * would be var texture = PIXI.Texture.fromFrame(frameName);
					 * var alien = new PIXI.Sprite(texture);
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