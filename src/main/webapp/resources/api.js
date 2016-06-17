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
        var order = 0;
        for (var index in type.fields) {
            var field = type.fields[index];
            field.order = order++;
        }
        var config = {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };
        $http.post(this.URL, type, config)
            .success(function (data, status, headers, config) {
                console.log(status);
            })
            .error(function (data, status, header, config) {
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
        fields: [],
        isValid: false
    };
    $scope.field = {
        name: "",
        required: false,
        type: TypeService.PRIMITIVE_TYPES[2]
    };

    $scope.$watch('type.name', function (name) {
        TypeService.isUnique(name, function (data) {
            console.log(data);
            $scope.type.isValid = data;
        });
    });
    $scope.addField = function () {
        $scope.type.fields.push({
            name: $scope.field.name,
            required: $scope.field.required,
            type: $scope.field.type
        });
        $scope.resetField();
    };
    $scope.resetField = function () {
        $scope.field.name = "";
        $scope.field.required = false;
        $scope.field.type = TypeService.PRIMITIVE_TYPES[2];
    };
    $scope.create = function () {
        TypeService.create(typeConverter())
    };
    $scope.reset = function () {
        $scope.type.fields = [];
        $scope.type.name = "";
        $scope.resetField();
    };

    var typeConverter = function () {
        var fields = [];
        for (var index in $scope.type.fields) {
            var field = $scope.type.fields[index];
            fields.push({
                name: field.name,
                required: field.required,
                type: field.type.native
            });
        }
        return {
            name: $scope.type.name,
            fields: fields
        };
    };
});