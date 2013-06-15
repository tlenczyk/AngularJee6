'use strict';

/* Filters */

angular.module('myApp.filters', [])
        .filter('priceFilter', function() {
    return function(items, priceFrom, priceTo) {
        var filtered = [];

        if (priceFrom && priceFrom.length > 0) {
            priceFrom = parseInt(priceFrom);
        } else {
            priceFrom = 0;

        }
        if (priceTo && priceTo.length > 0) {
            priceTo = parseInt(priceTo);
        } else {
            priceTo = 10000000; //max
        }
        angular.forEach(items, function(item) {
            if (item.price >= priceFrom && item.price <= priceTo) {
                filtered.push(item);
            }
        });
        return filtered;
    };
})
        .filter('yearFilter', function() {
    return function(items, from, to) {
        var filtered = [];

        if (from && from.length > 0) {
            from = parseInt(from);
        } else {
            from = 0;

        }
        if (to && to.length > 0) {
            to = parseInt(to);
        } else {
            to = 2013; //max
        }
        angular.forEach(items, function(item) {
            var date = new Date(item.productionYear);
            var year = date.getFullYear();
            if (year >= from && year <= to) {
                filtered.push(item);
            }
        });
        return filtered;
    };
})
        .filter('modelFilter', function() {
    return function(items, model) {
        var filtered = [];

        if (model) {
            angular.forEach(items, function(item) {
                if (item.model.name === model.name) {
                    filtered.push(item);
                }
            });
        } else {
            filtered = items;
        }
        return filtered;
    };
});
