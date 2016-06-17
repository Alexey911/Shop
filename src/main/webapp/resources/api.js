var app = angular.module("app", []);

app.service('TypeService', function ($http) {
    this.URL = 'http://' + window.location.host + '/types';
    this.PRIMITIVE_TYPES = [
        {name: 'Boolean', native: 'BOOLEAN'},
        {name: 'Float', native: 'DOUBLE'},
        {name: 'Integer', native: 'LONG'},
        {name: 'Date', native: 'DATE'},
        {name: 'Text', native: 'STRING'},
        {name: 'Photos', native: 'GALLERY'},
        {name: 'Location', native: 'MAP'},
        {name: 'Currency', native: 'CURRENCY'}
    ];

    this.create = function (type) {
        var config = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        $http.post(this.URL, type, config)
            .success(function (data, status) {
                console.log(status, data);
            })
            .error(function (data, status) {
                console.log(status);
            });
    };
    this.isUnique = function (name, success) {
        $http({
            method: "GET",
            url: this.URL + "?isFree=" + name
        }).then(function onSuccess(response) {
            success(response.data);
        }, function onError(response) {
            console.log("error", response);
        });
    }
});

app.controller('TypeController', function ($scope, $http, TypeService) {
    $scope.primitiveTypes = TypeService.PRIMITIVE_TYPES;
    $scope.type = {
        id: null,
        name: "",
        lastChange: null,
        fields: []
    };
    $scope.isValid = false;
    $scope.field = {
        name: "",
        required: false,
        type: TypeService.PRIMITIVE_TYPES[2]
    };

    $scope.$watch('type.name', function (name) {
        TypeService.isUnique(name, function (data) {
            console.log(data);
            $scope.isValid = data;
        });
    });
    $scope.addField = function () {
        $scope.type.fields.push(angular.copy($scope.field));
        $scope.resetField();
    };
    $scope.resetField = function () {
        $scope.field.name = "";
        $scope.field.required = false;
        $scope.field.type = TypeService.PRIMITIVE_TYPES[2];
    };
    $scope.create = function () {
        var type = angular.copy($scope.type);
        var order = 0;
        angular.forEach(type.fields, function (field) {
            field.order = order++;
            field.type = field.type.native;
        });
        TypeService.create(type);
    };
    $scope.reset = function () {
        $scope.type.fields = [];
        $scope.type.name = "";
        $scope.resetField();
    };
});