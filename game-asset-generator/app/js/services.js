'use strict';

/* Services */

// Demonstrate how to register services
// In this case it is a simple value service.
var serviceDefinitions = angular.module('myApp.services', []);
serviceDefinitions.value('version', '0.1');
//serviceDefinitions.value('appEngineUrl', 'http://1-dot-atomic-dahlia-541.appspot.com/');
serviceDefinitions.value('appEngineUrl', 'http://localhost:8888/');
serviceDefinitions.value('fileUploadRoute', 'game_asset_generator_server/uploadResource');
