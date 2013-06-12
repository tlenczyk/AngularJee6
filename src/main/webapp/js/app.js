'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['myApp.filters', 'myApp.services', 'myApp.directives', 'myApp.controllers']).
        config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/auctions', {templateUrl: 'partials/auctions.html', controller: 'AuctionsCtrl'});
        $routeProvider.when('/car/:auctionId', {templateUrl: 'partials/auctionDetails.html', controller: 'AuctionDetailsCtrl'});
        $routeProvider.when('/add', {templateUrl: 'partials/addAuction.html', controller: 'AddAuctionCtrl'});
        $routeProvider.when('/signin', {controller: 'SigninCtrl'});
        $routeProvider.otherwise({redirectTo: '/auctions'});
    }]);
