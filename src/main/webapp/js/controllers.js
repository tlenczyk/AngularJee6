'use strict';

/* Controllers */

angular.module('myApp.controllers', [])
        .controller('AuctionsCtrl', function($scope, Auction) {
    $scope.makes = Auction.makes.query();
    $scope.auctions = Auction.auctions.query();

}).controller('AuctionDetailsCtrl', function($scope, $routeParams, Auction) {
    $scope.auction = Auction.details.query({auctionId: $routeParams.auctionId});

}).controller('AddAuctionCtrl', function($scope, $routeParams, Auction) {
    $scope.makes = Auction.makes.query();
    $scope.colors = Auction.colors.query();
    $scope.fuelTypes = Auction.fuelTypes.query();
    $scope.save = function() {
        Auction.save.save({
            title: $scope.title,
            description: $scope.description,
            productionYear: $scope.productionYear,
            makeId: $scope.make.id,
            modelId: $scope.model.id,
            color: $scope.color.name,
            fuel: $scope.fuel.name,
            milage: $scope.milage,
            price: $scope.price
        });
    };

}).controller('SigninCtrl', function($scope) {
    console.log("SigninCtrl");
    $scope.showSignInDialog = function() {
        console.log("showSignInDialog");
        $('#myModal').modal("show");
    };
    $scope.signIn = function() {
        console.log("signIn");
    };
});