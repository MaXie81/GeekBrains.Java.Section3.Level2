angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.getProductPage =
        function () {
            let url = contextPath + 'api/v1/products';
            url = url + '?p=' + $scope.page.curr;

            if ($scope.filter != null) {
                if ($scope.filter.title != null) {url = url + '&title=' + $scope.filter.title;}
                if ($scope.filter.minPrice != null) {url = url + '&min_price=' + $scope.filter.minPrice;}
                if ($scope.filter.maxPrice != null) {url = url + '&max_price=' + $scope.filter.maxPrice;}
            }

            $http.get(url)
            .then(
                function (response) {
                    $scope.productsList = response.data;
                }
            );
        }

    $scope.getLastPageNumber =
        function () {
            let url = contextPath + 'api/v1/products/total-pages?';

            if ($scope.filter != null) {
                if ($scope.filter.title != null) {url = url + '&title=' + $scope.filter.title;}
                if ($scope.filter.minPrice != null) {url = url + '&min_price=' + $scope.filter.minPrice;}
                if ($scope.filter.maxPrice != null) {url = url + '&max_price=' + $scope.filter.maxPrice;}
            }

            $http.get(url)
            .then(
                function (response) {
                    $scope.page.last = response.data;
                    $scope.setPage();
                }
            );
        }

    $scope.loadProducts =
        function () {
            $scope.page.curr = 1;
            $scope.getProductPage();
            $scope.getLastPageNumber();
        }

    $scope.setProductPage =
        function (number) {
            $scope.page.curr = number;
            $scope.getProductPage();
            $scope.setPage();
        };

    $scope.setPage =
        function () {
            let pos = $scope.page.curr;

            if ($scope.page.last == 1) {
                $scope.page.navigate.prev = 1;
                $scope.page.navigate.next = 1;
            } else if (pos == $scope.page.last) {
                $scope.page.navigate.prev = pos - 1;
                $scope.page.navigate.next = pos;
            } else if (pos == 1) {
                $scope.page.navigate.prev = pos;
                $scope.page.navigate.next = pos + 1;
            } else {
                $scope.page.navigate.prev = pos - 1;
                $scope.page.navigate.next = pos + 1;
            }
        };

    $scope.page = {
        "curr" : -1,
        "last" : -1,
        "navigate" : {
            "prev" : -1,
            "next" : -1
        }
    };

    $scope.showProductInfo = function (productId) {
        $http.get(contextPath + 'api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.addToCart = function (productId) {
        console.log("webShopGuestCartId: " + $localStorage.webShopGuestCartId);
        $http.get(cartContextPath + 'api/v1/cart/' + $localStorage.webShopGuestCartId + '/add/' + productId).then(function (response) {
//            $scope.loadCart();
        });
    }

    $scope.loadProducts();
});