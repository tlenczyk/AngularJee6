'use strict';

/* Services */
angular.module('myApp.services', ['ngResource']).service("Auction", function($resource) {
    this.marks = $resource('rest/marks', {}, {
        query: {method: 'GET', params: {}, isArray: true}
    });
    this.colors = $resource('rest/colors', {}, {
        query: {method: 'GET', params: {}, isArray: true}
    });
    this.fuelTypes = $resource('rest/fuelTypes', {}, {
        query: {method: 'GET', params: {}, isArray: true}
    });
    this.auctions = $resource('rest/auctions', {}, {
        query: {method: 'GET', params: {}, isArray: true}
    });
    this.details = $resource('rest/auction/:auctionId', {}, {
        query: {method: 'GET', params: {auctionId: "@id"}, isArray: false}
    });
    this.save = $resource('rest/save', {}, {
        save: {method: 'POST'}
    });

});
