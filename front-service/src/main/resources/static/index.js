angular
    .module(
        'app',
        ['ngStorage']
    )
    .controller(
        'indexController',
        function (
            $scope,
            $http,
            $localStorage
        ) {
            $scope.tryToAuth =
                function () {
                    $http.post('http://localhost:5555/auth/auth', $scope.user)
                    .then(
                        function successCallback(response) {
                            if (response.data.token) {
                                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                                $localStorage.webShopUser =
                                    {
                                        username: $scope.user.username,
                                        token: response.data.token
                                    };

                                $scope.user.username = null;
                                $scope.user.password = null;
                            }
                        },
                        function errorCallback(response) {}
                    );
                };

            $scope.tryToLogout =
                function () {
                    $scope.clearUser();
                    $scope.user = null;
                };

            $scope.clearUser =
                function () {
                    delete $localStorage.webShopUser;
                    $http.defaults.headers.common.Authorization = '';
                };

            $scope.isUserLoggedIn =
                function () {
                    if ($localStorage.webShopUser) {
                        return true;
                    } else {
                        return false;
                    }
                };

//            $scope.loadProducts =
//                function () {
//                    $http.get('http://localhost:5555/core/api/v1/products')
//                    .then(
//                        function (response) {
//                            $scope.productsList = response.data;
//                        }
//                    );
//                };

            $scope.loadProducts =
                function (filter) {
                    $http.get('http://localhost:5555/core/api/v1/products?p=1'
                        + (filter.title != null ? '&title=' + filter.title : '')
                        + (filter.minPrice != null ? '&min_price=' + filter.minPrice : '')
                        + (filter.maxPrice != null ? '&max_price=' + filter.maxPrice : '')
                    )
                    .then(
                        function (response) {
                            $scope.productsList = response.data;
                        }
                    );
                };

            $scope.addToCart =
                function (productId) {
                    $http.get('http://localhost:5555/cart/api/v1/cart/add/' + productId)
                    .then(
                        function (response) {
                            $scope.loadCart();
                        }
                    );
                };

            $scope.removeFromCart =
                function (productId) {
                    $http.get('http://localhost:5555/cart/api/v1/cart/remove/' + productId)
                    .then(
                        function (response) {
                            $scope.loadCart();
                        }
                    );
                };

            $scope.clearCart =
                function () {
                    $http.get('http://localhost:5555/cart/api/v1/cart/clear')
                    .then(
                        function (response) {
                            $scope.loadCart();
                        }
                    );
                };

            $scope.loadCart =
                function () {
                    $http.get('http://localhost:5555/cart/api/v1/cart')
                    .then(
                        function (response) {
                            $scope.cart = response.data;
                        }
                    );
                };

            $scope.sendOrder =
                function () {
                    $http.post('http://localhost:5555/core/api/v1/orders')
                    .then(
                        function (response) {
                            $scope.clearCart();
                        }
                    );
                };

            $scope.loadProducts(0);
            $scope.loadCart();

            if ($localStorage.webShopUser) {
                try {
                    let jwt = $localStorage.webShopUser.token;
                    let payload = JSON.parse(atob(jwt.split('.')[1]));
                    let currentTime = parseInt(new Date().getTime() / 1000);
                    if (currentTime > payload.exp) {
                        console.log("Token is expired!!!");
                        delete $localStorage.webShopUser;
                        $http.defaults.headers.common.Authorization = '';
                    }
                } catch (e) {
                }

                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webShopUser.token;
            }
        }
    );
