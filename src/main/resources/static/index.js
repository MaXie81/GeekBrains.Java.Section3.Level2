angular.module('app', []).controller('indexController', function ($scope, $http) {
    $scope.loadProducts = function () {
        $http.get('http://localhost:8189/webshop/api/v1/products').then(function (response) {
            $scope.productsList = response.data;
        });
    }

    $scope.showProductInfo = function (productId) {
        $http.get('http://localhost:8189/webshop/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete('http://localhost:8189/webshop/api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.loadCartProducts = function () {
        $http.get('http://localhost:8189/webshop/api/v1/products/cart').then(function (response) {
            $scope.cartProductsList = response.data;
        });
    }

    $scope.addProductById = function (productId) {
        $http.get('http://localhost:8189/webshop/api/v1/products/cart/' + productId).then(function (response) {
            $scope.loadCartProducts();
        });
    }

    $scope.loadProducts();
    $scope.loadCartProducts();
});
