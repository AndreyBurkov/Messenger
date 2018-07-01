if (getCookie("id") === undefined) {
    document.location.href = "/login.html";
}

var myApp = angular.module('myApp', []).run(function ($http) {
    var token = getCookie("token");
    $http.defaults.headers.common['Authorization'] = 'Bearer ' + token;
});

deleteCookie("toUser");
deleteCookie("fromUser");



var allUsers = [];
var currentUser = {};
var allMessages = [];
allMessages.length = 0;
var lastMessageId = 0;
var userStatus;

// получения статуса юзера с последнего выхода из системы, переключение и обновление статуса в бд, выход на страницу авторизации
myApp.controller('loginController', function ($scope, $http) {
    $scope.currentUser = getCookie("firstname") + ' ' + getCookie("lastname");
    var updateStatus = function () {
        if (userStatus.currentStatus === 'OFFLINE') return;
        $http.put('/status/update', userStatus);
    };
    $http.get('/status/' + getCookie("id"))
        .then(function (response) {
            userStatus = response.data;
            $scope.status = userStatus.currentStatus = "ONLINE";
            $scope.statusStyle = {color: 'blue'};
            setInterval(updateStatus, 3000);
        });


    $scope.changeStatus = function () {
        if ($scope.status === 'ONLINE') {
            $scope.statusStyle = {color: 'red'};
            $scope.status = userStatus.currentStatus = 'OFFLINE';
            $http.put('/status/update', userStatus);
        } else {
            $scope.statusStyle = {color: 'blue'};
            $scope.status = userStatus.currentStatus = 'ONLINE';
        }
    };
    $scope.signOut = function () {
        userStatus.currentStatus = 'OFFLINE';
        updateStatus();
        deleteAllCookies();
        document.location.href = "/index.html";
    }
});

// Обновление списка allUsers и timestamp каждые 3 сек
myApp.controller('usersController', function ($scope, $http) {
    var refreshUsers = function () {
        $http({
            method: 'GET',
            url: '/user/all'
        }).then(function (response) {
            allUsers = response.data;
            for (var i = 0; i < allUsers.length; i++) {
                if (allUsers[i]["id"] == getCookie("id")) {
                    currentUser = allUsers[i];
                    allUsers.splice(i, 1);
                    break;
                }
            }
            $http.put('/user/update', currentUser);
            $scope.users = allUsers;
        })
    };

    refreshUsers();
    setInterval(refreshUsers, 3000);

    $scope.selectUser = function (user) {
        lastMessageId = 0;
        allMessages = [];
        setCookie("toUser", user.id, {path: "/"});
        setCookie("fromUser", getCookie("id"), {path: "/"});
        setCookie("friend", user.firstname + ' ' + user.lastname, {path: "/"});
        $scope.selectedUser = user.firstname + ' ' + user.lastname;
    }
});

// Получение сообщений
myApp.controller('messagesController', function ($scope, $http) {
    $scope.me = getCookie("firstname") + ' ' + getCookie("lastname");
    $scope.myId = getCookie("id");
    $scope.millsToDate = function (mills) {
        var messageDate = new Date();
        messageDate.setTime(mills);
        return messageDate.getDate() + '.' + messageDate.getMonth() + '.' +
            messageDate.getFullYear() + ' ' + messageDate.getHours() + ':' + messageDate.getMinutes();
    };
    var redisplay = function (response) {
        var newMessages = response.data;
        if (allMessages.length == 0) {
            allMessages = newMessages;
        } else {
            for (var i = 0; i < newMessages.length; i++) {
                if (allMessages[allMessages.length - 1].id < newMessages[i].id) {
                    allMessages.push(newMessages[i]);
                }
            }
        }
        $scope.messages = allMessages;
        $scope.friend = getCookie("friend");
        $scope.friendId = getCookie("toUser");
        var el = document.getElementById("MessageTable");
        el.scrollTop = 9999;
    };

    setInterval(function () {
        if (userStatus.currentStatus === 'OFFLINE') return;
        if (getCookie("toUser") == undefined) return;
        if (allMessages.length == 0) lastMessageId = 0;
        else lastMessageId = allMessages[allMessages.length - 1].id;
        $http({
            method: 'GET',
            url: '/message/correspondence/' + getCookie("fromUser") + '/' + getCookie('toUser') + '/' + lastMessageId
        }).then(redisplay)
    }, 1000);
});

// Отправка сообщений
myApp.controller('newMessage', function ($scope, $http) {
        $scope.doUploadFile = function (files) {
            if (userStatus.currentStatus === 'OFFLINE') return;
            if (getCookie('toUser') == undefined) {
                return;
            }
            var URL = '/message/addImage';
            var file = files[0];
            var myFunc = function (file) {
                var fd = new FormData();
                fd.append('file', file);
                fd.append('fromUser', getCookie('fromUser'));
                fd.append('toUser', getCookie('toUser'));
                var currentDate = new Date();
                fd.append('timestamp', currentDate.getTime() + '');
                $http.post(URL, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                }).then(function (response) {
                    }, function (error) {
                        console.log(error);
                    }
                )
            };
            myFunc(file);
        };

        $scope.sendMessage = function (model) {
            if (userStatus.currentStatus === 'OFFLINE') return;
            if (getCookie('toUser') == undefined) {
                return;
            }
            if (model == undefined || model == "") return;
            var currentDate = new Date();
            var obj = {
                message: model,
                fromUser: getCookie("id"),
                toUser: getCookie("toUser"),
                timestamp: currentDate.getTime()
            };
            $http.post("/message/add", obj)
                .then(function () {
                    $scope.textModel = "";
                });
        }
    }
);
