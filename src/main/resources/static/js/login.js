if (getCookie("id") !== undefined) {
    document.location.href = "messenger.html";
} else {
    deleteAllCookies();
}
var loginApp = angular.module("loginApp", []);

loginApp.controller('loginController', function loginController($scope, $http) {
    var currentUser = null;
    $scope.response = {};
    $scope.message = " ";
    $scope.sendUser = function () {
        $http({
            url: 'authenticate',
            method: "POST",
            params: {
                username: md5($scope.username),
                password: md5($scope.password)
            }
        }).then(function (res) {
            $scope.password = null;
            if (res.data.token) {
                console.log(res.data.user);
                currentUser = res.data.user;
                setCookie("id", currentUser["id"], { path: "/" });
                setCookie("firstname", currentUser["firstname"], { path: "/" });
                setCookie("lastname", currentUser["lastname"], { path: "/" });
                setCookie("token", res.data.token, { path: "/" });
                $scope.message = '';
                $http.defaults.headers.common['Authorization'] = 'Bearer ' + res.data.token;
                document.location.href = "messenger.html";
            } else {
                $scope.message = 'Authetication Failed!';
            }
        }, function (error) {
            alert("Error!!!");
            $scope.message = 'Authetication Failed !';
        });
    }
});

