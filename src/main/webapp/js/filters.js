'use strict';

/* Filters */

angular.module('myApp.filters', []).
        filter('priceFromFilter', function() {
    return function(items, priceFrom, priceTo) {
        var priceFrom = parseInt(priceFrom);
        var priceTo = parseInt(priceTo);
        var filtered = [];
        angular.forEach(items, function(item) {
            if (item.price >= priceFrom && item.price <= priceTo) {
                filtered.push(item);
            }
        });
        return filtered;
    };
});
