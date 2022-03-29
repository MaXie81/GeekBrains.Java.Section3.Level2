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

            $scope.getProductPage =
                function () {
                    let url = 'http://localhost:5555/core/api/v1/products';
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
                };

            $scope.getLastPageNumber =
                function () {
                    let url = 'http://localhost:5555/core/api/v1/products/total-pages?';
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
                };

            $scope.loadProducts =
                function () {
                    $scope.page.curr = 1;
                    $scope.getProductPage();
                    $scope.getLastPageNumber();
                };

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

            $scope.loadProducts();
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
